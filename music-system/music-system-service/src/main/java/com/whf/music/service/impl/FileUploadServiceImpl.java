package com.whf.music.service.impl;

import com.whf.music.constant.Constants;
import com.whf.music.domain.FileChunk;
import com.whf.music.domain.FileInfo;
import com.whf.music.domain.Singer;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.request.FileUploadRequest;
import com.whf.music.service.FileChunkService;
import com.whf.music.service.FileInfoService;
import com.whf.music.service.FileUploadService;
import com.whf.music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Cleaner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * 文件上传服务实现类
 *
 * @author whf
 * @date 2024/3/7
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private FileChunkService fileChunkService;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private SingerService singerService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件名
     */
    @Override
    public String upload(MultipartFile file) {
        // 判断目录是否存在，不存在则创建目录
        if (createDir(Constants.FILE_ROOT_PATH)) {
            log.error("upload file error: create dir fail");
            throw new ServiceException(ResultEnum.CREATE_DIR_ERROR);
        }
        //构建文件名称
        String fileName = UUID.randomUUID() + File.separator + StringUtils.getFilenameExtension(file.getOriginalFilename());
        //构建文件路径
        String path = Constants.FILE_ROOT_PATH + fileName;
        try {
            //保存文件
            file.transferTo(new File(path));
        } catch (IOException e) {
            log.error("upload file error:{}", e.getMessage());
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR);
        }
        return path;
    }

    /**
     * 检查上传
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> checkUpload(FileUploadRequest dto) {
        Map<String, Object> result = new HashMap<>();
        // 根据文件md5从数据库中查询文件数据
        FileInfo fileInfo = fileInfoService.getFileInfoByMd5(dto.getFileMd5());
        // 如果文件存在，则返回文件信息
        if (Objects.nonNull(fileInfo)) {
            result.put("upload", "success");
            result.put("fileInfo", fileInfo);
            return result;
        }
        // 如果文件不存在，查询分片记录
        List<FileChunk> fileChunks = fileChunkService.getByFileMd5(dto.getFileMd5());
        // 判断分片记录数量是否与所有分片数一致
        // 如果一致，保存文件信息到文件表中
        if (Objects.equals(fileChunks.size(), dto.getTotalChunk())) {
            fileInfo = fileInfoService.saveFileInfo(dto, fileChunks.get(0));
            result.put("upload", "success");
            result.put("fileInfo", fileInfo);
        } else {
            // 如果不一致，返回已上传完成的分片信息
            result.put("upload", "running");
            result.put("fileChunks", fileChunks);
        }
        return result;
    }

    /**
     * 分片上传
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean chunkUpload(FileUploadRequest dto) {
        //获取歌手信息
        Singer singer = singerService.getById(dto.getSingerId());
        if (Objects.isNull(singer)) {
            log.error("upload file error: singer is null");
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR);
        }
        if (Objects.isNull(dto.getFile())) {
            log.error("upload file error: file is null");
            throw new ServiceException(ResultEnum.UPLOAD_FILE_NOT_NULL);
        }
        //构建文件路径
        String path = System.getProperty("user.dir") + File.separator
                + "resource" + File.separator + "song" + File.separator +
                singer.getName();
        //判断目录是否存在，不存在则创建目录
        if (createDir(path)) {
            log.error("upload file error: create dir fail");
            throw new ServiceException(ResultEnum.CREATE_DIR_ERROR);
        }
        //文件存放位置
        String destFile = path + File.separator + dto.getFileName();
        Boolean upload = uploadFileByRandomAccessFile(destFile, dto);
        if (!upload) {
            return false;
        }
        //分片上传成功，写入分片表，如果所有分片上传完成，校验文件MD5与参数中md5是否一致，存入文件表
        fileChunkService.saveChunk(dto, destFile);
        return true;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    private Boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 通过RandomAccessFile写入分片
     *
     * @param filePath
     * @param dto
     * @return
     */
    private Boolean uploadFileByRandomAccessFile(String filePath, FileUploadRequest dto) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
            //获取分片文件大小 分片大小必须和前端匹配，否则上传会导致文件损坏
            long chunkSize = dto.getChunkSize() == 0L ? Constants.DEFAULT_CHUNK_SIZE : dto.getChunkSize();
            //计算偏移
            long offset = (dto.getChunkNumber() - 1) * chunkSize;
            //定位到分片位置
            randomAccessFile.seek(offset);
            //写入分片
            randomAccessFile.write(dto.getFile().getBytes());
        } catch (IOException e) {
            log.error("upload file write chunk error:{}", e.getMessage());
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR);
        }
        return true;
    }

    /**
     * 通过MappedByteBuffer写入分片
     *
     * @param filePath
     * @param dto
     * @return
     */
    private Boolean uploadFileByMappedByteBuffer(String filePath, FileUploadRequest dto) {
        // 分片上传
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
             FileChannel fileChannel = randomAccessFile.getChannel()) {
            // 获取分片文件大小 分片大小必须和前端匹配，否则上传会导致文件损坏
            long chunkSize = dto.getChunkSize() == 0L ? Constants.DEFAULT_CHUNK_SIZE : dto.getChunkSize().longValue();
            // 写入文件
            long offset = chunkSize * (dto.getChunkNumber() - 1);
            byte[] fileBytes = dto.getFile().getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileBytes.length);
            mappedByteBuffer.put(fileBytes);
            // 释放
            unmap(mappedByteBuffer);
        } catch (IOException e) {
            log.error("文件上传失败：" + e);
            return false;
        }
        return true;
    }

    /**
     * 释放 MappedByteBuffer
     * 在 MappedByteBuffer 释放后再对它进行读操作的话就会引发 jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是 crash 就发生了。所以为了系统稳定性释放前一般需要检
     * 查是否还有线程在读或写
     * 来源：https://my.oschina.net/feichexia/blog/212318
     *
     * @param mappedByteBuffer mappedByteBuffer
     */
    private void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }
            mappedByteBuffer.force();
            AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                try {
                    Method getCleanerMethod = mappedByteBuffer.getClass()
                            .getMethod("cleaner");
                    getCleanerMethod.setAccessible(true);
                    Cleaner cleaner =
                            (Cleaner) getCleanerMethod
                                    .invoke(mappedByteBuffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    log.error("MappedByteBuffer 释放失败：" + e);
                }
                System.out.println("clean MappedByteBuffer completed");
                return null;
            });
        } catch (Exception e) {
            log.error("unmap error:" + e);
        }
    }
}

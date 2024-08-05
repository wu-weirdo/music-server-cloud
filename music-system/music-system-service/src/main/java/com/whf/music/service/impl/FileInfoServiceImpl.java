package com.whf.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.domain.FileChunk;
import com.whf.music.domain.FileInfo;
import com.whf.music.domain.Video;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.FileInfoMapper;
import com.whf.music.request.FileUploadRequest;
import com.whf.music.service.FileInfoService;
import com.whf.music.service.VideoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author hfwu
* @description 针对表【file_info(文件详情)】的数据库操作Service实现
* @createDate 2024-08-05 14:30:31
*/
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo>
    implements FileInfoService {

    @Resource
    private VideoService videoService;

    @Override
    public FileInfo saveFileInfo(FileUploadRequest dto, FileChunk fileChunk) {
        //校验文件MD5是否一致
        String relativePath = fileChunk.getRelativePath();
        String fileMd5 = DigestUtils.md5Hex(relativePath);
        if (!Objects.equals(fileMd5, fileChunk.getFileMd5())) {
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "文件MD5不一致");
        }
        //保存文件信息
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileChunk.getFileName());
        fileInfo.setFileType(fileChunk.getFileType());
        fileInfo.setFileSize(fileChunk.getTotalSize());
        fileInfo.setFileMd5(fileChunk.getFileMd5());
        fileInfo.setRelativePath(fileChunk.getRelativePath());
        this.save(fileInfo);
        //保存视频信息
        Video video = new Video();
        video.setSingerId(dto.getSingerId());
        video.setName(dto.getFileName());
        video.setUrl(fileChunk.getRelativePath());
        videoService.save(video);
        return fileInfo;
    }

    @Override
    public FileInfo getFileInfoByMd5(String fileMd5) {
        return this.getOne(this.lambdaQuery().eq(FileInfo::getFileMd5, fileMd5));
    }
}





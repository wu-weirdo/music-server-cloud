package com.whf.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.domain.FileChunk;
import com.whf.music.mapper.FileChunkMapper;
import com.whf.music.request.FileUploadRequest;
import com.whf.music.service.FileChunkService;
import com.whf.music.service.FileInfoService;
import com.whf.music.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hfwu
 * @description 针对表【file_chunk】的数据库操作Service实现
 * @createDate 2024-08-05 13:58:21
 */
@Service
public class FileChunkServiceImpl extends ServiceImpl<FileChunkMapper, FileChunk>
        implements FileChunkService {

    @Resource
    private FileInfoService fileInfoService;

    @Override
    public void saveChunk(FileUploadRequest dto, String destFile) {
        //保存分片信息
        FileChunk fileChunk = new FileChunk();
        BeanUtils.copyProperties(dto, fileChunk);
        fileChunk.setRelativePath(destFile);
        this.save(fileChunk);
        // 当文件分片完整上传完成，保存到file_info表中
        if (dto.getChunkNumber().equals(dto.getTotalChunk())) {
            fileInfoService.saveFileInfo(dto, fileChunk);
        }
    }

    @Override
    public List<FileChunk> getByFileMd5(String fileMd5) {
        return this.baseMapper.selectList(this.lambdaQuery().eq(FileChunk::getFileMd5, fileMd5));
    }
}





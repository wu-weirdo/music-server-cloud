package com.whf.music.service;

import com.whf.music.domain.FileChunk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.request.FileUploadRequest;

import java.util.List;

/**
* @author hfwu
* @description 针对表【file_chunk】的数据库操作Service
* @createDate 2024-08-05 13:58:21
*/
public interface FileChunkService extends IService<FileChunk> {

    /**
     * 保存分片
     * @param dto
     * @param destFile
     */
    void saveChunk(FileUploadRequest dto, String destFile);

    List<FileChunk> getByFileMd5(String fileMd5);
}

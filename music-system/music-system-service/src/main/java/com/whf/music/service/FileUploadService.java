package com.whf.music.service;

import com.whf.music.request.FileUploadRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件上传服务
 * @author whf
 * @date 2024/3/7
 */
@Service
public interface FileUploadService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    /**
     * 检查上传
     * @param dto
     * @return
     */
    Map<String, Object> checkUpload(FileUploadRequest dto);

    /**
     * 分片上传
     * @param dto
     * @return
     */
    Boolean chunkUpload(FileUploadRequest dto);
}

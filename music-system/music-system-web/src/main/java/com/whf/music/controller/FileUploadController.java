package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.request.FileUploadRequest;
import com.whf.music.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * 文件上传
 *
 * @author whf
 * @date 2024/3/7
 */
@Tag(name = "文件上传")
@RestController
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @Operation(summary = "单文件上传")
    @RequestMapping("/upload")
    public R<Map<String, Object>> upload(MultipartFile file) {
        String path = fileUploadService.upload(file);
        return R.success(Collections.singletonMap("path", path));
    }

    /**
     * 检查文件上传
     *
     * @param dto
     * @return
     */
    @Operation(summary = "分片上传检查")
    @RequestMapping("/checkUpload")
    public R<Map<String, Object>> checkUpload(@RequestBody FileUploadRequest dto) {
        Map<String, Object> result = fileUploadService.checkUpload(dto);
        return R.success(result);
    }

    @Operation(summary = "分片上传")
    @PostMapping("/chunkUpload")
    public R<Boolean> chunkUpload(@RequestBody FileUploadRequest dto) {
        Boolean result = fileUploadService.chunkUpload(dto);
        return R.success(result);
    }
}

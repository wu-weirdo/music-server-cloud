package com.whf.music.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * @author whf
 * @date 2024/3/7
 */
@Data
public class FileUploadRequest {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型 1.音频 2.视频
     */
    private Integer fileType;

    /**
     * 当前分片，从1开始
     */
    private Integer chunkNumber;

    /**
     * 总分片数
     */
    private Integer totalChunk;

    /**
     * 分片大小
     */
    private Long chunkSize;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 分片MD5值
     */
    private String chunkMd5;

    /**
     * 文件MD5值
     */
    private String fileMd5;

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 歌手id
     */
    private Integer singerId;
}

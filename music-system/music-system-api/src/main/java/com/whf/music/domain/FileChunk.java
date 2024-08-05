package com.whf.music.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName file_chunk
 */
@TableName(value ="file_chunk")
@Data
public class FileChunk implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 文件路径
     */
    private String relativePath;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
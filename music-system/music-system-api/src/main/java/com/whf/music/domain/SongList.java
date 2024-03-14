package com.whf.music.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@TableName(value = "song_list")
@Data
public class SongList {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String pic;

    /**
     * 风格
     */
    private String style;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 歌手id
     */
    private Integer singerId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

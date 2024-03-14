package com.whf.music.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/6 13:11
 **/
@Data
public class CollectRequest {

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotNull(message = "类型不能为空")
    private Byte type;

    @NotNull(message = "歌曲id不能为空")
    private Integer songId;

    //    @NotNull(message = "专辑id不能为空")
    private Integer songListId;

    private Date createTime;
}

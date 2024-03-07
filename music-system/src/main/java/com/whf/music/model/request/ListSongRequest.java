package com.whf.music.model.request;

import lombok.Data;

import java.util.List;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/6 20:38
 **/
@Data
public class ListSongRequest {
    private Integer id;

    private Integer songId;

    private List<Integer> songIds;

    private Integer songListId;
}

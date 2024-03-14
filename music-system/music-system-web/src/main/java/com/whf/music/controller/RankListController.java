package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.request.RankListRequest;
import com.whf.music.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankList")
public class RankListController {

    @Autowired
    private RankListService rankListService;


    /**
     * 提交评分
     *
     * @param rankListAddRequest 排名列表添加请求
     * @return {@code R}
     */
    @PostMapping("/add")
    public R<Boolean> addRank(@RequestBody RankListRequest rankListAddRequest) {
        return R.success(rankListService.addRank(rankListAddRequest));
    }


    /**
     * 获取指定歌单的评分
     *
     * @param songListId 歌曲列表id
     * @return {@code R}
     */
    @GetMapping("/list")
    public R<Long> rankOfSongListId(@RequestParam Long songListId) {
        return R.success(rankListService.rankOfSongListId(songListId));
    }


    /**
     * 获取指定用户的歌单评分
     *
     * @param consumerId 消费者id
     * @param songListId 歌曲列表id
     * @return {@code R}
     */
    @GetMapping("/user")
    public R<Integer> getUserRank(@RequestParam Long consumerId, @RequestParam Long songListId) {
        return R.success(rankListService.getUserRank(consumerId, songListId));
    }
}

package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.RankListRequest;
import com.whf.music.service.RankListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评分管理")
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
    @Operation(summary = "提交评分")
    @Log(module = "评分管理", description = "提交评分", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<Boolean> addRank(@RequestBody RankListRequest rankListAddRequest) {
        Boolean result = rankListService.addRank(rankListAddRequest);
        return R.success(result, result ? "评分成功！" : "评分失败！");
    }


    /**
     * 获取指定歌单的评分
     *
     * @param songListId 歌曲列表id
     * @return {@code R}
     */
    @GetMapping("/list")
    @Operation(summary = "获取指定歌单的评分")
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
    @Operation(summary = "获取指定用户的歌单评分")
    public R<Integer> getUserRank(@RequestParam Long consumerId, @RequestParam Long songListId) {
        return R.success(rankListService.getUserRank(consumerId, songListId));
    }
}

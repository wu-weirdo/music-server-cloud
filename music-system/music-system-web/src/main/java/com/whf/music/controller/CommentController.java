package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.Comment;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.CommentRequest;
import com.whf.music.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 *
 * @author whf
 * @date 2023/04/27
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "评论管理")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     *
     * @param addCommentRequest 添加评论请求
     * @return {@code R}
     */
    @PostMapping("/add")
    @Operation(summary = "添加评论")
    @Log(module = "评论管理", description = "添加评论", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<Boolean> addComment(@RequestBody CommentRequest addCommentRequest) {
        return R.success(commentService.addComment(addCommentRequest));
    }

    /**
     * 删除评论
     *
     * @param id id
     * @return {@code R}
     */
    @GetMapping("/delete")
    @Operation(summary = "删除评论")
    @Log(module = "评论管理", description = "删除评论", businessType = BusinessType.DELETE, operatorType = OperatorType.CLIENT)
    public R<Boolean> deleteComment(@RequestParam Integer id) {
        return R.success(commentService.deleteComment(id));
    }

    /**
     * 获得指定歌曲 ID 的评论列表
     *
     * @param songId 歌id
     * @return {@code R}
     */
    @GetMapping("/song/detail")
    @Operation(summary = "获取指定歌曲评论列表")
    public R<List<Comment>> commentOfSongId(@RequestParam Integer songId) {
        return R.success(commentService.commentOfSongId(songId));
    }

    /**
     * 获得指定歌单 ID 的评论列表
     *
     * @param songListId 歌曲列表id
     * @return {@code R}
     */
    @GetMapping("/songList/detail")
    @Operation(summary = "获取指定歌单评论列表")
    public R<List<Comment>> commentOfSongListId(@RequestParam Integer songListId) {
        return R.success(commentService.commentOfSongListId(songListId));
    }

    /**
     * 点赞
     *
     * @param upCommentRequest 了评论请求
     * @return {@code R}
     */
    @PostMapping("/like")
    @Operation(summary = "点赞")
    @Log(module = "评论管理", description = "点赞", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<Boolean> commentOfLike(@RequestBody CommentRequest upCommentRequest) {
        return R.success(commentService.updateCommentMsg(upCommentRequest));
    }
}

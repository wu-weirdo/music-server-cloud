package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.model.domain.Comment;
import com.whf.music.model.request.CommentRequest;
import com.whf.music.service.CommentService;
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
    public R<Boolean> commentOfLike(@RequestBody CommentRequest upCommentRequest) {
        return R.success(commentService.updateCommentMsg(upCommentRequest));
    }
}

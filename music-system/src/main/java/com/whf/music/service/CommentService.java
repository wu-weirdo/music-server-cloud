package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.model.domain.Comment;
import com.whf.music.model.request.CommentRequest;

import java.util.List;

public interface CommentService extends IService<Comment> {

    /**
     * 添加评论
     *
     * @param addCommentRequest 添加评论请求
     * @return {@code Boolean}
     */
    Boolean addComment(CommentRequest addCommentRequest);

    /**
     * 点赞
     *
     * @param upCommentRequest 了评论请求
     * @return {@code Boolean}
     */
    Boolean updateCommentMsg(CommentRequest upCommentRequest);

    /**
     * 删除评论
     *
     * @param id id
     * @return {@code Boolean}
     */
    Boolean deleteComment(Integer id);

    /**
     * 获得指定歌曲 ID 的评论列表
     *
     * @param songId 歌id
     * @return {@code List<Comment>}
     */
    List<Comment> commentOfSongId(Integer songId);

    /**
     * 获得指定歌单 ID 的评论列表
     *
     * @param songListId 歌曲列表id
     * @return {@code List<Comment>}
     */
    List<Comment> commentOfSongListId(Integer songListId);

}

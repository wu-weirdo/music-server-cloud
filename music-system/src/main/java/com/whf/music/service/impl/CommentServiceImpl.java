package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.mapper.CommentMapper;
import com.whf.music.model.domain.Comment;
import com.whf.music.model.request.CommentRequest;
import com.whf.music.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Boolean addComment(CommentRequest addCommentRequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentRequest, comment);
        comment.setType(addCommentRequest.getNowType());
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public Boolean updateCommentMsg(CommentRequest addCommentRequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentRequest, comment);
        return commentMapper.updateById(comment) > 0;
    }

    @Override
    public Boolean deleteComment(Integer id) {
        return commentMapper.deleteById(id) > 0;
    }

    @Override
    public List<Comment> commentOfSongId(Integer songId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_id", songId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public List<Comment> commentOfSongListId(Integer songListId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        return commentMapper.selectList(queryWrapper);
    }
}

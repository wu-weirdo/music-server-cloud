package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.mapper.RankListMapper;
import com.whf.music.model.domain.RankList;
import com.whf.music.model.request.RankListRequest;
import com.whf.music.service.RankListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class RankListServiceImpl extends ServiceImpl<RankListMapper, RankList> implements RankListService {


    @Autowired
    private RankListMapper rankMapper;

    @Override
    public Boolean addRank(RankListRequest rankListAddRequest) {
        RankList rankList = new RankList();
        BeanUtils.copyProperties(rankListAddRequest, rankList);
        return rankMapper.insert(rankList) > 0;
    }

    @Override
    public Long rankOfSongListId(Long songListId) {
        // 评分总人数如果为 0，则返回0；否则返回计算出的结果
        QueryWrapper<RankList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        Long rankNum = rankMapper.selectCount(queryWrapper);
        return rankNum <= 0 ? 0 : rankMapper.selectScoreSum(songListId) / rankNum;
    }

    @Override
    public Integer getUserRank(Long consumerId, Long songListId) {
        return rankMapper.selectUserRank(consumerId, songListId);
    }
}

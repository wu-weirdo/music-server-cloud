package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.model.domain.Collect;
import com.whf.music.model.request.CollectRequest;

import java.util.List;

public interface CollectService extends IService<Collect> {

    /**
     * 添加收藏
     *
     * @param addCollectRequest 添加收集请求
     * @return {@code Boolean}
     */
    Boolean addCollect(CollectRequest addCollectRequest);

    /**
     * 是否已收藏
     *
     * @param isCollectRequest 收集请求
     * @return {@code Boolean}
     */
    Boolean existSongId(CollectRequest isCollectRequest);

    /**
     * 删除收藏
     *
     * @param userId 用户id
     * @param songId 歌id
     * @return {@code Boolean}
     */
    Boolean deleteCollect(Integer userId,Integer songId);

    /**
     * 查询用户收藏
     *
     * @param userId 用户id
     * @return {@code List<Collect>}
     */
    List<Collect> collectionOfUser(Integer userId);
}

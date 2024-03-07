package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.model.domain.Collect;
import com.whf.music.model.request.CollectRequest;
import com.whf.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 收藏管理
 *
 * @author whf
 * @date 2023/04/27
 */
@RestController
@RequestMapping("/collection")
public class CollectController {

    @Autowired
    private CollectService collectService;


    /**
     * 添加收藏
     *
     * @param addCollectRequest 添加收集请求
     * @return {@code R}
     */
    @PostMapping("/add")
    public R<Boolean> addCollection(@RequestBody @Valid CollectRequest addCollectRequest) {
        return R.success(collectService.addCollect(addCollectRequest));
    }

    /**
     * 删除收藏
     *
     * @param userId 用户id
     * @param songId 歌id
     * @return {@code R<Boolean>}
     */
    @DeleteMapping("/delete")
    public R<Boolean> deleteCollection(@RequestParam Integer userId, @RequestParam Integer songId) {
        return R.success(collectService.deleteCollect(userId, songId));
    }


    /**
     * 是否收藏歌曲
     *
     * @param isCollectRequest 收集请求
     * @return {@code R}
     */
    @PostMapping("/status")
    public R<Boolean> isCollection(@RequestBody @Valid CollectRequest isCollectRequest) {
        return R.success(collectService.existSongId(isCollectRequest));

    }

    /**
     * 返回的指定用户 ID 收藏的列表
     *
     * @param userId 用户id
     * @return {@code R}
     */
    @GetMapping("/detail")
    public R<List<Collect>> collectionOfUser(@RequestParam Integer userId) {
        return R.success(collectService.collectionOfUser(userId));
    }
}

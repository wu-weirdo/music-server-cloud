package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.domain.Collect;
import com.whf.music.request.CollectRequest;
import com.whf.music.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "收藏管理")
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
    @Operation(summary = "添加收藏")
    @PostMapping("/add")
    @Log(module = "收藏管理", description = "添加收藏", businessType = BusinessType.INSERT, operatorType = OperatorType.CLIENT)
    public R<Boolean> addCollection(@RequestBody @Valid CollectRequest addCollectRequest) {
        Boolean result = collectService.addCollect(addCollectRequest);
        return R.success(result, result ? "收藏成功！" : "收藏失败，请重试！");
    }

    /**
     * 删除收藏
     *
     * @param userId 用户id
     * @param songId 歌id
     * @return {@code R<Boolean>}
     */
    @Operation(summary = "删除收藏")
    @DeleteMapping("/delete")
    @Log(module = "收藏管理", description = "删除收藏", businessType = BusinessType.DELETE, operatorType = OperatorType.CLIENT)
    public R<Boolean> deleteCollection(@RequestParam Integer userId, @RequestParam Integer songId) {
        Boolean result = collectService.deleteCollect(userId, songId);
        return R.success(result, result ? "取消收藏成功！" : "取消收藏失败，请重试！");
    }


    /**
     * 是否收藏歌曲
     *
     * @param isCollectRequest 收集请求
     * @return {@code R}
     */
    @Operation(summary = "是否收藏歌曲")
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
    @Operation(summary = "返回的指定用户 ID 收藏的列表")
    @GetMapping("/detail")
    public R<List<Collect>> collectionOfUser(@RequestParam Integer userId) {
        return R.success(collectService.collectionOfUser(userId));
    }
}

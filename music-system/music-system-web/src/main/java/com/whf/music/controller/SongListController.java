package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.SongList;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.SongListRequest;
import com.whf.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 专辑管理
 *
 * @author whf
 * @date 2023/04/27
 */
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;


    /**
     * 添加专辑
     */
    @PostMapping("/add")
    @Log(module = "专辑管理", description = "添加专辑", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    public R<Boolean> addSongList(@RequestBody SongListRequest addSongListRequest) {
        Boolean result = songListService.addSongList(addSongListRequest);
        return R.success(result, result ? "添加成功！" : "添加失败！");
    }

    /**
     * 删除专辑
     */
    @GetMapping("/delete")
    @Log(module = "专辑管理", description = "删除专辑", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    public R<Boolean> deleteSongList(@RequestParam Integer id) {
        Boolean result = songListService.deleteSongList(id);
        return R.success(result, result ? "删除成功！" : "删除失败！");
    }

    /**
     * 查询专辑列表
     *
     * @param request 请求
     * @return {@code R<List<SongList>>}
     */
    @PostMapping("/list")
    public R<List<SongList>> getSongList(@RequestBody SongListRequest request) {
        return R.success(songListService.getSongList(request));
    }


    /**
     * 更新专辑信息
     *
     * @param request 更新专辑请求
     * @return {@code R<Boolean>}
     */
    @PostMapping("/update")
    @Log(module = "专辑管理", description = "更新专辑信息", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSongListMsg(@RequestBody SongListRequest request) {
        Boolean result = songListService.updateSongListMsg(request);
        return R.success(result, result ? "更新成功！" : "更新失败！");

    }

    /**
     * 更新专辑图片
     *
     * @param avatorFile avator文件
     * @param id         id
     * @return {@code R<Boolean>}
     */
    @PostMapping("/img/update")
    @Log(module = "专辑管理", description = "更新专辑图片", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Integer id) {
        Boolean result = songListService.updateSongListImg(avatorFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }
}

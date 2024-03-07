package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.model.domain.SongList;
import com.whf.music.model.request.SongListRequest;
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
    public R<Boolean> addSongList(@RequestBody SongListRequest addSongListRequest) {
        return R.success(songListService.addSongList(addSongListRequest));
    }

    /**
     * 删除专辑
     */
    @GetMapping("/delete")
    public R<Boolean> deleteSongList(@RequestParam Integer id) {
        return R.success(songListService.deleteSongList(id));
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
    public R<Boolean> updateSongListMsg(@RequestBody SongListRequest request) {
        return R.success(songListService.updateSongListMsg(request));

    }

    /**
     * 更新专辑图片
     *
     * @param avatorFile avator文件
     * @param id         id
     * @return {@code R<Boolean>}
     */
    @PostMapping("/img/update")
    public R<Boolean> updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Integer id) {
        return R.success(songListService.updateSongListImg(avatorFile, id));
    }
}

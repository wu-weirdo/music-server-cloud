package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.model.domain.Song;
import com.whf.music.model.reponse.TreeResponse;
import com.whf.music.model.request.SongRequest;
import com.whf.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     *
     * @param addSongRequest 添加歌曲请求
     * @param mpfile         mpfile
     * @return {@code R}
     */
    @PostMapping("/add")
    public R<Boolean> addSong(SongRequest addSongRequest, @RequestParam("file") MultipartFile mpfile) {
        return R.success(songService.addSong(addSongRequest, mpfile));
    }


    /**
     * 删除歌曲
     *
     * @param id id
     * @return {@code R}
     */
    @DeleteMapping("/delete")
    public R<Boolean> deleteSong(@RequestParam Integer id) {
        return R.success(songService.deleteSong(id));
    }


    /**
     * 返回所有歌曲
     *
     * @return {@code R}
     */
    @PostMapping("/list")
    public R<List<Song>> allSong(@RequestBody SongRequest request) {
        return R.success(songService.allSong(request));
    }

    /**
     * 返回指定歌手ID的歌曲树
     *
     * @param singerId 歌手id
     * @return {@code R}
     */
    @GetMapping("/singer/detail/tree")
    public R<List<TreeResponse>> songTreeOfSingerId(@RequestParam Integer singerId) {
        return R.success(songService.songTreeOfSingerId(singerId));
    }

    /**
     * 更新歌曲信息
     *
     * @param updateSongRequest 请求更新歌曲
     * @return {@code R}
     */
    @PostMapping("/update")
    public R<Boolean> updateSongMsg(@RequestBody SongRequest updateSongRequest) {
        return R.success(songService.updateSongMsg(updateSongRequest));
    }


    /**
     * 更新歌曲图片
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("/img/update")
    public R<Boolean> updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        return R.success(songService.updateSongPic(urlFile, id));
    }

    /**
     * 更新歌曲
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("/url/update")
    public R<Boolean> updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        return R.success(songService.updateSongUrl(urlFile, id));
    }
}

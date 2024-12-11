package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.Song;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.reponse.TreeResponse;
import com.whf.music.request.SongRequest;
import com.whf.music.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 歌曲管理
 * @author whf
 * @date 2024/12/11
 */
@RestController
@RequestMapping("/song")
@Tag(name = "歌曲管理")
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
    @Operation(summary = "添加歌曲")
    @Log(module = "歌曲管理", description = "添加歌曲", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    public R<Boolean> addSong(SongRequest addSongRequest, @RequestParam("file") MultipartFile mpfile) {
        Boolean result = songService.addSong(addSongRequest, mpfile);
        return R.success(result, result ? "添加成功！" : "添加失败！");
    }


    /**
     * 删除歌曲
     *
     * @param id id
     * @return {@code R}
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除歌曲")
    @Log(module = "歌曲管理", description = "删除歌曲", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    public R<Boolean> deleteSong(@RequestParam Integer id) {
        Boolean result = songService.deleteSong(id);
        return R.success(result, result ? "删除成功！" : "删除失败！");
    }


    /**
     * 返回所有歌曲
     *
     * @return {@code R}
     */
    @PostMapping("/list")
    @Operation(summary = "返回所有歌曲")
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
    @Operation(summary = "返回指定歌手ID的歌曲树")
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
    @Operation(summary = "更新歌曲")
    @Log(module = "歌曲管理", description = "更新歌曲", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSongMsg(@RequestBody SongRequest updateSongRequest) {
        Boolean result = songService.updateSongMsg(updateSongRequest);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }


    /**
     * 更新歌曲图片
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("/img/update")
    @Operation(summary = "更新歌曲图片")
    @Log(module = "歌曲管理", description = "更新歌曲图片", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        Boolean result = songService.updateSongPic(urlFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }

    /**
     * 更新歌曲
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("/url/update")
    @Operation(summary = "更新歌曲文件")
    @Log(module = "歌曲管理", description = "更新歌曲文件", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        Boolean result = songService.updateSongUrl(urlFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }
}

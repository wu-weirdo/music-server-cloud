package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.domain.Singer;
import com.whf.music.request.SingerRequest;
import com.whf.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 歌手控制器
 *
 * @author whf
 * @date 2023/04/27
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;


    /**
     * 添加歌手
     *
     * @param addSingerRequest 添加歌手请求
     * @return {@code R}
     */
    @PostMapping("/add")
    public R<Boolean> addSinger(@RequestBody SingerRequest addSingerRequest) {
        return R.success(singerService.addSinger(addSingerRequest));
    }

    /**
     * 删除歌手
     *
     * @param id id
     * @return {@code R}
     */
    @DeleteMapping("/delete")
    public R<Boolean> deleteSinger(@RequestParam int id) {
        return R.success(singerService.deleteSinger(id));
    }

    /**
     * 所有歌手
     *
     * @return {@code R}
     */
    @GetMapping("/list")
    public R<List<Singer>> allSinger() {
        return R.success(singerService.allSinger());
    }


    /**
     * 根据歌手名查找歌手
     *
     * @param name 名字
     * @return {@code R}
     */
    @GetMapping("/name/detail")
    public R<List<Singer>> singerOfName(@RequestParam String name) {
        return R.success(singerService.singerOfName(name));
    }

    /**
     * 根据歌手性别查找歌手
     *
     * @param sex 性
     * @return {@code R}
     */
    @GetMapping("/sex/detail")
    public R<List<Singer>> singerOfSex(@RequestParam int sex) {
        return R.success(singerService.singerOfSex(sex));
    }

    /**
     * 更新歌手信息
     *
     * @param updateSingerRequest 更新歌手请求
     * @return {@code R}
     */
    @PostMapping("/update")
    public R<Boolean> updateSingerMsg(@RequestBody SingerRequest updateSingerRequest) {
        return R.success(singerService.updateSingerMsg(updateSingerRequest));
    }

    /**
     * 更新歌手图片
     *
     * @param avatorFile avator文件
     * @param id         id
     * @return {@code R}
     */
    @PostMapping("/avatar/update")
    public R<Boolean> updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        return R.success(singerService.updateSingerPic(avatorFile, id));
    }
}

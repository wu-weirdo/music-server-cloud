package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.Singer;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.SingerRequest;
import com.whf.music.service.SingerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "歌手管理")
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
    @Operation(summary = "添加歌手")
    @Log(module = "歌手管理", description = "添加歌手", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    public R<Boolean> addSinger(@RequestBody SingerRequest addSingerRequest) {
        Boolean result = singerService.addSinger(addSingerRequest);
        return R.success(result, result ? "添加成功！" : "添加失败！");
    }

    /**
     * 删除歌手
     *
     * @param id id
     * @return {@code R}
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除歌手")
    @Log(module = "歌手管理", description = "删除歌手", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    public R<Boolean> deleteSinger(@RequestParam int id) {
        Boolean result = singerService.deleteSinger(id);
        return R.success(result, result ? "删除成功！" : "删除失败！");
    }

    /**
     * 所有歌手
     *
     * @return {@code R}
     */
    @GetMapping("/list")
    @Operation(summary = "所有歌手")
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
    @Operation(summary = "根据歌手名查找歌手")
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
    @Operation(summary = "根据歌手性别查找歌手")
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
    @Operation(summary = "更新歌手信息")
    @Log(module = "歌手管理", description = "更新歌手", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSingerMsg(@RequestBody SingerRequest updateSingerRequest) {
        Boolean result = singerService.updateSingerMsg(updateSingerRequest);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }

    /**
     * 更新歌手图片
     *
     * @param avatorFile avator文件
     * @param id         id
     * @return {@code R}
     */
    @PostMapping("/avatar/update")
    @Operation(summary = "更新歌手图片")
    @Log(module = "歌手管理", description = "更新歌手图片", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        Boolean result = singerService.updateSingerPic(avatorFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }
}

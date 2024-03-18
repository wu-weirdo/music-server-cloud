package com.whf.music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.Video;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.reponse.TreeResponse;
import com.whf.music.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * (Video)表控制层
 *
 * @author whf
 * @since 2023-04-14 10:00:29
 */
@RestController
@RequestMapping("/video/")
public class VideoController {
    /**
     * 服务对象
     */
    @Resource
    private VideoService videoService;

    /**
     * 分页查询所有数据
     *
     * @param video 查询实体
     * @return 所有数据
     */
    @PostMapping("list")
    public R<List<Video>> selectAll(@RequestBody Video video) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>(video);
        queryWrapper.eq(Objects.nonNull(video.getName()), "name", video.getName());
        queryWrapper.eq(Objects.nonNull(video.getSingerId()), "singer_id", video.getSingerId());
        return R.success(this.videoService.list(queryWrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("detail")
    public R<Video> selectOne(@RequestParam Serializable id) {
        return R.success(this.videoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param video 实体对象
     * @return 新增结果
     */
    @PostMapping("add")
    @Log(module = "视频管理", description = "新增视频", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> insert(@RequestBody Video video, @RequestParam("file") MultipartFile file) {
        return R.success(this.videoService.addVideo(video, file));
    }

    /**
     * 修改数据
     *
     * @param video 实体对象
     * @return 修改结果
     */
    @PostMapping("update")
    @Log(module = "视频管理", description = "更新视频信息", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> update(@RequestBody Video video) {
        boolean result = this.videoService.updateById(video);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @GetMapping("delete")
    @Log(module = "视频管理", description = "删除视频", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        boolean result = this.videoService.removeByIds(idList);
        return R.success(result, result ? "删除成功！" : "删除失败！");
    }

    /**
     * 更新图片
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("img/update")
    @Log(module = "视频管理", description = "更新视频图片", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updatePic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        boolean result = videoService.updatePic(urlFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }

    /**
     * 更新视频
     *
     * @param urlFile url文件
     * @param id      id
     * @return {@code R}
     */
    @PostMapping("url/update")
    @Log(module = "视频管理", description = "更新视频文件", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    public R<Boolean> updateVideo(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") Integer id) {
        boolean result = videoService.updateVideo(urlFile, id);
        return R.success(result, result ? "更新成功！" : "更新失败！");
    }

    /**
     * 歌手视频树
     *
     * @return {@code R}
     */
    @GetMapping("tree")
    public R<List<TreeResponse>> singerVideoTree() {
        return R.success(videoService.singerVideoTree());
    }
}


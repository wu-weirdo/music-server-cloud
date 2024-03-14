package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.domain.Banner;
import com.whf.music.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author whf
 * @date 2023/04/27
 */
@Tag(name = "轮播图管理")
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取所有轮播图
     *
     * @return {@code R<List<Banner>>}
     */

    @Operation(summary = "获取所有轮播图")
    @GetMapping("/list")
    public R<List<Banner>> getAllBanner() {
        return R.success(bannerService.getAllBanner());
    }
}

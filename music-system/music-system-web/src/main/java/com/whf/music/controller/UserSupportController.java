package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.UserSupportRequest;
import com.whf.music.service.UserSupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论点赞管理
 *
 * @Author whf
 * @Time : 2022/6/11 16:07
 **/
@RestController
@RequestMapping("/userSupport")
@Tag(name = "评论点赞管理")
public class UserSupportController {

    @Autowired
    UserSupportService userSupportService;

    @PostMapping("/test")
    @Operation(summary = "是否点赞")
    public R<Boolean> isUserSupportComment(@RequestBody UserSupportRequest request) {
        return R.success(userSupportService.isUserSupportComment(request));
    }

    @PostMapping("/insert")
    @Operation(summary = "新增点赞")
    @Log(module = "评论点赞管理", description = "新增点赞", businessType = BusinessType.INSERT, operatorType = OperatorType.MANAGE)
    public R<Boolean> insertCommentSupport(@RequestBody UserSupportRequest request) {
        return R.success(userSupportService.insertCommentSupport(request));
    }

    @PostMapping("/delete")
    @Operation(summary = "取消点赞")
    @Log(module = "评论点赞管理", description = "取消点赞", businessType = BusinessType.DELETE, operatorType = OperatorType.MANAGE)
    public R<Boolean> deleteCommentSupport(@RequestBody UserSupportRequest request) {
        return R.success(userSupportService.deleteCommentSupport(request));
    }
}

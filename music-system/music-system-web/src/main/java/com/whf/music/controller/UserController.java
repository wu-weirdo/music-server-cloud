package com.whf.music.controller;

import com.whf.music.annotation.Log;
import com.whf.music.common.R;
import com.whf.music.domain.User;
import com.whf.music.enums.BusinessType;
import com.whf.music.enums.OperatorType;
import com.whf.music.request.UserRequest;
import com.whf.music.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户管理
 *
 * @author whf
 * @date 2024/12/11
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 返回所有用户
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有用户")
    public R<List<User>> allUser() {
        return R.success(userService.list());
    }


    /**
     * 返回指定 ID 的用户
     */
    @GetMapping("/detail")
    @Operation(summary = "获取用户详情")
    public R<User> userOfId(@RequestParam Integer id) {
        return R.success(userService.getById(id));
    }

    /**
     * 删除用户
     */
    @GetMapping("/delete")
    @Operation(summary = "删除用户")
    @Log(module = "用户管理", description = "删除用户", businessType = BusinessType.DELETE, operatorType = OperatorType.CLIENT)
    public R<Boolean> deleteUser(@RequestParam Integer id) {
        return R.success(userService.removeById(id));
    }


    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    @Operation(summary = "更新用户信息")
    @Log(module = "用户管理", description = "更新用户信息", businessType = BusinessType.UPDATE, operatorType = OperatorType.CLIENT)
    public R<Boolean> updateUserMsg(@RequestBody UserRequest updateRequest) {
        Boolean result = userService.updateUserMsg(updateRequest);
        return R.success(result, result ? "更新信息成功！" : "更新信息失败，请重试！");
    }

    /**
     * 更新用户密码
     */
    @PostMapping("/updatePassword")
    @Operation(summary = "更新用户密码")
    @Log(module = "用户管理", description = "更新用户密码", businessType = BusinessType.UPDATE, operatorType = OperatorType.CLIENT)
    public R<Boolean> updatePassword(@RequestBody UserRequest updatePasswordRequest) {
        Boolean result = userService.updatePassword(updatePasswordRequest);
        return R.success(result, result ? "更新密码成功！" : "更新密码失败，请重试！");
    }

    /**
     * 更新用户头像
     */
    @PostMapping("/avatar/update")
    @Operation(summary = "更新用户头像")
    @Log(module = "用户管理", description = "更新用户头像", businessType = BusinessType.UPDATE, operatorType = OperatorType.CLIENT)
    public R<Boolean> updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Long id) {
        Boolean result = userService.updateUserAvator(avatorFile, id);
        return R.success(result, result ? "更新头像成功！" : "更新头像失败，请重试！");
    }
}

package com.whf.music.controller;

import com.whf.music.common.R;
import com.whf.music.model.domain.User;
import com.whf.music.model.request.UserRequest;
import com.whf.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 返回所有用户
     */
    @GetMapping("/list")
    public R<List<User>> allUser() {
        return R.success(userService.list());
    }


    /**
     * 返回指定 ID 的用户
     */
    @GetMapping("/detail")
    public R<User> userOfId(@RequestParam Integer id) {
        return R.success(userService.getById(id));
    }

    /**
     * 删除用户
     */
    @GetMapping("/delete")
    public R<Boolean> deleteUser(@RequestParam Integer id) {
        return R.success(userService.removeById(id));
    }


    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public R<Boolean> updateUserMsg(@RequestBody UserRequest updateRequest) {
        Boolean result = userService.updateUserMsg(updateRequest);
        return R.success(result, result ? "更新信息成功！" : "更新信息失败，请重试！");
    }

    /**
     * 更新用户密码
     */
    @PostMapping("/updatePassword")
    public R<Boolean> updatePassword(@RequestBody UserRequest updatePasswordRequest) {
        Boolean result = userService.updatePassword(updatePasswordRequest);
        return R.success(result, result ? "更新密码成功！" : "更新密码失败，请重试！");
    }

    /**
     * 更新用户头像
     */
    @PostMapping("/avatar/update")
    public R<Boolean> updateUserPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") Long id) {
        Boolean result = userService.updateUserAvator(avatorFile, id);
        return R.success(result, result ? "更新头像成功！" : "更新头像失败，请重试！");
    }
}

package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.model.domain.User;
import com.whf.music.model.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    Boolean updateUserMsg(UserRequest updateRequest);

    Boolean updateUserAvator(MultipartFile avatorFile, int id);

    Boolean updatePassword(UserRequest updatePasswordRequest);

    Boolean existUser(String username);

    User selectUserByUserName(String userName);

}

package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.domain.User;
import com.whf.music.request.UserRequest;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    Boolean updateUserMsg(UserRequest updateRequest);

    Boolean updateUserAvator(MultipartFile avatorFile, Long id);

    Boolean updatePassword(UserRequest updatePasswordRequest);

    Boolean existUser(String username);

    User selectUserByUserName(String userName);

    User saveByAuthUser(AuthUser authUser);
}

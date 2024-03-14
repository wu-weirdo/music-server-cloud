package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.domain.UserSupport;
import com.whf.music.request.UserSupportRequest;

/**
 * @author asus
 * @description 针对表【user_support】的数据库操作Service
 * @createDate 2022-06-11 16:06:28
 */
public interface UserSupportService extends IService<UserSupport> {

    Boolean isUserSupportComment(UserSupportRequest userSupportRequest);

    Boolean insertCommentSupport(UserSupportRequest userSupportRequest);

    Boolean deleteCommentSupport(UserSupportRequest userSupportRequest);
}

package com.whf.music.convert;

import com.whf.music.domin.UserRemote;
import com.whf.music.entity.LoginUser;
import com.whf.music.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author whf
 * @date 2024/3/6
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    LoginUser convert(User user);

    User RemoteConvert(UserRemote userRemote);
}

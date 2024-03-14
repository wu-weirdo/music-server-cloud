package com.whf.music.convert;

import com.whf.music.domain.User;
import com.whf.music.entity.LoginUser;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T15:47:17+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public LoginUser convert(User user) {
        if ( user == null ) {
            return null;
        }

        LoginUser loginUser = new LoginUser();

        loginUser.setId( user.getId() );
        loginUser.setUserName( user.getUserName() );
        loginUser.setPassword( user.getPassword() );
        loginUser.setSex( user.getSex() );
        loginUser.setPhoneNum( user.getPhoneNum() );
        loginUser.setEmail( user.getEmail() );
        loginUser.setBirth( user.getBirth() );
        loginUser.setIntroduction( user.getIntroduction() );
        loginUser.setLocation( user.getLocation() );
        loginUser.setCreateTime( user.getCreateTime() );
        loginUser.setUpdateTime( user.getUpdateTime() );

        return loginUser;
    }
}

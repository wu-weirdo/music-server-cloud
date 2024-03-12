package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.constant.Constants;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.UserMapper;
import com.whf.music.model.domain.User;
import com.whf.music.model.request.UserRequest;
import com.whf.music.service.UserService;
import com.whf.music.utils.ExceptionUtils;
import com.whf.music.utils.SecurityUtils;
import com.whf.music.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Value("${music.security.initPassword:123456}")
    private String initPassword;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean updateUserMsg(UserRequest updateRequest) {
        User user = new User();
        BeanUtils.copyProperties(updateRequest, user);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Boolean updatePassword(UserRequest updatePasswordRequest) {
        User user = userMapper.selectById(updatePasswordRequest.getId());
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultEnum.USER_NOT_EXIST);
        }
        if (!SecurityUtils.matchesPassword(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new ServiceException(ResultEnum.USERNAME_PASSWORD_ERROR);
        }
        String secretPassword = SecurityUtils.encryptPassword(updatePasswordRequest.getPassword());
        user.setPassword(secretPassword);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Boolean updateUserAvator(MultipartFile avatorFile, Long id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //路径 他这个会根据你的系统获取对应的文件分隔符
        String filePath = Constants.PROJECT_PATH + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "avatorImages";
        File file = new File(filePath);
        if (!file.exists() && !file.mkdir()) {
            log.error("UserService updateUserAvator mkdir error");
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "图片上传失败");
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/resource/img/avatorImages/" + fileName;
        try {
            avatorFile.transferTo(dest);
        } catch (IOException e) {
            log.error("SongService updateUserAvator error:{}", ExceptionUtils.getStackTraceMessage(e));
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "图片上传失败");
        }
        User user = new User();
        user.setId(id);
        user.setAvator(imgPath);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Boolean existUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public User selectUserByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User saveByAuthUser(AuthUser authUser) {
        String userName = authUser.getUsername();
        if (existUser(userName)) {
            log.warn("用户名重复 openId:{} userName:{}", authUser.getUuid(), userName);
            userName += UUID.randomUUID().toString(true);
        }
        String password = SecurityUtils.encryptPassword(initPassword);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(authUser.getEmail());
        user.setIntroduction(authUser.getRemark());
        user.setLocation(authUser.getLocation());
        user.setAvator(authUser.getAvatar());
        this.save(user);
        return user;
    }
}

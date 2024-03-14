package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.SingerMapper;
import com.whf.music.domain.Singer;
import com.whf.music.request.SingerRequest;
import com.whf.music.service.SingerService;
import com.whf.music.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public Boolean updateSingerMsg(SingerRequest updateSingerRequest) {
        Singer singer = new Singer();
        BeanUtils.copyProperties(updateSingerRequest, singer);
       return singerMapper.updateById(singer) > 0;
    }

    @Override
    public Boolean updateSingerPic(MultipartFile avatorFile, int id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "resource" + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "singerPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/resource/img/singerPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
        } catch (IOException e) {
            log.error("SingerService updateSingerPic error:{}", ExceptionUtils.getStackTraceMessage(e));
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "更新歌手图片失败");
        }
        Singer singer = new Singer();
        singer.setId(id);
        singer.setPic(imgPath);
        return singerMapper.updateById(singer) > 0;
    }

    @Override
    public Boolean deleteSinger(Integer id) {
        return singerMapper.deleteById(id) > 0;
    }

    @Override
    public List<Singer> allSinger() {
        return singerMapper.selectList(null);
    }

    @Override
    public Boolean addSinger(SingerRequest addSingerRequest) {
        Singer singer = new Singer();
        BeanUtils.copyProperties(addSingerRequest, singer);
        String pic = "/resource/img/avatorImages/user.jpg";
        singer.setPic(pic);
        return singerMapper.insert(singer) > 0;
    }

    @Override
    public List<Singer> singerOfName(String name) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return singerMapper.selectList(queryWrapper);
    }

    @Override
    public List<Singer> singerOfSex(Integer sex) {
        QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("sex",sex);
        return singerMapper.selectList(queryWrapper);
    }
}

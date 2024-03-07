package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.SongListMapper;
import com.whf.music.model.domain.SongList;
import com.whf.music.model.request.SongListRequest;
import com.whf.music.service.SongListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.whf.music.enums.ResultEnum.FILE_UPLOAD_ERROR;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public Boolean updateSongListMsg(SongListRequest updateSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(updateSongListRequest, songList);
        return songListMapper.updateById(songList) > 0;
    }

    @Override
    public Boolean deleteSongList(Integer id) {
        return songListMapper.deleteById(id) > 0;
    }

    @Override
    public List<SongList> getSongList(SongListRequest request) {
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(request.getSingerId()), SongList::getSingerId, request.getSingerId());
        wrapper.like(Objects.nonNull(request.getTitle()), SongList::getTitle, request.getTitle());
        wrapper.like(Objects.nonNull(request.getStyle()), SongList::getStyle, request.getStyle());
        return songListMapper.selectList(wrapper);
    }

    @Override
    public Boolean addSongList(SongListRequest addSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(addSongListRequest, songList);
        String pic = "/resource/img/songListPic/123.jpg";
        songList.setPic(pic);
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public Boolean updateSongListImg(MultipartFile avatorFile, @RequestParam("id") int id) {
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "resource" + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "songListPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/resource/img/songListPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
        } catch (IOException e) {
            throw new ServiceException(FILE_UPLOAD_ERROR.getCode(), FILE_UPLOAD_ERROR.getMessage());
        }
        SongList songList = new SongList();
        songList.setId(id);
        songList.setPic(imgPath);
        return songListMapper.updateById(songList) > 0;
    }
}

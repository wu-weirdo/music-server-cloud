package com.whf.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.enums.ResultEnum;
import com.whf.music.excepetion.ServiceException;
import com.whf.music.mapper.SingerMapper;
import com.whf.music.mapper.VideoMapper;
import com.whf.music.domain.Singer;
import com.whf.music.domain.Video;
import com.whf.music.reponse.TreeResponse;
import com.whf.music.service.VideoService;
import com.whf.music.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (Video)表服务实现类
 *
 * @author whf
 * @since 2023-04-14 10:00:31
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public Boolean addVideo(Video video, MultipartFile file) {
        Singer singer = singerMapper.selectOne(new QueryWrapper<Singer>().eq("id", video.getSingerId()));
        String pic = "/resource/img/songPic/tubiao.jpg";
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "resource" + System.getProperty("file.separator")
                + "video" + System.getProperty("file.separator") + singer.getName();
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                log.error("VideoService addVideo mkdir error");
                throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "视频上传失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/resource/video/" + singer.getName() + "/" + fileName;
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("VideoService addVideo error:{}", ExceptionUtils.getStackTraceMessage(e));
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "视频上传失败");
        }
        video.setCreateTime(LocalDateTime.now());
        video.setUpdateTime(LocalDateTime.now());
        video.setPic(pic);
        video.setUrl(storeUrlPath);
        return this.baseMapper.insert(video) > 0;
    }

    @Override
    public Boolean updatePic(MultipartFile urlFile, Integer id) {
        String fileName = System.currentTimeMillis() + urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "videoPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                log.error("VideoService updatePic mkdir error");
                throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "图片上传失败");
            }
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/resource/img/videoPic/" + fileName;
        try {
            urlFile.transferTo(dest);
        } catch (IOException e) {
            log.error("VideoService updatePic error:{}", ExceptionUtils.getStackTraceMessage(e));
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "图片上传失败");
        }
        Video video = new Video();
        video.setId(id);
        video.setPic(storeUrlPath);
        return this.baseMapper.updateById(video) > 0;
    }

    @Override
    public Boolean updateVideo(MultipartFile urlFile, Integer id) {
        Video video = baseMapper.selectById(id);
        if (Objects.isNull(video)) {
            return false;
        }
        Singer singer = singerMapper.selectOne(new QueryWrapper<Singer>().eq("id", video.getSingerId()));
        String fileName = urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "video" + System.getProperty("file.separator") + singer.getName();
        File file1 = new File(filePath);
        if (!file1.exists()) {
            if (!file1.mkdir()) {
                log.error("VideoService updateVideo mkdir error");
                throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "视频上传失败");
            }
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/resource/video/" + singer.getName() + "/" + fileName;
        try {
            urlFile.transferTo(dest);
        } catch (IOException e) {
            log.error("VideoService updateVideo error:{}", ExceptionUtils.getStackTraceMessage(e));
            throw new ServiceException(ResultEnum.FILE_UPLOAD_ERROR.getCode(), "视频上传失败");
        }
        video.setUpdateTime(LocalDateTime.now());
        video.setUrl(storeUrlPath);
        return this.baseMapper.updateById(video) > 0;
    }

    @Override
    public List<TreeResponse> singerVideoTree() {
        List<TreeResponse> responseList = new ArrayList<>();
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        List<Video> videos = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(videos)) {
            return responseList;
        }
        Map<Integer, List<Video>> collect = videos.stream().collect(Collectors.groupingBy(Video::getSingerId, Collectors.toList()));
        for (Map.Entry<Integer, List<Video>> videoMap : collect.entrySet()) {
            TreeResponse response = new TreeResponse();
            response.setValue(String.valueOf(videoMap.getKey()));
            Singer singer = singerMapper.selectOne(new QueryWrapper<Singer>().eq("id", videoMap.getKey()));
            response.setLabel(singer.getName());
            List<Video> songList = videoMap.getValue();
            if (!CollectionUtils.isEmpty(songList)) {
                List<TreeResponse> childrenList = new ArrayList<>();
                for (Video video : songList) {
                    TreeResponse children = new TreeResponse();
                    children.setValue(String.valueOf(video.getId()));
                    children.setLabel(video.getName());
                    children.setChildren(Collections.emptyList());
                    childrenList.add(children);
                }
                response.setChildren(childrenList);
            }
            responseList.add(response);
        }
        return responseList;
    }
}


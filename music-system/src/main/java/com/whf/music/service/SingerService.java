package com.whf.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.model.domain.Singer;
import com.whf.music.model.request.SingerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SingerService extends IService<Singer> {

    /**
     * 添加歌手
     *
     * @param addSingerRequest 添加歌手请求
     * @return {@code Boolean}
     */
    Boolean addSinger(SingerRequest addSingerRequest);

    /**
     * 更新歌手信息
     *
     * @param updateSingerRequest 更新歌手请求
     * @return {@code Boolean}
     */
    Boolean updateSingerMsg(SingerRequest updateSingerRequest);

    /**
     * 更新歌手图片
     *
     * @param avatorFile avator文件
     * @param id         id
     * @return {@code Boolean}
     */
    Boolean updateSingerPic(MultipartFile avatorFile, int id);

    /**
     * 删除歌手
     *
     * @param id id
     * @return {@code Boolean}
     */
    Boolean deleteSinger(Integer id);

    /**
     * 所有歌手
     *
     * @return {@code List<Singer>}
     */
    List<Singer> allSinger();

    /**
     * 根据歌手名称查找歌手
     *
     * @param name 名字
     * @return {@code List<Singer>}
     */
    List<Singer> singerOfName(String name);

    /**
     * 根据歌手性别查找歌手
     *
     * @param sex 性别
     * @return {@code List<Singer>}
     */
    List<Singer> singerOfSex(Integer sex);
}

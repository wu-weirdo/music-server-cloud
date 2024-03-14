package com.whf.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whf.music.mapper.BannerMapper;
import com.whf.music.domain.Banner;
import com.whf.music.service.BannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author asus
 * @description 针对表【banner】的数据库操作Service实现
 * @createDate 2022-06-13 13:13:42
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
        implements BannerService {

    @Cacheable(value = "banner", key = "'banner'")
    @Override
    public List<Banner> getAllBanner() {
        return baseMapper.selectList(null);
    }
}

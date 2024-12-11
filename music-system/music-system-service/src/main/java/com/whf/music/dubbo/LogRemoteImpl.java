package com.whf.music.dubbo;

import com.whf.music.domain.SysLog;
import com.whf.music.domin.SysLogRemote;
import com.whf.music.service.SysLogService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author whf
 * @date 2024/3/14
 */
@Service
@DubboService
public class LogRemoteImpl implements LogRemoteService {

    @Resource
    private SysLogService sysLogService;

    /**
     * 保存日志
     *
     * @param sysLogRemote 日志
     */
    @Override
    public void saveLog(SysLogRemote sysLogRemote) {
        SysLog sysLog = new SysLog();
        BeanUtils.copyProperties(sysLogRemote, sysLog);
        sysLogService.save(sysLog);
    }
}

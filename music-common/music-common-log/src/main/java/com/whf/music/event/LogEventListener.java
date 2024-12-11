package com.whf.music.event;

import com.whf.music.domin.SysLogRemote;
import com.whf.music.dubbo.LogRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 日志事件监听
 * @author whf
 * @date 2024/3/14
 */
@Component
public class LogEventListener {

    @DubboReference
    private LogRemoteService logRemoteService;

    @Async
    @EventListener
    public void saveLog(OperationLogEvent operationLogEvent) {
        SysLogRemote sysLog = new SysLogRemote();
        BeanUtils.copyProperties(operationLogEvent, sysLog);
        sysLog.setCreateTime(operationLogEvent.getOperationTime());
        sysLog.setUpdateTime(operationLogEvent.getOperationTime());
        logRemoteService.saveLog(sysLog);
    }
}

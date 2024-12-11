package com.whf.music.dubbo;

import com.whf.music.domin.SysLogRemote;

/**
 * @author whf
 * @date 2024/3/14
 */
public interface LogRemoteService {

    /**
     * 保存日志
     * @param sysLog 日志
     */
    void saveLog(SysLogRemote sysLog);
}

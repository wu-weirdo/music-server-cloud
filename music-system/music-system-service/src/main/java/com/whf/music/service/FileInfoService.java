package com.whf.music.service;

import com.whf.music.domain.FileChunk;
import com.whf.music.domain.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whf.music.request.FileUploadRequest;

/**
* @author hfwu
* @description 针对表【file_info(文件详情)】的数据库操作Service
* @createDate 2024-08-05 14:30:31
*/
public interface FileInfoService extends IService<FileInfo> {

    FileInfo saveFileInfo(FileUploadRequest dto, FileChunk fileChunk);

    FileInfo getFileInfoByMd5(String fileMd5);
}

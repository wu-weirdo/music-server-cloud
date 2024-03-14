package com.whf.music.controller;

import com.whf.music.domain.Song;
import com.whf.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * 下载
 *
 * @author whf
 * @date 2023/04/27
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    @Resource
    private SongService songService;

    @RequestMapping("/song")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("songId") Long songId) throws IOException {
        log.info("download song id:{}", songId);
        Song song = songService.getById(songId);
        if (Objects.isNull(song)) {
            log.error("download file id:{} error: song not exist", songId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String url = song.getUrl();
        url = System.getProperty("user.dir") + url;
        FileSystemResource file = new FileSystemResource(url);
        if (!file.exists()) {
            log.error("download file id:{} error: file not exist", songId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}

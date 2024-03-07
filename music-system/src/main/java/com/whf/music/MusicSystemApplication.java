package com.whf.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.whf.music.mapper")
public class MusicSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSystemApplication.class, args);
    }

}

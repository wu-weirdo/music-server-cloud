package com.whf.music;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDubbo
@MapperScan("com.whf.music.mapper")
public class MusicSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSystemApplication.class, args);
    }

}

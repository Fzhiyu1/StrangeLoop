package com.xiaofeng.strangeloop;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaofeng.strangeloop.system.mapper")
public class StrangeLoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrangeLoopApplication.class, args);
    }

}

package com.kaguya.ktvadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kaguya.ktvadmin.mapper")
public class KtvadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(KtvadminApplication.class, args);
    }

}

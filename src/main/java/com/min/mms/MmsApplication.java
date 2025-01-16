package com.min.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmsApplication.class, args);

        System.out.println("우분투 서버에 젠킨스 배포하고 실행");
    }

}

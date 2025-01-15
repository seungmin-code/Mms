package com.min.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmsApplication.class, args);

        System.out.println("젠킨스 빌드 후 배치파일 실행");
    }

}

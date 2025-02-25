package com.min.mms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MmsApplication {

    @Value("${gmail}")
    private static String mailUsername;

    @Value("${gmailpw}")
    private static String mailPassword;

    public static void main(String[] args) {
        SpringApplication.run(MmsApplication.class, args);
        System.out.println(mailUsername + mailPassword);
    }

}

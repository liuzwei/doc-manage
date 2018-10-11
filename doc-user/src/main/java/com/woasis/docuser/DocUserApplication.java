package com.woasis.docuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = "com.woasis.docuser.mapper")
@EnableTransactionManagement
public class DocUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocUserApplication.class, args);
    }
}

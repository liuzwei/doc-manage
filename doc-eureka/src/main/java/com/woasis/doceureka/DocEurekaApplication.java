package com.woasis.doceureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DocEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocEurekaApplication.class, args);
    }
}

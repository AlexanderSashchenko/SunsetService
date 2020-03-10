package com.datax.sunset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SunsetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunsetServiceApplication.class, args);
    }

}

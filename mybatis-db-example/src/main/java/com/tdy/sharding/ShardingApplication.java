package com.tdy.sharding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ShardingApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }


    @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            log.info("{}", "started");
        };
    }

}

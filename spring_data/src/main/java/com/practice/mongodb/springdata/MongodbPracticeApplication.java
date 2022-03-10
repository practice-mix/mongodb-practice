package com.practice.mongodb.springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MongodbPracticeApplication {


    public static void main(String[] args) {
        SpringApplication.run(MongodbPracticeApplication.class, args);
    }
}

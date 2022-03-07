package com.practice.mongodb.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MongodbPracticeApplication {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongodbPracticeApplication.class, args);
    }
}

package com.practice.mongodb.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
public class ZipsService {

    @Autowired
    private MongoTemplate mongoTemplate;


}

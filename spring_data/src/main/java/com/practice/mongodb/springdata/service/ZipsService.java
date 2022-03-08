package com.practice.mongodb.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ZipsService {

    @Autowired
    private MongoTemplate mongoTemplate;


}

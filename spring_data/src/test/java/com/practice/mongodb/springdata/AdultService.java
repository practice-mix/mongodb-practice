package com.practice.mongodb.springdata;

import com.practice.mongodb.springdata.entity.Adult;
import com.practice.mongodb.springdata.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@DataMongoTest
public class AdultService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void insert() {
        Adult person = new Adult("Joe", 20, "Lily");
        mongoTemplate.insert(person);
        System.out.println("person = " + person);
        Person byId = mongoTemplate.findById(person.getId(), Person.class);
        System.out.println("byId = " + byId);
    }

    @Test
    public void findAmbiguity() {
        List<Object> person = mongoTemplate.findAll(Object.class, "person");
        System.out.println("person = " + person);
    }
}

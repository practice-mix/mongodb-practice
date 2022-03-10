package com.practice.mongodb.springdata;

import com.practice.mongodb.springdata.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@DataMongoTest
public class PersonService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void insert() {
        Person person = new Person("Joe", 20);
        mongoTemplate.insert(person);
        System.out.println("person = " + person);
        Person byId = mongoTemplate.findById(person.getId(), Person.class);
        System.out.println("byId = " + byId);
    }

    @Test
    public void find() {
        List<Person> person = mongoTemplate.find(query(where("name").is("Joe")), Person.class);
        System.out.println("person = " + person);
    }

    @Test
    public void findAmbiguity() {
        List<Object> person = mongoTemplate.findAll(Object.class, "person");
        System.out.println("person = " + person);
    }

    @Test
    public void modify() {
        mongoTemplate.updateMulti(query(where("name").is("Joe")), update("age", 21), Person.class);
        Person person = mongoTemplate.findOne(query(where("name").is("Joe")), Person.class);
        System.out.println("person = " + person);

    }

    @Test
    public void delete() {
        Person person = mongoTemplate.findOne(query(where("name").is("Joe")), Person.class);
        System.out.println("person = " + person);
        mongoTemplate.remove(person);

        Person byId = mongoTemplate.findById(person.getId(), Person.class);
        System.out.println("byId = " + byId);
    }
}

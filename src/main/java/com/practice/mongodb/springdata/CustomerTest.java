package com.practice.mongodb.springdata;

import com.practice.mongodb.MongodbPracticeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MongodbPracticeApplication.class)
@AutoConfigureDataMongo
public class CustomerTest {
    @Autowired
    private CustomerRepository repository;

    @Test
    public void save() {
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

    }

    @Test
    public void find(){
        for (Customer customer : repository.findAll()) {
            System.out.println("customer = " + customer);
        }
        System.out.println(repository.findByFirstName("Alice"));
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println("customer = " + customer);
        }

    }

}

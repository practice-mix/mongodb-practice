package com.practice.mongodb.springdata;

import com.practice.mongodb.springdata.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

//@SpringBootTest(classes = MongodbPracticeApplication.class)
//@AutoConfigureDataMongo
@DataMongoTest
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
        System.out.println(repository.findByFirstName("Alice").get(0));
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println("customer = " + customer);
        }

    }

}

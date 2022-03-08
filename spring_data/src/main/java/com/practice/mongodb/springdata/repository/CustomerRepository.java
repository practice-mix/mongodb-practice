package com.practice.mongodb.springdata.repository;

import com.practice.mongodb.springdata.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}

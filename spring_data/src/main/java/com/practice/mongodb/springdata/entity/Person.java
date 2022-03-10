package com.practice.mongodb.springdata.entity;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@TypeAlias("person")
@Data
@Document("person")
public class Person {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private int age;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @CreatedBy
    private String createUser;

    @LastModifiedBy
    private String updateUser;


    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
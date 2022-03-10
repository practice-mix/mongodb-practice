package com.practice.mongodb.springdata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("person")
@TypeAlias("adult")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Adult extends Person {
    private String wife;

    public Adult(String name, int age, String wife) {
        super(name, age);
        this.wife = wife;
    }

}

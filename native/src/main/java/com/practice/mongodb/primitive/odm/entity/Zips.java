package com.practice.mongodb.primitive.odm.entity;

import lombok.Data;

import java.util.List;

@Data
public class Zips {
    public String _id;
    public String city;
    public List<Double> loc;
    public int pop;
    public String state;

}

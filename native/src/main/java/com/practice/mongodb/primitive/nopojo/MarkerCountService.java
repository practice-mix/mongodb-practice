package com.practice.mongodb.primitive.nopojo;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MarkerCountService {
    private static com.mongodb.client.MongoClient mongoClient;
    private static MongoCollection<Document> collection;

    @BeforeAll
    public static void prepare() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        collection = mongoClient.getDatabase("java_primitive").getCollection("middleware");

    }

    @Test
    public void test(){

        List<Document> docs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            docs.add(new Document("i", i));
        }
        collection.insertMany(docs);
        collection.createIndex(new Document("i", 1));


    }

    @Test
    public void find(){
        Document eq = collection.find(Filters.eq("i", 71)).first();
        System.out.println("eq = " + eq);

        Consumer<? super Document> consumer = document -> System.out.println(document.toJson());
        collection.find(Filters.gt("i", 50)).forEach(consumer);
        collection.find(Filters.and(Filters.gt("i",10),Filters.lt("i",50))).forEach(consumer);

    }

    @Test
    public void update(){
        collection.updateOne(Filters.eq("i", 10), Updates.set("i", 1000));
        UpdateResult updateResult = collection.updateMany(Filters.lt("i", 50), Updates.inc("i", 100));
        System.out.println("ModifiedCount: " +updateResult.getModifiedCount());
    }

    @Test
    public void delete(){
        collection.deleteOne(Filters.eq("i", 1));

    }
}

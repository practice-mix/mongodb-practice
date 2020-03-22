package com.practice.mongodb.primitive.nopojo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StudentService {
    private static com.mongodb.client.MongoClient mongoClient;
    private static MongoDatabase database;

    @BeforeAll
    public static void prepare() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("java_primitive");

    }
    @Test
    public  void insert(String[] args) {
        /*
person = {
  _id: "jo",
  name: "Jo Bloggs",
  age: 34,
  address: {
    street: "123 Fake St",
    city: "Faketon",
    state: "MA",
    zip: &#x201C;12345&#x201D;
  }
  books: [ 27464, 747854, ...]
}
 * */
        MongoCollection<Document> collection = database.getCollection("student");
        Document peron = new Document("_id", "jo")
                .append("name", "Jo Bloggs")
                .append("age", 32)
                .append("address", new BasicDBObject("street", "123 Fake St")
                        .append("city", "Faketon")
                        .append("state", "MA")
                        .append("zip", "&#x201C;12345&#x201D")
                )
                .append("books", Arrays.asList("27464","747854"))
                ;
        collection.insertOne(peron);

    }


}

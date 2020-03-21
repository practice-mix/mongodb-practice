package com.practice.mongodb.primitive;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class MongoCrudDemo {

    @BeforeAll
    public static void prepare() {

    }

    @Test
    public void find() {
/*
  {
   "name" : "MongoDB",
   "type" : "database",
   "count" : 1,
   "versions": [ "v3.2", "v3.0", "v2.6" ],
   "info" : { x : 203, y : 102 }
  }
 */
        com.mongodb.client.MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoCollection<Document> collection = mongoClient.getDatabase("java_primitive").getCollection("middleware");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
        System.out.println("count: "+collection.countDocuments());
        Document first = collection.find().first();
        System.out.println("first = " + first);

        MongoCursor<Document> cursor = collection.find().iterator();
        try{
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }finally {
            cursor.close();
        }
        collection.createIndex(new Document("i", 1));


        List<Document> docs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            docs.add(new Document("i", i));
        }
        collection.insertMany(docs);

        Document eq = collection.find(Filters.eq("i", 71)).first();
        System.out.println("eq = " + eq);

        Consumer<Document> consumer= document -> System.out.println(document.toJson());

        collection.find(Filters.gt("i", 50)).forEach(consumer);

        collection.find(Filters.and(Filters.gt("i",10),Filters.lt("i",50))).forEach(consumer);

        collection.updateOne(Filters.eq("i", 10), Updates.set("i", 1000));
        UpdateResult updateResult = collection.updateMany(Filters.lt("i", 50), Updates.inc("i", 100));
        System.out.println("ModifiedCount: " +updateResult.getModifiedCount());
        collection.deleteOne(Filters.eq("i", 1));
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
        MongoClient client = new MongoClient("localhost");
        MongoDatabase database = client.getDatabase("java_primitive");
        MongoCollection<Document> collection = database.getCollection("people");
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

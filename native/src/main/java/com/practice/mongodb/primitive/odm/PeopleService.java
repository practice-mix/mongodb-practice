package com.practice.mongodb.primitive.odm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.practice.mongodb.primitive.odm.entity.Address;
import com.practice.mongodb.primitive.odm.entity.Person;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PeopleService {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Person> collection;

    /**
     * @see ConnectionString
     */
    @BeforeAll
    public static void beforeAll() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:27017/?maxPoolSize=100&waitQueueTimeoutMS=3000&maxConnecting=5"))
                .codecRegistry(codecRegistry)
                .build();

        mongoClient = MongoClients.create(clientSettings);

//        mongoClient = MongoClients.create(MongoClientSettings.builder().codecRegistry(codecRegistry)
//                .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
//                .build());

//        mongoClient = MongoClients.create("mongodb://localhost:27017/?maxPoolSize=100&waitQueueTimeoutMS=3000&maxConnecting=5");

        database = mongoClient.getDatabase("java_primitive");
        collection = database.getCollection("people", Person.class);


    }

    @Test
    public void insert() {
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        collection.insertOne(ada);

        List<Person> people = Arrays.asList(
                new Person("Charles Babbage", 45, new Address("5 Devonshire Street", "London", "W11")),
                new Person("Alan Turing", 28, new Address("Bletchley Hall", "Bletchley Park", "MK12")),
                new Person("Timothy Berners-Lee", 61, new Address("Colehill", "Wimborne", null))
        );

        collection.insertMany(people);

    }

    @Test
    public void find(){
        Consumer<? super Person> consumer = person -> {
            System.out.println("person = " + person);
        };
        collection.find().forEach(consumer);

        Person wimborne = collection.find(Filters.eq("address.city", "Wimborne")).first();
        System.out.println("wimborne = " + wimborne);

        collection.find(Filters.gt("age", 30)).forEach(consumer);

    }

    @Test
    public void update(){
        collection.updateOne(Filters.eq("name", "Ada Byron"),
                Updates.combine(Updates.set("age", 55), Updates.set("name", "Ada Lovelace")));

        UpdateResult updateResult = collection.updateMany(Filters.not(Filters.eq("zip", null)), Updates.set("zip", "blank"));
        System.out.println("updateResult.getModifiedCount() = " + updateResult.getModifiedCount());


    }

    @Test
    public void replace(){
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        collection.replaceOne(Filters.eq("name"), ada);
    }

    @Test
    public void delete() {
        collection.deleteOne(Filters.eq("address.city", "Wimborne"));
        DeleteResult deleteResult = collection.deleteMany(Filters.eq("address.city", "London"));
        System.out.println("deleteResult.getDeletedCount() = " + deleteResult.getDeletedCount());

    }
}

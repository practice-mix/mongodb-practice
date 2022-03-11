package com.practice.mongodb.primitive.odm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ZipsService {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

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

        database = mongoClient.getDatabase("test");
        collection = database.getCollection("zips");

    }

    /**
     * <pre> {@code
     * db.zips.aggregate([
     * {
     * $group: {
     * _id: "$state",
     * totalPop: {
     * $sum: "$pop"
     * }
     * }
     * }, {
     * $match: {
     * totalPop: {
     * $gte: 10 * 1000 * 1000
     * }
     * }
     * }
     *
     * ])
     * }</pre>
     */
    @Test
    public void aggregate1() {
        AggregateIterable<Document> aggregate = collection.aggregate(Arrays.asList(
                Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
                Aggregates.match(Filters.gte("totalPop", 10 * 1000 * 1000))
        ));

        for (Document document : aggregate) {
            System.out.println(document.toJson());
        }

    }
}

package com.practice.mongodb.primitive.odm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.practice.mongodb.primitive.odm.entity.Zips;
import lombok.Data;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

public class ZipsService {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Zips> collection;

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
        collection = database.getCollection("zips", Zips.class);

    }

    /*
db.zips.aggregate([
    {
        $group: {
            _id: "$state",
            totalPop: {
                $sum: "$pop"
            }
        }
    }, {
        $match: {
            totalPop: {
                $gte: 10 * 1000 * 1000
            }
        }
    }

])
     */
    @Test
    public void aggregate1() {
        AggregateIterable<Root1> aggregate = collection.aggregate(asList(
                Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
                Aggregates.match(Filters.gte("totalPop", 10 * 1000 * 1000))
        ), Root1.class);

        for (Root1 document : aggregate) {
            System.out.println(document);
        }

    }

    @Data
    public static class Root1 {
        public String _id;
        public int totalPop;
    }

    /*
     <pre>
     {@code
db.zips.aggregate([
    {
        $group: {
            _id: {
                state: "$state",
                city: "$city"
            },
            cityPop: {
                $sum: "$pop"
            }
        }
    },
    {
        $group: {
            _id: "$_id.state",
            avgPop: {
                $avg: "$cityPop"
            }
        }
    }
])
     }
     </pre>

     */
    @Test
    public void aggregate2() {
        collection.aggregate(asList(
                Aggregates.group(Document.parse("{\n" +
                        "                state: \"$state\",\n" +
                        "                city: \"$city\"\n" +
                        "            }"), Accumulators.sum("cityPop", "$pop")),
                Aggregates.group("$_id.state", Accumulators.avg("avgPop", "$cityPop"))

        ), Root2.class).forEach(root -> System.out.println(root));
    }

    @Data
    public static class Root2 {
        public String _id;
        public double avgPop;
    }


    @Test
    public void aggregate3() {
        collection.aggregate(asList(
                        Aggregates.group(Document.parse("{\n" +
                                "                state: \"$state\",\n" +
                                "                city: \"$city\"\n" +
                                "            }"), Accumulators.sum("cityPop", "$pop"))
                        , Aggregates.sort(Sorts.ascending("cityPop"))
                        , Aggregates.group("$_id.state", asList(
                                        Accumulators.last("maxCity", "$_id.city"),
                                        Accumulators.last("maxPop", "$cityPop"),
                                        Accumulators.first("minCity", "$_id.city"),
                                        Accumulators.first("minPop", "$cityPop")

                                )

                        ),
                        Aggregates.project(Document.parse("{\n" +
                                "            _id: 0,\n" +
                                "            state: \"$_id\",\n" +
                                "            max: {\n" +
                                "                city: \"$maxCity\",\n" +
                                "                pop: \"$maxPop\"\n" +
                                "            },\n" +
                                "            min: {\n" +
                                "                city: \"$minCity\",\n" +
                                "                pop: \"$minPop\"\n" +
                                "            }\n" +
                                "        }"))

                )
                , Root3.class
        ).forEach(doc -> System.out.println(doc.toString()));

    }

    @Data
    public static class Max {
        public String city;
        public int pop;
    }

    @Data
    public static class Min {
        public String city;
        public int pop;
    }

    @Data
    public static class Root3 {
        public String state;
        public Max max;
        public Min min;
    }


}

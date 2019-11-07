package db;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class MongoMain {
    // TODO: https://www.journaldev.com/3963/mongodb-java-crud-example-tutorial

    private static final String uri = "mongodb://localhost:27017/mongodb";
    private static MongoClientURI clientURI = new MongoClientURI(uri);
    private static MongoClient mongoClient = new MongoClient(clientURI);
    protected static MongoDatabase mongoDatabase = mongoClient.getDatabase("mongodb");
    private static MongoCollection collection = mongoDatabase.getCollection("test");

    public static void main(String args[]) {
        System.out.println("Database connected.");
//        addElement();
//        updateItem();
        findValue();
    }

    public static void addElement() {
        Document document = new Document("name", "Bob");
        document.append("Sex", "male");
        document.append("Age", "43");
        document.append("Race", "European");
        collection.insertOne(document);
    }

    public static void updateItem() {
        Document found = (Document) collection.find(new Document("name", "Bob")).first();
        if (found != null) {
            System.out.println("Found user.");

            Bson updatedValue = new Document("Age", 25);
            Bson updateOperation = new Document("$set", updatedValue);
            collection.updateOne(found, updateOperation);
            System.out.println("User updated");
        }
    }

    public static void findValue() {
        // will try to find person with age 25 and sort by Race
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("Age", 25)),
                        Aggregates.group("$Race", Accumulators.sum("count", 1)
                        ))
                ).forEach(printBlock);

        System.out.println("Collection Aggregated.");
    }
}

package com.db;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;


public class MongoMain {
    private static final String uri = "mongodb://localhost:27017/mongodb";
    private static MongoClientURI clientURI = new MongoClientURI(uri);
    private static MongoClient mongoClient = new MongoClient(clientURI);
    protected static MongoDatabase mongoDatabase = mongoClient.getDatabase("mongodb");
}

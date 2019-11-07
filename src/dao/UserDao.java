//package dao;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import db.MongoMain;
//import org.bson.Document;
//
//public class UserDao extends MongoMain {
////    private static final String uri = "mongodb://localhost:27017/mongodb";
////    private static MongoClientURI clientURI = new MongoClientURI(uri);
////    private static MongoClient mongoClient = new MongoClient(clientURI);
////    private static MongoDatabase mongoDatabase = mongoClient.getDatabase("mongodb");
//    private static MongoCollection collection = mongoDatabase.getCollection("user");
//
//    public void addUser(String username, String password, String email, String name, String surname) {
//        Document document = new Document("Username", username);
//        document.append("Password", password);
//        document.append("Email", email);
//        document.append("Name", name);
//        document.append("Surname", surname);
//        collection.insertOne(document);
//    }
//
//    public void login
//}

package com.dao;

import com.db.MongoMain;
import com.models.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;


public class UserDao extends MongoMain {
    private static MongoCollection<Document> collection = mongoDatabase.getCollection("users");

    public static void main(String[] args) {
        populateUsers();
    }

    public static void addUser(User user) {
        Document document = new Document("username", user.getUsername());
        document.append("uuid", user.getUuid());
        document.append("password", user.getPassword());
        document.append("email", user.getEmail());
        document.append("name", user.getName());
        document.append("surname", user.getPassword());
        document.append("active", user.isActive());
        collection.insertOne(document);
    }

    public static User login(String username, String password, ObservableList<User> users) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActive()) {
                return u;
            }
        }
        return null;
    }

    public static ObservableList<User> fetchUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        FindIterable<Document> find = collection.find();
        for (Document userObj : find) {
            User user = getUser(userObj);
            users.add(user);
        }
        return users;
    }

    private static User getUser(Document userObj) {
        String uuid = userObj.getString("uuid");
        String username = userObj.getString("username");
        String password = userObj.getString("password");
        String email = userObj.getString("email");
        String name = userObj.getString("name");
        String surname = userObj.getString("surname");
        Boolean active = userObj.getBoolean("active");
        return new User(username, password, email, name, surname, active, uuid);
    }

    public static User getUserByUuid(String uuid) {
        Document found = (Document) collection.find(new Document("uuid", uuid)).first();
        if (found != null) {
            return getUser(found);
        }
        return null;
    }

    private static void populateUsers() {
        User user1 = new User("user", "pass", "us@pas.com", "Petras", "Petravicius");
        User user2 = new User("tom", "tom", "jonas@pas.com", "Jonas", "Jonavicius");
        addUser(user1);
        addUser(user2);
    }
}

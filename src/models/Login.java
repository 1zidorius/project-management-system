package models;

import helpers.DummyData;
import javafx.collections.ObservableList;

public class Login {
    private ObservableList<User> users = DummyData.fetchUsers();

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActive()) {
                return u;
            }
        }
        return null;
    }
}

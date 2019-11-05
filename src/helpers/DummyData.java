package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Task;
import models.User;

public class DummyData {
    public static ObservableList<User> fetchUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("admin", "admin", "admin@admin.com", "admin", "admin"));
        users.add(new User("vilius", "mileris", "vilius@mileris.com", "Vilius", "Mileris"));
        return users;
    }

    public static ObservableList<Task> fetchTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        tasks.add(new Task("Android", "Fix API request", "Vilius", "", null));
        tasks.add(new Task("iOS", "Fix API request", "Vilius", "1", null));
        tasks.add(new Task("iOS", "Fix API request", "Vilius", "1", null));
        return tasks;
    }
}

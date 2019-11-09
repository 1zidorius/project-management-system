package com.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private int id;
    private static int idCounter = 1;
    private String title;
    private ArrayList<User> users = new ArrayList();
    private ArrayList<Task> tasks = new ArrayList();

    public Project(String title, User creator) {
        this.title = title;
        users.add(creator);
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addMember(User u) {
        users.add(u);

    }

    public void addTask(Task u) {
        tasks.add(u);

    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> all = new ArrayList();
        all.addAll(this.tasks);
        for (Task t : tasks) {
            all.addAll(t.getAllTasks());
        }
        return all;
    }
}

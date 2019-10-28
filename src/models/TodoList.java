package models;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TodoList implements Serializable {
    ArrayList<User> users = new ArrayList();
    ArrayList<Task> tasks = new ArrayList();
    ArrayList<Project> projects = new ArrayList();

    public static void main(String[] args) throws IOException {
        String filename = "data.tdl";
        TodoList tdl = new TodoList();
        tdl.addPerson("admin", "admin", "admin@admin.com", "admin", "admin");
        tdl.addPerson("tomas", "tomas", "tomas@tomas.com", "tomas", "tomavicius");
        saveToFile(tdl, filename);
    }

    public void addPerson(String username, String password, String email, String name, String surname) {
        if (isUsernameOccupied(username)) return;

        User newPerson = new User(username, password, email, name, surname);
        users.add(newPerson);
    }

    public void addTask(String subject, String description, User addedBy, User assignee, TaskPriority priority, Project project) {
        Task task = new Task.TaskBuilder(subject, addedBy, project)
                .description(description)
                .assignee(assignee)
                .priority(priority)
                .build();
        tasks.add(task);
    }

    public void deactivateUser(int id) {
        User current = this.getUserById(id);
        if (current != null && current.isActive()) {
            current.setActive(false);
        }
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<User> getAllActiveUsers() {
        ArrayList<User> filtered = new ArrayList();
        for (User u : users) {
            if (u.isActive()) {
                filtered.add(u);
            }
        }
        return filtered;
    }

    public boolean isUsernameOccupied(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username))
            {
                return true;
            }
        }

        return false;
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActive()) {
                return u;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User u : users) {
            if (Objects.equals(u.getUsername(), username)) {
                return u;
            }
        }
        return null;
    }

    public Project getProjectByName(String name) {
        for (Project p : projects) {
            if (Objects.equals(p.getTitle(), name)) {
                return p;
            }
        }
        return null;
    }

    public boolean updateUserInfo(int id, String username, String password, String email, String name, String surname) {
        User current = this.getUserById(id);
        if (current != null) {
            if (username != null && username.trim().length() > 0) {
                current.setUsername(username);
            }
            if (password != null && password.trim().length() > 0) {
                current.setPassword(password);
            }
            if (email != null && email.trim().length() > 0) {
                current.setEmail(email);
            }
        }
        return false;
    }

    public static void saveToFile(TodoList tdl, String filename) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        out.writeObject(tdl);
        out.close();
    }

    public static TodoList readFromFile(String filename) throws IOException {
        TodoList tdl = null;
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
        try {
            tdl = (TodoList) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        in.close();
        return tdl;
    }

    public static TaskPriority getTaskPriorityFromText(String priority) {
        TaskPriority taskPriority = null;
        String p = priority.toUpperCase();
        if (p.equals("VERYLOW")) {
            taskPriority = TaskPriority.VERYLOW;
        } else if (p.equals("LOW")) {
            taskPriority = TaskPriority.LOW;
        } else if (p.equals("MEDIUM")) {
            taskPriority = TaskPriority.MEDIUM;
        } else if (p.equals("HIGH")) {
            taskPriority = TaskPriority.HIGH;
        } else if (p.equals("CRITICAL")) {
            taskPriority = TaskPriority.CRITICAL;
        }

        return taskPriority;
    }

}

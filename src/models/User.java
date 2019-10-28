package models;

import java.io.Serializable;

public class User implements Serializable {
    private static int userCounter = 1;
    private int id;
    private String username, password, email;
    private boolean active = true;
    private String name, surname;

    User(String username, String password, String email, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = userCounter;
        this.name = name;
        this.surname = surname;
        userCounter++;
    }

    @Override
    public String toString() {
        return "User{" + "Id=" + id + ", username=" + username + ", email=" + email + ", active=" + active + "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}

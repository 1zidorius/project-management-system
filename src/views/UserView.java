package views;

import models.TodoList;
import models.User;

import java.util.ArrayList;
import java.util.Scanner;

import static helpers.DataValidation.*;
import static helpers.Helpers.isStringInt;
import static helpers.Helpers.printUpdatedItemsList;

public class UserView {
    public static void addPersonView(Scanner sc, TodoList tdl) {
        String username = validateUsername(sc, tdl);
        String password = validatePassword(sc);
        String email = validateEmail(sc);
        String name = validateName(sc);
        String surname = validateSurname(sc);
        tdl.addPerson(username, password, email, name, surname);
        System.out.println("User is created successfully.");
    }

    public static void deactivateUserView(Scanner sc, TodoList tdl) {
        System.out.println("Enter username or user ID");
        String user = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(user);
            tdl.deactivateUser(id);
        } catch (Exception e) {
            User u = tdl.getUserByUsername(user);
            tdl.deactivateUser(u.getId());
        }
    }

    public static void updateUserInfoView(Scanner sc, TodoList tdl) {
        boolean allowToEditUsername = false;
        int userId = 0;
        System.out.println("Enter username or user ID. Write q to quit.");
        boolean quit = false;
        User user = null;
        while (!quit) {
            String input = sc.nextLine().trim();
            if (input.equals("q")) {
                quit = true;
            } else if (isStringInt(input)) {
                userId = Integer.parseInt(input);
                user = tdl.getUserById(userId);
                if (user == null) {
                    System.out.println("Such user doesn't exist. Try again.");
                } else {
                    allowToEditUsername = true;
                    quit = true;
                }
            } else {
                user = tdl.getUserByUsername(input);
                if (user == null) {
                    System.out.println("Such user doesn't exist. Try again.");
                } else {
                    quit = true;
                }
            }
        }
        ArrayList<String> updatedItems = new ArrayList();
        String username = "";
        if (allowToEditUsername) {
            System.out.println("Do you want to update your username? Write YES or press ENTER to skip.");
            username = sc.nextLine().trim();
            if (!username.equals("")) {
                username = validateUsername(sc, tdl);
                updatedItems.add("Username");
            }
        }

        System.out.println("Do you want to update your password? Write YES or press ENTER to skip.");
        String password = sc.nextLine().trim();
        if (!password.equals("")) {
            password = validatePassword(sc);
            updatedItems.add("Password");
        }

        System.out.println("Do you want to update your email? Write YES or press ENTER to skip.");
        String email = sc.nextLine().trim();
        if (!email.equals("")) {
            email = validateEmail(sc);
            updatedItems.add("Email");
        }

        System.out.println("Do you want to update your name? Write YES or press ENTER to skip.");
        String name = sc.nextLine().trim();
        if (!name.equals("")) {
            name = validateName(sc);
            updatedItems.add("Name");
        }

        System.out.println("Do you want to update your surname? Write YES or press ENTER to skip.");
        String surname = sc.nextLine().trim();
        if (!surname.equals("")) {
            surname = validateSurname(sc);
            updatedItems.add("Surname");
        }

        if (user != null) {
            userId = user.getId();
        }

        tdl.updateUserInfo(userId, username, password, email, name, surname);
        if (updatedItems.size() > 0) {
            printUpdatedItemsList(updatedItems);
        }
    }

    public static void printUsersView(TodoList tdl) {
        ArrayList<User> allUsers = tdl.getAllUsers();
        for (User v : allUsers) {
            System.out.println(v);
        }
    }

    public static void printActiveUsersView(TodoList list) {
        ArrayList<User> allActive = list.getAllActiveUsers();
        for (User v : allActive) {
            System.out.println(v);
        }
    }

    public static User loginView(Scanner sc, TodoList tdl) {
        int attempts = 3;
        for (int i = 0; i < attempts; i++) {
            System.out.println("Username:");
            String username = sc.nextLine();
            System.out.println("Password:");
            String password = sc.nextLine();
            User user = tdl.login(username, password);
            if (user != null) {
                return user;
            } else if (attempts != i + 1) {
                System.out.println("Username or password is incorrect, try again. Attempts left - " + (attempts - i - 1));
            } else {
                System.out.println("Your credentials are incorrect. Contact to workspace admin.");
            }
        }
        return null;
    }
}

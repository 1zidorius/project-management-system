package menu;

import models.TodoList;
import models.User;

import java.io.IOException;
import java.util.Scanner;

import static models.TodoList.*;
import static views.TaskView.addTaskView;
import static views.TaskView.printAllTasksView;
import static views.UserView.*;


public class Menu {
    private static String[] unauthorizedMenu = new String[]{"Log in", "Register", "Exit"};
    private static String[] personMenu = new String[]{"Add person", "Deactivate user", "Update user info", "Print all users", "Print active users", "Open task menu", "Exit"};
    private static String[] taskMenu = new String[]{"Add task", "Show task details", "Print all tasks", "Update task details", "Delete task", "Back", "Exit"};

    public void showMenu(TodoList tdl, String dataFile) {
        unauthorizedMenu(tdl, dataFile);
    }

    private void unauthorizedMenu(TodoList tdl, String dataFile) {
        Scanner keyboardInput = new Scanner(System.in);
        boolean running = true;
        while (running) {
            try {
                System.out.println("Select option from the list:");
                showMenuOptions(unauthorizedMenu);
                String option = keyboardInput.nextLine();
                switch (option) {
                    case ("1"):
                        loginForm(tdl, dataFile);
                        running = false;
                        break;
                    case ("2"):
                        addPersonView(keyboardInput, tdl);
                        break;
                    case ("3"):
                        System.out.println("Goodbye.");
                        running = false;
                        break;
                    default:
                        System.out.println("Selection out of range. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Selection out of range. Try again.");
            }
        }
    }

    private static void showMenuOptions(String[] menu) {
        for (int i = 0; i < menu.length; i++) {
            System.out.println(i + 1 + ". " + menu[i]);
        }
    }

    private static void loginForm(TodoList tdl, String dataFile) {
        Scanner keyboardInput = new Scanner(System.in);
        User authorized = loginView(keyboardInput, tdl);
        if (authorized != null && authorized.getUsername().equals("admin")) {
            adminMenu(tdl, dataFile);
        } else if (authorized != null) {
            authorizedMenu(tdl, dataFile);
        }
    }

    private static void adminMenu(TodoList tdl, String dataFile) {
        Scanner keyboardInput = new Scanner(System.in);
        boolean running = true;
        while (running) {
            try {
                showMenuOptions(personMenu);
                String option = keyboardInput.nextLine();
                switch (option) {
                    case ("1"):
                        addPersonView(keyboardInput, tdl);
                        break;
                    case ("2"):
                        deactivateUserView(keyboardInput, tdl);
                        break;
                    case ("3"):
                        updateUserInfoView(keyboardInput, tdl);
                        break;
                    case ("4"):
                        printUsersView(tdl);
                        break;
                    case ("5"):
                        printActiveUsersView(tdl);
                        break;
                    case ("6"):
                        System.out.println("Open task menu");
                        running = false;
                        authorizedMenu(tdl, dataFile);
                    case ("7"):
                        saveToFile(tdl, dataFile);
                        running = false;
                        break;
                    default:
                        System.out.println("Selection out of range. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Selection out of range. Try again:");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void authorizedMenu(TodoList tdl, String dataFile) {
        Scanner keyboardInput = new Scanner(System.in);
        boolean running = true;
        while (running) {
            try {
                showMenuOptions(taskMenu);
                String option = keyboardInput.nextLine();
                switch (option) {
                    case ("1"):
                        addTaskView(keyboardInput, tdl);
                        System.out.println("addTask was selected.");
                        break;
                    case ("2"):
//                        showTaskDetails(keyboardInput, tdl);
                        System.out.println("showTaskDetails was selected.");
                        break;
                    case ("3"):
                        printAllTasksView(tdl);
                        System.out.println("printAllTasks was selected.");
                        break;
                    case ("4"):
//                        updateTaskDetails(tdl);
                        System.out.println("Update task details");
                        break;
                    case ("5"):
//                        deleteTask(tdl);
                        System.out.println("delete task was selected");
                        break;
                    case ("6"):
//                        backToMain(tdl);
                        System.out.println("back to main");
                        running = false;
                        adminMenu(tdl, dataFile);
                        break;
                    case ("7"):
                        saveToFile(tdl, dataFile);
//                        System.out.println("Goodbye.");
                        running = false;
                        break;
                    default:
                        System.out.println("Selection out of range. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Selection out of range. Try again:");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

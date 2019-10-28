package helpers;

import models.Project;
import models.TaskPriority;
import models.TodoList;

import java.util.Scanner;

import static models.TodoList.getTaskPriorityFromText;

public class DataValidation {

    public static String validateUsername(Scanner sc, TodoList tdl) {
        String username = "";
        boolean validData = false;
        while (!validData) {
            System.out.println("Username:");
            username = sc.nextLine();
            validData = isUsernameValid(tdl, username);
        }
        return username;
    }

    public static String validatePassword(Scanner sc) {
        String password = "";
        int passwordLength = 5;
        boolean validData = false;
        while (!validData) {
            System.out.println("Password:");
            password = sc.nextLine();
            validData = Helpers.isTextValid(password, passwordLength);
            if (!validData) {
                System.out.printf("Password cannot be less than %d characters. Try again.\n", passwordLength);
            }
        }
        return password;
    }

    public static String validateEmail(Scanner sc) {
        String email = "";
        int emailLength = 5;
        boolean validData = false;
        while (!validData) {
            System.out.println("Email:");
            email = sc.nextLine().trim();
            validData = Helpers.isTextValid(email, emailLength) && email.contains("@");
            if (!validData) {
                System.out.printf("Email must contain '@' and be more than %d character length. Try again.\n", emailLength);
            }
        }
        return email;
    }

    public static String validateName(Scanner sc) {
        String name = "";
        int nameLength = 1;
        boolean validData = false;
        while (!validData) {
            System.out.println("Name:");
            name = sc.nextLine().trim();
            validData = Helpers.isTextValid(name, nameLength);
            if (!validData) {
                System.out.printf("Name cannot bet less than %d character length. Try again.\n", nameLength);
            }
        }
        return name;
    }

    public static String validateSurname(Scanner sc) {
        String surname = "";
        int surnameLength = 1;
        boolean validData = false;
        while (!validData) {
            System.out.println("Surname:");
            surname = sc.nextLine().trim();
            validData = Helpers.isTextValid(surname, surnameLength);
            if (!validData) {
                System.out.printf("Surname cannot bet less than %d character length. Try again.\n", surnameLength);
            }
        }
        return surname;
    }

    private static boolean isUsernameValid(TodoList list, String username) {
        int usernameLength = 3;
        if (!Helpers.isTextValid(username, usernameLength)) {
            System.out.printf("Username cannot be less than %d characters length.\n", usernameLength);
            return false;
        }
        if (list.isUsernameOccupied(username)) {
            System.out.println("Username already exists. Try again.");
            return false;
        }
        return true;
    }

    public static String validateText(Scanner sc, String slug, int maxLength, boolean required) {
        String text = "";
        boolean validData = false;
        while (!validData) {
            System.out.println(slug);
            text = sc.nextLine();
            if (text.length() < maxLength && required) {
                validData = true;
            } else if (text.length() < maxLength && !required) {
                validData = true;
            } else if (text.length() == 0 && !required) {
                validData = true;
            } else {
                System.out.println("Text is not valid. Try again.");
            }
        }
        return text;
    }

    public static TaskPriority validateTaskPriority(Scanner sc) {
        TaskPriority taskPriority = null;
        String text = "";
        boolean validData = false;
        while (!validData) {
            System.out.println("Task priority: ");
            text = sc.nextLine();
            taskPriority = getTaskPriorityFromText(text);
            if (taskPriority != null) {
                validData = true;
            }
        }
        return taskPriority;
    }

    public static Project validateProjectByName(Scanner sc, TodoList tdl) {
        String text = "";
        Project project = null;
        boolean validData = false;
        while (!validData) {
            System.out.println("Project name:");
            text = sc.nextLine();
            project = tdl.getProjectByName(text);
            if (project != null) {
                validData = true;
            }
        }
        return project;
    }
}

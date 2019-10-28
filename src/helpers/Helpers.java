package helpers;

import models.TodoList;
import models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Helpers {
    private ArrayList<User> users = new ArrayList();

    public static boolean isTextValid(String text, int length) {
        if (text.length() < length) {
            return false;
        }
        return true;
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void printUpdatedItemsList(ArrayList<String> updatedItems) {
        System.out.print("You have successfully updated - ");
        for (String updatedItem : updatedItems) {
            System.out.print(updatedItem + " ");
        }
        System.out.println();
    }

    public static User getUserByIdOrUsername(Scanner sc, TodoList tdl) {
        User user = null;
        System.out.println("Enter username or user ID:");
        String input = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            user = tdl.getUserById(id);
        } catch (Exception e) {
            user = tdl.getUserByUsername(input);
        } finally {
            return user;
        }
    }

}

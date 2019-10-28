package views;

import models.*;

import java.util.ArrayList;
import java.util.Scanner;

import static helpers.DataValidation.*;
import static helpers.Helpers.getUserByIdOrUsername;

public class TaskView {
    public static void addTaskView(Scanner sc, TodoList tdl) {
        String subject = validateText(sc, "Subject: ", 120, true);
        String description = validateText(sc, "Description: ", 500, false);
        System.out.println("Enter assignee by - ");
        User assignee = getUserByIdOrUsername(sc, tdl);
        TaskPriority priority = validateTaskPriority(sc);
        Project project = validateProjectByName(sc, tdl);
        User addedBy = null;
        tdl.addTask(subject, description, null, assignee, priority, project);
    }

    public static void printAllTasksView(TodoList tdl) {
        ArrayList<Task> tasks = tdl.getAllTasks();
        for (Task t : tasks) {
            System.out.println(t);
        }
    }
}

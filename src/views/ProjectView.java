package views;

import models.TodoList;

import java.util.Scanner;

import static helpers.DataValidation.validateText;

public class ProjectView {
    public static void addProject(Scanner sc, TodoList tdl) {
        String title = validateText(sc, "Project name:", 120, true);
    }
}

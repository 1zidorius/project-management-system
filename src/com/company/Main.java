package com.company;

import menu.Menu;
import models.TodoList;

import java.io.IOException;

public class Main {
    private final static String dataFile = "data.tdl";

    public static void main(String[] args) throws IOException {
        TodoList tdl = null;
        Menu menuController = new Menu();
        tdl = TodoList.readFromFile(dataFile);
        menuController.showMenu(tdl, dataFile);
    }
}

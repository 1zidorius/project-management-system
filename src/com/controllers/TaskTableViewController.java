package com.controllers;

import com.models.Task;
import com.models.User;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dao.TaskDao.*;


public class TaskTableViewController {
    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, String> projectColumn;

    @FXML
    private TableColumn<Task, String> subjectColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, String> assigneeColumn;

    @FXML
    private TableColumn<Task, String> addedByColumn;

    @FXML
    private ComboBox<String> projectComboBox;

    @FXML
    private TextField subjectTextField;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<User> assigneeComboBox;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private Button getMoreDetailsButton;

    private ObservableList<String> projects = FXCollections.observableArrayList("Android", "iOS", "Windows");
    private ObservableList<String> priorities = FXCollections.observableArrayList("1", "2", "3");
    private ObservableList<User> users;
    private ObservableList<Task> tasks = fetchTasks();
    private User authorizedUser;

    @FXML
    void initialize() {
        addTaskButton.disableProperty().bind(
                projectComboBox.valueProperty().isNull().and(subjectTextField.textProperty().isEmpty())
        );
        deleteTaskButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        getMoreDetailsButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));


    }

    public void setTable() {
        projectColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("project"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("subject"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
        assigneeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("assignee"));
        addedByColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("addedBy"));

        tableView.setItems(tasks);
        projectComboBox.setItems(projects);
        priorityComboBox.setItems(priorities);
        assigneeComboBox.setItems(users);
        tableView.setEditable(true);
        subjectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    void initData(User user, ObservableList<User> users) {
        this.authorizedUser = user;
        this.users = users;
        setTable();
    }

    public void handleAddNewTaskButtonAction() {
        String project = projectComboBox.getValue();
        String subject = subjectTextField.getText();
        String priority = priorityComboBox.getValue();
        User assignee = assigneeComboBox.getValue();
        Task newTask = new Task(project, subject, authorizedUser, priority, assignee);
        tableView.getItems().add(newTask);
        addTask(newTask);
        clearEnteredValues();
    }

    public void handleDeleteTaskButtonAction() {
        String contentText = "Are you sure you want to delete the task?";
        Alert alert = new Alert(Alert.AlertType.WARNING, contentText, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            ObservableList<Task> selectedRows, allTasks;
            allTasks = tableView.getItems();
            selectedRows = tableView.getSelectionModel().getSelectedItems();
            for (Task task : selectedRows) {
                allTasks.remove(task);
                deleteTask(task);
            }
        }

    }

    private void clearEnteredValues() {
        projectComboBox.getSelectionModel().clearSelection();
        subjectTextField.setText("");
        priorityComboBox.getSelectionModel().clearSelection();
        assigneeComboBox.getSelectionModel().clearSelection();
    }

    public void handleChangeSceneToDetailedTaskViewButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/TaskView.fxml"));
        changeScene(actionEvent, loader);
    }

    public void changeSubjectCellEvent(TableColumn.CellEditEvent editedCell) {
        Task taskSelected = tableView.getSelectionModel().getSelectedItem();
        taskSelected.setSubject(editedCell.getNewValue().toString());
        updateTask(taskSelected);
    }

    public void handleCloseMenuAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void handleLogOutMenuAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/LoginView.fxml"));
        changeScene(actionEvent, loader);
    }

    private void changeScene(ActionEvent actionEvent, FXMLLoader loader) throws IOException {
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        TaskViewController controller = loader.getController();
        controller.initData(tableView.getSelectionModel().getSelectedItem());
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}

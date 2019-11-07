package controllers;

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
import models.Task;
import models.User;

import java.io.IOException;

import static helpers.DummyData.fetchTasks;
import static helpers.DummyData.fetchUsers;

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

    ObservableList<String> projects = FXCollections.observableArrayList("Android", "iOS", "Windows");
    ObservableList<String> priorities = FXCollections.observableArrayList("1", "2", "3");

    ObservableList<User> users = fetchUsers();
    private User authorizedUser;

    @FXML
    void initialize() {
        setTable();
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

        tableView.setItems(fetchTasks());
        projectComboBox.setItems(projects);
        priorityComboBox.setItems(priorities);
        assigneeComboBox.setItems(users);

        tableView.setEditable(true);
        subjectColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    void initData(User user) {
        this.authorizedUser = user;
    }

    public void handleAddNewTaskButtonAction() {
        Task newTask = new Task(
                projectComboBox.getValue(),
                subjectTextField.getText(),
                authorizedUser.getFullName(),
                priorityComboBox.getValue(),
                assigneeComboBox.getValue()
        );
        tableView.getItems().add(newTask);
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
        taskSelected.setProject(editedCell.getNewValue().toString());
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

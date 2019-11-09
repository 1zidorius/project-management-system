package com.controllers;

import com.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskViewController {
    @FXML
    private Label projectLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label assigneeLabel;

    @FXML
    private Label addedBy;

    @FXML
    private Label updatedOn;

    @FXML
    private Label createdOn;

    private Task selectedTask;

    public void initData(Task task) {
        selectedTask = task;
        projectLabel.setText(selectedTask.getProject());
        subjectLabel.setText(selectedTask.getSubject());
        statusLabel.setText(selectedTask.getStatus());
        priorityLabel.setText(selectedTask.getPriority());
        assigneeLabel.setText(String.valueOf(selectedTask.getAssignee()));
        addedBy.setText(selectedTask.getAddedBy().getFullName());
        createdOn.setText(String.valueOf(selectedTask.getCreatedOn()));
        updatedOn.setText(String.valueOf(selectedTask.getUpdatedOn()));
    }

    public void handleBackButtonAction(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../views/TaskTableView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}

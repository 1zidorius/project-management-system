package com.controllers;

import com.models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dao.UserDao.fetchUsers;
import static com.dao.UserDao.login;


public class LoginViewController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessageLabel;

    private ObservableList<User> users = fetchUsers();

    @FXML
    void initialize() {
        errorMessageLabel.setText("");
        loginButton.disableProperty().bind(
                usernameTextField.textProperty().isEmpty().and(passwordTextField.textProperty().isEmpty())
        );
    }

    public void handleLoginButtonAction(ActionEvent actionEvent) throws IOException {

        User user = login(usernameTextField.getText(), passwordTextField.getText(), users);
        if (user != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/TaskTableView.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);
            TaskTableViewController controller = loader.<TaskTableViewController>getController();
            controller.initData(user, users);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Tasks table view");
            window.setScene(tableViewScene);
            window.show();
        } else {
            errorMessageLabel.setText("Username or password is incorrect.");
        }
    }
}

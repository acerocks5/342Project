package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_sign_up;
    @FXML
    private Button button_login;
    @FXML
    private RadioButton rb_administrator;
    @FXML
    private RadioButton rb_client;
    @FXML
    private RadioButton rb_instructor;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_client.setToggleGroup(toggleGroup);
        rb_administrator.setToggleGroup(toggleGroup);
        rb_instructor.setToggleGroup(toggleGroup);
        rb_client.setSelected(true);

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String role = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(actionEvent, tf_username.getText(), tf_password.getText(), role);
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up");
                    alert.show();
                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "log-in.fxml", "Log in", null, null);
            }
        });
    }
}

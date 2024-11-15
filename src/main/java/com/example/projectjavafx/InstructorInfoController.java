package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Instructor;
import model.Offering;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstructorInfoController implements Initializable {
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_phoneNumber;
    @FXML
    private TextField tf_specialization;
    @FXML
    private TextField tf_availability;
    @FXML
    private Label label_user;
    @FXML
    private Button button_addInfo;
    String username = null;

    String query= null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Instructor instructor = null;
    private boolean update;
    int instructorId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        button_addInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                addInfo();
            }
        });
    }

    private void addInfo(){
        connection = DBUtils.getConnection();
        String name = tf_name.getText();
        String phoneNumber = tf_phoneNumber.getText();
        String specialization = tf_specialization.getText();
        String availability = tf_availability.getText();
        System.out.println(username);
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            String user = label_user.getText();
            System.out.println(user);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM instructors WHERE username = '"+user+"'");
            resultSet = psCheckUserExists.executeQuery();
            if (name.isEmpty() || phoneNumber.isEmpty() || specialization.isEmpty() || availability.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all data");
                alert.showAndWait();
            } else if (resultSet.isBeforeFirst()) {
                System.out.println("instructor info already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot add info twice");
                alert.show();
            } else {
                getQuery();
                insert();
                clean();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void clean(){
        tf_name.setText(null);
        tf_availability.setText(null);
        tf_phoneNumber.setText(null);
        tf_specialization.setText(null);
    }

    private void getQuery(){
        if(update == false){
            query = "INSERT INTO `instructors`(`name`, `phoneNumber`, `specialization`, `availability`, `username`) VALUES (?,?,?,?,?)";
        } else {
            query = "UPDATE `instructors` SET "
                    + "`name`=?"
                    + "`phoneNumber`=?"
                    + "`specialization`=?"
                    + "`availability`=?"
                    + "`username`=? WHERE id ='"+instructorId+"'";
        }
    }

    private void insert(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tf_name.getText());
            preparedStatement.setString(2, tf_phoneNumber.getText());
            preparedStatement.setString(3, tf_specialization.getText());
            preparedStatement.setString(4, tf_availability.getText());
            preparedStatement.setString(5, username);
            preparedStatement.execute();
        } catch (SQLException e){
            Logger.getLogger(InstructorInfoController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void setUpdate(boolean update){
        this.update = update;
    }

    public void setUserInformation(String username){
        label_user.setText(username);
    }

}

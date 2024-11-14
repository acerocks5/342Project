package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Offering;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddOfferingController implements Initializable {

    @FXML
    private Button button_addOffering;
    @FXML
    private TextField tf_lessonType;
    @FXML
    private TextField tf_instructor;
    @FXML
    private DatePicker date_startDate;
    @FXML
    private DatePicker date_endDate;
    @FXML
    private TextField tf_startTime;
    @FXML
    private TextField tf_endTime;
    @FXML
    private TextField tf_dayOfWeek;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_location;
    @FXML
    private TextField tf_duration;
    @FXML
    private TextField tf_privacy;
    @FXML
    private TextField tf_availability;

    String query= null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Offering offering = null;
    private boolean update;
    int offeringId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        button_addOffering.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addOffering();
            }
        });
    }

    private void addOffering(){
        connection = DBUtils.getConnection();
        LocalDate startDate = date_startDate.getValue();
        LocalDate endDate= date_endDate.getValue();
        String startTime = tf_startTime.getText();
        String endTime = tf_endTime.getText();
        String dayOfWeek = tf_dayOfWeek.getText();
        String lessonType = tf_lessonType.getText();
        String duration = tf_duration.getText();
        String lessonPrivacy = tf_privacy.getText();
        String city = tf_city.getText();
        String locationType = tf_location.getText();
        String availability = tf_availability.getText();
        String instructor= tf_instructor.getText();

        if(startTime.isEmpty() || endTime.isEmpty() || dayOfWeek.isEmpty() || lessonType.isEmpty() || duration.isEmpty() || lessonPrivacy.isEmpty() || city.isEmpty() || locationType.isEmpty() || availability.isEmpty() || instructor.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all data");
            alert.showAndWait();
        } else {
            getQuery();
            insert();
            clean();
        }
    }

    private void clean(){
        date_startDate.setValue(null);
        date_endDate.setValue(null);
        tf_startTime.setText(null);
        tf_endTime.setText(null);
        tf_dayOfWeek.setText(null);
        tf_lessonType.setText(null);
        tf_duration.setText(null);
        tf_privacy.setText(null);
        tf_city.setText(null);
        tf_location.setText(null);
        tf_availability.setText(null);
        tf_instructor.setText(null);
    }

    private void getQuery(){
        if(update == false){
            query = "Insert INTO `offering`( `startDate`, `endDate`, `startTime`, `endTime`, `dayOfWeek`, `lessonType`, `lessonDuration`, `city`, `locationType`, `status`, `lessonPrivacy`, `instructor`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        } else {
            query = "UPDATE `offering` SET "
                    + "`startDate`=?"
                    + "`endDate`=?"
                    + "`startTime`=?"
                    + "`endTime`=?"
                    + "`dayOfWeek`=?"
                    + "`lessonType`=?"
                    + "`lessonDuration`=?"
                    + "`city`=?"
                    + "`locationType`=?"
                    + "`status`=?"
                    + "`lessonPrivacy`=?"
                    + "`instructor`=? WHERE id ='"+offeringId+"'";
        }
    }

    private void insert() {
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date_startDate.getValue()));
            preparedStatement.setDate(2, Date.valueOf(date_endDate.getValue()));
            preparedStatement.setString(3, tf_startTime.getText());
            preparedStatement.setString(4, tf_endTime.getText());
            preparedStatement.setString(5, tf_dayOfWeek.getText());
            preparedStatement.setString(6, tf_lessonType.getText());
            preparedStatement.setString(7, tf_duration.getText());
            preparedStatement.setString(8, tf_city.getText());
            preparedStatement.setString(9, tf_location.getText());
            preparedStatement.setString(10, tf_availability.getText());
            preparedStatement.setString(11, tf_privacy.getText());
            preparedStatement.setString(12, tf_instructor.getText());
            preparedStatement.execute();
        } catch (SQLException e){
            Logger.getLogger(AddOfferingController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setUpdate(boolean update){
        this.update = update;
    }

    public void setTextField(int id, LocalDate startDate, LocalDate endDate, String startTime, String endTime, String dayOfWeek, String lessonType, String duration, String city, String location, String availability, String privacy, String instructor ){
        offeringId = id;
        date_startDate.setValue(startDate);
        date_endDate.setValue(endDate);
        tf_startTime.setText(startTime);
        tf_endTime.setText(endTime);
        tf_dayOfWeek.setText(dayOfWeek);
        tf_lessonType.setText(lessonType);
        tf_duration.setText(duration);
        tf_city.setText(city);
        tf_location.setText(location);
        tf_availability.setText(availability);
        tf_privacy.setText(privacy);
        tf_instructor.setText(instructor);
    }
}

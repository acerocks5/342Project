package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Offering;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    @FXML
    private Button button_login;

    @FXML
    private TableView<Offering> offeringTable;
    @FXML
    private TableColumn<Offering, String> lesson_col;
    @FXML
    private TableColumn<Offering, String> instructor_col;
    @FXML
    private TableColumn<Offering, String> startDate_col;
    @FXML
    private TableColumn<Offering, String> endDate_col;
    @FXML
    private TableColumn<Offering, String> startTime_col;
    @FXML
    private TableColumn<Offering, String> endTime_col;
    @FXML
    private TableColumn<Offering, String> dayOfWeek_col;
    @FXML
    private TableColumn<Offering, String> city_col;
    @FXML
    private TableColumn<Offering, String> location_col;
    @FXML
    private TableColumn<Offering, String> timeSlot_col;
    @FXML
    private TableColumn<Offering, String> privacy_col;
    @FXML
    private TableColumn<Offering, String> availability_col;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Offering offering = null;

    ObservableList<Offering> OfferingList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTable();
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "log-in.fxml", "Login", null, null);
            }
        });

    }

    public void refreshTable(){
        try{
            OfferingList.clear();

            query = "SELECT * FROM `offering`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfferingList.add(new Offering(
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getString("startTime"),
                        resultSet.getString("endTime"),
                        resultSet.getString("dayOfWeek"),
                        resultSet.getString("lessonType"),
                        resultSet.getString("lessonDuration"),
                        resultSet.getString("lessonPrivacy"),
                        resultSet.getString("city"),
                        resultSet.getString("locationType"),
                        resultSet.getString("status"),
                        resultSet.getString("instructor")
                ));
                offeringTable.setItems(OfferingList);
            }
        }catch(SQLException e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadTable() {
        connection = DBUtils.getConnection();
        refreshTable();

        lesson_col.setCellValueFactory(new PropertyValueFactory<>("lessonType"));
        instructor_col.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        startDate_col.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate_col.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        startTime_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        dayOfWeek_col.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        location_col.setCellValueFactory(new PropertyValueFactory<>("locationType"));
        timeSlot_col.setCellValueFactory(new PropertyValueFactory<>("duration"));
        privacy_col.setCellValueFactory(new PropertyValueFactory<>("lessonPrivacy"));
        availability_col.setCellValueFactory(new PropertyValueFactory<>("availability"));


    }


    public void setUserInformation(String username, String role){

    }
}
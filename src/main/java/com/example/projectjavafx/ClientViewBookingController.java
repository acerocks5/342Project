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
import model.Booking;
import model.Offering;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientViewBookingController implements Initializable {
    @FXML
    private Button button_refresh;
    @FXML
    private Label label_user;
    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, String> lesson_col;
    @FXML
    private TableColumn<Booking, String> instructor_col;
    @FXML
    private TableColumn<Booking, String> startDate_col;
    @FXML
    private TableColumn<Booking, String> endDate_col;
    @FXML
    private TableColumn<Booking, String> startTime_col;
    @FXML
    private TableColumn<Booking, String> endTime_col;
    @FXML
    private TableColumn<Booking, String> dayOfWeek_col;
    @FXML
    private TableColumn<Booking, String> city_col;
    @FXML
    private TableColumn<Booking, String> location_col;
    @FXML
    private TableColumn<Booking, String> timeSlot_col;
    @FXML
    private TableColumn<Booking, String> privacy_col;
    @FXML
    private TableColumn<Booking, String> availability_col;
    @FXML
    private TableColumn<Booking, String> client_col;
    @FXML
    private TableColumn<Booking, String> id_col;
    private String username;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Offering offering = null;

    ObservableList<Booking> BookingList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTable();
        button_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshTable();
            }
        });

    }
    public void refreshTable(){
        try{
            setUsername(label_user.getText());
            System.out.println(username);
            BookingList.clear();
            query = "SELECT * FROM `booking` WHERE user ='"+username+"'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                BookingList.add(new Booking(
                        resultSet.getString("idbooking"),
                        resultSet.getString("startDate"),
                        resultSet.getString("endDate"),
                        resultSet.getString("startTime"),
                        resultSet.getString("endTime"),
                        resultSet.getString("dayOfWeek"),
                        resultSet.getString("lessonType"),
                        resultSet.getString("lessonDuration"),
                        resultSet.getString("lessonPrivacy"),
                        resultSet.getString("city"),
                        resultSet.getString("locationType"),
                        resultSet.getString("status"),
                        resultSet.getString("instructor"),
                        resultSet.getString("client"),
                        resultSet.getString("user")
                ));
                bookingTable.setItems(BookingList);
            }
        }catch (SQLException e){
            Logger.getLogger(ClientViewBookingController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadTable(){
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
        client_col.setCellValueFactory(new PropertyValueFactory<>("client"));
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

    }



    public void setUserInformation(String username){
        label_user.setText(username);
    }

    public void setUsername(String username){
        this.username = username;
    }
}

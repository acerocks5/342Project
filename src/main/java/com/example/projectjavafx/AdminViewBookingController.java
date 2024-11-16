package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Booking;
import model.Offering;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class AdminViewBookingController implements Initializable {
    @FXML
    private Button button_refresh;
    @FXML
    private Button button_delete;
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
    String queryInstructor = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    Offering offering = null;
    String name = null;

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
        button_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteData();
            }
        });

    }
    public void refreshTable(){
        try{

            BookingList.clear();
            query = "SELECT * FROM `booking`";
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

    private void deleteData(){
        TableView.TableViewSelectionModel<Booking> selectionModel = bookingTable.getSelectionModel();
        if(selectionModel.isEmpty()){
            System.out.println("You need to select offering before deleting");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select offering before deleting");
            alert.showAndWait();
        }
        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);

        for(int i = selectedIndices.length -1; i >= 0; i--){
            selectionModel.clearSelection(selectedIndices[i].intValue());
            try{
                int id = selectedIndices[i].intValue()+1;
                int bookId = parseInt(id_col.getCellData(id-1));
                query = "DELETE FROM `booking` WHERE idbooking  ="+bookId;
                connection = DBUtils.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                refreshTable();

            } catch (SQLException ex) {
                Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

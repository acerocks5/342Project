package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Offering;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class ClientPageController implements Initializable {
    @FXML
    private Button button_logout;
    @FXML
    private Button button_refresh;
    @FXML
    private Button button_bookOffering;
    @FXML
    private Button button_viewBookings;
    @FXML
    private Button button_info;
    @FXML
    private Label label_user;
    @FXML
    private ChoiceBox<String> choiceBox_choose;

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
    @FXML
    private TableColumn<Offering, String> id_col;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String lessonType;
    private String duration;
    private String lessonPrivacy;
    private String city;
    private String locationType;
    private String availability;
    private String instructor;


    private String username = null;
    String query = null;
    String query2 = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    Offering offering = null;

    ObservableList<Offering> OfferingList = FXCollections.observableArrayList();
    ObservableList<String> ClientList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTable();


        button_info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               setUsername(label_user.getText());
               getClientInfoView(username);
            }
        });

        button_bookOffering.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bookOffering();
            }
        });

        button_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshTable();
                selectBox();
            }
        });

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "main-view.fxml", "Main Page", null, null);
            }
        });

        button_viewBookings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getClientViewBookings();
            }
        });

    }

    public void refreshTable(){
        try{
            setUsername(label_user.getText());
            OfferingList.clear();

            query = "SELECT * FROM `offering` WHERE instructor <> 'none'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfferingList.add(new Offering(
                        resultSet.getString("idoffering"),
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
        id_col.setCellValueFactory((new PropertyValueFactory<>("id")));


    }

    private void getClientInfoView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("client-info.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            ClientInfoController clientInfoController = loader.getController();
            clientInfoController.setUserInformation(username);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e){
            Logger.getLogger(ClientPageController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void selectBox(){
        refreshTable();
        connection = DBUtils.getConnection();
        System.out.println(label_user.getText());
        System.out.println(username);

        try{
            query = "SELECT name FROM `clients` WHERE username ='"+username+"'";
            query2 = "SELECT name FROM `children` WHERE parent='"+username+"'";
            preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            resultSet2 = preparedStatement2.executeQuery();

            while(resultSet.next()){
                String name = resultSet.getString("name");
                ClientList.add(name);
            }
            while(resultSet2.next()){
                String name = resultSet2.getString("name");
                ClientList.add(name);
            }
            choiceBox_choose.setItems(ClientList);
        } catch (SQLException e){
            Logger.getLogger(ClientPageController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void bookOffering(){
        connection = DBUtils.getConnection();


        TableView.TableViewSelectionModel<Offering> selectionModel = offeringTable.getSelectionModel();
        if(selectionModel.isEmpty()){
            System.out.println("You need to select offering before booking");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select offering before booking");
            alert.showAndWait();
        }
        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);



        for(int i = selectedIndices.length -1; i >= 0; i--){
            selectionModel.clearSelection(selectedIndices[i].intValue());

                int id = selectedIndices[i].intValue();
                startDate =  startDate_col.getCellData(id);
                endDate= endDate_col.getCellData(id);
                startTime = startTime_col.getCellData(id);
                endTime = endTime_col.getCellData(id);
                dayOfWeek = dayOfWeek_col.getCellData(id);
                lessonType = lesson_col.getCellData(id);
                duration = timeSlot_col.getCellData(id);
                lessonPrivacy = privacy_col.getCellData(id);
                city = city_col.getCellData(id);
                locationType = location_col.getCellData(id);
                availability = availability_col.getCellData(id);
                instructor= instructor_col.getCellData(id);
                System.out.println(startDate + endDate + startTime + endTime + dayOfWeek + lessonType + duration + lessonPrivacy + city + locationType + availability + instructor);
                try{
                    int offerId = parseInt(id_col.getCellData(id));
                    query = "UPDATE 342Project.offering SET status = 'not available' WHERE idoffering="+offerId;
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.execute();
                }catch (SQLException e){
                    Logger.getLogger(ClientPageController.class.getName()).log(Level.SEVERE, null, e);
                }

                getQuery();
                insert();
                clean();
                refreshTable();


                System.out.println(lesson_col.getCellData(id));
                refreshTable();


        }
    }
    private void getQuery(){
        query = "Insert INTO `booking`(`startDate`, `endDate`, `startTime`, `endTime`, `dayOfWeek`, `lessonType`, `lessonDuration`, `city`, `locationType`, `status`, `lessonPrivacy`, `instructor`, `client`, `user`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }
    private void insert(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setString(3, startTime);
            preparedStatement.setString(4, endTime);
            preparedStatement.setString(5, dayOfWeek);
            preparedStatement.setString(6, lessonType);
            preparedStatement.setString(7, duration);
            preparedStatement.setString(8, city);
            preparedStatement.setString(9, locationType);
            preparedStatement.setString(10, availability);
            preparedStatement.setString(11, lessonPrivacy);
            preparedStatement.setString(12, instructor);
            preparedStatement.setString(13, choiceBox_choose.getValue());
            preparedStatement.setString(14, username);
            preparedStatement.execute();
        } catch (SQLException e){
            Logger.getLogger(ClientPageController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private void clean(){
        startDate = null;
        endDate = null;
        startTime =null;
        endTime = null;
        dayOfWeek = null;
        lessonType = null;
        duration = null;
        lessonPrivacy = null;
        city = null;
        locationType = null;
        availability = null;
        instructor = null;
    }

    private void getClientViewBookings(){
        try{
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("view-booking-client.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            ClientViewBookingController clientViewBookingController = loader.getController();
            clientViewBookingController.setUserInformation(username);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e){
            Logger.getLogger(ClientPageController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void setUserInformation(String username){
        label_user.setText(username);
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}

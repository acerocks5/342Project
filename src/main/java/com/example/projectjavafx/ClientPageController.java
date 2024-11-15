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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    private String username = null;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Offering offering = null;

    ObservableList<Offering> OfferingList = FXCollections.observableArrayList();

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
                //IMPLEMENT bookLesson();
            }
        });

        button_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshTable();
            }
        });

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "main-view.fxml", "Main Page", null, null);
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
                        resultSet.getInt("idoffering"),
                        resultSet.getDate("startDate").toLocalDate(),
                        resultSet.getDate("endDate").toLocalDate(),
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
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void selectLesson(){
        connection = DBUtils.getConnection();
        PreparedStatement psCheckUsername = null;
        ResultSet resultSet = null;

        TableView.TableViewSelectionModel<Offering> selectionModel = offeringTable.getSelectionModel();
        if(selectionModel.isEmpty()){
            System.out.println("You need to select offering before applying");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select offering before applying");
            alert.showAndWait();
        }
        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);



        for(int i = selectedIndices.length -1; i >= 0; i--){
            selectionModel.clearSelection(selectedIndices[i].intValue());
            try{
                psCheckUsername = connection.prepareStatement("SELECT name FROM 342Project.instructors WHERE username = username");
                resultSet = (psCheckUsername.executeQuery());
                int id = selectedIndices[i].intValue()+1;

                while(resultSet.next()){
                    String name = resultSet.getString(1);
                    query = "UPDATE 342Project.offering SET instructor = '"+name+"', status = 'available' WHERE idoffering ="+id;
                }
                connection = DBUtils.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
                refreshTable();

            } catch (SQLException ex) {
                Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
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

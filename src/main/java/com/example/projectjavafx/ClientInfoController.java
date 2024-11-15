package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Child;
import model.Offering;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientInfoController implements Initializable {
    @FXML
    private TableView<Child> childTable;
    @FXML
    private Label label_user;
    @FXML
    private Button button_refresh;
    @FXML
    private Button button_addInfo;
    @FXML
    private Button button_addChild;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_phoneNumber;
    @FXML
    private TextField tf_age;
    @FXML
    private TextField tf_childName;
    @FXML
    private TextField tf_childAge;
    @FXML
    private TableColumn<Child, String> age_col;
    @FXML
    private TableColumn<Child, String> child_col;
    private String username;
    private boolean update;
    private boolean updateChild;
    int clientId;
    int childId;

    String query = null;
    String queryChild = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Child child = null;
    ObservableList<Child> ChildList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTable();

        button_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshTable();
            }
        });

        button_addInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addInfo();
            }
        });
        button_addChild.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addChild();
                refreshTable();
            }
        });

    }

    public void refreshTable(){
        try{
            setUsername(label_user.getText());

            ChildList.clear();
            query = "SELECT * FROM `children` WHERE parent = '"+username+"'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ChildList.add(new Child(
                        resultSet.getInt("age"),
                        resultSet.getString("name"),
                        resultSet.getString("parent")
                ));
                childTable.setItems(ChildList);
            }
        }catch (SQLException e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadTable(){
        connection = DBUtils.getConnection();
        refreshTable();

        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
        child_col.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void addInfo(){
        connection = DBUtils.getConnection();
        String name = tf_name.getText();
        String phoneNumber = tf_phoneNumber.getText();
        String age = tf_age.getText();
        System.out.println(username);
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            psCheckUserExists = connection.prepareStatement("SELECT * FROM `clients` WHERE username= '"+username+"'");
            resultSet = psCheckUserExists.executeQuery();
            if(name.isEmpty() || phoneNumber.isEmpty() || age.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all data");
                alert.showAndWait();
            }else if(resultSet.isBeforeFirst()){
                System.out.println("client info already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot add info twice");
                alert.show();
            } else {
                getQuery();
                insert();
                clean();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void clean(){
        tf_name.setText(null);
        tf_age.setText(null);
        tf_phoneNumber.setText(null);
    }
    private void getQuery(){
        if(update == false){
            query = "INSERT INTO `clients`(`name`, `phoneNumber`, `age`, `username`) VALUES (?,?,?,?)";
        } else {
            query = "UPDATE `clients` SET "
                    +"`name`=?"
                    +"`phoneNumber`=?"
                    +"`age`=?"
                    +"`username`=? WHERE id ='"+clientId+"'";
        }
    }
    private void insert(){
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tf_name.getText());
            preparedStatement.setString(2, tf_phoneNumber.getText());
            preparedStatement.setString(3, tf_age.getText());
            preparedStatement.setString(4, username);
            preparedStatement.execute();
        } catch (SQLException e){
            Logger.getLogger(ClientInfoController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private void addChild(){
        connection = DBUtils.getConnection();
        String name = tf_childName.getText();
        String age = tf_childAge.getText();
        String parent = username;

        if(name.isEmpty() || age.isEmpty() || parent.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all data");
            alert.showAndWait();
        } else {
            getQueryChild();
            insertChild();
            cleanChild();
        }
    }
    private void cleanChild(){
        tf_childAge.setText(null);
        tf_childName.setText(null);
    }
    private void getQueryChild(){
        if(updateChild == false){
            queryChild = "INSERT INTO `children`(`name`, `age`, `parent`) VALUES (?,?,?)";
        } else {
            queryChild = "UPDATE `children` SET "
                    +"`name`=?"
                    +"`age`=?"
                    +"'parent`=? WHERE id ='"+childId+"'";
        }
    }
    private void insertChild() {
        try{
            preparedStatement = connection.prepareStatement(queryChild);
            preparedStatement.setString(1,tf_childName.getText());
            preparedStatement.setString(2,tf_childAge.getText());
            preparedStatement.setString(3,username);
            preparedStatement.execute();
        }catch (SQLException e){
            Logger.getLogger(ClientInfoController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setUserInformation(String username){
        label_user.setText(username);
    }

    public void setUsername(String username){
        this.username = username;
    }


}

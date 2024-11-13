package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Location;
import model.Offering;

import java.sql.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {

    private static Connection connection;

    public static Connection getConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/342project", "root", "password");
        } catch (SQLException e){
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return connection;
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role){
        Parent root = null;

        if (username != null && role != null) {
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                MainController mainController = loader.getController();
                mainController.setUserInformation(username, role);
            } catch(IOException e){
                e.printStackTrace();
            }
        } else {
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String role){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/342project", "root", "password");
            psCheckUserExists= connection.prepareStatement("SELECT  * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("user already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, role);
                psInsert.executeUpdate();

                changeScene(event, "main-view.fxml", "Welcome", username, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/342project", "root", "password");
            preparedStatement = connection.prepareStatement("SELECT password, role FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet= preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedRole = resultSet.getString("role");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "main-view.fxml", "welcome", username, retrievedRole);
                    } else {
                        System.out.println("Incorrect Password");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect");
                        alert.show();
                    }
                }

            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }


}

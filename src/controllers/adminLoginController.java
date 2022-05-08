package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class adminLoginController implements Initializable{

    @FXML
    private JFXButton goBackBtn;

    @FXML
    private TextField adminUsername;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private JFXButton loginBtn;   

    @FXML
    void login(ActionEvent event) {
        loadStage("/fxml/Admin-Insert-Product.fxml", event);
        // connectDB();
    }

    @FXML
    private void changeScene(ActionEvent event){
        if (event.getSource() == goBackBtn){
            loadStage("/fxml/main.fxml", event);
        } 
        // else if (event.getSource() == adminPanelBtn){
        //     loadStage("/fxml/Admin-Login.fxml", event);
        // }
    }

    @FXML
    public void connectDB(ActionEvent event){
        // / Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        String username = adminUsername.getText();
        String password = adminPassword.getText();

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM Employee WHERE Username = '"+username+"' and Pass = '"+password+"'";
            ResultSet rs = stmt.executeQuery(SQL);

            if(!rs.next()){

            } else {
                loadStage("/fxml/Admin-Insert-Product.fxml", event);
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadStage(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

}

package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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


public class adminInsertProductController {
    
   
    @FXML
    private JFXButton signOutBtn;

    @FXML
    private TextField itemName;

    @FXML
    private JFXButton submitItemsBtn;

    @FXML
    private TextField itemPrice;

    @FXML
    private Label itemSubmitIndication;

    @FXML
    private TextField itemBrand;

    

    // @FXML
    // void browseImg(ActionEvent event) {

    // }

    String itemNameTxt;
    int itemPriceTxt;
    String itemBrandTxt;

    @FXML
    void submitItem(ActionEvent event) {
        // loadStage("/fxml/main.fxml", event);
        itemNameTxt = itemName.getText();
        itemPriceTxt = Integer.parseInt(itemPrice.getText());
        itemBrandTxt = itemBrand.getText();
        connectDB();
        itemSubmitIndication.setText("Item Submitted Successfully!");
    }

    void connectDB(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "INSERT INTO ITEM(NAME_OF_ITEM, PRICE_PER_UNIT, NAME_OF_BRAND) VALUES ('"+itemNameTxt+"', "+itemPriceTxt+", '"+itemBrandTxt+"')";
            stmt.execute(SQL);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void changeScene(ActionEvent event){
        if (event.getSource() == signOutBtn){
            loadStage("/fxml/main.fxml", event);
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

}

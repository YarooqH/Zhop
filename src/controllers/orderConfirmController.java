package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;


public class orderConfirmController implements Initializable {

    @FXML
    private JFXButton goBackBtn;

    @FXML
    private JFXButton confirmOrderBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox orderVBox;

    int nodeCount;

    private void generateNodes(){
        connectDB();
        Node [] nodes = new Node[nodeCount];
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = (Node)FXMLLoader.load(getClass().getResource("/fxml/Order.fxml"));
                orderVBox.getChildren().add(nodes[i]);
                if (i >= 1){
                    orderController.itemIndex = i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static ArrayList<String> itemNameData = new ArrayList<>();
    static ArrayList<String> itemPriceData = new ArrayList<>();
    static ArrayList<String> itemQuantityData = new ArrayList<>();

    public void connectDB(){
        // / Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT Count(*) FROM Cart WHERE ID = " + mainPageController.orderID;
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            nodeCount = rs.getInt(1);

            String sql = "SELECT * FROM Cart WHERE ID = " + mainPageController.orderID;
            ResultSet RS = stmt.executeQuery(sql);
            while(RS.next()){
                itemNameData.add(RS.getString("Item_Name"));
                itemPriceData.add(RS.getString("Item_Price"));
                itemQuantityData.add(RS.getString("Item_Quantity"));
            }

            // System.out.println(nodeCount);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeScene(ActionEvent event){
        if (event.getSource() == goBackBtn){
            loadStage("/fxml/main.fxml", event);
        } else if (event.getSource() == confirmOrderBtn){
            loadStage("/fxml/Customer-Details.fxml", event);
            // insertOrder();
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
        generateNodes();
        // getData();
        
    }

}

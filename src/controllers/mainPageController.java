package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// import controllers.itemController;

public class mainPageController implements Initializable {

    @FXML
    private JFXButton cartBtn;

    @FXML
    private JFXButton adminPanelBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox itemsVBox;

    static int nodeCount;

    static int orderID;
    
    @FXML
    private void changeScene(ActionEvent event){
        if (event.getSource() == cartBtn){
            loadStage("/fxml/Order-Confirm.fxml", event);
        } else if (event.getSource() == adminPanelBtn){
            loadStage("/fxml/Admin-Login.fxml", event);
        }
    }

    public void loadWindow(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root1;
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
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

    private int getID(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        ArrayList<Integer> ids = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT MAX(ID) AS Max_ID FROM Cart ";
            ResultSet rs = stmt.executeQuery(SQL);

            if(rs.next()){
                orderID = rs.getInt(1);
            }
            // System.out.println(orderID);
            // String SQL = "SELECT * FROM Cart";
            // ResultSet rs = stmt.executeQuery(SQL);

            // while(rs.next()){
            //     ids.add(rs.getInt("ID"));
            // }

            // orderID = ids.get(ids.size() - 1);

            // rs.last();
            // orderID = rs.getInt("ID");
            
            // return orderID;
            // System.out.println(nodeCount);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

        return orderID;

    }

    public static void connectDB(){
        // / Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT Count(*) FROM ITEM";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            nodeCount = rs.getInt(1);

            String sql = "SELECT * FROM ITEM";
            ResultSet RS = stmt.executeQuery(sql);
            while(RS.next()){
                itemController.itemNameData.add(RS.getString("NAME_OF_ITEM"));
                itemController.itemPriceData.add(RS.getString("PRICE_PER_UNIT"));
            }

            // System.out.println(nodeCount);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        orderID = getID() + 1;
        connectDB();
        Node [] nodes = new Node[nodeCount];
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = (Node)FXMLLoader.load(getClass().getResource("/fxml/Item.fxml"));
                itemsVBox.getChildren().add(nodes[i]);
                if (i >= 1){
                    itemController.itemIndex = i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

}

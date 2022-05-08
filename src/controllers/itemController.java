package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;


public class itemController implements Initializable{

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private JFXButton itemQuantityIncrease;

    @FXML
    private Label itemQuantity;

    @FXML
    private JFXButton itemQuantityDecrease;

    @FXML
    private JFXButton itemAddToCart;

    @FXML
    void addToCart(){
        int itemQuan = Integer.parseInt(itemQuantity.getText());
        // int itemIndividualPrice = Integer.parseInt(itemPrice.getText());
        int totalPrice = itemQuan * originalPrice;
        
        // System.out.println(event.getTarget());
        // / Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "INSERT INTO CART(ID, Item_Name, Item_Price, Item_Quantity, Total_Amount) values ("+mainPageController.orderID+",'"+itemNameData.get(subItemIndex)+"',"+itemPriceData.get(subItemIndex)+","+itemQuan+","+totalPrice+")";
            
            // PreparedStatement prepStatDB = con.prepareStatement(SQL);
            // prepStatDB.setString(1,  String.valueOf(totalPrice));

            stmt.execute(SQL);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int count = 1;

    @FXML
    void decreaseQuantity(ActionEvent event) {
        if(count == 1){
            count = count + 0;
            itemQuantity.setText(String.valueOf(count));
            // sad
        } else {
            count = count - 1;
            itemQuantity.setText(String.valueOf(count));
        }
    }

    @FXML
    void increaseQuantity(ActionEvent event) {
        count = count + 1;
        itemQuantity.setText(String.valueOf(count));
    }

    int subItemIndex = 0;    
    static int itemIndex = 0;   
    int originalPrice;

    static ArrayList<String> itemNameData = new ArrayList<>();
    static ArrayList<String> itemPriceData = new ArrayList<>();

    ArrayList<String> itemData = new ArrayList<>();

    private void setData(){
        subItemIndex = itemIndex;
        originalPrice = Integer.parseInt(itemPriceData.get(itemIndex));
        itemName.setText(itemNameData.get(itemIndex));
        itemPrice.setText("Rs. " + itemPriceData.get(itemIndex));
        // System.out.println(itemPriceData.get(itemIndex));
    }

    public void connectDB(){
        // / Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM ITEM";
            // SQL.
            // PreparedStatement prepStatementDB = con.prepareStatement(SQL);
            // prepStatementDB.setString(1, String.valueOf(itemIndex));  

            ResultSet rs = stmt.executeQuery(SQL);
            
            // itemPrice.setText(String.valueOf(itemIndex));
            // System.out.println(itemIndex+1);

            // while(rs.next()){
            //     itemData.add(rs.getString("NAME_OF_ITEM"));
            //     itemData.add(rs.getString("PRICE_PER_UNIT"));
            // }

            // System.out.println(itemData.size());
            // rs.next();

            // nodeCount = rs.getInt(1);

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
        // System.out.println(itemIndex);
        setData();
        connectDB();
        
    }
}

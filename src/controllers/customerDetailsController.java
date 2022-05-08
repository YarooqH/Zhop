package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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

public class customerDetailsController {

    @FXML
    private JFXButton goBackBtn;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private TextField customerFName;

    @FXML
    private TextField customerLName;

    @FXML
    private TextField customerEmail;

    @FXML
    private TextField customerPhoneNum;

    @FXML
    private TextField customerAddress;

    @FXML
    private TextField customerProvince;

    @FXML
    private TextField customerCity;

    static String customerID;

    String CustomerFName;
    String CustomerLName;
    String CustomerEmail;
    String CustomerPhoneNum;
    String CustomerAddress;
    String CustomerProvince;
    String CustomerCity;
    
    ArrayList<Integer> itemPrices = new ArrayList<>();
    ArrayList<String> customerDetails = new ArrayList<>();
    int totalPrice = 0;

    private void getDataSQL(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            // String SQL = "SELECT TOP 1 * FROM Cart WHERE ID = " +mainPageController.orderID;
            String SQL = "SELECT Cart.Total_Amount FROM Cart INNER JOIN Customer ON Customer.Order_Num = Cart.Order_Num WHERE Customer.Order_Num = '"+customerDetailsController.customerID+"'";
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()){
                itemPrices.add(rs.getInt("Total_Amount"));
            }
            // rs.next();            
            for (Integer integer : itemPrices) {
                totalPrice = totalPrice + integer;
            }

            String sql = "SELECT Customer.First_Name, Customer.Last_Name, Customer.Email, Customer.Phone_Number, Customer.User_Address FROM Customer INNER JOIN Cart ON Cart.Order_Num = Customer.Order_Num WHERE Cart.Order_Num = '"+customerDetailsController.customerID+"'";
            ResultSet RS = stmt.executeQuery(sql);
            while(RS.next()){
                customerDetails.add(RS.getString("First_Name"));
                customerDetails.add(RS.getString("Last_Name"));
                customerDetails.add(RS.getString("Email"));
                customerDetails.add(RS.getString("Phone_Number"));
                customerDetails.add(RS.getString("User_Address"));
            }
            
            // for (String str : customerDetails) {
                //     System.out.println(str);
                // }
            // System.out.println(rs);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void insertOrderSQL(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "INSERT INTO Orders(Order_Num, Customer_Name, Phone_Num, Customer_Address, Customer_Email, Total_Amount) VALUES ('"+mainPageController.orderID+"', '"+customerDetails.get(0)+" "+customerDetails.get(1)+"','"+customerDetails.get(3)+"','"+customerDetails.get(4)+"','"+customerDetails.get(2)+"',"+mainPageController.orderID+") ";
            stmt.execute(SQL);        
            // System.out.println(nodeCount);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getData(){
        CustomerFName = customerFName.getText();
        CustomerLName = customerLName.getText();
        CustomerEmail = customerEmail.getText();
        CustomerPhoneNum = customerPhoneNum.getText();
        CustomerAddress = customerAddress.getText();
        CustomerProvince = customerProvince.getText();
        CustomerCity = customerCity.getText();

        if(CustomerFName.isEmpty() || CustomerLName.isEmpty() || CustomerEmail.isEmpty() || CustomerPhoneNum.isEmpty() || CustomerAddress.isEmpty() || CustomerProvince.isEmpty() || CustomerCity.isEmpty()){

        } else {
            getID(); 
            insertOrder();           
        }
    }

    private void getID(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP 1 * FROM Cart WHERE ID = " +mainPageController.orderID;
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            customerID = rs.getString("Order_Num");
            // System.out.println(rs.getString("Order_Num"));
            // System.out.println(rs.next());
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void insertOrder(){
        String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "INSERT INTO Customer(Order_Num, First_Name, Last_Name, Email, Phone_Number, User_Address, Province, City) values ('"+customerID+"', '"+CustomerFName+"', '"+CustomerLName+"', '"+CustomerEmail+"', "+"0"+CustomerPhoneNum+", '"+CustomerAddress+"', '"+CustomerProvince+"', '"+CustomerCity+"')";
            stmt.execute(SQL);
            // System.out.println(rs.getString("Order_Num"));
            // System.out.println(rs.next());
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void changeScene(ActionEvent event){
        if (event.getSource() == goBackBtn){
            loadStage("/fxml/Order-Confirm.fxml", event);
        } 
    }
    
    @FXML
    void submitCustomerDetails(ActionEvent event) {
        getData();
        loadStage("/fxml/Order-Submitted.fxml", event);   
        getDataSQL();
        insertOrderSQL();     
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

    // public void connectDB(){
    //     // / Create a variable for the connection string.
    //     String connectionUrl = "jdbc:sqlserver://YQ;databaseName=PMS;integratedSecurity=true";

    //     try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
    //         String SQL = "INSERT INTO Orders()";
    //         ResultSet rs = stmt.executeQuery(SQL);
    //     }
    //     // Handle any errors that may have occurred.
    //     catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

}

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class invoiceController implements Initializable {

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label emailAddress;

    @FXML
    private Label orderAddress;

    @FXML
    private Label totalAmount;

    @FXML
    private Label orderSubmissionDate;

    @FXML
    private Label orderNum;

    
    ArrayList<Integer> itemPrices = new ArrayList<>();
    ArrayList<String> customerDetails = new ArrayList<>();
    int totalPrice = 0;

    private void getData(){
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

    private void setData(){
        orderNum.setText(customerDetailsController.customerID);
        firstName.setText(customerDetails.get(0));
        lastName.setText(customerDetails.get(1));
        emailAddress.setText((customerDetails.get(2)));
        totalAmount.setText(String.valueOf(totalPrice));
        orderAddress.setText(customerDetails.get(4));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        getData();   
        setData();     
    }

}

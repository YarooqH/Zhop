package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class orderController implements Initializable {

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Label itemQuantity;

    static int itemIndex;

    void setData(){
        itemName.setText("Item Name: " + orderConfirmController.itemNameData.get(itemIndex));
        itemPrice.setText("Item Price: " + orderConfirmController.itemPriceData.get(itemIndex));
        itemQuantity.setText("Quantity: " + orderConfirmController.itemQuantityData.get(itemIndex) + "x");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        setData();
        
    }

}

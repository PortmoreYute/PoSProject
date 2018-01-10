package Admin;


import Objects.InventoryItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditDeleteItemController implements Initializable {
    @FXML private TextField itemQuantityTF,itemPriceTF;
    @FXML private Button editBtn,deleteBtn,cancelBtn;
    @FXML private ComboBox<String> itemCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
        addListener();
    }

    private void addListener(){
        itemQuantityTF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!itemQuantityTF.getText().matches("^\\d+$")){

                    //set the textField empty
                    itemQuantityTF.setText("");
                }
            }
        });

        itemPriceTF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!itemPriceTF.getText().matches("^\\d*\\.?\\d*$")){

                    //set the textField empty
                    itemPriceTF.setText("");
                }
            }
        });
    }

    public void buttonAction(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(editBtn)){
           if(checkControls()) {
               editDeleteItem("Edit");
               stage.close();
               showInformationAlert("Edited");
           }
        }
        if(actionEvent.getSource().equals(deleteBtn)) {
            if(checkControls()){
                editDeleteItem("Delete");
                stage.close();
                showInformationAlert("Deleted");
            }
        }
        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }
    }

    private void initializeComboBox(){
        ArrayList<String> itemList = new ArrayList<>();
        Scanner inFile;
        File accFile = new File("ItemList.txt");
        InventoryItem tempItem = new InventoryItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempItem.setItemName(inFile.next());
                tempItem.setQuantity(inFile.nextInt());
                tempItem.setItemPrice(inFile.nextFloat());
                itemList.add(tempItem.getItemName());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemCB.getItems().addAll(itemList);
    }

    //Logic Functions
    public void itemCBAction() {
        findItem();
    }

    private void findItem(){
        Scanner inFile;
        File itemFile = new File("ItemList.txt");
        InventoryItem tempItem = new InventoryItem();
        try {
            inFile = new Scanner(itemFile);
            while (inFile.hasNext()){
                tempItem.setItemName(inFile.next());
                tempItem.setQuantity(inFile.nextInt());
                tempItem.setItemPrice(inFile.nextFloat());
                if(tempItem.getItemName().equals(itemCB.getValue())){
                    itemQuantityTF.setText(String.valueOf(tempItem.getQuantity()));
                    itemPriceTF.setText(String.valueOf(tempItem.getItemPrice()));
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void editDeleteItem(String action){
        Scanner inFile;
        File accFile = new File("ItemList.txt");
        File tempFile = new File("TempItemFile.txt");
        InventoryItem tempItem = new InventoryItem();

        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()) {
                tempItem.setItemName(inFile.next());
                tempItem.setQuantity(inFile.nextInt());
                tempItem.setItemPrice(inFile.nextFloat());
                if (!tempItem.getItemName().equals(itemCB.getValue())) {
                    addItemToTempFile(tempItem);
                }else{
                    if(action.equals("Edit")){
                        tempItem.setQuantity(Integer.parseInt(itemQuantityTF.getText()));
                        tempItem.setItemPrice(Float.parseFloat(itemPriceTF.getText()));
                        addItemToTempFile(tempItem);
                    }
                }
            }
            inFile.close();
            accFile.delete();
            tempFile.renameTo(new File("ItemList.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addItemToTempFile(InventoryItem tempItem ){
        File itemFile = new File("TempItemFile.txt");
        try {
            FileWriter itemFW = new FileWriter(itemFile, true);
            BufferedWriter itemBW = new BufferedWriter(itemFW);
            itemBW.write(tempItem.getItemName() + " ");
            itemBW.write(tempItem.getQuantity() + " ");
            itemBW.write(String.valueOf(tempItem.getItemPrice())+ "\n");
            itemBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){

        if(itemCB.getValue() == null){
            showErrorAlert("Item Name","An item must be selected");
            return false;
        }
        if(itemQuantityTF.getText().equals("")){
            showErrorAlert("Quantity","Field Cannot Be Empty");
            return false;
        }
        if(itemPriceTF.getText().equals("")){
            showErrorAlert("Price","Field Cannot Be Empty");
            return false;
        }
        return true;
    }

    void showErrorAlert(String fieldError, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Input");
        alert.setContentText(fieldError + " has invalid input\n" + message);
        alert.showAndWait();
    }

    void showInformationAlert(String action){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Item was successfully "+action);
        alert.showAndWait();
    }
}

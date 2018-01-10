package Admin;


import Objects.ServingItem;
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

public class EditDeleteServingController implements Initializable {

    @FXML
    private TextField servingPriceTF;
    @FXML private Button editBtn,deleteBtn,cancelBtn;
    @FXML private ComboBox<String> servingCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
        addListener();
    }

    private void addListener(){
        servingPriceTF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!servingPriceTF.getText().matches("^\\d*\\.?\\d*$")){

                    //set the textField empty
                    servingPriceTF.setText("");
                }
            }
        });
    }

    public void buttonAction(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(editBtn)){
            if(checkControls()) {
                editDeleteServing("Edit");
                stage.close();
                showInformationAlert("Edited");
            }
        }
        if(actionEvent.getSource().equals(deleteBtn)){
            if(checkControls()) {
                editDeleteServing("Delete");
                stage.close();
                showInformationAlert("Delete");
            }
        }
        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }
    }

    public void itemCBAction() {
        findItem();
    }

    private void initializeComboBox(){
        ArrayList<String> servingList = new ArrayList<>();
        Scanner inFile;
        File accFile = new File("ServingList.txt");
        ServingItem tempServing = new ServingItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempServing.setName(inFile.next());
                tempServing.setPrice(inFile.nextFloat());
                servingList.add(tempServing.getName());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        servingCB.getItems().addAll(servingList);
    }

    //Logic Function
    private void findItem(){
        Scanner inFile;
        File accFile = new File("ServingList.txt");
        ServingItem tempServing = new ServingItem();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempServing.setName(inFile.next());
                tempServing.setPrice(inFile.nextFloat());
                if(tempServing.getName().equals(servingCB.getValue())){
                    servingPriceTF.setText(String.valueOf(tempServing.getPrice()));
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void editDeleteServing(String action){
        Scanner inFile;
        File accFile = new File("ServingList.txt");
        File tempFile = new File("TempServing.txt");
        ServingItem tempServing = new ServingItem();

        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()) {
                tempServing.setName(inFile.next());
                tempServing.setPrice(inFile.nextFloat());
                if (!tempServing.getName().equals(servingCB.getValue())) {
                    addItemToTempFile(tempServing);
                }else{
                    if(action.equals("Edit")){
                        tempServing.setPrice(Float.parseFloat(servingPriceTF.getText()));
                        addItemToTempFile(tempServing);
                    }
                }

            }
            inFile.close();
            accFile.delete();
            tempFile.renameTo(new File("ServingList.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addItemToTempFile(ServingItem tempServing ){
        File itemFile = new File("TempServing.txt");
        try {
            FileWriter itemFW = new FileWriter(itemFile, true);
            BufferedWriter itemBW = new BufferedWriter(itemFW);
            itemBW.write(tempServing.getName() + " ");
            itemBW.write(String.valueOf(tempServing.getPrice())+ "\n");
            itemBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){

        if(servingCB.getValue()== null){
            showErrorAlert("Serving Name","A serving must be selected");
            return false;
        }
        if(servingPriceTF.getText().equals("")){
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
        alert.setContentText("Serving was successfully "+action);
        alert.showAndWait();
    }

}

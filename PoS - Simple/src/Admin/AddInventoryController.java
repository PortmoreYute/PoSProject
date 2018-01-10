package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddInventoryController implements Initializable {
    @FXML private TextField itemNameTF,itemQuantityTF,itemPriceTF;
    @FXML private Button addBtn,cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void buttonAction(ActionEvent actionEvent){
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(addBtn)){

            if(checkControls()){
                addItemToFile();
                stage.close();
                showInformationAlert();
            }

        }
        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }
    }

    //Logic Functions
    private void addItemToFile(){
        File itemFile = new File("ItemList.txt");
        try {
            FileWriter itemFW = new FileWriter(itemFile, true);
            BufferedWriter itemBW = new BufferedWriter(itemFW);
            itemBW.write(itemNameTF.getText().replaceAll("\\s","_") + " ");
            itemBW.write(itemQuantityTF.getText() + " ");
            itemBW.write(itemPriceTF.getText());
            itemBW.newLine();
            itemBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){

        if(itemNameTF.getText().equals("")){
            showErrorAlert("Item Name","Field Cannot Be Empty");
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

    void showInformationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Item was successfully added");
        alert.showAndWait();
    }


}

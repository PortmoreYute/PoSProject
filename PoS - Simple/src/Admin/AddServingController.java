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

public class AddServingController implements Initializable{
    @FXML
    private TextField servingNameTF,servingPriceTF;
    @FXML private Button addBtn,cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void buttonAction(ActionEvent actionEvent){
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(addBtn)){
            if(checkControls()) {
                addServingToFile();
                stage.close();
                showInformationAlert();
            }
        }
        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }
    }

    //Logic Function
    private void addServingToFile(){
        File servingFile = new File("ServingList.txt");
        try {
            FileWriter servingFW = new FileWriter(servingFile, true);
            BufferedWriter servingBW = new BufferedWriter(servingFW);
            servingBW.write(servingNameTF.getText().replaceAll("\\s","_") + " ");
            servingBW.write(servingPriceTF.getText());
            servingBW.newLine();
            servingBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){

        if(servingNameTF.getText().equals("")){
            showErrorAlert("Serving Name","Field Cannot Be Empty");
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

    void showInformationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Serving was successfully added");
        alert.showAndWait();
    }

}

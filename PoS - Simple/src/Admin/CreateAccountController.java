package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {
    @FXML private TextField firstNameTF,lastNameTF, phoneNumTF,usernameTF;
    @FXML private PasswordField passwordTF;
    @FXML private Button createBtn,cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener(){
        phoneNumTF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!phoneNumTF.getText().matches("^\\d{3}-\\d{3}-\\d{4}$")){

                    //set the textField empty
                    phoneNumTF.setText("");
                }
            }

        });
    }

    //Control Actions
    public void buttonAction(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(createBtn)){
            if(checkControls()){
                createAccount();
                stage.close();
                showInformationAlert();
            }
        }
        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }

    }

    //Logic Functions
    private void createAccount(){
        File accFile = new File("UserAccount.txt");
        try {
            FileWriter accFW = new FileWriter(accFile, true);
            BufferedWriter accBW = new BufferedWriter(accFW);
            accBW.write(firstNameTF.getText().replaceAll("\\s","_") + " ");
            accBW.write(lastNameTF.getText().replaceAll("\\s","_") + " ");
            accBW.write(phoneNumTF.getText() + " ");
            accBW.write(usernameTF.getText().replaceAll("\\s","_") + " ");
            accBW.write(passwordTF.getText().replaceAll("\\s",""));
            accBW.newLine();
            accBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){

        if(firstNameTF.getText().equals("")){
            showErrorAlert("First Name","Field Cannot Be Empty");
            return false;
        }
        if(lastNameTF.getText().equals("")){
            showErrorAlert("Last Name","Field Cannot Be Empty");
            return false;
        }
        if(phoneNumTF.getText().equals("")){
            showErrorAlert("Phone Number","Must Use The Pattern 000-000-0000");
            return false;
        }
        if(usernameTF.getText().equals("")){
            showErrorAlert("Username","Field Cannot Be Empty");
            return false;
        }
        if(passwordTF.getText().equals("")){
            showErrorAlert("Password","Field Cannot Be Empty");
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
        alert.setContentText("Account was successfully created");
        alert.showAndWait();
    }
}

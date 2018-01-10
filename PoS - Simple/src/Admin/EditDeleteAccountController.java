package Admin;

import Objects.Account;
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

public class EditDeleteAccountController implements Initializable {
    @FXML private ComboBox<String> userCB;
    @FXML private TextField firstNameTF,lastNameTF, phoneNumTF,passwordTF;
    @FXML private Button editBtn,deleteBtn,cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
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

    private void initializeComboBox(){
        ArrayList<String> accList = new ArrayList<>();
        Scanner inFile;
        File accFile = new File("UserAccount.txt");
        Account tempAccount = new Account();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempAccount.setFirstName(inFile.next());
                tempAccount.setLastName(inFile.next());
                tempAccount.setPhoneNumber(inFile.next());
                tempAccount.setUsername(inFile.next());
                tempAccount.setPassword(inFile.next());
                accList.add(tempAccount.getUsername());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userCB.getItems().addAll(accList);
    }

    public void buttonAction(ActionEvent actionEvent){
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        if(actionEvent.getSource().equals(editBtn)){
            if(checkControls()) {
                editDeleteAccount("Edit");
                stage.close();
                showInformationAlert("Edited");
            }
        }

        if(actionEvent.getSource().equals(deleteBtn)){
            if(checkControls()){
                editDeleteAccount("Delete");
                stage.close();
                showInformationAlert("Deleted");
            }

        }

        if(actionEvent.getSource().equals(cancelBtn)){
            stage.close();
        }
    }

    public void userCBAction() {
        findAccount();
    }

    //Logic Functions
    private void findAccount(){
        Scanner inFile;
        File accFile = new File("UserAccount.txt");
        Account tempACC = new Account();
        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                tempACC.setFirstName(inFile.next());
                tempACC.setLastName(inFile.next());
                tempACC.setPhoneNumber(inFile.next());
                tempACC.setUsername(inFile.next());
                tempACC.setPassword(inFile.next());
                if (userCB.getValue().equals(tempACC.getUsername())) {
                    firstNameTF.setText(tempACC.getFirstName());
                    lastNameTF.setText(tempACC.getLastName());
                    phoneNumTF.setText(tempACC.getPhoneNumber());
                    passwordTF.setText(tempACC.getPassword());
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void editDeleteAccount(String action){
        Scanner inFile;
        File accFile = new File("UserAccount.txt");
        File tempFile = new File("TempAccountFile.txt");
        Account tempACC = new Account();

        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()) {
                tempACC.setFirstName(inFile.next());
                tempACC.setLastName(inFile.next());
                tempACC.setPhoneNumber(inFile.next());
                tempACC.setUsername(inFile.next());
                tempACC.setPassword(inFile.next());
                if (!userCB.getValue().equals(tempACC.getUsername())) {
                    writeTempFile(tempACC);
                }else
                {
                    if(action.equals("Edit")){
                        tempACC.setFirstName(firstNameTF.getText().replaceAll("\\s","_"));
                        tempACC.setLastName(lastNameTF.getText().replaceAll("\\s","_"));
                        tempACC.setPhoneNumber(phoneNumTF.getText());
                        tempACC.setPassword(passwordTF.getText().replaceAll("\\s",""));
                        writeTempFile(tempACC);
                    }
                }
            }
            inFile.close();
            accFile.delete();
            tempFile.renameTo(new File("UserAccount.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeTempFile(Account tempAcc) {
        File accFile = new File("TempAccountFile.txt");
        try {
            FileWriter accFW = new FileWriter(accFile, true);
            BufferedWriter accBW = new BufferedWriter(accFW);
            accBW.write(tempAcc.getFirstName() + " ");
            accBW.write(tempAcc.getLastName() + " ");
            accBW.write(tempAcc.getPhoneNumber() + " ");
            accBW.write(tempAcc.getUsername() + " ");
            accBW.write(tempAcc.getPassword() + "\n");
            accBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkControls(){
        if(userCB.getValue() == null){
            showErrorAlert("Username","A user must be selected");
            return false;
        }
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

    void showInformationAlert(String action){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Account was successfully "+action);
        alert.showAndWait();
    }


}

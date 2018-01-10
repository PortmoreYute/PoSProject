package Login;


import Objects.Account;
import Objects.Log;
import Sales.SaleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController implements Initializable {

    //Attributes, Variables
    @FXML private TextField usernameTF;
    @FXML private PasswordField passwordTF;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Control Actions
    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();

        if(passwordTF.getText().equals("admin") && usernameTF.getText().equals("admin")){
            adminView();
            stage.close();
        }else {
            if (loginValid()) {
                saleView();
                stage.close();
            }
        }
    }

    //View Functions
    private void saleView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sales/SaleView.fxml"));
        Parent root = loader.load();

        Log log = new Log();
        log.setUser(usernameTF.getText());
        log.setLoggedIn(Date.from(Instant.now()));

        SaleController sales = loader.getController();
        sales.setLog(log);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> sales.getLog().logUser());
        stage.show();
    }

    private void adminView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AdminView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();

    }

    //Logic Functions
    private boolean loginValid(){
        Scanner inFile;
        boolean valid = false;
        File accFile = new File("UserAccount.txt");
        Account temp = new Account();

        try {
            inFile = new Scanner(accFile);
            while (inFile.hasNext()){
                temp.setFirstName(inFile.next());
                temp.setLastName(inFile.next());
                temp.setPhoneNumber(inFile.next());
                temp.setUsername(inFile.next());
                temp.setPassword(inFile.next());
                if (usernameTF.getText().equals(temp.getUsername()) && passwordTF.getText().equals(temp.getPassword())){
                    valid = true;
                    break;
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return valid;
    }
}

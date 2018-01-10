package Admin;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    @FXML private AnchorPane adminAP;
    @FXML private Button createAccBtn,editAccBtn,addInventoryBtn,editInventoryBtn,addServingBtn,editServingBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Control Actions
    public void buttonAction(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource().equals(createAccBtn)){
            createAccView();
        }
        if(actionEvent.getSource().equals(editAccBtn)){
            editAccView();
        }
        if(actionEvent.getSource().equals(addInventoryBtn)){
            addInventorView();
        }
        if(actionEvent.getSource().equals(editInventoryBtn)){
            editItemView();
        }
        if(actionEvent.getSource().equals(addServingBtn)){
            addServingView();
        }
        if(actionEvent.getSource().equals(editServingBtn)){
            editServingView();
        }
    }

    public void closeMIAction() throws IOException {
        Node source = adminAP;
        Stage stage  = (Stage) source.getScene().getWindow();

        stage.close();
        loginView();
    }

    //View Functions
    private void loginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/LoginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void createAccView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/CreateAccountView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void editAccView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditDeleteAccountView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void addInventorView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddInventoryView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void editItemView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditDeleteItemView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void addServingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddServingView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }

    private void editServingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditDeleteServingView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //stage.initStyle(StageStyle.TRANSPARENT);
        //scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("Resource/modena_dark.css");
        stage.setScene(scene);
        stage.show();
    }
}

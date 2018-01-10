package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        //this.primaryStage.initStyle(StageStyle.UNDECORATED);
        showView();
    }

    private  void showView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Login.class.getResource("LoginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Resource/modena_dark.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

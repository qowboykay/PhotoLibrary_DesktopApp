package photos.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Brayan Dominguez Quintero
 * @author Rahul Kayithi
 */
public class Photos extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load the login FXML
        try{
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/photos/view/login.fxml"));
            Stage loginStage = new Stage();
            loginStage = primaryStage;
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(loginRoot));
            loginStage.setResizable(false);
            loginStage.show();
        }
        catch(IOException e){
            e.printStackTrace();

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.app.AllUsers;

public class loginController {

    
    private AllUsers allUsers;

    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;

    @FXML
    private Button loginButton;

    public loginController(){
        allUsers = new AllUsers();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        
    }
    
    public void loginAttempt(ActionEvent login) throws Exception{
    

    }
    
    public void initialize() {
    }
}
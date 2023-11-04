package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {

    @FXML
    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;

    public void loginAttempt(ActionEvent login) throws Exception{

    }
    
    public void initialize() {
    }
}
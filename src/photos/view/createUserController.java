package photos.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import photos.app.User;

public class createUserController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordNameField;
    private adminController admin;

    public createUserController(){

    }

    public void setAdminController(adminController adminController) {
        this.admin = adminController;
    }

    @FXML
    protected void onCreateUserButtonClicked() throws IOException{
        String username = usernameField.getText();
        String password = passwordNameField.getText();
        if(username.equals("") || password.equals("")){
            Alert error = new Alert(Alert.AlertType.ERROR, "Cannot leave any of the fields blank, please try again", ButtonType.OK);
            error.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to create this user?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                admin.handleNewUser(username,password);
                usernameField.getScene().getWindow().hide();
            }

        }
    }

    @FXML
    protected void onCancelButtonClicked(){
        usernameField.getScene().getWindow().hide();
    }
}

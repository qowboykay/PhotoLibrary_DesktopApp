package photos.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photos.app.*;
import java.io.*;

public class loginController {

    
    private AllUsers allUsers;

    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;

    @FXML TextField password;

    @FXML
    private Button loginButton;

    public loginController(){
        allUsers = new AllUsers();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception{
        initialize();
        String user = username.getText();
        String pass = password.getText();
        if(allUsers.searchForUser(user) != null && allUsers.searchForUser(user).getPassword().equals(pass)){
            if(user.equals("admin")){

            }
                else{
                    loginForUsers(event,allUsers.searchForUser(user));
                }
        }
        else{
            Alert incorrectAlert = new Alert(AlertType.ERROR, "Username or Password is incorrect", ButtonType.OK);
            incorrectAlert.showAndWait();
        }
    }
    
    public void loginForUsers(ActionEvent event, User user) throws Exception{
       //AlbumController albumController = new AlbumController(user);

    }
    
    public void initialize() throws IOException, ClassNotFoundException {
        if(allUsers.isEmpty()){
            User newUser = new User("stock","stock");
            allUsers.addUser(newUser);
            AllUsers.setAllUsers(allUsers);
        }
        else{
            allUsers = AllUsers.getAllUsers();
        }
    }
}
package photos.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
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

    @FXML 
    private PasswordField password;

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
                loginForAdmin(event,allUsers);
            }
                else{
                    loginForUsers(event,allUsers.searchForUser(user),allUsers);
                }
        }
        else{
            Alert incorrectAlert = new Alert(AlertType.ERROR, "Username or Password is incorrect", ButtonType.OK);
            incorrectAlert.showAndWait();
        }
    }
    
    public void loginForUsers(ActionEvent event, User user, AllUsers allUsers) throws Exception{
        albumController album = new albumController(user,allUsers);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/album.fxml"));
        loader.setController(album);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Welcome to Your Albums" + " " + username.getText() + "!");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void loginForAdmin(ActionEvent event,AllUsers userList) throws Exception{
        adminController admin = new adminController(userList);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/admin.fxml"));
        loader.setController(admin);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin Window");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }


    public void initialize() throws IOException, ClassNotFoundException {
        allUsers = AllUsers.getAllUsers();
        if(allUsers.isEmpty()){
            User newUser = new User("stock","stock");
            User admin = new User("admin","admin");
            Album newAlbum = new Album("stock");
            Picture newPic = new Picture("data/ducky.jpg","ducky");
            newAlbum.addPicture(newPic);
            newUser.addAlbum(newAlbum);
            allUsers.addUser(newUser);
            allUsers.addUser(admin);
            allUsers.saveData(); // Use the saveData method
        } else {
            allUsers = AllUsers.getAllUsers();
        }
    }


}
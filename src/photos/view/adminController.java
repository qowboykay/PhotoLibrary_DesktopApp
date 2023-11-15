package photos.view;

import photos.app.AllUsers;
import photos.app.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class adminController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordNameField;
    @FXML private ListView<User> userListView;
    @FXML private Button createUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button logoutButton;
    @FXML private Button exitButton;

    private Stage stage;
    private Scene scene;
    private AllUsers allUsers;


    public adminController(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    @FXML
    public void initialize(){
        ObservableList<User> users = FXCollections.observableArrayList(allUsers.getUserList().stream()
        .filter(user -> !user.getUsername().equals("admin")).collect(Collectors.toList()));
        userListView.setItems(users);
        userListView.getItems();
        userListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Lists all users in the system.
     *
     * @return List of usernames.
     */
    public List<String> listUsers() {
        return allUsers.getUserList().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }


    /**
     * Creates a new user with the specified username.
     *
     * @param username The username of the new user.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    protected void onCreateUserButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/createUser.fxml"));
        Parent root = loader.load();

        // Create a new controller
        createUserController createUserController = loader.getController();
        createUserController.setAdminController(this);

        Stage createUserStage = new Stage();
        createUserStage.initModality(Modality.APPLICATION_MODAL);
        createUserStage.setTitle("Create User");
        createUserStage.setScene(new Scene(root));

        createUserStage.showAndWait();

    }

    public void handleNewUser(String username ,String password) throws IOException{
        User newUser = new User(username, password);
        allUsers.addUser(newUser);
        userListView.getItems().add(newUser);
        userListView.refresh();
        allUsers.saveData();
    }

    @FXML
    protected void onDeleteUserButtonClicked() throws IOException {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        allUsers.removeUser(selectedUser.getUsername());
        userListView.getItems().remove(selectedUser);
        allUsers.saveData();
        
    }

    @FXML
    protected void onExitButtonClicked() throws IOException{
        allUsers.saveData();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onLogoutButtonClicked() throws IOException{
        allUsers.saveData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/login.fxml"));
        stage = (Stage) logoutButton.getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


}

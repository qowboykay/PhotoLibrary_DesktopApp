package photos.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.Picture;
import photos.app.User;
import photos.app.AllUsers;
import javafx.stage.*;
import javafx.scene.*;

public class albumController {

    @FXML private ListView<Album> albumListView;
    @FXML private TextField albumNameField;
    @FXML private Button createAlbumButton;
    @FXML private Button deleteAlbumButton;
    @FXML private Button renameAlbumButton;
    @FXML private Button openAlbumButton;
    @FXML private Button exitButton;
    @FXML private Button logoutButton;

    private Parent root;
    private Scene scene;
    private Stage stage;
    private User currentUser;
    private AllUsers allUsers;

    public albumController(User currentUser, AllUsers allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }

    @FXML
    public void initialize() {
        ObservableList<Album> albums = FXCollections.observableArrayList(currentUser.getListOfUserAlbums());
        albumListView.setItems(albums);
        albumListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    protected void onCreateAlbumButtonClicked() {
        String albumName = albumNameField.getText();
        if (!albumName.isEmpty()) {
            Album newAlbum = new Album(albumName);
            currentUser.addAlbum(newAlbum);
            albumListView.getItems().add(newAlbum);
            albumListView.refresh();
        }
    }

    @FXML
    protected void onDeleteAlbumButtonClicked() {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            currentUser.getListOfUserAlbums().remove(selectedAlbum);
            albumListView.getItems().remove(selectedAlbum);
        }
    }

    @FXML
    protected void onRenameAlbumButtonClicked() {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        String newAlbumName = albumNameField.getText();
        if (selectedAlbum != null && !newAlbumName.isEmpty()) {
            selectedAlbum.setAlbumName(newAlbumName);
            // Refresh the ListView to show the updated name
            albumListView.refresh();
        }
    }

    @FXML
    protected void onOpenAlbumButtonClicked() {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            // TODO: Open the selected album
        }
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



    // Additional methods for handling photos within an album can be added here
}





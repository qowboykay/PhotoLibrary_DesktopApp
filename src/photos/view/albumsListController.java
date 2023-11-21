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

public class albumsListController {

    @FXML private ListView<Album> albumListView;
    @FXML private TextField albumNameField;
    @FXML private Button createAlbumButton;
    @FXML private Button deleteAlbumButton;
    @FXML private Button renameAlbumButton;
    @FXML private Button openAlbumButton;
    @FXML private Button exitButton;
    @FXML private Button logoutButton;

    private Scene scene;
    private Stage stage;
    private User currentUser;
    private AllUsers allUsers;
    /**
     * This method is the constructor for the albumslistcontroller
     * @param currentUser
     * @param allUsers
     */
    public albumsListController(User currentUser, AllUsers allUsers) {
        this.currentUser = currentUser;
        this.allUsers = allUsers;
    }
    /**
     * This method runs when the albums list controller is opened and sets up the listview
     */
    @FXML
    public void initialize() {
        ObservableList<Album> albums = FXCollections.observableArrayList(currentUser.getListOfUserAlbums());
        albumListView.setItems(albums);
        albumListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    /**
     * This method creates an album using the specified name in the album name field when the create button is clicked
     */
    @FXML
    protected void onCreateAlbumButtonClicked() {
        String albumName = albumNameField.getText();
        if (!albumName.isEmpty()) {
             if(!albumListView.getItems().stream().anyMatch(album -> album.getAlbumName().equals(albumName))){
            Album newAlbum = new Album(albumName);
            currentUser.addAlbum(newAlbum);
            albumListView.getItems().add(newAlbum);
            albumListView.refresh();
             }
             else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "An album with that name already exists", ButtonType.OK);
                alert.showAndWait();
             }
        }
        else{
             // Handle the case where no album is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter an album name to create a new album.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * This method deletes an album that is selected when the delete button is pressed
     */
    @FXML
    protected void onDeleteAlbumButtonClicked() {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this album?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
    
            if (alert.getResult() == ButtonType.YES) {
                currentUser.getListOfUserAlbums().remove(selectedAlbum);
                albumListView.getItems().remove(selectedAlbum);
            }
        }
        else{
             // Handle the case where no album is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an album to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * This method renames an album that is selected to the specified name in the album name field when the rename button is clicked
     */
    @FXML
    protected void onRenameAlbumButtonClicked() {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        String newAlbumName = albumNameField.getText();
        if (selectedAlbum != null && !newAlbumName.isEmpty()) {
            if(!albumListView.getItems().stream().anyMatch(album -> album.getAlbumName().equals(newAlbumName))){
            selectedAlbum.setAlbumName(newAlbumName);
            // Refresh the ListView to show the updated name
            albumListView.refresh();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "An album with that name already exists", ButtonType.OK);
                alert.showAndWait();
            }
        }
        else{
             // Handle the case where no album is selected
            Alert alert = new Alert(Alert.AlertType.ERROR, "An album has not been selected or the album name field is blank.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * This method opens the specified album clicked creating a new album view controller
     * @throws IOException
     */
    @FXML
    protected void onOpenAlbumButtonClicked() throws IOException {
        Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            allUsers.saveData();
            albumViewController album = new albumViewController(selectedAlbum);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
            loader.setController(album);
            album.setAlbumListController(this);
            album.setCurrentUser(currentUser);
            stage = (Stage) openAlbumButton.getScene().getWindow();
            scene = new Scene(loader.load());
            stage.setScene(scene);
            album.setStage(stage);
            stage.setTitle("Welcome to" + " " +selectedAlbum.getAlbumName() + " " + "album!");
            stage.centerOnScreen();
            stage.show();
        }
        else {
            // Handle the case where no album is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an album to open.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    /**
     * This method exits the album list view controller and closes the entire app when exit button is clicked
     * @throws IOException
     */
    @FXML
    protected void onExitButtonClicked() throws IOException{
        allUsers.saveData();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    /**
     * This method logs out of the current user prompting a new login when the logout button is clicked
     * @throws IOException
     */
    @FXML
    protected void onLogoutButtonClicked() throws IOException{
        allUsers.saveData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/login.fxml"));
        stage = (Stage) logoutButton.getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}





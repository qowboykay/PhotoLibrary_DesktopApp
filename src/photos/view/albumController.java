package photos.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import photos.app.Album;
import photos.app.Picture;
import photos.app.User;

public class albumController {

    @FXML private ListView<Album> albumListView;
    @FXML private TextField albumNameField;
    @FXML private Button createAlbumButton;
    @FXML private Button deleteAlbumButton;
    @FXML private Button renameAlbumButton;
    @FXML private Button openAlbumButton;

    private User currentUser;

    public albumController(User currentUser) {
        this.currentUser = currentUser;
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

    // Additional methods for handling photos within an album can be added here
}





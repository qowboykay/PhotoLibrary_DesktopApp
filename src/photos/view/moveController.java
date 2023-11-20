package photos.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.Picture;

public class moveController {
    
    @FXML
    private ComboBox<Album> destinationAlbumComboBox;
    
    private Stage stage;
    private ObservableList<Album> destinationAlbums;
    private Album currentAlbum;
    private Album selectedAlbum;
    private Picture currentPic;
    private boolean canceled = false;

    public moveController(){

    }

    public moveController(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
    }
    
    public void setCurrentPic(Picture currentPic){
        this.currentPic = currentPic;
    }
    
    public void setCurrentAlbum(Album currentAlbum){
        this.currentAlbum = currentAlbum;
    }

    public void setDestinationAlbums(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
        destinationAlbumComboBox.setItems(destinationAlbums); // Update the ComboBox here
    }

    @FXML
    private void initialize() {
            destinationAlbumComboBox.setItems(destinationAlbums);
    }
    
    @FXML
    private void onMoveButtonClicked() {
       selectedAlbum = destinationAlbumComboBox.getValue();
       if(selectedAlbum.returnPictures().stream().noneMatch(pic -> pic.equals(currentPic))){
            selectedAlbum.addPicture(currentPic);
            currentAlbum.deletePicture(currentPic);
            Alert alert = new Alert(AlertType.INFORMATION, "Moved Successfully!",ButtonType.OK);
            alert.showAndWait();
       }
        else{
            Alert alert = new Alert(AlertType.ERROR, "This album already contains this picture",ButtonType.OK);
            canceled = true;
            alert.showAndWait();
        }
        
        stage.close();
    }
    
    @FXML
    private void onCancelButtonClicked() {
        canceled = true;
        stage.close();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isMoveCanceled(){
        return canceled;
    }
}
    

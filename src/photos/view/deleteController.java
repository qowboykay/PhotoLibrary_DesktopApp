package photos.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.Picture;

public class deleteController {
    
    @FXML
    private ComboBox<Picture> destinationPictureComboBox;
    
    private Stage stage;
    private ObservableList<Picture> destinationPictures;
    private Album currentAlbum;
    private Picture selectedPicture;

    public deleteController(){

    }
    
    public deleteController(ObservableList<Picture> destinationPictures) {
        this.destinationPictures = destinationPictures;
    }
    
    public void setCurrentAlbum(Album currentAlbum){
        this.currentAlbum = currentAlbum;
    }

    public void setDestinationPictures(ObservableList<Picture> destinationPictures) {
        this.destinationPictures = destinationPictures;
        destinationPictureComboBox.setItems(destinationPictures); // Update the ComboBox here
    }

    @FXML
    private void initialize() {
            destinationPictureComboBox.setItems(destinationPictures);
    }
    
    @FXML
    private void onDeleteButtonClicked() {
        selectedPicture = destinationPictureComboBox.getValue();
        if(selectedPicture != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this picture?",ButtonType.YES,ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES){
                currentAlbum.deletePicture(selectedPicture);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please choose a picture to delete.",ButtonType.OK);
            alert.showAndWait();
        }
        stage.close();
    }
    
    @FXML
    private void onCancelButtonClicked() {
        stage.close();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
    

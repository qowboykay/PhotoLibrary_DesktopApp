package photos.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.Picture;

public class recaptionController {
    
    @FXML
    private ComboBox<Picture> destinationPictureComboBox;
    
    @FXML
    private TextField recaptionField;
    private Stage stage;
    private ObservableList<Picture> destinationPictures;
    private Album currentAlbum;
    private Picture selectedPicture;

    public recaptionController(){

    }
    
    public recaptionController(ObservableList<Picture> destinationPictures) {
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
    private void onRecaptionButtonClicked() {
        selectedPicture = destinationPictureComboBox.getValue();
        String newCaption = recaptionField.getText();
        if(selectedPicture != null || newCaption != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to recaption this picture?",ButtonType.YES,ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES){
                selectedPicture.recaptionPhoto(newCaption);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"One of the fields is blank please fill in both fields to continue",ButtonType.OK);
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
    

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
    /**
     * This method is the constructor for the delete controller and takes an observable list
     * @param destinationPictures
     */
    public deleteController(ObservableList<Picture> destinationPictures) {
        this.destinationPictures = destinationPictures;
    }
    /**
     * This method sets the current album in the delete controller
     * @param currentAlbum
     */
    public void setCurrentAlbum(Album currentAlbum){
        this.currentAlbum = currentAlbum;
    }
    /**
     * This method sets the destination pictures and loads the combobox properly
     * @param destinationPictures
     */
    public void setDestinationPictures(ObservableList<Picture> destinationPictures) {
        this.destinationPictures = destinationPictures;
        destinationPictureComboBox.setItems(destinationPictures); // Update the ComboBox here
    }
    /**
     * This method is initialized when the delete controller is opened
     */
    @FXML
    private void initialize() {
            destinationPictureComboBox.setItems(destinationPictures);
    }
    /**
     * This method is deletes the picture selected from the combobox
     */
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
    /**
     * This method runs when the cancel button is clicked and closes the stage
     */
    @FXML
    private void onCancelButtonClicked() {
        stage.close();
    }
    /**
     * This method sets the stage for the delete controller 
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
    

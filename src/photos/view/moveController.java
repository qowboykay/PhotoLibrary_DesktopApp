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
    /**
     * This is the method to construct a move controller
     * @param destinationAlbums
     */
    public moveController(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
    }
    /**
     * This method sets the current picture to the move controller
     * @param currentPic
     */
    public void setCurrentPic(Picture currentPic){
        this.currentPic = currentPic;
    }
    /**
     * This method sets the current album to the move controller
     * @param currentAlbum
     */
    public void setCurrentAlbum(Album currentAlbum){
        this.currentAlbum = currentAlbum;
    }
    /**
     * This method sets the observable list for the combobox of the move controller
     * @param destinationAlbums
     */
    public void setDestinationAlbums(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
        destinationAlbumComboBox.setItems(destinationAlbums); // Update the ComboBox here
    }
    /**
     *This method is initalized when move controller is opened
     */
    @FXML
    private void initialize() {
            destinationAlbumComboBox.setItems(destinationAlbums);
    }

    /**
     * This method runs when the move button is clicked and movees the specificed picture to the selected album
     */
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
    /**
     * This method runs when the cancel button is clicked and closes the move controller
     */
    @FXML
    private void onCancelButtonClicked() {
        canceled = true;
        stage.close();
    }
    /**
     * This method sets the stage for the move controller
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method checks if the move controller is canceled and closed
     * @return
     */
    public boolean isMoveCanceled(){
        return canceled;
    }
}
    

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

public class copyController {
    
    @FXML
    private ComboBox<Album> destinationAlbumComboBox;
    
    private Stage stage;
    private ObservableList<Album> destinationAlbums;
    private Album currentAlbum;
    private Album selectedAlbum;
    private Picture currentPic;

    public copyController(){

    }
    /**
     * This method is the constructor for copy controller and takes an observable list
     * @param destinationAlbums
     */
    public copyController(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
    }
    /**
     * This method sets the current picture for the copy controller
     * @param currentPic
     */
    public void setCurrentPic(Picture currentPic){
        this.currentPic = currentPic;
    }
    /**
     * This method sets the current album for the copy controller
     * @param currentAlbum
     */
    public void setCurrentAlbum(Album currentAlbum){
        this.currentAlbum = currentAlbum;
    }
    /**
     * This method sets the destination album and updates the associated combobox
     * @param destinationAlbums
     */
    public void setDestinationAlbums(ObservableList<Album> destinationAlbums) {
        this.destinationAlbums = destinationAlbums;
        destinationAlbumComboBox.setItems(destinationAlbums); // Update the ComboBox here
    }
    /**
     * This method initializes when the copy controller is opened
     */
    @FXML 
    private void initialize() {
            destinationAlbumComboBox.setItems(destinationAlbums);
    }
    /**
     *  Thus method copies the photo to the corrected selected album when the copy button is clicked
     * */ 
    @FXML
    private void onCopyButtonClicked() {
       selectedAlbum = destinationAlbumComboBox.getValue();
       if(selectedAlbum.returnPictures().stream().noneMatch(pic -> pic.equals(currentPic))){
            selectedAlbum.addPicture(currentPic);
            Alert alert = new Alert(AlertType.INFORMATION, "Copied Successfully!",ButtonType.OK);
            alert.showAndWait();
       }
        else{
            Alert alert = new Alert(AlertType.ERROR, "This album already contains this picture",ButtonType.OK);
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
     * This method sets the stage of the copy controller 
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
    

package photos.view;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.Picture;
import photos.app.Tag;
import photos.app.User;

public class pictureController {

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Tag> tagsListView;

    @FXML
    private ComboBox<String> tagNameField;
    @FXML
    private TextField tagValueField;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button renameButton;

    @FXML
    private Button addButton;

    @FXML
    private Button returnToAlbumButton;

    @FXML
    private Button copyButton;

    @FXML
    private Button moveButton;

    @FXML
    private Label captionLabel;

    @FXML 
    private Label dateLabel;

    private Stage currentStage;
    private Stage parentStage;
    private Scene scene;
    private ObservableList<String> options;
    private Picture currentPic;
    private Album currentAlbum;
    private int currentIndex;
    private User currentUser;
    private albumViewController parentController;
    /**
     * This method is the constructor for picture controller
     * @param currentPic
     * @param currentAlbum
     * @param parentController
     */
    public pictureController(Picture currentPic, Album currentAlbum,albumViewController parentController) {
        this.currentPic = currentPic;
        this.currentAlbum = currentAlbum;
        this.currentIndex = 0;
        this.parentController = parentController;
    }
    /**
     * This method is used to set the current user
     * @param currentUser
     */
    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }
    /**
     * This method is initialized when the picture controller is opened
     */
    @FXML 
    public void initialize(){
        setupDefaultTagNames();
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        tagsListView.setItems(tags);
        tagsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        displayPicture();
    }
    /**
     * This method is run when the add button is clicked creating a new tag with a new tag value that were specified in there respective fields
     */
    @FXML
    private void onAddButtonClicked(){
        String tagName = tagNameField.getEditor().getText();
        String tagValue = tagValueField.getText();
        if (!tagName.isEmpty() || !tagValue.isEmpty()) {
            if(options.contains(tagName)){

                Optional<Tag> existingTag = tagsListView.getItems().stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findFirst();

                if (existingTag.isPresent()) {
                    if (existingTag.get().getAllTagValues().stream().noneMatch(value -> value.equals(tagValue))){
                    existingTag.get().addTagValue(tagValue);
                    tagsListView.refresh();
                    }
                    else{
                        showAlert(Alert.AlertType.ERROR, "Tag value already exists for the selected tag name");
                    }
                }
                else{
                    Tag newTag = new Tag(tagName);
                    newTag.addTagValue(tagValue);
                    currentPic.addTag(newTag);
                    tagsListView.getItems().add(newTag);
                    tagsListView.refresh();
                }
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Selected tag name is not in the set options, please select an available option or create a new tag");
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR, "One of the field is blank, please specify tag name and tag value"); 
        }
    }
    /**
     * This method deletes a tag that is selected by a user in the tagslistview
     */
    @FXML
    private void onDeleteButtonClicked(){
        Tag selectedTag = tagsListView.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this tag?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
    
            if (alert.getResult() == ButtonType.YES) {
                currentPic.getPictureTags().remove(selectedTag);
                tagsListView.getItems().remove(selectedTag);
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Please select a tag to delete.");
        }
    }

    @FXML
    private void onClearButtonClicked(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will reset all you tag names to default, are you sure you want to do this?",ButtonType.YES, ButtonType.NO );
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            options.clear();
            setupDefaultTagNames();
        }
    }
    /**
     * This method is used to copy a photo from the current album to a speficied album using the copy controller
     * @throws IOException
     */
    @FXML
    private void onCopyButtonClicked() throws IOException{
        ObservableList<Album> userAlbums = FXCollections.observableArrayList(currentUser.getListOfUserAlbums());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/copy.fxml"));
        Stage copyStage = new Stage();
        copyStage.setScene(new Scene(loader.load()));
    
        // Get the controller from the loader
        copyController copyController = loader.getController();
        copyController.setStage(copyStage);
        copyController.setCurrentAlbum(currentAlbum); // Set the current album here
        copyController.setDestinationAlbums(userAlbums); // Pass the ObservableList<Album> here
        copyController.setCurrentPic(currentPic);

        copyStage.showAndWait();
    }  
    /**
     * This method is used to move the specified picture to a selected album in the move controller 
     * @throws IOException
     */
    @FXML
    private void onMoveButtonClicked() throws IOException {
        ObservableList<Album> userAlbums = FXCollections.observableArrayList(currentUser.getListOfUserAlbums());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/move.fxml"));
        Stage moveStage = new Stage();
        moveStage.setScene(new Scene(loader.load()));
    
        // Get the controller from the loader
        moveController moveController = loader.getController();
        moveController.setStage(moveStage);
        moveController.setCurrentAlbum(currentAlbum); // Set the current album here
        moveController.setDestinationAlbums(userAlbums); // Pass the ObservableList<Album> here
        moveController.setCurrentPic(currentPic);
        moveStage.setResizable(false);
        moveStage.showAndWait();
    
        if (!moveController.isMoveCanceled()) {
            parentController.isMoveCanceled(moveController.isMoveCanceled());
            if (currentAlbum.returnPictures().isEmpty()) {
                // If it's the last picture, close the current stage
                showAlert(Alert.AlertType.ERROR, "This album contains no pictures");
                updateParentView();
                currentStage.close();
            } else {
                // If it's not the last picture, load a new picture view
                FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/photos/view/picture.fxml"));
                pictureController newController = new pictureController(currentPic, currentAlbum, parentController);
                newLoader.setController(newController);
                newController.setCurrentUser(currentUser);
                currentStage.close();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(newLoader.load()));
                newController.setStage(newStage);
                newStage.setTitle("Picture Details");
                updateParentView(); // Update the parent view
                newStage.show();
            }
        } 
    }
    /**
     * This method is activated when the return button is clicked closing the current stage
     * @throws IOException
     */
    @FXML
    private void onReturnButtonClicked() throws IOException{
        currentStage.close();
    }
    /**
     * This method is used to pan through the pictures currently in the album this method enables the viewing of the previous picture
     */
    @FXML
    private void onPrevButtonClicked() {
        currentIndex = (currentIndex - 1 + currentAlbum.returnPictures().size()) % currentAlbum.returnPictures().size();
        displayPicture();
    }
    /**
     * This method is used to pan through the pictures currently in the album this method enables the viewing of the next picture
     */
    @FXML
    private void onNextButtonClicked() {
        System.out.println("Current Index: " + currentIndex);
        currentIndex = (currentIndex + 1) % currentAlbum.returnPictures().size();
        displayPicture();
    }
    /**
     * This method is used to create a new tag button when the tag name is a new tag name and the tag value field is blank
     */
    @FXML
    private void onNewTagButtonClicked(){
        String tagName = tagNameField.getEditor().getText();
        String tagValue = tagValueField.getText();
        boolean tagExists = options.stream().anyMatch(tag -> tag.equals(tagName));
        if (!tagName.isEmpty() && tagValue.isEmpty()) {
            if (!tagExists) {
                Tag newTag = new Tag(tagName);
                options.add(newTag.getTagName());
                updateOptions(options);
            } else {
                showAlert(Alert.AlertType.ERROR, "That tag already exists");
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR,"When creating a new tag enter a new tag name and leave the tag value blank.");
        }
    }
    /**
     * This method is used to display the pictures of the album
     */
    private void displayPicture() {
        if (!currentAlbum.returnPictures().isEmpty()) {
            currentPic = currentAlbum.returnPictures().get(currentIndex);
            File pictureFile = new File(currentPic.getPicturePath());
            Image image = new Image(pictureFile.toURI().toString());
            imageView.setImage(image);
            imageView.setPreserveRatio(false);
            captionLabel.setText(currentPic.getCaption());
            Date date = currentPic.getDate();
            dateLabel.setText("" + date);

            ObservableList<Tag> tags = FXCollections.observableArrayList(currentPic.getPictureTags());
            tagsListView.setItems(tags);
            tagsListView.refresh();
        }

    }
    /**
     * This method is used to setup default tags for when the user wants to add a tag
     */
    private void setupDefaultTagNames(){
            Tag animal = new Tag("animal");
            Tag spooky = new Tag("spooky");
            Tag cute = new Tag("cute");
            Tag place = new Tag("place");
            Tag person = new Tag("person");
            ObservableList<String> options = FXCollections.observableArrayList(animal.getTagName(),
            spooky.getTagName(),cute.getTagName(),place.getTagName(),person.getTagName());
            updateOptions(options);
    }
    /**
     * This method updates the possible options in the combobox
     * @param newOptions
     */
    private void updateOptions(ObservableList<String> newOptions){
        options = newOptions;
        tagNameField.setItems(options);
    }
    /**
     * This method is used for creating alerts
     * @param type
     * @param content
     */
    private void showAlert(Alert.AlertType type, String content) {
    Alert alert = new Alert(type, content, ButtonType.OK);
    alert.showAndWait();
    }
    /**This method is used to set the current stage 
     * 
    */
    public void setStage(Stage currentStage){
        this.currentStage = currentStage;
    }
    /**
     * This method is used to set thet parent stage
     * @param parentStage
     */
    public void setParentStage(Stage parentStage){
        this.parentStage = parentStage;
    }
    /**
     * This method updates the parent controller
     */
    private void updateParentView() {
        parentController.updateAlbumView();
    }
}
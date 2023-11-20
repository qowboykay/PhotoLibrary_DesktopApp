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

    private Stage stage;
    private Scene scene;
    private ObservableList<String> options;
    private Picture currentPic;
    private Album currentAlbum;
    private int currentIndex;
    private User currentUser;

    public pictureController(Picture currentPic, Album currentAlbum) {
        this.currentPic = currentPic;
        this.currentAlbum = currentAlbum;
        this.currentIndex = 0;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    @FXML 
    public void initialize(){
        setupDefaultTagNames();
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        tagsListView.setItems(tags);
        tagsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        displayPicture();
    }

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
                    existingTag.get().addTagValue(tagValue);
                    tagsListView.refresh();
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

    @FXML
    private void onMoveButtonClicked() throws IOException{
        Stage currentStage = (Stage) imageView.getScene().getWindow();
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
        
        if (currentAlbum.returnPictures().isEmpty()) {
            // If it's the last picture, close the current stage
            showAlert(Alert.AlertType.ERROR, "This album contains no pictures");
            currentStage.close();
        } else {
            // If it's not the last picture, load a new picture view
            FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/photos/view/picture.fxml"));
            pictureController newController = new pictureController(currentPic, currentAlbum);
            newLoader.setController(newController);
            newController.setCurrentUser(currentUser);
    
            Stage newStage = (Stage) imageView.getScene().getWindow();
            newStage.setScene(new Scene(newLoader.load()));
            newStage.show();
        }
    }

    @FXML
    private void onReturnButtonClicked() throws IOException{
        Stage stage = (Stage) returnToAlbumButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onPrevButtonClicked() {
        currentIndex = (currentIndex - 1 + currentAlbum.returnPictures().size()) % currentAlbum.returnPictures().size();
        displayPicture();
    }

    @FXML
    private void onNextButtonClicked() {
        System.out.println("Current Index: " + currentIndex);
        currentIndex = (currentIndex + 1) % currentAlbum.returnPictures().size();
        displayPicture();
    }

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
    }

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

    private void setupDefaultTagNames(){
            Tag bread = new Tag("animal");
            Tag spooky = new Tag("spooky");
            Tag cute = new Tag("cute");
            Tag place = new Tag("place");
            Tag person = new Tag("person");
            ObservableList<String> options = FXCollections.observableArrayList(bread.getTagName(),
            spooky.getTagName(),cute.getTagName(),place.getTagName(),person.getTagName());
            updateOptions(options);
    }

    private void updateOptions(ObservableList<String> newOptions){
        options = newOptions;
        tagNameField.setItems(options);
    }

    private void showAlert(Alert.AlertType type, String content) {
    Alert alert = new Alert(type, content, ButtonType.OK);
    alert.showAndWait();
    }

    public void updatePictureView() {
        displayPicture();
    }

}
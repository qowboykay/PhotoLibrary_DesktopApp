package photos.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class pictureController {

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Tag> tagsListView;

    @FXML
    private ComboBox<Tag> tagNameField;
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
    private ObservableList<Tag> options;
    private Picture currentPic;
    private Album currentAlbum;
    private int currentIndex;
    private albumViewController albumViewController;

    public pictureController(Picture currentPic, Album currentAlbum) {
        this.currentPic = currentPic;
        this.currentAlbum = currentAlbum;
        this.currentIndex = 0;
    }

    public void setAlbumViewController(albumViewController albumViewController){
        this.albumViewController = albumViewController;
    }

    @FXML 
    public void initialize(){
        setupDefaultTagNames();
        ObservableList<Tag> tags = FXCollections.observableArrayList(currentPic.getPictureTags());
        tagsListView.setItems(tags);
        tagsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        displayPicture();
    }

    @FXML
    private void onAddButtonClicked(){
        String tagName = tagNameField.getValue().getTagName();
        String tagValue = tagValueField.getText();
        if (!tagName.isEmpty() || !tagValue.isEmpty()) {
            if(tagsListView.getItems().equals(tagNameField.getValue()));
            Tag newTag = new Tag(tagName);
            currentPic.addTag(newTag);
            tagsListView.getItems().add(newTag);
            tagsListView.refresh();
        }
        else{
             // Handle the case where no album is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "One of the field is blank, please specify tag name and tag value", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onDeleteButtonClicked(){

    }

    @FXML
    private void onRenameButtonClicked(){

    }

    @FXML
    private void onCopyButtonClicked(){

    }

    @FXML
    private void onMoveButtonClicked(){
        
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
        currentIndex = (currentIndex + 1) % currentAlbum.returnPictures().size();
        displayPicture();
    }

    @FXML
    private void onNewTagButtonClicked(){
        String tagName = tagNameField.getValue().getTagName();
        boolean tagExists = tagNameField.getItems().stream().anyMatch(tag -> tag.getTagName().equals(tagName));
        if(!tagName.isEmpty() && tagName.isEmpty()){
            if(tagExists){
                Alert error = new Alert(AlertType.ERROR,"That tag already exists", ButtonType.OK);
                error.showAndWait();
            }
            else{
                Tag newTag = new Tag(tagName);
                options.add(newTag);
                tagNameField.setItems(options);
            }
        }
        else{
             Alert alert = new Alert(Alert.AlertType.WARNING, "When creating a new tag the tag value field must be left blank.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void displayPicture() {
        if (!currentAlbum.returnPictures().isEmpty()) {
            File pictureFile = new File(currentPic.getPicturePath());
            System.out.println("Picture Path: " + pictureFile.toURI().toString());
            Image image = new Image(pictureFile.toURI().toString());
            imageView.setImage(image);
            imageView.setPreserveRatio(false);
            captionLabel.setText(currentPic.getCaption());
            Date date = currentPic.getDate();
            dateLabel.setText("" + date);
        }
    }

    private void setupDefaultTagNames(){
        if(tagNameField.getItems().isEmpty()){
            Tag animal = new Tag("animal");
            Tag spooky = new Tag("spooky");
            Tag cute = new Tag("cute");
            Tag place = new Tag("place");
            Tag person = new Tag("person");
            ObservableList<Tag> options = FXCollections.observableArrayList(animal,
            spooky,cute,place,person);
            tagNameField.setItems(options);
        }

    }
}
package photos.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photos.app.Album;
import photos.app.AllUsers;
import photos.app.Picture;
import photos.app.User;

import java.io.File;
import java.io.IOException;

public class albumViewController {

    @FXML
    private Button backButton;

    @FXML
    private GridPane pictureGrid;

    @FXML
    private Button deleteButton;

    @FXML
    private Button recaptionButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    private Stage currentStage;
    private Stage pictureDetailsStage;
    private Scene scene;

    private Album currentAlbum;
    private albumsListController albumsListController;
    private User currentUser;
    private boolean canceled;

    public albumViewController(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public void setAlbumListController(albumsListController albumsListController){
        this.albumsListController = albumsListController;
    }

    public void setStage(Stage currentStage){
        this.currentStage = currentStage;
    }

    @FXML
    private void initialize() {
        displayPictures();
    }

    private void displayPictures() {
        int columnIndex = 0;
        int rowIndex = 0;

        for (Picture picture : currentAlbum.returnPictures()) {
            ImageView imageView = createImageView(picture);
            Label captionLabel = new Label(picture.getCaption());

            VBox pictureBox = new VBox();
            pictureBox.getChildren().addAll(imageView, captionLabel);

            pictureGrid.add(pictureBox, columnIndex, rowIndex);

            columnIndex++;
            if (columnIndex == 3) { // Adjust the number of columns as needed
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    private ImageView createImageView(Picture picture) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); // Adjust the size as needed
        imageView.setFitHeight(100);

        File pictureFile = new File(picture.getPicturePath());
        Image image = new Image(pictureFile.toURI().toString());
        imageView.setImage(image);

        // Add click event to show details when a picture is clicked
        imageView.setOnMouseClicked(event -> showPictureDetails(picture,currentAlbum));

        return imageView;
    }

    private void showPictureDetails(Picture picture, Album currentAlbum) {
        pictureDetailsStage = new Stage();
        pictureDetailsStage.setTitle("Picture Details");
        pictureController pictureController = new pictureController(picture, currentAlbum, this);
        pictureDetailsStage.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/picture.fxml"));
        loader.setController(pictureController);
        pictureController.setCurrentUser(currentUser);
    
        try {
            Parent root = loader.load();
            pictureDetailsStage.setScene(new Scene(root));
            pictureDetailsStage.setResizable(false);
            pictureController.setStage(pictureDetailsStage);
            pictureController.setParentStage(currentStage);
            pictureDetailsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    protected void onBackButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumsList.fxml"));
        loader.setController(albumsListController);
        currentStage = (Stage) backButton.getScene().getWindow();
        scene = new Scene(loader.load());
        currentStage.setTitle("Welcome to your Albums" + " " + currentUser.getUsername() + "!");
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    public void updateAlbumView() {
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
        albumViewController newController = new albumViewController(currentAlbum);
        newLoader.setController(newController);
        newController.setAlbumListController(albumsListController);
        newController.setCurrentUser(currentUser);
        
        Stage newStage = new Stage();  // Create a new stage for the album view
        if(!canceled){
            if (currentStage != null) {
            currentStage.close();
        }
        }
        try {
            newStage.setScene(new Scene(newLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        currentStage = newStage; //show the new stage
        newStage.setTitle("Welcome to" + " " + currentAlbum.getAlbumName() + " " + "album!");
        newStage.show();
    }

    @FXML 
    private void onRecaptionButtonClicked() throws IOException{
        ObservableList<Picture> albumPics = FXCollections.observableArrayList(currentAlbum.returnPictures());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/recaption.fxml"));
        Stage recaptionStage = new Stage();
        recaptionStage.setScene(new Scene(loader.load()));
    
        // Get the controller from the loader
        recaptionController recaptionController = loader.getController();
        recaptionController.setStage(recaptionStage);
        recaptionController.setCurrentAlbum(currentAlbum); // Set the current album here
        recaptionController.setDestinationPictures(albumPics); // Pass the ObservableList<Album> here
        recaptionStage.showAndWait();  
        updateAlbumView();    
    }

    @FXML
    private void onSearchButtonClicked(){

    }

    @FXML
    private void onAddButtonClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Picture");
        File selectedFile = fileChooser.showOpenDialog(addButton.getScene().getWindow());

        if (selectedFile != null) {
            if (isPictureAlreadyInAlbum(selectedFile.getAbsolutePath())){
                Alert alert = new Alert(AlertType.ERROR,"This picture is already in the album", ButtonType.OK);
                alert.showAndWait();
            }
            else{
            String caption = "Default Caption"; // Set a default caption or prompt the user for one
            Picture newPicture = new Picture(selectedFile.getAbsolutePath(), caption);
            currentAlbum.addPicture(newPicture);
            updateAlbumView();
            }
        }
    }

    @FXML 
    private void onDeleteButtonClicked() throws IOException{
        ObservableList<Picture> albumPics = FXCollections.observableArrayList(currentAlbum.returnPictures());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/delete.fxml"));
        Stage deleteStage = new Stage();
        deleteStage.setScene(new Scene(loader.load()));
    
        // Get the controller from the loader
        deleteController deleteController = loader.getController();
        deleteController.setStage(deleteStage);
        deleteController.setCurrentAlbum(currentAlbum); // Set the current album here
        deleteController.setDestinationPictures(albumPics); // Pass the ObservableList<Album> here
        deleteStage.showAndWait();
        updateAlbumView();
    }

    public void isMoveCanceled(boolean move){
        canceled = move;
    }

    private boolean isPictureAlreadyInAlbum(String filePath) {
        // Normalize the file paths before comparison
    String normalizedFilePath = new File(filePath).getAbsolutePath().toLowerCase();

    // Check if a picture with the same normalized file path already exists in the album
    return currentAlbum.returnPictures().stream()
            .anyMatch(picture -> new File(picture.getPicturePath()).getAbsolutePath().toLowerCase().equals(normalizedFilePath));
    }
}
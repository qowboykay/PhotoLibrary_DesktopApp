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
import java.time.LocalDate;
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
    /**
     * This method is the constructor for album view controller taking only the current album as a parameter
     * @param currentAlbum
     */
    public albumViewController(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
    /**
     * This method sets the current users of the album view controller
     * @param currentUser
     */
    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }
    /**
     * This method sets the album list controllers of the album viewer controller
     * @param albumsListController
     */
    public void setAlbumListController(albumsListController albumsListController){
        this.albumsListController = albumsListController;
    }
    /**
     * This method sets the current stage of the album view controller
     * @param currentStage
     */
    public void setStage(Stage currentStage){
        this.currentStage = currentStage;
    }
    /**
     * This method initializes when the album view controller is opened displaying all the pictures in the current album
     */
    @FXML
    private void initialize() {
        displayPictures();
    }
    /**
     * This method prints all the pictures in the current album by creating new image views and adding them to a vbox
     */
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
            if (columnIndex == 5) { // Adjust the number of columns as needed
                columnIndex = 0;
                rowIndex++;
            }
        }
    }
    /**
     * This method create a new image based on the picture path given from the picture and ensures that picture controller is opened when a picture is clicked
     */
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
    /**
     * This method opens a new picture controller to show the specific details associated with each picture in the album
     */
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
    
    /**
     * This method closes the album view controller and opents the album list controller
     * @throws IOException
     */
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
    /**
     * This album updates the view when a new picture is added, deleted, renamed, moved, coped, and searched
     */
    public void updateAlbumView() {
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
        albumViewController newController = new albumViewController(currentAlbum);
        newLoader.setController(newController);
        newController.setAlbumListController(albumsListController);
        newController.setCurrentUser(currentUser);
        
        Stage newStage = new Stage();  // Create a new stage for the album view
        
        if(!canceled){
            currentStage.close();
        }

        try {
            newStage.setScene(new Scene(newLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage = newStage;
        newController.setStage(currentStage);

        //show the new stage
        newStage.setTitle("Welcome to" + " " + currentAlbum.getAlbumName() + " " + "");
        newStage.show();
    }
    /**
     * This method enables the recaptioning of pictures in the current album when the speficied picture is clicked in the list
     * @throws IOException
     */
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
    /**
     * This method opens a new search controller when the search button is clicked 
     */
    @FXML
    private void onSearchButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/search.fxml"));
            Stage searchStage = new Stage();
            searchStage.setScene(new Scene(loader.load()));
            searchStage.setTitle("Search");
            SearchController searchController = loader.getController();
            searchController.setCurrentUser(currentUser);

            // Show the search stage and wait for it to close
            searchStage.showAndWait();

            LocalDate startDate = searchController.getStartDate();
            LocalDate endDate = searchController.getEndDate();

            // If both start date and end date are null, it likely means no search was attempted
            if (startDate == null && endDate == null) {
                return; // Exit the method early
            }

            // If either date is null, show an error alert
            if (startDate == null || endDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both start and end dates.", ButtonType.OK);
                alert.showAndWait();
                return; // Exit the method if the dates are not properly set
            }

            // Proceed with your search logic using startDate and endDate

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error loading the search window: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * This method opens up the users files and allows the user to add a picture with the appropriate extensions
     */
    @FXML
    private void onAddButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(addButton.getScene().getWindow());

        if (selectedFile != null) {
            if (isPictureAlreadyInAlbum(selectedFile.getAbsolutePath())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This picture is already in the album", ButtonType.OK);
                alert.showAndWait();
                return; // Exit the method if the picture is already in the album
            }

            String caption = "Default Caption"; // Set a default caption or prompt the user for one
            try {
                Picture newPicture = new Picture(selectedFile.getAbsolutePath(), caption);
                currentAlbum.addPicture(newPicture);
                updateAlbumView();
            } catch (IOException e) {
                // Show an error alert if the image couldn't be loaded
                Alert alert = new Alert(Alert.AlertType.ERROR, "The selected file is not an image or cannot be opened: " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * This method is used when the delete button clicked and deletes the current stage while updating the album view
     * @throws IOException
     */
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
    /**
     * This method is used to check if the move was canceled
     */
    public void isMoveCanceled(boolean move){
        canceled = move;
    }
    /**
     * This method is used to check if the picture is already in the album using a filepath
     * @param filePath
     * @return
     */
    private boolean isPictureAlreadyInAlbum(String filePath) {
        // Normalize the file paths before comparison
    String normalizedFilePath = new File(filePath).getAbsolutePath().toLowerCase();

    // Check if a picture with the same normalized file path already exists in the album
    return currentAlbum.returnPictures().stream()
            .anyMatch(picture -> new File(picture.getPicturePath()).getAbsolutePath().toLowerCase().equals(normalizedFilePath));
    }
}
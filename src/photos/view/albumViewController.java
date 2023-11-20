package photos.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    private Stage stage;
    private Stage pictureDetailsStage;
    private Scene scene;

    private Album currentAlbum;
    private albumsListController albumsListController;
    private User currentUser;

    public albumViewController(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public void setAlbumListController(albumsListController albumsListController){
        this.albumsListController = albumsListController;
    }


    @FXML
    void initialize() {
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
        pictureController pictureController = new pictureController(picture, currentAlbum);
        pictureDetailsStage.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/picture.fxml"));
        loader.setController(pictureController);
        pictureController.setCurrentUser(currentUser);
    
        // Set OnCloseRequest for the pictureDetailsStage
        pictureDetailsStage.setOnCloseRequest(event -> reloadAlbumView(currentAlbum));
    
        try {
            Parent root = loader.load();
            pictureDetailsStage.setScene(new Scene(root));
            pictureDetailsStage.setResizable(false);
            pictureDetailsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    protected void onBackButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumsList.fxml"));
        loader.setController(albumsListController);
        stage = (Stage) backButton.getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setTitle("Welcome to your Albums" + " " + currentUser.getUsername() + "!");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void reloadAlbumView(Album currentAlbum) {
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/photos/view/albumView.fxml"));
        albumViewController newController = new albumViewController(currentAlbum);
        newLoader.setController(newController);
        newController.setCurrentUser(currentUser);
        
        Stage newStage = new Stage();  // Create a new stage for the album view
        
        try {
            newStage.setScene(new Scene(newLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Close the current stage
        Stage currentStage = (Stage) pictureGrid.getScene().getWindow();
        currentStage.close();
        
        // Show the new stage
        newStage.setTitle("Album View");
        newStage.show();
    }
}
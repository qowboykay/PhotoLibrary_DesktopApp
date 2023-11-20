package photos.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import photos.app.*;

import java.time.LocalDate;
import java.util.*;

public class SearchController {

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField tag1NameField;
    @FXML private TextField tag1ValueField;
    @FXML private TextField tag2NameField;
    @FXML private TextField tag2ValueField;
    @FXML private ListView<Picture> searchResultsListView;
    @FXML private TextField albumNameField;
    @FXML private Button searchButton;
    @FXML private Button createAlbumButton;

    private User currentUser;


    // Method called when the search button is pressed
    @FXML
    protected void onSearchButtonClicked() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String tag1Name = tag1NameField.getText();
        String tag1Value = tag1ValueField.getText();
        String tag2Name = tag2NameField.getText();
        String tag2Value = tag2ValueField.getText();

        List<Picture> results = new ArrayList<>();

        if (!tag1Name.isEmpty() && !tag1Value.isEmpty() && !tag2Name.isEmpty() && !tag2Value.isEmpty()) {
            // Assuming conjunctive search as default for two tags
            results.addAll(searchByConjunctiveTags(tag1Name, tag1Value, tag2Name, tag2Value));
        } else if (!tag1Name.isEmpty() && !tag1Value.isEmpty()) {
            results.addAll(currentUser.searchPicturesByTag(tag1Name, tag1Value));
        } else {
            Calendar startCal = Calendar.getInstance();
            startCal.set(startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
            Calendar endCal = Calendar.getInstance();
            endCal.set(endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());
            results.addAll(currentUser.searchPicturesByDate(startCal, endCal));
        }

        ObservableList<Picture> observableResults = FXCollections.observableArrayList(results);
        searchResultsListView.setItems(observableResults);
    }

    private List<Picture> searchByConjunctiveTags(String tag1, String value1, String tag2, String value2) {
        // This method should search for pictures that match both tag-value pairs
        return currentUser.searchPicturesByTagsConjunctive(tag1, value1, tag2, value2);
    }
    private List<Picture> searchByDisjunctiveTags(String tag1, String value1, String tag2, String value2) {
        // This method should search for pictures that match either of the tag-value pairs
        return currentUser.searchPicturesByTagsDisjunctive(tag1, value1, tag2, value2);
    }

    @FXML
    protected void onCreateAlbumFromResults() {
        String albumName = albumNameField.getText();
        if (albumName == null || albumName.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Album name cannot be empty.");
            alert.showAndWait();
            return;
        }

        Album newAlbum = new Album(albumName);
        searchResultsListView.getItems().forEach(newAlbum::addPicture);
        currentUser.addAlbum(newAlbum);
    }

    }


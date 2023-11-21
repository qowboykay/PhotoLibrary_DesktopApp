package photos.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import photos.app.*;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.*;

/**
 * Controller for handling search functionality in the application.
 */
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
    /**
     * Sets the current user for the search operation.
     *
     * @param currentUser The user performing the search.
     */
    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }
    /**
     * Retrieves the start date from the date picker.
     *
     * @return The selected start date.
     */

    public LocalDate getStartDate() {
        return startDatePicker.getValue();
    }
    /**
     * Retrieves the end date from the date picker.
     *
     * @return The selected end date.
     */
    public LocalDate getEndDate() {
        return endDatePicker.getValue();
    }

    /**
     * Handles the action when the search button is clicked. Performs a search based on the
     * specified criteria and displays the results.
     */
    @FXML
    protected void onSearchButtonClicked() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String tag1Name = tag1NameField.getText();
        String tag1Value = tag1ValueField.getText();
        String tag2Name = tag2NameField.getText();
        String tag2Value = tag2ValueField.getText();

        if (startDate == null || endDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Both start date and end date are required for a date range search.");
            alert.showAndWait();
            return;
        }

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
    /**
     * Searches for photos that match a conjunctive combination of two tag-value pairs.
     *
     * @param tag1 The first tag name.
     * @param value1 The value for the first tag.
     * @param tag2 The second tag name.
     * @param value2 The value for the second tag.
     * @return A list of pictures that match both tag-value pairs.
     */
    private List<Picture> searchByConjunctiveTags(String tag1, String value1, String tag2, String value2) {
        // This method should search for pictures that match both tag-value pairs
        return currentUser.searchPicturesByTagsConjunctive(tag1, value1, tag2, value2);
    }
    /**
     * Searches for photos that match either of two tag-value pairs (disjunctive search).
     *
     * @param tag1 The first tag name.
     * @param value1 The value for the first tag.
     * @param tag2 The second tag name.
     * @param value2 The value for the second tag.
     * @return A list of pictures that match either of the tag-value pairs.
     */
    private List<Picture> searchByDisjunctiveTags(String tag1, String value1, String tag2, String value2) {
        // This method should search for pictures that match either of the tag-value pairs
        return currentUser.searchPicturesByTagsDisjunctive(tag1, value1, tag2, value2);
    }
    /**
     * Creates a new album from the search results.
     *
     * The method checks if the album name is empty and displays an error if so. Otherwise, it creates a new album
     * and adds the search results to it.
     */
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



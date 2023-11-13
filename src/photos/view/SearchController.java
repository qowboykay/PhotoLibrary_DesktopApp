package photos.view;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import photos.app.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class SearchController {

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField tagNameField;
    @FXML private TextField tagValueField;
    @FXML private ListView<Picture> searchResultsListView;

    private User currentUser; // The current user

    // Constructor to set the current user
    public SearchController(User currentUser) {
        this.currentUser = currentUser;
    }

    // Method called when the search button is pressed
    @FXML
    protected void onSearchButtonClicked() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String tagName = tagNameField.getText();
        String tagValue = tagValueField.getText();

        List<Picture> results;
        if (!tagName.isEmpty() && !tagValue.isEmpty()) {
            results = currentUser.searchPicturesByTag(tagName, tagValue);
        } else {
            Calendar startCal = Calendar.getInstance();
            startCal.set(startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
            Calendar endCal = Calendar.getInstance();
            endCal.set(endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());

            results = currentUser.searchPicturesByDate(startCal, endCal);
        }

        ObservableList<Picture> observableResults = FXCollections.observableArrayList(results);
        searchResultsListView.setItems(observableResults);
    }


}

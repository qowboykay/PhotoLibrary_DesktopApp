package photos.app;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Picture implements Serializable {

    static final long serialVersionUID = 1L;
    private String picturePath;
    private String caption;
    private ArrayList<Tag> tags;
    private Calendar cal;

    public Picture(String picturePath, String caption) {
        this.picturePath = picturePath;
        this.caption = caption;
        this.tags = new ArrayList<>();
        // Initialize the calendar here if necessary, e.g., based on file's last modified date
        // this.cal = ...;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getCaption() {
        return caption;
    }

    public void recaptionPhoto(String caption) {
        this.caption = caption;
    }

    public Date getDate() {
        // Assuming you want to set the Calendar instance when getting the date
        // This seems a bit unusual as typically the date would be set once
        // and then retrieved, not reset every time it's retrieved
        cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public ArrayList<Tag> getPictureTags() {
        return tags;
    }

    public boolean hasTag(String tagName, String tagValue) {
        // Using the correct method name from Tag.java
        return tags.stream().anyMatch(tag -> tag.getTagName().equals(tagName) && tag.getAllTagValues().contains(tagValue));
    }

    // Method to check if a picture matches a given date range
    public boolean isWithinDateRange(Calendar startDate, Calendar endDate) {
        // Assuming 'cal' should be already set to the date of the photo,
        // not reset each time this method is called
        return (cal.equals(startDate) || cal.after(startDate)) && (cal.equals(endDate) || cal.before(endDate));
    }

}

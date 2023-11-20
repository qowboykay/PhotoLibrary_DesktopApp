package photos.app;

import java.io.*;
import java.util.*;

public class Picture implements Serializable {

    static final long serialVersionUID = 1L;
    private String picturePath;
    private String caption;
    private ArrayList<Tag> tags;
    private Calendar cal;
    private Date creationDate;  // New field for the creation date

    public Picture(String picturePath, String caption) {
        this.picturePath = picturePath;
        this.caption = caption;
        this.tags = new ArrayList<>();
        File file = new File(picturePath);
        this.creationDate = new Date();  // Set the creation date when the object is created
        long lastModifiedTimestamp = file.lastModified();
        this.creationDate = new Date(lastModifiedTimestamp);
        this.cal = Calendar.getInstance();
        this.cal.set(Calendar.MILLISECOND, 0);
        this.cal.setTime(creationDate);
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
        return creationDate;
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
    
    @Override
    public String toString() {
        return caption;
    }

}

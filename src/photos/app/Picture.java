package photos.app;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;  // Import for BufferedImage
public class Picture implements Serializable {


    static final long serialVersionUID = 1L;
    private String picturePath;
    private String caption;
    private ArrayList<Tag> tags;
    private Calendar cal;
    private Date creationDate;  // New field for the creation date
    private transient Image image;  // JavaFX Image, marked as transient for serialization


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


        loadPicture(picturePath);
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
    public void loadPicture(String filePath) {
        try {
            // Check if the file exists
            if (!Files.exists(Paths.get(filePath))) {
                throw new IOException("File not found: " + filePath);
            }


            // Loading the image with JavaFX
            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                BufferedImage bufferedImage = ImageIO.read(fileInputStream);
                this.image = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (FileNotFoundException e) {
                System.err.println("File not found when attempting to load: " + e.getMessage());
                // Additional handling like logging or updating GUI
            } catch (IOException e) {
                System.err.println("IO error when loading picture: " + e.getMessage());
            }


        } catch (IOException e) {
            System.err.println("Error loading picture: " + e.getMessage());
            // Additional handling like logging or updating GUI
        }
    }


    // Getter for the JavaFX image
    public Image getImage() {
        return image;
    }


}

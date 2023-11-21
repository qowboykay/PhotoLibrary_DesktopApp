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

    /**
     * Constructs a new Picture object with specified path and caption.
     *
     * @param picturePath The file path of the picture.
     * @param caption     The caption of the picture.
     * @throws IOException If there is an issue loading the picture from the path.
     */
    public Picture(String picturePath, String caption) throws IOException {
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

    /**
     * Loads the image from the specified file path and converts it to a JavaFX Image.
     *
     * @param filePath The path to the image file.
     * @throws IOException If the file does not exist or is not a valid image.
     */
    public void loadPicture(String filePath) throws IOException {
        try {
            // Check if the file exists
            if (!Files.exists(Paths.get(filePath))) {
                throw new IOException("File not found: " + filePath);
            }

            // Attempt to load the image from the file path
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            if (bufferedImage == null) {
                // The file is not an image or is not recognized as one
                throw new IOException("The file is not an image or cannot be recognized as an image.");
            }
            this.image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            // Handle the case where the image cannot be loaded
            throw e;
        }
    }



    // Getter for the JavaFX image
    public Image getImage() {
        return image;
    }


}

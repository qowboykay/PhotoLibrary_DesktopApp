package photos.app;

import java.util.*;

public class Picture {
    
    private String picturePath;
    private String caption;
    private ArrayList<Tag> tags;
    private Calendar cal;

    public Picture(String picturePath, String caption){
        this.picturePath = picturePath;
        this.caption = caption;
        this.tags = new ArrayList<>();
    }

    public String getPicturePath(){
        return picturePath;
    }

    public String getCaption(){
        return caption;
    }

    public void recaptionPhoto(String caption){
        this.caption = caption;
    }

    public Date getDate(){
        cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();

    }

    public void addTag(String tagName, String tagValue){
        Tag newTag = new Tag(tagName);
        newTag.addTagValue(tagValue);
    }

}

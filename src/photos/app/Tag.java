package photos.app;

import java.util.*;

public class Tag {
    private String tagName;
    private ArrayList<String> tagValues;

    public Tag(String tagName){
        this.tagName = tagName;
        this.tagValues = new ArrayList<>();
    }

    public String getTagName(){
        return tagName;
    }
    public void addTagValue(String tagValue){
        tagValues.add(tagValue);
    }

    public ArrayList<String> getAllTagValues(){
        return tagValues;
    }

    public void removeTagValue(String tagValue){
        tagValues.removeIf(element -> equals(tagValue));
        
    }
}

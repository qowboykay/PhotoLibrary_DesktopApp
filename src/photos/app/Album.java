package photos.app;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Album implements Serializable {

    static final long serialVersionUID = 1L;
    private String albumName;
    private ArrayList<Picture> pics;

    public Album(String albumName){
        this.albumName = albumName;
        this.pics = new ArrayList<>();
    }

    public void setAblum(String albumName){
        this.albumName = albumName;

    }

    public String getAlbumName(){
        return albumName;
    }
/**
 * This method allows you to set your album name
 * @param albumName
 */
    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }

/**
 * This adds a picture to the specified album, a new picture must be initialized before adding.
 * @param pic
 */
    public void addPicture(Picture pic){
        this.pics.add(pic);
    }
/**
 * This method returns the list of pictures in an album as an ArrayList.
 * @return
 */
    public ArrayList<Picture> returnPictures(){
        return pics;
    }

    // Method to search photos by date range
    public List<Picture> searchPicturesByDate(Calendar startDate, Calendar endDate) {
        return pics.stream()
                .filter(picture -> picture.isWithinDateRange(startDate, endDate))
                .collect(Collectors.toList());
    }

    // Method to search photos by a single tag-value pair
    public List<Picture> searchPicturesByTag(String tagName, String tagValue) {
        return pics.stream()
                .filter(picture -> picture.hasTag(tagName, tagValue))
                .collect(Collectors.toList());
    }

    public List<Picture> searchPicturesByTagsConjunctive(Map<String, String> tags) {
        return pics.stream()
                .filter(picture -> tags.entrySet().stream()
                        .allMatch(entry -> picture.hasTag(entry.getKey(), entry.getValue())))
                .collect(Collectors.toList());
    }

    public List<Picture> searchPicturesByTagsDisjunctive(Map<String, String> tags) {
        return pics.stream()
                .filter(picture -> tags.entrySet().stream()
                        .anyMatch(entry -> picture.hasTag(entry.getKey(), entry.getValue())))
                .collect(Collectors.toList());
    }
    public Album createAlbumFromSearchResults(String newAlbumName, List<Picture> searchResults) {
        Album newAlbum = new Album(newAlbumName);
        for (Picture pic : searchResults) {
            newAlbum.addPicture(pic);
        }
        return newAlbum;
    }

    @Override
    public String toString() {
        return albumName;
    }

}


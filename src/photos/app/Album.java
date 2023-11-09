package photos.app;

import java.io.*;
import java.util.*;

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
}

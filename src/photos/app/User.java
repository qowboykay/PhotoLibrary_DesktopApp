package photos.app;


import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class User implements Serializable{

    static final long serialVersionUID = 1L;
    private String username; 
    private String password;
    private final ArrayList<Album> albumList;
    

    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.albumList = new ArrayList<>();
    }

    public void setNewUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public void addAlbum(Album album){
        this.albumList.add(album);
    }

    public int getNumberofAlbums(){
        return albumList.size();
    }
    
    public ArrayList<Album> getListOfUserAlbums(){
       return albumList;
    }
    
    @Override
    public String toString(){
        return username;
    }

    // Method to search photos by date across all albums
    public List<Picture> searchPicturesByDate(Calendar startDate, Calendar endDate) {
        return albumList.stream()
                .flatMap(album -> album.searchPicturesByDate(startDate, endDate).stream())
                .collect(Collectors.toList());
    }

    // Method to search photos by tag across all albums
    public List<Picture> searchPicturesByTag(String tagName, String tagValue) {
        return albumList.stream()
                .flatMap(album -> album.searchPicturesByTag(tagName, tagValue).stream())
                .collect(Collectors.toList());
    }
    public List<Picture> searchPicturesByTagsConjunctive(String tag1, String value1, String tag2, String value2) {
        List<Picture> result = new ArrayList<>();
        Map<String, String> tags = new HashMap<>();
        tags.put(tag1, value1);
        tags.put(tag2, value2);

        for (Album album : albumList) {
            result.addAll(album.searchPicturesByTagsConjunctive(tags));
        }
        return result;
    }

    public List<Picture> searchPicturesByTagsDisjunctive(String tag1, String value1, String tag2, String value2) {
        List<Picture> result = new ArrayList<>();
        Map<String, String> tags = new HashMap<>();
        tags.put(tag1, value1);
        tags.put(tag2, value2);

        for (Album album : albumList) {
            result.addAll(album.searchPicturesByTagsDisjunctive(tags));
        }
        return result;
    }


}

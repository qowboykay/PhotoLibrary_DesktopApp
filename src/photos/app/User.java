package photos.app;

import java.util.ArrayList;

public class User {

    private String username; 
    private final ArrayList<Album> albumList;

    public User(String username){
        this.username = username;
        this.albumList = new ArrayList<>();
    }

    public void setNewUsername(String username){
        this.username = username;
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
        return "User(" + "username=" + username + ")";
    }
}

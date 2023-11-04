package photos.app;

import java.util.ArrayList;

public class User {

    private String username; 
    private final ArrayList<Albums> album;

    public User(String username){
        this.username = username;
        this.album= new ArrayList<>();
    }

    public void setNewUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void addAlbum(Albums album){
        this.album.add(album);
    }

    public int getNumberofAlbums(){
        return this.album.size();
    }
    
    public ArrayList<Albums> getListOfUserAlbums(){
       return this.album;
    }
    
    @Override
    public String toString(){
        return "User(" + "username=" + username + ")";
    }
}

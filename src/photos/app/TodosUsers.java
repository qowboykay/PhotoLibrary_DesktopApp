package photos.app;

import java.io.*;
import java.util.*;

public class TodosUsers implements Serializable {

    private ArrayList<User> allUsers;
    static final long serialVersionUID = 1L;
    public static final String storeFile= "filestorage.dat";

    public TodosUsers(){
        this.allUsers = new ArrayList<>();

    }
    
}

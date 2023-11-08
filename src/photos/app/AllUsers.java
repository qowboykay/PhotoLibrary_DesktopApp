package photos.app;

import java.io.*;
import java.util.*;

public class AllUsers implements Serializable {

    private ArrayList<User> allUsers;
    static final long serialVersionUID = 1L;
    public static final String storeFile= "filestorage.dat";

    public AllUsers(){
        this.allUsers = new ArrayList<>();
    }
    
    public void addUser(User user){
        this.allUsers.add(user);
    }

    public void removeUser(String username){
        allUsers.removeIf(user -> user.getUsername().equals(username));
    }

    public User searchForUser(String username){
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user; 
            }
        }
        return null;
    }

    public void editUsername(String Old, String New){
        for (User user : allUsers) {
            if (user.getUsername().equals(Old)) {
                user.setNewUsername(New);
            }
        }
    }
    public static void setAllUsers(AllUsers allUsers) throws IOException, IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile))) {
            oos.writeObject(allUsers);
        }
    }

    public static AllUsers getAllUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile))) {
            AllUsers allUsers = (AllUsers)ois.readObject();
            return allUsers;
        }
   
    }
}
    

package photos.app;

import java.io.*;
import java.util.ArrayList;

public class AllUsers implements Serializable {

    private ArrayList<User> allUsers;
    static final long serialVersionUID = 1L;
    public static final String storeFile = "filestorage.dat";
    /**
     * This method is the constructor of AllUsers
     */
    public AllUsers() {
        this.allUsers = new ArrayList<>();
    }
    /**
     * This method add the specified user into the allUsers arraylist
     * @param user
     */
    public void addUser(User user) {
        this.allUsers.add(user);
    }
    /**
     * This method removes the specified user using the username
     * @param username
     */
    public void removeUser(String username) {
        allUsers.removeIf(user -> user.getUsername().equals(username));
    }
    /**
     * This method is used to search for a user using the user's username
     * @param username
     * @return
     */
    public User searchForUser(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    /**
    * This method is used to change the older username to the new username
    * @param oldUsername
    * @param newUsername
    */
    public void editUsername(String oldUsername, String newUsername) {
        for (User user : allUsers) {
            if (user.getUsername().equals(oldUsername)) {
                user.setNewUsername(newUsername);
            }
        }
    }

    /**
     * Serializes and saves the current state of all users to a file.
     * @throws IOException If an I/O error occurs during writing to the file.
     */
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile))) {
            oos.writeObject(this);
        }
    }
    /**
     * This method is used to obtain all the users that have been saved in the file storage
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static AllUsers getAllUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile))) {
            return (AllUsers) ois.readObject();
        }
    }
    /**
     * This method returns all the users in the allUsers arraylist
     * @return
     */
    public ArrayList<User> getUserList() {
        return allUsers;
    }
    /**
     * This method returns true if the userlist is empty, else it returns false
     * @return
     */
    public boolean isEmpty() {
        return allUsers.isEmpty();
    }
}

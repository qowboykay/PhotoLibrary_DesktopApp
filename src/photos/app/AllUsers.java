package photos.app;

import java.io.*;
import java.util.ArrayList;

public class AllUsers implements Serializable {

    private ArrayList<User> allUsers;
    static final long serialVersionUID = 1L;
    public static final String storeFile = "filestorage.dat";

    public AllUsers() {
        this.allUsers = new ArrayList<>();
    }

    public void addUser(User user) {
        this.allUsers.add(user);
    }

    public void removeUser(String username) {
        allUsers.removeIf(user -> user.getUsername().equals(username));
    }

    public User searchForUser(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

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

    public static AllUsers getAllUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile))) {
            return (AllUsers) ois.readObject();
        }
    }

    public ArrayList<User> getUserList() {
        return allUsers;
    }

    public boolean isEmpty() {
        return allUsers.isEmpty();
    }
}

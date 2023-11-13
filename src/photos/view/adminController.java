package photos.view;

import photos.app.AllUsers;
import photos.app.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

public class adminController {

    private AllUsers allUsers;

    public adminController(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Lists all users in the system.
     *
     * @return List of usernames.
     */
    public List<String> listUsers() {
        return allUsers.getUserList().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }


    /**
     * Creates a new user with the specified username.
     *
     * @param username The username of the new user.
     * @throws IOException If an I/O error occurs.
     */
    public void createUser(String username) throws IOException {
        User newUser = new User(username, "defaultPassword");
        allUsers.addUser(newUser);
        allUsers.saveData();
    }

    /**
     * Deletes an existing user with the specified username.
     *
     * @param username The username of the user to delete.
     * @throws IOException If an I/O error occurs.
     */
    public void deleteUser(String username) throws IOException {
        allUsers.removeUser(username);
        allUsers.saveData();
    }

    /**
     * Saves the current state of all users. Calls the saveData method from the AllUsers class.
     * @throws IOException If an I/O error occurs.
     */
    private void saveData() throws IOException {
        allUsers.saveData(); // Call the saveData method from the AllUsers class.
    }

}

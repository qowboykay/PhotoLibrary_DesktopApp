package photos.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import photos.app.*;


public class loginController {

    
    private AllUsers allUsers;

    private Label label;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;

    @FXML 
    private PasswordField password;

    @FXML
    private Button loginButton;

    public loginController(){
        allUsers = new AllUsers();
    }
    /**
     * This method handles the login button being pressed
     * @param event
     * @throws Exception
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception{
        initialize();
        String user = username.getText();
        String pass = password.getText();
        if(allUsers.searchForUser(user) != null && allUsers.searchForUser(user).getPassword().equals(pass)){
            if(user.equals("admin")){
                loginForAdmin(event,allUsers);
            }
                else{
                    loginForUsers(event,allUsers.searchForUser(user),allUsers);
                }
        }
        else{
            Alert incorrectAlert = new Alert(AlertType.ERROR, "Username or Password is incorrect", ButtonType.OK);
            incorrectAlert.showAndWait();
        }
    }
    /**
     * This method loads an album list view that lists all the album that the user that logged in owns, it takes an instance of the current user and allusers list.
     * @param event
     * @param user
     * @param allUsers
     * @throws Exception
     */
    public void loginForUsers(ActionEvent event, User user, AllUsers allUsers) throws Exception{
        albumsListController album = new albumsListController(user,allUsers);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/albumsList.fxml"));
        loader.setController(album);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Welcome to your Albums" + " " + username.getText() + "!");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    /**
     * This method loads the admin controller for the login of an admin, it takes a userList.
     * @param event
     * @param userList
     * @throws Exception
     */
    public void loginForAdmin(ActionEvent event,AllUsers userList) throws Exception{
        adminController admin = new adminController(userList);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/photos/view/admin.fxml"));
        loader.setController(admin);
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin Window");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

/**
 * This method initializes when the login controller is opened and sets the default user and admin if not already set.
 * @throws IOException
 * @throws ClassNotFoundException
 */
    public void initialize() throws IOException, ClassNotFoundException {
        allUsers = AllUsers.getAllUsers();
        if(!allUsers.getUserList().stream().anyMatch(user -> user.getUsername().equals("stock"))){
            User newUser = new User("stock","stock");
            User admin = new User("admin","admin");
            Tag tag1 = new Tag("animal");
            tag1.addTagValue("duck");
            Tag tag2 = new Tag("animal");
            tag2.addTagValue("cat");
            Tag tag3  = new Tag("animal");
            tag3.addTagValue("mouse");
            Tag tag4 = new Tag("animal");
            tag4.addTagValue("squid");
            Tag tag5 = new Tag("place");
            tag5.addTagValue("wano");
            Tag tag6 = new Tag("spooky");
            tag6.addTagValue("skeleton");
            Tag tag7 = new Tag("spooky");
            tag7.addTagValue("creeper");
            Tag tag8 = new Tag("cute");
            tag8.addTagValue("hair");
            Tag tag9 = new Tag("person");
            tag9.addTagValue("meowth");
            Tag tag10 = new Tag("person");
            tag10.addTagValue("boss");
            Tag tag11 = new Tag("place");
            tag11.addTagValue("minecraft");
            Tag tag12 = new Tag("person");
            tag12.addTagValue("zucker");
            Album newAlbum = new Album("stock");
            Picture duck = new Picture("data/ducky.jpg","ducky"); 
            duck.addTag(tag1);
            duck.addTag(tag8);
            Picture meowth = new Picture("data/meowth.jpg","meowth");
            meowth.addTag(tag2);
            meowth.addTag(tag9);
            Picture boss = new Picture("data/boss.jpg","boss");
            boss.addTag(tag3);
            boss.addTag(tag10);
            Picture creeper = new Picture("data/creeper.jpg","creeper");
            creeper.addTag(tag7);
            creeper.addTag(tag11);
            Picture rubberducky = new Picture("data/rubberducky.jpg","rubberducky");
            rubberducky.addTag(tag1);
            Picture skelly = new Picture("data/skelly.jpg","skelly");
            skelly.addTag(tag6);
            Picture wano = new Picture("data/wano.jpg","wano");
            wano.addTag(tag5);
            Picture zucker = new Picture("data/zucker.jpg","zucker");
            zucker.addTag(tag4);
            zucker.addTag(tag12);
            newAlbum.addPicture(duck);
            newAlbum.addPicture(meowth);
            newAlbum.addPicture(boss);
            newAlbum.addPicture(creeper);
            newAlbum.addPicture(rubberducky);
            newAlbum.addPicture(skelly);
            newAlbum.addPicture(wano);
            newAlbum.addPicture(zucker);
            newUser.addAlbum(newAlbum);
            allUsers.addUser(newUser);
            allUsers.addUser(admin);
            allUsers.saveData(); // Use the saveData method
        } else {
            allUsers = AllUsers.getAllUsers();
        }
    }


}
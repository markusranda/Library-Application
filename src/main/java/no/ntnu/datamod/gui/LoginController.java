package no.ntnu.datamod.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private DatabaseClient databaseClient;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        setKeyAndClickListeners();
    }

    /**
     * Tries to login with the field values.
     */
    private void doLogin(Event event){

        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            if (databaseClient.tryLogin(username, password)) {
                try {
                    // Set current user
                    Model.getInstance().currentUser().setUsername(username);

                    Parent mainMenuParent;
                    mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenu.fxml")));
                    Scene scene = new Scene(mainMenuParent);
                    scene.getStylesheets().add("styles/style.css");
                    // This line gets the Stage information
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setTitle("Library Application - Main Menu");

                    window.setScene(scene);
                    window.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // TODO: 22.11.2018 Add real feedback to the user.
                System.out.println("WRONG USERNAME OR PASSWORD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        loginBtn.setOnMouseClicked(this::doLogin);
        passwordField.setOnAction(this::doLogin);
    }
}

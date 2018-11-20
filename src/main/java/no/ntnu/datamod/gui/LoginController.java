package no.ntnu.datamod.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setKeyAndClickListeners();
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        loginBtn.setOnMouseClicked(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            //Todo
            // Use the strings username and password to authenticate with the database.
            // Then change to the welcomeWindow with buttons unlocked corresponding to the usertype.
            try {
                Parent mainMenuParent;
                mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenu.fxml")));
                Scene scene = new Scene(mainMenuParent);
                // This line gets the Stage information
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Library Leopard Leo - Main Menu");
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

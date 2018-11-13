package no.ntnu.datamod.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private void setKeyAndClickListeners() {
        loginBtn.setOnMouseClicked(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            //Todo
            // Use the strings username and password to authenticate with the database.
            // Then change to the welcomeWindow with buttons unlocked corresponding to the usertype.
            try {
                Parent welcomeParent;
                welcomeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("welcomeWindow.fxml")));
                Scene scene = new Scene(welcomeParent);
                // This line gets the Stage information
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Library Leopard Leo - Welcome");
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

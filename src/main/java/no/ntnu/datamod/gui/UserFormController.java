package no.ntnu.datamod.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.datamod.logic.DatabaseClient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usertypeField;

    @FXML
    private Button createUserBtn;

    @FXML
    private Button cancelBtn;


    private DatabaseClient databaseClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        buildUserForm();
        setKeyAndClickListeners();
    }


    /**
     * Sets up this form.
     */
    private void buildUserForm() {

    }

    /**
     * Sets up mouse and keyboard listening.
     */
    private void setKeyAndClickListeners() {
        createUserBtn.setOnMouseClicked(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String usertype = usertypeField.getText();

            try {
                // TODO: 28.11.2018 Check if the user exists, if it exists display this to the user.
                databaseClient.addUserToDatabase(username, password, usertype);

            } catch (SQLException e) {

                e.printStackTrace();
            }

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        });
        cancelBtn.setOnMouseClicked(event -> {

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        });
    }
}

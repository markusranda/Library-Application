package no.ntnu.datamod.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.datamod.logic.DatabaseClient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthorFormController implements Initializable {


    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button createAuthorBtn;

    @FXML
    private Button cancelBtn;


    private DatabaseClient databaseClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        buildAuthorForm();
        setKeyAndClickListeners();
    }


    /**
     * Sets up this form.
     */
    private void buildAuthorForm() {

    }

    /**
     * Sets up mouse and keyboard listening.
     */
    private void setKeyAndClickListeners() {
        createAuthorBtn.setOnMouseClicked(event -> {
            String firstname = firstnameField.getText();
            String lastname = lastnameField.getText();

            try {
                // TODO: 30.11.2018 Check if the author exists, if it exists display this to the author.
                databaseClient.addAuthorToDatabase(firstname, lastname);

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

package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.User;
import no.ntnu.datamod.logic.AutoCompleteComboBoxListener;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField accNumField;

    @FXML
    private TextField ssnField;

    @FXML
    private TextField phoneNumField;

    @FXML
    private TextField positionField;

    @FXML
    private ComboBox<String> branchList;

    @FXML
    private ComboBox<String> userList;

    @FXML
    private Button addNewUser = new Button();

    @FXML
    private Button createEmployeeBtn = new Button();

    @FXML
    private Button cancelBtn = new Button();

    private String selectedUser;
    private String selectedBranch;
    private DatabaseClient databaseClient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        buildEmployeeForm();
        setKeyAndClickListeners();
    }

    /**
     * Sets up the EmployeeForm
     */
    private void buildEmployeeForm() {
        updateUserlist();
        try {
            ArrayList<Branch> branchArrayList = databaseClient.getBranchList();

            // Adds all the branches to the comboboxes
            for (Branch branch : branchArrayList) {
                branchList.getItems().add(branch.getIdBranch() + " - " + branch.getName());
            }

            new AutoCompleteComboBoxListener<>(branchList);

            branchList.setVisibleRowCount(5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the comboboxes with information from the database
     */
    private void updateUserlist() {
        try {
            userList.getItems().clear();
            ArrayList<User> userArrayList = databaseClient.getUsersList();

            // Adds all the users to the comboboxes
            for (User user : userArrayList) {
                userList.getItems().add(user.getUsername());
            }

            new AutoCompleteComboBoxListener<>(userList);

            userList.setVisibleRowCount(5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    private void setKeyAndClickListeners() {

        createEmployeeBtn.setOnMouseClicked(event -> {

            try {
                selectedUser = userList.getValue();
                selectedBranch = branchList.getValue();
                String[] splittedBranchString = selectedBranch.split("\\ - ");
                int branchId = Integer.valueOf(splittedBranchString[0]);

                int employeeID = databaseClient.addEmployeeToDatabase(
                        fnameField.getText(), lnameField.getText(), addressField.getText(),
                        phoneNumField.getText(), accNumField.getText(), ssnField.getText(), positionField.getText(), branchId);

                databaseClient.addEmployeeUserJunctionToDatabase(selectedUser, employeeID);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        cancelBtn.setOnMouseClicked(event -> {
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        });
        addNewUser.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                try {
                    new UserFormApp().start(new Stage());
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            });
        });
        userList.setOnMouseClicked(event -> updateUserlist());
    }

}

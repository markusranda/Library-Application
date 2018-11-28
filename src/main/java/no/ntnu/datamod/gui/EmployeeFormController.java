package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.User;
import no.ntnu.datamod.logic.DatabaseClient;

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
    private MenuButton branchList;

    @FXML
    private MenuButton userList;

    @FXML
    private Button addNewUser = new Button();

    @FXML
    private Button addNewBranch = new Button();

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

        try {
            // Sets up the user chooser
            userList.setText("Choose User..");
            // TODO: 28.11.2018 Only retrieve users that doesnt already have an Employee/Customer
            ArrayList<User> userArrayList = databaseClient.getUsersList();

            for (User user : userArrayList) {
                String username = user.getUsername();

                // Create menuItem with username
                MenuItem menuItem = new MenuItem(username);

                // Add menuItem to MenuButton
                userList.getItems().add(menuItem);

                // Add eventlistener to the menuItem
                menuItem.setOnAction(event -> {
                    userList.setText(username);
                    selectedUser = username;
                });
            }

            // Sets up the Branch chooser
            branchList.setText("Choose Branch..");
            ArrayList<Branch> branchArrayList = databaseClient.getBranchList();

            for (Branch branch : branchArrayList) {
                String branchNameWithID = branch.getIdBranch() + " - " + branch.getName();

                // Create menuItem with username
                MenuItem menuItem = new MenuItem(branchNameWithID);

                // Add menuItem to MenuButton
                branchList.getItems().add(menuItem);

                // Add eventlistener to the menuItem
                menuItem.setOnAction(event -> {
                    branchList.setText(branchNameWithID);
                    selectedBranch = branchNameWithID;
                });
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    private void setKeyAndClickListeners() {
        createEmployeeBtn.setOnMouseClicked(event -> {
            String[] splittedBranchString = selectedBranch.split("\\ - ");
            int branchId = Integer.valueOf(splittedBranchString[0]);
            try {
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
    }

}

package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Author;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Genre;
import no.ntnu.datamod.logic.AutoCompleteComboBoxListener;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookFormController implements Initializable {

    @FXML
    private TextField titleField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField imageField;

    @FXML
    private ComboBox<String> authorList;

    @FXML
    private ComboBox<String> genreList;

    @FXML
    private Spinner<Integer> quantityField;

    @FXML
    private ComboBox<String> branchList;

    @FXML
    private Button addNewAuthor = new Button();

    @FXML
    private Button createBookBtn = new Button();

    @FXML
    private Button cancelBtn = new Button();

    private String selectedAuthor;
    private String selectedBranch;
    private String selectedGenre;
    private DatabaseClient databaseClient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        buildBookForm();
        setKeyAndClickListeners();
    }

    /**
     * Sets up the EmployeeForm
     */
    private void buildBookForm() {

        try {
            //updateUserlist();

            // Sets up the Branch chooser
            ArrayList<Author> authorArrayList = databaseClient.getAuthorList();
            ArrayList<Branch> branchArrayList = databaseClient.getBranchList();
            ArrayList<Genre> genreArrayList = databaseClient.getGenreList();

            for (Branch branch : branchArrayList) {
                branchList.getItems().add(branch.getIdBranch() + " - " + branch.getName());
            }

            for (Author author : authorArrayList) {
                authorList.getItems().add(author.getIdAuthors() + " - " + author.getFName() + " " + author.getLName());
            }

            for (Genre genre : genreArrayList){
                genreList.getItems().add(genre.getIdGenre() + " - " + genre.getName());
            }

            new AutoCompleteComboBoxListener<>(branchList);
            new AutoCompleteComboBoxListener<>(authorList);

            branchList.setVisibleRowCount(5);
            authorList.setVisibleRowCount(5);

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    private void setKeyAndClickListeners() {

        createBookBtn.setOnMouseClicked(event -> {

            try {
                selectedAuthor = authorList.getValue();
                selectedBranch = branchList.getValue();
                selectedGenre = genreList.getValue();

                String[] splittedAuthorString = selectedAuthor.split("\\ - ");
                String[] splittedBranchString = selectedBranch.split("\\ - ");
                String[] splittedGenreString = selectedGenre.split("\\ - ");

                int branchId = Integer.valueOf(splittedBranchString[0]);
                int authorID = Integer.valueOf(splittedAuthorString[0]);
                int bookID = databaseClient.addBookToDatabase(titleField.getText(), publisherField.getText(), isbnField.getText(), imageField.getText());
                int genreID = Integer.valueOf(splittedGenreString[0]);

                int quantity = quantityField.getValue();

                databaseClient.addBookQuantityJunctionToDatabase(bookID, branchId, quantity);
                databaseClient.addBookAuthorJunctionToDatabase(bookID, authorID);
                databaseClient.addBookGenreJunctionToDatabase(bookID, genreID);
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
        addNewAuthor.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                try {
                    new AuthorFormApp().start(new Stage());
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            });
        });
    }
}

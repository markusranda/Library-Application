package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Author;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Genre;
import no.ntnu.datamod.logic.AutoCompleteComboBoxListener;
import no.ntnu.datamod.logic.DatabaseClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.nio.file.Files.readAllBytes;

public class BookFormController implements Initializable {

    @FXML
    private TextField titleField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField isbnField;

    @FXML
    private ComboBox<String> authorList;

    @FXML
    private ComboBox<String> genreList;

    @FXML
    private Spinner<Integer> quantityField;

    @FXML
    private ComboBox<String> branchList;

    @FXML
    private Button addNewAuthor;

    @FXML
    private Button createBookBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addImageBtn;

    //test
    @FXML
    private ImageView imageContainer;

    private String selectedAuthor;
    private String selectedBranch;
    private String selectedGenre;
    private DatabaseClient databaseClient;
    private File selectedFile;


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
            selectedFile = null;

            updateAuthorList();

            // Sets up the Branch chooser
            ArrayList<Branch> branchArrayList = databaseClient.getBranchList();
            ArrayList<Genre> genreArrayList = databaseClient.getGenreList();

            for (Branch branch : branchArrayList) {
                branchList.getItems().add(branch.getIdBranch() + " - " + branch.getName());
            }

            for (Genre genre : genreArrayList){
                genreList.getItems().add(genre.getIdGenre() + " - " + genre.getName());
            }

            new AutoCompleteComboBoxListener<>(branchList);

            branchList.setVisibleRowCount(5);
            authorList.setVisibleRowCount(5);

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    /**
     * Updates the Authorlist with authors from the database
     */
    private void updateAuthorList() {
        try {
            authorList.getItems().clear();
            ArrayList<Author> authorArrayList = databaseClient.getAuthorList();

            for (Author author : authorArrayList) {
                authorList.getItems().add(author.getIdAuthors() + " - " + author.getFName() + " " + author.getLName());
            }

            new AutoCompleteComboBoxListener<>(authorList);
            authorList.setVisibleRowCount(5);

        } catch (SQLException e)  {
            e.printStackTrace();
        }

    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    private void setKeyAndClickListeners() {
        addImageBtn.setOnMouseClicked(event -> {

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg"));
            selectedFile = fileChooser.showOpenDialog(window);

            BufferedImage bf;

            if (selectedFile != null) {

                try {
                    bf = ImageIO.read(selectedFile);
                    Image image = SwingFXUtils.toFXImage(bf, null);
                    imageContainer.setImage(image);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

        });
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
                int bookID;
                if (selectedFile != null) {
                    bookID = databaseClient.addBookToDatabase(titleField.getText(), publisherField.getText(), isbnField.getText(), selectedFile);
                } else {
                    bookID = databaseClient.addBookToDatabase(titleField.getText(), publisherField.getText(), isbnField.getText());
                }
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
        authorList.setOnMouseClicked(event -> updateAuthorList());
    }
}

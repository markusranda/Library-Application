package no.ntnu.datamod.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Author;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Customer;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageInventoryController implements Initializable {

    private ObservableList masterData;
    private DatabaseClient databaseClient;


    @FXML
    private Button backBtn;

    @FXML
    private Pane tableContainer;

    @FXML
    private ListView listMenu;



    public void initialize(URL location, ResourceBundle resources) {
        setKeyAndClickListeners();
        databaseClient = new DatabaseClient();

        buildManageInventoryScene();
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        backBtn.setOnMouseClicked(event -> {
            //noinspection Duplicates
            try {
                Parent welcomeParent;
                welcomeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenu.fxml")));
                Scene scene = new Scene(welcomeParent);
                // This line gets the Stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Library Leopard Leo - Welcome");
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    // Create Tables //

    private TableView<Book> createBookTable() {

        try {
            TableView<Book> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getBooksList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Book> filteredData = masterData.filtered(o -> o instanceof Book);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Book, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idBook"));

            TableColumn<Book, String> titleCol = new TableColumn<>("Title");
            titleCol.setMinWidth(300);
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Book, String> publisherCol = new TableColumn<>("Publisher");
            publisherCol.setMinWidth(77);
            publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));

            TableColumn<Book, String> ISBNCol = new TableColumn<>("ISBN");
            ISBNCol.setMinWidth(100);
            ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));



            table.getColumns().addAll(idCol, titleCol, publisherCol, ISBNCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }


    private TableView<Author> createAuthorTable() {

        try {
            TableView<Author> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getAuthorList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Author> filteredData = masterData.filtered(o -> o instanceof Author);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Author, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idAuthors"));

            TableColumn<Author, String> lnameCol = new TableColumn<>("Lastname");
            lnameCol.setMinWidth(300);
            lnameCol.setCellValueFactory(new PropertyValueFactory<>("lName"));

            TableColumn<Author, String> fnameCol = new TableColumn<>("Firstname");
            fnameCol.setMinWidth(77);
            fnameCol.setCellValueFactory(new PropertyValueFactory<>("fName"));

            table.getColumns().addAll(idCol, lnameCol, fnameCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }



    private TableView<Branch> createBranchTable() {

        try {
            TableView<Branch> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getBranchList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Branch> filteredData = masterData.filtered(o -> o instanceof Branch);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Branch, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idBranch"));

            TableColumn<Branch, String> nameCol = new TableColumn<>("Name");
            nameCol.setMinWidth(300);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Branch, String> addressCol = new TableColumn<>("Address");
            addressCol.setMinWidth(77);
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            table.getColumns().addAll(idCol, nameCol, addressCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }


    private TableView<Customer> createCustomerTable() {

        try {
            TableView<Customer> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getCustomerList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Customer> filteredData = masterData.filtered(o -> o instanceof Customer);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Customer, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));

            TableColumn<Customer, String> fnameCol = new TableColumn<>("Firstname");
            fnameCol.setMinWidth(300);
            fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));

            TableColumn<Customer, String> lnameCol = new TableColumn<>("Lastname");
            lnameCol.setMinWidth(77);
            lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));

            TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
            addressCol.setMinWidth(100);
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            TableColumn<Customer, String> phoneCol = new TableColumn<>("Phonenumber");
            phoneCol.setMinWidth(100);
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            table.getColumns().addAll(idCol, fnameCol, lnameCol, addressCol, phoneCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }



    private void buildManageInventoryScene(){

        // Table settings
        Label productLabel = new Label("Produktliste");
        productLabel.setFont(new Font("Arial", 20));


        // Top menu bar
        String showAll = "Everything";
        String bookString = "Booklist";
        String authorString = "Authorlist";
        String branchString = "Branchlist";
        String customerString = "Customerlist";


        ObservableList<String> listItems =FXCollections.observableArrayList (bookString, authorString, branchString, customerString);
        listMenu.setItems(listItems);
        listMenu.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listMenu.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {

                    public void changed(
                            ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        // change the label text value to the newly selected
                        // item.
                        //productLabel.setText("You Selected " + newValue);

                        if(newValue.equals(bookString)){
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createBookTable());
                        }
                        if(newValue.equals(authorString)){
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createAuthorTable());
                        }
                        if(newValue.equals(branchString)){
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createBranchTable());
                        }
                        if(newValue.equals(customerString)){
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createCustomerTable());
                        }

                    }
                });

    }



}

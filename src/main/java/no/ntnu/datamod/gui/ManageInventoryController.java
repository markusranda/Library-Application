package no.ntnu.datamod.gui;

import javafx.application.Platform;
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
import no.ntnu.datamod.data.*;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageInventoryController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Pane tableContainer;

    @FXML
    private ListView listMenu;

    private ObservableList masterData;
    private DatabaseClient databaseClient;
    private String selectedItem;

    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        selectedItem = "";
        buildManageInventoryScene();

        // Listen for user interaction
        setKeyAndClickListeners();
    }



    /**
     * Setup mouse and keyboard event handlers.
     */
    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        backBtn.setOnMouseClicked(event -> {
            try {
                Parent welcomeParent;
                welcomeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenu.fxml")));
                Scene scene = new Scene(welcomeParent);
                scene.getStylesheets().add("styles/style.css");
                // This line gets the Stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Library Leopard Leo - Welcome");
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addBtn.setOnMouseClicked(event -> {
                Platform.runLater(() -> {
                    try {
                        if (selectedItem.equals("employee")) {
                            new EmployeeFormApp().start(new Stage());
                        } else {
                            // TODO: 30.11.2018 Add user feedback telling the user to choose a list
                            System.out.println("please choose a list");
                        }
                        // TODO: 30.11.2018 add more choices like the above one.
                    } catch (IOException e ) {
                        e.printStackTrace();
                    }
                });
        });
    }

    // Create Tables //

    private TableView<Book> createBookTable() {

        try {
            TableView<Book> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getDetailedBooksList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            //FilteredList<Book> filteredData = masterData.filtered(o -> o instanceof Book);
            table.setItems(masterData);
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

            TableColumn<Book, String> authorsCol = new TableColumn<>("Authors");
            authorsCol.setMinWidth(100);
            authorsCol.setCellValueFactory(new PropertyValueFactory<>("Authors"));

            TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
            genreCol.setMinWidth(100);
            genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

            TableColumn<Book, String> quantityCol = new TableColumn<>("Quantity");
            quantityCol.setMinWidth(100);
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            TableColumn<Book, String> branchCol = new TableColumn<>("Branch");
            branchCol.setMinWidth(100);
            branchCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Book, String> idBranchCol = new TableColumn<>("Branch ID");
            idBranchCol.setMinWidth(100);
            idBranchCol.setCellValueFactory(new PropertyValueFactory<>("idBranch"));

            TableColumn<Book, String> imageCol = new TableColumn<>("Image");
            imageCol.setMinWidth(100);
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

            table.getColumns().addAll(idCol, titleCol, authorsCol, publisherCol, ISBNCol, genreCol, idBranchCol, branchCol, quantityCol);
            return table;
        }catch (Exception e){
            e.printStackTrace();
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
            table.setItems(masterData);
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


    private TableView<User> createUserTable() {

        try {
            TableView<User> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getUsersList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<User> filteredData = masterData.filtered(o -> o instanceof User);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<User, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));

            TableColumn<User, String> usernameCol = new TableColumn<>("Username");
            usernameCol.setMinWidth(300);
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<User, String> passwordCol = new TableColumn<>("Password");
            passwordCol.setMinWidth(77);
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

            TableColumn<User, String> usertypeCol = new TableColumn<>("Usertype");
            usertypeCol.setMinWidth(100);
            usertypeCol.setCellValueFactory(new PropertyValueFactory<>("usertype"));

            table.getColumns().addAll(idCol, usernameCol, passwordCol, usertypeCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }


    private TableView<Loan> createLoanTable() {

        try {
            TableView<Loan> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getLoansList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Loan> filteredData = masterData.filtered(o -> o instanceof Loan);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Loan, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idLoan"));

            TableColumn<Loan, String> loanDateCol = new TableColumn<>("Loan date");
            loanDateCol.setMinWidth(77);
            loanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanDate"));

            TableColumn<Loan, String> loanDueCol = new TableColumn<>("Due date");
            loanDueCol.setMinWidth(77);
            loanDueCol.setCellValueFactory(new PropertyValueFactory<>("loanDue"));

            TableColumn<Loan, String> idBookCol = new TableColumn<>("Book ID");
            idBookCol.setMinWidth(30);
            idBookCol.setCellValueFactory(new PropertyValueFactory<>("idBook"));

            TableColumn<Loan, String> idUserCol = new TableColumn<>("Username");
            idUserCol.setMinWidth(30);
            idUserCol.setCellValueFactory(new PropertyValueFactory<>("username"));

            table.getColumns().addAll(idCol, loanDateCol, loanDueCol, idBookCol, idUserCol);
            return table;
        }catch (Exception e){
            return null;
        }
    }


    private TableView<Employee> createEmployeeTable() {

        try {
            TableView<Employee> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getEmployeeList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Employee> filteredData = masterData.filtered(o -> o instanceof Employee);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Employee, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));

            TableColumn<Employee, String> fnameCol = new TableColumn<>("First name");
            fnameCol.setMinWidth(300);
            fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));

            TableColumn<Employee, String> lnameCol = new TableColumn<>("Last name");
            lnameCol.setMinWidth(77);
            lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));

            TableColumn<Employee, String> addressCol = new TableColumn<>("Address");
            addressCol.setMinWidth(150);
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            TableColumn<Employee, String> phoneCol = new TableColumn<>("Phonenumber");
            phoneCol.setMinWidth(100);
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            TableColumn<Employee, String> accountNumberCol = new TableColumn<>("Account number");
            accountNumberCol.setMinWidth(100);
            accountNumberCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

            TableColumn<Employee, String> ssnCol = new TableColumn<>("SSN");
            ssnCol.setMinWidth(100);
            ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));

            TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
            positionColumn.setMinWidth(100);
            positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

            TableColumn<Employee, String> branchColumn = new TableColumn<>("Branch");
            branchColumn.setMinWidth(150);
            branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));

            TableColumn<Employee, String> usernameCol = new TableColumn<>("Username");
            usernameCol.setMinWidth(100);
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));


            table.getColumns().addAll(idCol, fnameCol, lnameCol, addressCol, phoneCol, accountNumberCol, ssnCol, positionColumn, branchColumn, usernameCol);
            table.setPrefSize(1500, 800);
            return table;

        }catch (Exception e){
            return null;
        }
    }


    private TableView<Genre> createGenreTable() {

        try {
            TableView<Genre> table = new TableView<>();
            {
                try {
                    masterData = FXCollections.observableList(databaseClient.getGenreList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Inserts the Data to the table
            FilteredList<Genre> filteredData = masterData.filtered(o -> o instanceof Genre);
            table.setItems(filteredData);
            // Table column variables
            TableColumn<Genre, String> idCol = new TableColumn<>("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(new PropertyValueFactory<>("idGenre"));

            TableColumn<Genre, String> nameCol = new TableColumn<>("Genre name");
            nameCol.setMinWidth(300);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            table.getColumns().addAll(idCol, nameCol);
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
        String userString = "Userlist";
        String loanString = "Loanlist";
        String employeeString = "Employeelist";
        String genreString = "Genrelist";


        ObservableList<String> listItems =FXCollections.observableArrayList (bookString, authorString, branchString, customerString, userString, loanString, employeeString, genreString);
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
                            selectedItem = "book";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createBookTable());
                        }
                        if(newValue.equals(authorString)){
                            selectedItem = "author";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createAuthorTable());
                        }
                        if(newValue.equals(branchString)){
                            selectedItem = "branch";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createBranchTable());
                        }
                        if(newValue.equals(customerString)){
                            selectedItem = "customer";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createCustomerTable());
                        }
                        if(newValue.equals(userString)){
                            selectedItem = "user";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createUserTable());
                        }
                        if(newValue.equals(loanString)){
                            selectedItem = "loan";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createLoanTable());
                        }
                        if(newValue.equals(employeeString)){
                            selectedItem = "employee";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createEmployeeTable());
                        }
                        if(newValue.equals(genreString)){
                            selectedItem = "genre";
                            tableContainer.getChildren().clear();
                            tableContainer.getChildren().add(createGenreTable());
                        }
                    }
                });

    }
}

package no.ntnu.datamod.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import no.ntnu.datamod.data.DetailedLoan;
import no.ntnu.datamod.data.Loan;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyPageController implements Initializable {

    @FXML
    private Pane tableContainer;

    @FXML
    private Button backBtn;

    @FXML
    private Button returnSelectedBook;

    private DatabaseClient databaseClient;
    private TableView<DetailedLoan> detailedLoanTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        setKeyAndClickListeners();
        updateLoanView();
    }

    /**
     * Updates the view of all the users loans with data from the database
     */
    private void updateLoanView() {
        tableContainer.getChildren().clear();
        detailedLoanTable = createLoansTable();
        tableContainer.getChildren().add(detailedLoanTable);
    }

    private TableView<DetailedLoan> createLoansTable() {
        TableView<DetailedLoan> table = new TableView<>();
        table.setEditable(true);

        try {

            ObservableList tableData = FXCollections.observableList(databaseClient.getLoansForUser());

            table.setItems(tableData);

            // Table column variables

            TableColumn<DetailedLoan, String> libraryCol = new TableColumn<>("Library");
            libraryCol.setMinWidth(150);
            libraryCol.setCellValueFactory(new PropertyValueFactory<>("library"));

            TableColumn<DetailedLoan, String> bookTitleCol = new TableColumn<>("Book Title");
            bookTitleCol.setMinWidth(150);
            bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

            TableColumn<DetailedLoan, String> authorsCol = new TableColumn<>("Authors");
            authorsCol.setMinWidth(300);
            authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));

            TableColumn<DetailedLoan, String> loanDateCol = new TableColumn<>("Date Loaned");
            loanDateCol.setMinWidth(77);
            loanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanDate"));

            TableColumn<DetailedLoan, String> loanDueCol = new TableColumn<>("Date Due");
            loanDueCol.setMinWidth(77);
            loanDueCol.setCellValueFactory(new PropertyValueFactory<>("loanDue"));

            TableColumn<DetailedLoan, String> remainingDaysCol = new TableColumn<>("Remaining Days for loan");
            remainingDaysCol.setMinWidth(77);
            remainingDaysCol.setCellValueFactory(new PropertyValueFactory<>("remainingDays"));

            TableColumn<DetailedLoan, String> fineCol = new TableColumn<>("Fine (NOK)");
            fineCol.setMinWidth(77);
            fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));

            table.getColumns().addAll
                    (libraryCol, bookTitleCol, authorsCol, loanDateCol, loanDueCol, remainingDaysCol, fineCol);
            table.setPrefSize(1300,800);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return table;
    }

    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        returnSelectedBook.setOnMouseClicked(event -> {
                DetailedLoan detailedLoan = detailedLoanTable.getSelectionModel().getSelectedItem();
                try {
                    databaseClient.returnBook(detailedLoan);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                updateLoanView();
        });
        backBtn.setOnMouseClicked(event -> {
            try {
                Parent welcomeParent;
                welcomeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("mainMenu.fxml")));
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

package no.ntnu.datamod.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import no.ntnu.datamod.data.Loan;
import no.ntnu.datamod.logic.DatabaseClient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyPageController implements Initializable {

    @FXML
    private Pane tableContainer;

    @FXML
    private Button backBtn;

    private DatabaseClient databaseClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        updateLoanView();
    }

    /**
     * Updates the view of all the users loans with data from the database
     */
    private void updateLoanView() {
        tableContainer.getChildren().clear();
        tableContainer.getChildren().add(createLoansTable());
    }

    private TableView<Loan> createLoansTable() {
        TableView<Loan> table = new TableView<>();
        table.setEditable(true);

        try {

            ObservableList tableData = FXCollections.observableList(databaseClient.getLoansForUser());

            table.setItems(tableData);

            // Table column variables

            TableColumn<Loan, String> libraryCol = new TableColumn<>("Library");
            libraryCol.setMinWidth(300);
            libraryCol.setCellValueFactory(new PropertyValueFactory<>("library"));

            TableColumn<Loan, String> bookTitleCol = new TableColumn<>("Book Title");
            bookTitleCol.setMinWidth(300);
            bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

            TableColumn<Loan, String> authorsCol = new TableColumn<>("Authors");
            authorsCol.setMinWidth(100);
            authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));

            TableColumn<Loan, String> loanDateCol = new TableColumn<>("Date Loaned");
            loanDateCol.setMinWidth(77);
            loanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanDate"));

            TableColumn<Loan, String> loanDueCol = new TableColumn<>("Date Due");
            loanDueCol.setMinWidth(150);
            loanDueCol.setCellValueFactory(new PropertyValueFactory<>("loanDue"));

            TableColumn<Loan, String> remainingDaysCol = new TableColumn<>("Remaining Days for loan");
            remainingDaysCol.setMinWidth(150);
            remainingDaysCol.setCellValueFactory(new PropertyValueFactory<>("remainingDays"));

            TableColumn<Loan, String> fineCol = new TableColumn<>("Fine (NOK)");
            fineCol.setMinWidth(150);
            fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));

            table.getColumns().addAll
                    (libraryCol, bookTitleCol, authorsCol, loanDateCol, loanDueCol, remainingDaysCol, fineCol);


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return table;
    }
}

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

            TableColumn<Loan, String> loanDateCol = new TableColumn<>("Loan Date");
            loanDateCol.setMinWidth(300);
            loanDateCol.setCellValueFactory(new PropertyValueFactory<>("Loan Date"));

            TableColumn<Loan, String> loanDueCol = new TableColumn<>("Due Date");
            loanDueCol.setMinWidth(300);
            loanDueCol.setCellValueFactory(new PropertyValueFactory<>("Due Date"));

            TableColumn<Loan, String> remainingDaysCol = new TableColumn<>("Remaining Days until due");
            remainingDaysCol.setMinWidth(100);
            remainingDaysCol.setCellValueFactory(new PropertyValueFactory<>("Remaining Days until due"));

            TableColumn<Loan, String> bookTitleCol = new TableColumn<>("Book Title");
            bookTitleCol.setMinWidth(77);
            bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("Book Title"));

            TableColumn<Loan, String> libraryCol = new TableColumn<>("Library");
            libraryCol.setMinWidth(150);
            libraryCol.setCellValueFactory(new PropertyValueFactory<>("Library"));

            TableColumn<Loan, String> fineCol = new TableColumn<>("Fine");
            fineCol.setMinWidth(150);
            fineCol.setCellValueFactory(new PropertyValueFactory<>("Fine"));

            table.getColumns().addAll(loanDateCol, loanDueCol, remainingDaysCol, bookTitleCol, libraryCol, fineCol);


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return table;
    }
}

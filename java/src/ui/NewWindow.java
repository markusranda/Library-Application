package ui;

import entities.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewWindow {


    public NewWindow(Stage window) {

        // Tutorial found here: https://docs.oracle.com/javafx/2/ui_controls/table-view.htm

        //Sets title to the window
        window.setTitle("Product list");
        //window.setWidth(1000);
        //window.setHeight(500);

        //Table settings
        Label label = new Label("Bokliste");
        label.setFont(new Font("Arial", 20));
        TableView<Book> table = new TableView<>();
        table.setEditable(true);


        // Adds example Books
        final ObservableList<Book> exampleData = FXCollections.observableArrayList(
                new Book("JBB AS", "Det siste verset", 200.0, "Yaroslav", "1"),
                new Book("JBB AS", "Jamaikansk kultur", 200.0, "Kent", "1"),
                new Book("JBB AS", "Snømannen", 200.0, "Frode Nilsberg", "1"),
                new Book("JBB AS", "Trailertips Med Roald", 200.0, "Roald", "1"),
                new Book("JBB AS", "Kjøtt eller løk", 200.0, "SlakterArild", "1"),
                new Book("JBB AS", "Den komplette skuterguiden", 200.0, "Rainer", "1")
        );


        // Inserts the exampleData to the table
        table.setItems(exampleData);
        // Table column variables
        TableColumn publisherCol = new TableColumn("Utgiver");
        publisherCol.setMinWidth(100);
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));

        TableColumn titleCol = new TableColumn("Tittel");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        TableColumn priceCol = new TableColumn("Pris");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));

        TableColumn authorCol = new TableColumn("Forfatter");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        TableColumn editionCol = new TableColumn("Utgave");
        editionCol.setMinWidth(100);
        editionCol.setCellValueFactory(new PropertyValueFactory<Book, String>("edition"));

        table.getColumns().addAll(publisherCol, titleCol, priceCol, authorCol, editionCol);

        //HBox (invisible box that holds Add and Remove buttons)
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        // Button variables
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        hbox.getChildren().addAll(addButton, removeButton);

        // VBox (box that contains table)
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table, hbox);

        // This does the scene stuff
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        window.setScene(scene);
        window.show();

    }
}

package ui;

import entities.Book;
import entities.Literature;
import entities.Magazine;
import entities.Newspaper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.LiteratureRegistry;

import javax.swing.event.TreeModelEvent;
import java.io.File;
import java.util.Iterator;

public class ApplicationGUI {

    private LiteratureRegistry registry = new LiteratureRegistry();
    private Scene welcomeScene, manageInventoryScene, addProductScene;
    private Button bckBtn, addBtn, manageBtn;
    private InputValidator validator = new InputValidator();
    private Pane tableContainer = new Pane();

    public ApplicationGUI(Stage window) {
        fillLitteratureRegistry();
        buildWelcomeScene();
        buildManageInventoryScene();

        window.setTitle("Lil Leo");
        window.getIcons().add(new Image("/assets/img/Lil_icon.png"));

        //Declare button functionality between scenes
        manageBtn.setOnAction(e -> {
            window.setScene(manageInventoryScene);
            AudioClip rawr = new AudioClip("https://www.google.com/logos/fnbx/animal_sounds/tiger.mp3");
            rawr.play();
        });
        bckBtn.setOnAction(e -> window.setScene(welcomeScene));
        addBtn.setOnAction(e -> addProduct());

        // Initialize the first window in the application
        window.setScene(welcomeScene);
        window.show();
    }

    private void buildWelcomeScene() {
        StackPane root = new StackPane();
        root.setId("backgroundImage");
        welcomeScene = new Scene(root, 700, 750);

        Label welcomeMessage = new Label("Litteraturleoparden Leo");
        welcomeMessage.autosize();

        Button storeBtn = new Button("STORE");
        Button exitBtn = new Button("Exit");
        manageBtn = new Button("MANAGE INVENTORY");
        storeBtn.setPrefSize(150.0, 50.0);
        exitBtn.setPrefSize(90.0, 50.0);
        exitBtn.setOnAction(e -> Platform.exit());
        storeBtn.setOnAction(e -> AlertBox.display());

        VBox vBtns = new VBox();
        vBtns.setSpacing(20);
        vBtns.setAlignment(Pos.CENTER);
        vBtns.getChildren().addAll(
                welcomeMessage, storeBtn, manageBtn, exitBtn);

        // Import stylesheet
        File f = new File("src/assets/css/StyleSheet.css");
        welcomeScene.getStylesheets().clear();
        welcomeScene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        // Packs vBtns into root
        root.getChildren().addAll(vBtns);
    }

    /**
     * Creates the scene buildWelcomeScene with all the buttons and functionality
     * that this scene requires.
     */
    private void buildManageInventoryScene() {
        StackPane manageLayout = new StackPane();
        manageInventoryScene = new Scene(manageLayout, 700, 750);

        // Creating the buttons that will be used
        bckBtn = new Button("Back");
        addBtn = new Button("Add Product");
        Button removeBtn = new Button("Remove product");
        bckBtn.setPrefSize(150.0, 50.0);
        addBtn.setPrefSize(150.0, 50.0);
        removeBtn.setPrefSize(150.0, 50.0);

        // Table settings
        Label label = new Label("Produktliste");
        label.setFont(new Font("Arial", 20));

        //Table container
        tableContainer.getChildren().addAll(BookTable());

        //top menu bar
        MenuItem book = new MenuItem("Bok");
        MenuItem magazine = new MenuItem("Magasin");
        MenuItem newspaper = new MenuItem("Avis");
        newspaper.setOnAction(e -> {
            tableContainer.getChildren().clear();
            tableContainer.getChildren().add(NewspaperTable());
        });
        book.setOnAction(e -> {
            tableContainer.getChildren().clear();
            tableContainer.getChildren().add(BookTable());
        });
        magazine.setOnAction(e -> {
            tableContainer.getChildren().clear();
            tableContainer.getChildren().add(MagazineTable());
        });
        MenuButton typeSelect = new MenuButton("Velg Litteratur-type", null, book, magazine, newspaper);

        HBox menuBar = new HBox();
        menuBar.getChildren().addAll(label, typeSelect);
        // This does the scene stuff
        //Scene manageInventoryScene = new Scene(new Group());
        //((Group) manageInventoryScene.getRoot()).getChildren().addAll(vBox);

        /*TableView<Book> bookTable = new TableView<>();
        bookTable.setEditable(true);

        // Creating all the columns in the table
        TableColumn publisherCol = new TableColumn("Utgiver");
        publisherCol.setMinWidth(100);
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        TableColumn titleCol = new TableColumn("Tittel");
        titleCol.setMinWidth(300);
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TableColumn priceCol = new TableColumn("Pris");
        priceCol.setMinWidth(77);
        priceCol.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));
        TableColumn authorCol = new TableColumn("Forfatter");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        TableColumn editionCol = new TableColumn("Utgave");
        editionCol.setMinWidth(100);
        editionCol.setCellValueFactory(new PropertyValueFactory<Book, String>("edition"));

        // Adds all the colomns to the table
        bookTable.getColumns().addAll(publisherCol, titleCol, priceCol, authorCol, editionCol);*/

        // HBox (invisible box that holds Add and Remove buttons)
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(addBtn, removeBtn, bckBtn);

        // VBox (box that contains table)
        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(menuBar, tableContainer, hbox);

        // Adds vBox to manageLayoutscene
        manageLayout.getChildren().addAll(vBox);


        // Fill the table with books
        //bookTable.setItems(buildBookList());
    }

    private TableView BookTable() {
        TableView<Book> table = new TableView<>();
        table.setEditable(true);
        // Inserts the exampleData to the table
        table.setItems(buildBookList());
        // Table column variables
        TableColumn<Book, String> publisherCol = new TableColumn<>("Utgiver");
        publisherCol.setMinWidth(100);
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Book, String> titleCol = new TableColumn<>("Tittel");
        titleCol.setMinWidth(300);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> priceCol = new TableColumn<>("Pris");
        priceCol.setMinWidth(77);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, String> authorCol = new TableColumn<>("Forfatter");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> editionCol = new TableColumn<>("Utgave");
        editionCol.setMinWidth(100);
        editionCol.setCellValueFactory(new PropertyValueFactory<>("edition"));

        table.getColumns().addAll(publisherCol, titleCol, priceCol, authorCol, editionCol);
        return table;
    }

    private TableView NewspaperTable() {
        TableView<Newspaper> table = new TableView<>();
        table.setEditable(true);
        // Inserts the exampleData to the table
        table.setItems(buildNewspaperList());
        // Table column variables
        TableColumn<Newspaper, String> publisherCol = new TableColumn<>("Utgiver");
        publisherCol.setMinWidth(100);
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Newspaper, String> titleCol = new TableColumn<>("Tittel");
        titleCol.setMinWidth(300);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Newspaper, String> priceCol = new TableColumn<>("Pris");
        priceCol.setMinWidth(77);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Newspaper, String> dateCol = new TableColumn<>("Dato");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Newspaper, String> pagesCol = new TableColumn<>("Sider");
        pagesCol.setMinWidth(100);
        pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));

        TableColumn<Newspaper, String> comicsCol = new TableColumn<>("Tegneserier");
        comicsCol.setMinWidth(100);
        comicsCol.setCellValueFactory(new PropertyValueFactory<>("includesComics"));

        table.getColumns().addAll(publisherCol, titleCol, priceCol, dateCol, pagesCol, comicsCol);
        return table;
    }

    private TableView MagazineTable() {
        TableView<Magazine> table = new TableView<>();
        table.setEditable(true);
        // Inserts the exampleData to the table
        table.setItems(buildMagazineList());
        // Table column variables
        TableColumn<Magazine, String> publisherCol = new TableColumn<>("Utgiver");
        publisherCol.setMinWidth(100);
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Magazine, String> titleCol = new TableColumn<>("Tittel");
        titleCol.setMinWidth(300);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Magazine, String> priceCol = new TableColumn<>("Pris");
        priceCol.setMinWidth(77);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Magazine, String> dateCol = new TableColumn<>("Dato");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Magazine, String> pagesCol = new TableColumn<>("Sider");
        pagesCol.setMinWidth(100);
        pagesCol.setCellValueFactory(new PropertyValueFactory<>("pages"));

        table.getColumns().addAll(publisherCol, titleCol, priceCol, dateCol, pagesCol);
        return table;
    }
    /**
     * Creates an observable list of all the avaiable Books.
     *
     * @return Returns an ObservableList of all the Books.
     */
    private ObservableList<Book> buildBookList() {

        Iterator<Literature> it = this.registry.getIterator();
        ObservableList<Book> tableList = FXCollections.observableArrayList();

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit.getClass().equals(Book.class)) {
                Book book = (Book) lit;
                tableList.add(book);
            }
        }
        return tableList;
    }

    /**
     * Creates an observable list of all the avaiable Magazine.
     *
     * @return Returns an ObservableList of all the Magazine.
     */
    private ObservableList<Magazine> buildMagazineList() {

        Iterator<Literature> it = this.registry.getIterator();
        ObservableList<Magazine> tableList = FXCollections.observableArrayList();

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit.getClass().equals(Magazine.class)) {
                Magazine magazine = (Magazine) lit;
                tableList.add(magazine);
            }
        }
        return tableList;
    }

    /**
     * Creates an observable list of all the avaiable Newspaper.
     *
     * @return Returns an ObservableList of all the Newspaper.
     */
    private ObservableList<Newspaper> buildNewspaperList() {

        Iterator<Literature> it = this.registry.getIterator();
        ObservableList<Newspaper> tableList = FXCollections.observableArrayList();

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit.getClass().equals(Newspaper.class)) {
                Newspaper newspaper = (Newspaper) lit;
                tableList.add(newspaper);
            }
        }
        return tableList;
    }

    /**
     * Creates a new window for adding literature
     */
    private void addProduct() {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add new literature");
        Text scenetitle = new Text("Please select a product");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        //root container
        VBox root = new VBox(8);

        //grid
        GridPane grid = new GridPane();
        initGrid(grid);

        //container for the grid
        Pane formContainer = new Pane();
        formContainer.getChildren().add(grid);

        //menu
        MenuItem book = new MenuItem("Bok");
        MenuItem magazine = new MenuItem("Magasin");
        MenuItem newspaper = new MenuItem("Avis");
        newspaper.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(addNewNewspaper());
        });
        book.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(addNewBook());
        });
        magazine.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(addNewMagazine());
        });
        MenuButton typeSelect = new MenuButton("Velg Litteratur-type", null, book, magazine, newspaper);

        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(scenetitle, typeSelect);

        root.getChildren().addAll(menu, formContainer);

        //Placeholder fields
        //1
        TextField field1 = new TextField();
        field1.setDisable(true);
        grid.add(field1, 0, 1, 2, 1);

        //2
        TextField field2 = new TextField();
        field2.setDisable(true);
        grid.add(field2, 0, 2, 2, 1);

        //3
        TextField field3 = new TextField();
        field3.setDisable(true);
        grid.add(field3, 0, 3, 2, 1);

        //4
        TextField field4 = new TextField();
        field4.setDisable(true);
        grid.add(field4, 0, 4, 2, 1);

        //5
        TextField field5 = new TextField();
        field5.setDisable(true);
        grid.add(field5, 0, 5, 2, 1);


        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * The form in a grid-layout for adding books
     * @return the grid-layout
     */
    private GridPane addNewBook() {
        GridPane grid = new GridPane();
        initGrid(grid);

        Text scenetitle = new Text("Ny bok");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        //1
        Label Publisher = new Label("Utgiver:");
        grid.add(Publisher, 0, 1);

        TextField publisherField = new TextField();
        //publisherField.setPromptText("Utgiver");

        grid.add(publisherField, 1, 1);

        //2
        Label Title = new Label("Tittel:");
        grid.add(Title, 0, 2);

        TextField titleField = new TextField();
        //titleField.setPromptText("Tittel");

        grid.add(titleField, 1, 2);

        //3
        Label Author = new Label("Forfatter:");
        grid.add(Author, 0, 3);

        TextField authorField = new TextField();
        //authorField.setPromptText("Forfatter");

        grid.add(authorField, 1, 3);

        //4
        Label Edition = new Label("Utgave:");
        grid.add(Edition, 0, 4);

        TextField editionField = new TextField();
        //editionField.setPromptText("Utgave");
        grid.add(editionField, 1, 4);

        //5
        Label Price = new Label("Pris:");
        grid.add(Price, 0, 5);

        TextField priceField = new TextField();
        //priceField.setPromptText("Pris");
        grid.add(priceField, 1, 5);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        //Submit btn
        Button submitBtn = new Button("Legg til bok");
        submitBtn.setOnAction(
                e ->
                {
                    String publisher = publisherField.getText();
                    String title = titleField.getText();
                    Double price = Double.valueOf(priceField.getText());
                    String author = authorField.getText();
                    String edition = editionField.getText();
                    registry.addLiterature(new Book(publisher, title, price, author, edition));
                    System.out.println(publisher + ", " + title + ", " + price + ", " + author + ", " + edition);
                    tableContainer.getChildren().add(BookTable());
                    ((Node) (e.getSource())).getScene().getWindow().hide();
                });
        //Cancel btn
        Button exitBtn = new Button("Avbryt");
        exitBtn.setOnAction(
                e ->
                        ((Node) (e.getSource())).getScene().getWindow().hide()
        );

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(exitBtn, submitBtn);
        grid.add(hbBtn, 0, 6, 2, 1);
        grid.setAlignment(Pos.CENTER);

        return grid;
        /*Scene scene = new Scene(grid);
        secondaryStage.setScene(scene);
        secondaryStage.show();*/

    }
    /**
     * The form in a grid-layout for adding Newspapers
     * @return the grid-layout
     */
    private GridPane addNewNewspaper() {
        GridPane grid = new GridPane();
        initGrid(grid);

        Text scenetitle = new Text("Ny avis");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //1 Publisher
        Label publisherLabel = new Label("Utgiver:");
        grid.add(publisherLabel, 0, 1);

        TextField publisherField = new TextField();
        grid.add(publisherField, 1, 1);

        //2 Title
        Label titleLabel = new Label("Tittel:");
        grid.add(titleLabel, 0, 2);

        TextField titleField = new TextField();
        grid.add(titleField, 1, 2);

        //3 Date
        Label dateLabel = new Label("Dato:");
        grid.add(dateLabel, 0, 3);

        DatePicker dateField = new DatePicker();
        grid.add(dateField, 1, 3);

        //4 Pages
        Label pageLabel = new Label("Sider:");
        grid.add(pageLabel, 0, 4);

        TextField pagesField = new TextField();
        grid.add(pagesField, 1, 4);

        //5 Price
        Label priceLabel = new Label("Pris:");
        grid.add(priceLabel, 0, 5);

        TextField priceField = new TextField();
        grid.add(priceField, 1, 5);

        //5 Includes comics
        Label comicLabel = new Label("Tegneserier:");
        grid.add(comicLabel, 0, 6);

        ToggleGroup comicField = new ToggleGroup();
        RadioButton buttonTrue = new RadioButton("true");
        RadioButton buttonFalse = new RadioButton("false");
        buttonFalse.setToggleGroup(comicField);
        buttonTrue.setToggleGroup(comicField);
        HBox toggleBox = new HBox(20, buttonFalse, buttonTrue);
        grid.add(toggleBox, 1, 6);

        //Submit btn


        Button btn = new Button("Legg til avis");
        btn.setOnAction(
                e ->
                {
                    String publisher = publisherField.getText();
                    String title = titleField.getText();
                    String date = dateField.getValue().toString();
                    int pages = Integer.valueOf(pagesField.getText());
                    Double price = Double.valueOf(priceField.getText());
                    Boolean includesComics = Boolean.valueOf(comicField.getSelectedToggle().toString());
                    registry.addLiterature(new Newspaper(publisher, title, price, date, pages, includesComics));
                    System.out.println(date + " " + pages + " " + includesComics);
                    tableContainer.getChildren().add(NewspaperTable());

                    ((Node) (e.getSource())).getScene().getWindow().hide();
                });
        //cancel btn
        Button exitBtn = new Button("Avbryt");
        exitBtn.setOnAction(
                e ->
                        ((Node) (e.getSource())).getScene().getWindow().hide()
        );


        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(exitBtn, btn);
        grid.add(hbBtn, 1, 7);

        return grid;

    }
    /**
     * The form in a grid-layout for adding magazines
     * @return the grid-layout
     */
    private GridPane addNewMagazine() {
        GridPane grid = new GridPane();
        initGrid(grid);
        Text scenetitle = new Text("Nytt magasin");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        //1 Publisher
        Label publisherLabel = new Label("Utgiver:");
        grid.add(publisherLabel, 0, 1);

        TextField publisherField = new TextField();
        grid.add(publisherField, 1, 1);

        //2 Title
        Label titleLabel = new Label("Tittel:");
        grid.add(titleLabel, 0, 2);

        TextField titleField = new TextField();
        grid.add(titleField, 1, 2);

        //3 Date
        Label dateLabel = new Label("Dato:");
        grid.add(dateLabel, 0, 3);

        DatePicker dateField = new DatePicker();
        grid.add(dateField, 1, 3);

        //4 Pages
        Label pageLabel = new Label("Sider:");
        grid.add(pageLabel, 0, 4);

        TextField pagesField = new TextField();
        grid.add(pagesField, 1, 4);

        //5 Price
        Label priceLabel = new Label("Pris:");
        grid.add(priceLabel, 0, 5);

        TextField priceField = new TextField();
        grid.add(priceField, 1, 5);

        //Submit btn
        //String publisher = publisherField.getText();
        //String title = titleField.getText();
        //String date = dateField.getValue().toString();
        //int pages = Integer.valueOf(pagesField.getText());
        //Double price = Double.valueOf(priceField.getText());

        Button btn = new Button("Legg til magasin");
        btn.setOnAction(
                e -> {
                    String publisher = publisherField.getText();
                    String title = titleField.getText();
                    String date = dateField.getValue().toString();
                    int pages = Integer.valueOf(pagesField.getText());
                    Double price = Double.valueOf(priceField.getText());
                    registry.addLiterature(new Magazine(publisher, title, price, date, pages));
                    tableContainer.getChildren().add(MagazineTable());
                    ((Node) (e.getSource())).getScene().getWindow().hide();
                });

        Button exitBtn = new Button("Avbryt");
        exitBtn.setOnAction(
                e ->
                        ((Node) (e.getSource())).getScene().getWindow().hide()
        );

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(exitBtn, btn);
        grid.add(hbBtn, 1, 7, 2, 2);
        grid.setAlignment(Pos.CENTER);

        /*Scene scene = new Scene(grid);
        secondaryStage.setScene(scene);
        secondaryStage.show();*/
        return grid;
    }

    /**
     * Initializes common variables for the grid
     * @param grid
     */
    private void initGrid(GridPane grid) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setMinWidth(300);
        grid.setPadding(new Insets(25, 25, 25, 25));
    }


    private void fillLitteratureRegistry() {
        fillRegistryWithBooks();
        //fillRegistryWithMagazines();
        //fillRegistryWithNewspapers();
        //fillRegistryWithBookseries();
    }

    private void fillRegistryWithBooks() {
        registry.addLiterature
                (new Book("JBB AS", "Det siste verset", 200.0, "Yaroslav", "1"));
        registry.addLiterature
                (new Book("JBB AS", "Jamaikansk kultur", 200, "Kent", "1"));
        registry.addLiterature
                (new Book("JBB AS", "Snømannen", 200, "Frode Nilsberg", "1"));
        registry.addLiterature
                (new Book("JBB AS", "Trailertips Med Roald", 200, "Roald", "1"));
        registry.addLiterature
                (new Book("JBB AS", "Kjøtt eller løk", 200, "SlakterArild", "1"));
        registry.addLiterature
                (new Book("JBB AS", "Den komplette skuterguiden", 200, "Rainer", "1"));
    }
}

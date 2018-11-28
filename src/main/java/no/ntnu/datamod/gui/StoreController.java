package no.ntnu.datamod.gui;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Literature;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class StoreController implements Initializable {

    @FXML
    private ListView<String> shoppingCartListView;

    @FXML
    private VBox scrollPaneContainer;

    @FXML
    private MenuButton branchMenu;

    @FXML
    private Button checkoutBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Text userFeedBack;

    private DatabaseClient databaseClient;
    private HashMap<Literature, Branch> shoppingCartMappings;
    private ObservableList<String> shoppingCartObsList;
    private Branch currentBranch;
    private GridPane literatureTable;
    private ScrollPane tableContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shoppingCartObsList = FXCollections.observableArrayList();
        shoppingCartMappings = new HashMap<>();
        databaseClient = new DatabaseClient();
        literatureTable = new GridPane();
        tableContainer = new ScrollPane();
        scrollPaneContainer.setMargin(tableContainer, new Insets(70,40,70,40));

        fillBranchMenu();
        fillLiteratureTable();
        setKeyAndClickListeners();

        // Places the GridPane -> ScrollPane -> VBox
        tableContainer.setContent(literatureTable);
        scrollPaneContainer.getChildren().add(tableContainer);
    }

    /**
     * Fills the branchComboBox with data from the database
     */
    private void fillBranchMenu() {
        try {
            branchMenu.setText("Choose Library..");
            ArrayList<Branch> branches = databaseClient.getBranchList();
            currentBranch = new Branch(0,"No libraries", "Empty address");
            for (Branch branch : branches) {
                MenuItem menuItem = new MenuItem(branch.getName());
                branchMenu.getItems().add(menuItem);
                menuItem.setOnAction(event -> {
                    currentBranch = branch;
                    branchMenu.setText(branch.getName());
                    fillLiteratureTable();
                });
                currentBranch = branch;
                branchMenu.setText(branch.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the LiteratureTable with data from the database
     */
    private void fillLiteratureTable() {
        try {
            // Clear remove all child objects from the table, if there are any.
            literatureTable.getChildren().clear();
            ArrayList<Book> books = databaseClient.getBooksList();
            Iterator<Book> it = books.iterator();

        int columnIndex = 0;
        int rowIndex = 0;

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit instanceof Book) {
                if (columnIndex > 3) {
                    VBox product = createProduct(lit);
                    literatureTable.add(product, columnIndex, rowIndex,1, 1);
                    columnIndex = 0;
                    rowIndex++;
                }
                else {
                    VBox product = createProduct(lit);
                    literatureTable.add(product, columnIndex, rowIndex,1, 1);
                    columnIndex++;
                }
            }
        }
        literatureTable.setPrefSize( USE_COMPUTED_SIZE , USE_COMPUTED_SIZE );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is a helper-method for the createGridPane() method,
     * that creates each individual object that's going to be placed
     * in the GridPane.
     * @param lit takes a Literature parameter
     * @return returns a VBox fully equipped with a the product's information.
     */
    private VBox createProduct(Literature lit) {
        TextField quantityField = new TextField();
        quantityField.setMaxWidth(60);
        Button loanBtn = new Button("Borrow");
        final double MAX_IMAGE_WIDTH = 190;
        VBox product = new VBox();
        loanBtn.setAlignment(Pos.TOP_RIGHT);
        loanBtn.setOnAction( e -> {

            if (!shoppingCartMappings.containsKey(lit)) {
                shoppingCartMappings.put(lit, currentBranch);
                shoppingCartObsList.add(lit.getTitle());
                shoppingCartListView.setItems(shoppingCartObsList);
            } else {


            }
        });
        Label title;
        if (lit.getTitle().length() > 17) {
            title = new Label(lit.getTitle());
            title.setWrapText(true);
        }
        else {
            title = new Label(lit.getTitle());
        }
        title.setMaxWidth(200);

        // In case the imageUrl field is null, empty or not working,
        // a default image will be set.
        Image productImg;
        try {
            productImg = new Image(lit.getImageURL());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            productImg = new Image("image/default_book_img_01.png");
        }

        long bookID = ((Book) lit).getIdBook();
        long branchID = currentBranch.getIdBranch();
        long quantity = databaseClient.getQuantity(bookID, branchID);
        quantityField.setText(String.valueOf(quantity));
        HBox hBox = new HBox(loanBtn, quantityField);

        ImageView productImgView = new ImageView();
        productImgView.setFitWidth(MAX_IMAGE_WIDTH);
        productImgView.setPreserveRatio(true);
        productImgView.setImage(productImg);
        product.getChildren().addAll(productImgView, title, hBox);
        product.setPrefSize( USE_COMPUTED_SIZE , USE_COMPUTED_SIZE );
        product.setPadding(new Insets(10,8,10,8));
        return product;
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    @SuppressWarnings("Duplicates")
    private void setKeyAndClickListeners() {
        checkoutBtn.setOnMouseClicked(event -> {

            HashMap<Literature, Branch> booksToBeRented = shoppingCartMappings;

            HashMap<Literature, Branch> leftoverBooks = databaseClient.updateQuantity(booksToBeRented);

            databaseClient.createLoans(booksToBeRented);

            if (!leftoverBooks.isEmpty()) {
                StringBuilder books = new StringBuilder();

                for (HashMap.Entry<Literature, Branch> entry : leftoverBooks.entrySet())
                {
                    Book book = (Book) entry.getKey();
                    String title = book.getTitle();
                    books.append(title).append("\n");
                }

                FadeTransition ft = new FadeTransition(Duration.millis(15000), userFeedBack);
                userFeedBack.setText(
                        "These books did not get rented: \n" + books.toString());
                userFeedBack.setStyle("-fx-font: 10 arial;");
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setCycleCount(1);
                ft.play();

            } else {

                FadeTransition ft = new FadeTransition(Duration.millis(7000), userFeedBack);
                userFeedBack.setText("Books was rented successfully");
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setCycleCount(1);
                ft.play();
            }


            shoppingCartObsList.clear();
            shoppingCartMappings.clear();
            fillLiteratureTable();
        });
        backBtn.setOnMouseClicked(event -> {
            //noinspection Duplicates
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

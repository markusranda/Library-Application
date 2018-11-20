package no.ntnu.datamod.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Branch;
import no.ntnu.datamod.data.Literature;
import no.ntnu.datamod.logic.DatabaseClient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class StoreController implements Initializable {

    private DatabaseClient databaseClient;
    private ObservableList<String> shoppingCartObsList;
    private Branch currentBranch;

    @FXML
    private GridPane literatureTable;

    @FXML
    private ListView<String> shoppingCartListView;

    @FXML
    private Button checkoutBtn;

    @FXML
    private MenuButton branchMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shoppingCartObsList = FXCollections.observableArrayList();
        databaseClient = new DatabaseClient();
        fillBranchMenu();
        fillLiteratureTable();
        setKeyAndClickListeners();
    }

    /**
     * Fills the branchComboBox with data from the database
     */
    private void fillBranchMenu() {
        try {
            ArrayList<Branch> branches = databaseClient.getBranchList();
            for (Branch branch : branches) {
                MenuItem menuItem = new MenuItem(branch.getName());
                branchMenu.getItems().add(menuItem);
                menuItem.setOnAction(event -> {
                    currentBranch = branch;
                    branchMenu.setText(branch.getName());
                    fillLiteratureTable();
                });
                currentBranch = branch;
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
            ArrayList<Book> books = databaseClient.getBooksList();
            Iterator<Book> it = books.iterator();

        int columnIndex = 0;
        int rowIndex = 0;

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit instanceof Book) {
                if (columnIndex > 1) {
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
        /*
        // Calculating the required height using the number of rows and a fixed set of pixels
        int height = 0;
        int numberOfElements = registry.getSize();
        int numberOfRows = numberOfElements / 3;
        height = numberOfRows * 185;
        literatureTable.setMinHeight(height);
        */
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
        final double MAX_IMAGE_WIDTH = 200;
        VBox product = new VBox();
        loanBtn.setAlignment(Pos.TOP_RIGHT);
        loanBtn.setOnAction( e -> {
            //todo add loan functionality
            shoppingCartObsList.add(lit.getTitle() + "\n");
            shoppingCartListView.setItems(shoppingCartObsList);
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
            productImg = new Image(((Book) lit).getImageURL());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            productImg = new Image("image/default_store_img.png");
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
        product.setPadding(new Insets(10,10,10,10));
        return product;
    }

    /**
     * Setup mouse and keyboard event handlers.
     */
    private void setKeyAndClickListeners() {
        checkoutBtn.setOnMouseClicked(event -> {
            //TODO Update the database with borrowers name and shoppingcartlist.
            shoppingCartObsList.clear();
        });
    }
}

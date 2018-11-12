package no.ntnu.datamod.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Literature;
import no.ntnu.datamod.logic.LiteratureRegistry;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


public class StoreController implements Initializable {


    @FXML
    private GridPane literatureTable;

    @FXML
    private ListView shoppingCartList;

    private LiteratureRegistry registry = new LiteratureRegistry();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createExampleRegistry();
        fillLiteratureTable();
    }

    /**
     * Creates some example data in the registry for testing purposes.
     */
    private void createExampleRegistry() {
        registry.addLiterature(new Book("Roald As","Svaner og Spader", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Spader og Svaner", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Historien om Sverige", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Vi er alle barn og/eller barnebarn", "Roald", "69"));
        registry.addLiterature(new Book("Roald As","Spanske vitser", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Svaner og Spader", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Spader og Svaner", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Historien om Sverige", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Vi er alle barn og/eller barnebarn", "Roald", "69"));
        registry.addLiterature(new Book("Roald As","Spanske vitser", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Svaner og Spader", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Spader og Svaner", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Historien om Sverige", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Vi er alle barn og/eller barnebarn", "Roald", "69"));
        registry.addLiterature(new Book("Roald As","Spanske vitser", "Roald", "1"));
    }

    /**
     * Fills the LiteratureTable with data from the registry
     */
    private void fillLiteratureTable() {
        Iterator<Literature> it = registry.getIterator();
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
        literatureTable.setHgap(10);
        literatureTable.setVgap(10);    }

    /**
     * This method is a helper-method for the createGridPane() method,
     * that creates each individual object that's going to be placed
     * in the GridPane.
     * @param lit takes a Literature parameter
     * @return returns a VBox fully equipped with a the book's information.
     */
    private VBox createProduct(Literature lit) {
        Button loanBtn = new Button("Borrow");
        final double MAX_IMAGE_WIDTH = 200;
        VBox product = new VBox();
        loanBtn.setAlignment(Pos.TOP_RIGHT);
        Label title;
        if (lit.getTitle().length() > 17) {
            title = new Label(lit.getTitle());
            title.setWrapText(true);
        }
        else {
            title = new Label(lit.getTitle());
        }
        title.setMaxWidth(200);
        HBox hBox = new HBox(loanBtn);

        // In case the imageUrl field is null, empty or not working,
        // a default image will be set.
        Image productImg;
        try {
            productImg = new Image(((Book) lit).getImageURL());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            productImg = new Image("image/default_store_img.png");
        }

        ImageView productImgView = new ImageView();
        productImgView.setFitWidth(MAX_IMAGE_WIDTH);
        productImgView.setPreserveRatio(true);
        productImgView.setImage(productImg);
        product.getChildren().addAll(productImgView, title, hBox);
        product.setPadding(new Insets(80, 10, 10, 10));
        return product;
    }
}

package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Literature;
import no.ntnu.datamod.facade.DatabaseListener;
import no.ntnu.datamod.logic.DatabaseClient;
import no.ntnu.datamod.logic.LiteratureRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GUIController implements DatabaseListener {

    private LiteratureRegistry registry = new LiteratureRegistry();

    @FXML
    private Label serverStatus;

    @FXML
    private Button connectBtn;

    @FXML
    private Button storeBtn;

    @FXML
    private GridPane literatureTable;

    @FXML
    private ListView shoppingCartList;

    @FXML
    private Button loanBtn = new Button("Loan");

    // Interface to the logic
    private DatabaseClient databaseClient;

    /**
     * Called by the FXML loader after the labels declared above are injected.
     */
    public void initialize() {
        databaseClient = new DatabaseClient();
        // Set default values
        /*
        //hostInput.setText("jonoie.com");
        //portInput.setText("1300");
        textOutput.heightProperty().addListener((observable, oldValue, newValue)
               -> outputScroll.setValue(1.0));
        */

        createExampleRegistry();
        setKeyAndClickListeners();
    }

    private void createExampleRegistry() {
        registry.addLiterature(new Book("Roald As","Svaner og Spader", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Spader og Svaner", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Historien om Sverige", "Roald", "1"));
        registry.addLiterature(new Book("Roald As","Vi er alle barn og/eller barnebarn", "Roald", "69"));
        registry.addLiterature(new Book("Roald As","Spanske vitser", "Roald", "1"));
    }

    /**
     * Set up keyboard and mouse event handlers.
     */
    private void setKeyAndClickListeners() {
    connectBtn.setOnMouseClicked(event -> {
            // "Connect" button clicked
            if (!databaseClient.isConnectionActive()) {
                // If the connection is not active, connect to the server
                boolean finish = false;
                while(!finish){
                    setupConnection("192.168.50.50", 3306);
                    connectBtn.setDisable(true);
                    finish = true;
                }
            } else {
                databaseClient.disconnect();
                System.out.println("Connection was not established!");
                // If the connection is already active, this button actually means "Disconnect".
            }
        });
    storeBtn.setOnMouseClicked(event -> {
        Stage storeStage = new Stage();
        try {
            URL url = getClass().getClassLoader().getResource("storeWindow.fxml");
            Scene scene = new Scene(FXMLLoader.load(url));
            scene.getStylesheets().add("styles/style.css");
            storeStage.setTitle("Library Leopard Leo - Store");
            literatureTable = createGridPane();
            literatureTable.
            storeStage.setScene(scene);
            storeStage.show();
        } catch (IOException e) { System.out.println(e.getMessage()); }
    });
    }

    /**
     * This method creates and returns a GridPane that contains all
     * book-objects that lies within the literatureregistry.
     * @return Returns a GridPane fully equipped with all the products of the store.
     */
    private GridPane createGridPane() {
        GridPane table = new GridPane();
        Iterator<Literature> it = registry.getIterator();
        int columnIndex = 0;
        int rowIndex = 0;

        while (it.hasNext()) {
            Literature lit = it.next();
            if (lit instanceof Book) {
                if (columnIndex > 1) {
                    VBox product = createProduct(lit);
                    table.add(product, columnIndex, rowIndex,1, 1);
                    columnIndex = 0;
                    rowIndex++;
                }
                else {
                    VBox product = createProduct(lit);
                    table.add(product, columnIndex, rowIndex,1, 1);
                    columnIndex++;
                }
            }
        }
        table.setHgap(10);
        table.setVgap(10);
        return table;
    }

    /**
     * This method is a helper-method for the createGridPane() method,
     * that creates each individual object that's going to be placed
     * in the GridPane.
     * @param lit takes a Literature parameter
     * @return returns a VBox fully equipped with a the book's information.
     */
    private VBox createProduct(Literature lit) {
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
        return product;
    }

    /**
     * Start a connection to the server: try to connect Socket, log in and start
     * listening for incoming messages
     *
     * @param host Address of Chat server. Can be IP address, can be hostname
     * @param port TCP port for the chat server
     */
    private void setupConnection(String host, int port) {
        serverStatus.setText("Connecting...");
        connectBtn.setText("Connecting");
        System.out.println("Connecting to " + host + ", port " + port);
        // Run the connection in a new background thread to avoid GUI freeze
        Thread connThread = new Thread(() -> {
            boolean connected = databaseClient.connect(host, port);
            if (connected) {
                System.out.println("Connection is established!");
                // Added a change of status in the serverStatus textfield.
                Platform.runLater(() -> serverStatus.setText("Connected"));
            }
            else {
                // Added a change of status in the serverStatus textfield.
                Platform.runLater(() -> serverStatus.setText("Disconnected, " + databaseClient.getLastError()));
                //updateButtons(connected);
            }
            //updateButtons(connected);
        });
        connThread.start();
    }
}

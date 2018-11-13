package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.datamod.data.Book;
import no.ntnu.datamod.data.Literature;
import no.ntnu.datamod.facade.DatabaseListener;
import no.ntnu.datamod.logic.DatabaseClient;
import no.ntnu.datamod.logic.LiteratureRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

public class GUIController implements DatabaseListener, Initializable {


    @FXML
    private Label serverStatus;

    @FXML
    private Button connectBtn;

    @FXML
    private Button storeBtn;

    @FXML
    private Button loanBtn = new Button("Loan");

    private DatabaseClient databaseClient;

    /**
     * Called by the FXML loader after the labels declared above are injected.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseClient = new DatabaseClient();
        setKeyAndClickListeners();
    }

    @FXML
    /**
     * When this method is called it will change the scene to the store page.
     */
    private void openStoreWindow(ActionEvent event) throws IOException {
        Parent storeParent;
        storeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("storeWindow.fxml")));
        Scene scene = new Scene(storeParent);
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Library Leopard Leo - Store");
        window.setScene(scene);
        window.show();
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
                while (!finish) {
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

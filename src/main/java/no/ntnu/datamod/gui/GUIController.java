package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import no.ntnu.datamod.logic.DatabaseClient;

public class GUIController {

    @FXML
    private Label serverStatus;

    @FXML
    private Button connectBtn;

    // Interface to the logic
    private DatabaseClient databaseClient;

    // One background thread will be used to poll for user list every 10 seconds
    private Thread userPollThread;

    /**
     * Called by the FXML loader after the labels declared above are injected.
     */
    public void initialize() {
        databaseClient = new DatabaseClient();
        // Set default values
        //hostInput.setText("jonoie.com");
        //portInput.setText("1300");
        /*textOutput.heightProperty().addListener((observable, oldValue, newValue)
                -> outputScroll.setValue(1.0));
        */
        setKeyAndClickListeners();
    }

    /**
     * Set up keyboard and mouse event handlers.
     */
    private void setKeyAndClickListeners() {
    /*    textInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && event.isShiftDown()) {
                // Shift+Enter pressed
                textInput.setText(textInput.getText() + "\n");
                textInput.requestFocus();
                textInput.end();
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                // Enter pressed, send the message
                submitMessage();
                event.consume();
            }
        });
        submitBtn.setOnMouseClicked(event -> {
            // "Submit" button clicked, send the message
            submitMessage();
            textInput.requestFocus();
        });
    */
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
    /*    loginBtn.setOnMouseClicked(event -> {
            // "Authorize" button clicked
            if (!loginInput.getText().isEmpty()) {
                tcpClient.tryLogin(loginInput.getText());
                username = loginInput.getText();
                loginInput.clear();
            }
            else {
                loginInput.setPromptText("Enter username!");
            }
        });
        helpBtn.setOnMouseClicked(event -> {
            tcpClient.askSupportedCommands();
        });
    */
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

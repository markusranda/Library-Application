package no.ntnu.datamod.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import no.ntnu.datamod.logic.DatabaseClient;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {


    @FXML
    private Label serverStatus;

    @FXML
    private Button connectBtn;

    @FXML
    private Button storeBtn;

    /**
     * Called by the FXML loader after the labels declared above are injected.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setKeyAndClickListeners();
    }

    /**
     * When this method is called it will change the scene to the store page.
     */
    @FXML
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
        });
    }
}

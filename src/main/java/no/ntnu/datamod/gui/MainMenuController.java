package no.ntnu.datamod.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {


    @FXML
    private Button storeBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button manageBtn;

    /**
     * Called by the FXML loader after the labels declared above are injected.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

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
     * Closes the window
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage window = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * When this method is called it will change the scene to the manage inventory page.
     */
    @FXML
    private void openManageInventory(ActionEvent event) throws IOException {
        Parent manageParent;
        manageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("manageInventory.fxml")));
        Scene scene = new Scene(manageParent);
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Library Leopard Leo - Manage inventory");
        window.setScene(scene);
        window.show();
    }

    /**
     * When this method is called it will change the scene to the manage inventory page.
     */
    @FXML
    private void openMyPage(ActionEvent event) throws IOException {
        Parent myPageParent;
        myPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("myPage.fxml")));
        Scene scene = new Scene(myPageParent);
        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Library Leopard Leo - My Page");
        window.setScene(scene);
        window.show();
    }
}

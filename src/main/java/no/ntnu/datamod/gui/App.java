package no.ntnu.datamod.gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

/**
 * Class representing the main JavaFX application.
 *
 * For the assignment you probably don't need to modify anything in this file.
 */
public class App extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called automatically by JavaFX when the application is
     * launched
     *
     * @param primaryStage The main "stage" where the GUI will be rendered
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL r = getClass().getClassLoader().getResource("loginWindow.fxml");
        Parent root = FXMLLoader.load(r);
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("styles/style.css");
        primaryStage.setTitle("Library Leopard Leo - Login screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Converts an Byte Array to with width and height parameters to a image.
     * @param raw
     * @param width
     * @param height
     * @return Returns image.
     */
    static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);

        try {

            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);


        } catch (IOException ex) {

            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

}

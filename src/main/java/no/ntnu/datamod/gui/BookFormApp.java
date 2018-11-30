package no.ntnu.datamod.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BookFormApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL r = getClass().getClassLoader().getResource("bookForm.fxml");
        Parent root = FXMLLoader.load(r);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Add new Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

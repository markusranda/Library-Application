package no.ntnu.datamod.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AuthorFormApp extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        URL r = getClass().getClassLoader().getResource("authorForm.fxml");
        Parent root = FXMLLoader.load(r);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Add new Author");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

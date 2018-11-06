import javafx.application.Application;
import javafx.stage.Stage;
import ui.ApplicationGUI;

/**
 * The Main-class of the application. This class only holds the main()-method
 * to start the application.
 * 
 * @author 
 * @version 
 */
public class Main extends Application
{
    ApplicationGUI newWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        newWindow = new ApplicationGUI(primaryStage);
    }
}

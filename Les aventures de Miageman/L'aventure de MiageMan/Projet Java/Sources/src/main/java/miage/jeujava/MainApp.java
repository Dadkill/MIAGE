package miage.jeujava;

import Models.Zone;
import java.io.FileInputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Lanceur de l'application
 * @author theobredoux
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Les aventures de MiageMan");
        stage.setScene(scene);
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/img/logo.png")));
        stage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class App extends Application{
    @Override
    public void start(Stage parentStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./main.fxml"));
        parentStage.setTitle("The Zhop");

        // parentStage.getIcons().add(new Image("/StudentPortal/src/icons/icon.png"));

        Scene scene = new Scene(root);

        // scene.getStylesheets().add("/StudentPortal/src/css/styles.css");

        parentStage.setScene(scene);
        parentStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

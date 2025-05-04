package app.utilities;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    public static void switchScene(Stage stage, Scene scene) {
        System.out.println("Switching scene");
        stage.setScene(scene);
    }
}

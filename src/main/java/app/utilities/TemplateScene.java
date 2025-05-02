package app.utilities;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TemplateScene {
    // GUI Property Values
    private static final int GUI_WIDTH = 800;
    private static final int GUI_HEIGHT = 700;

    // GUI Layout Objects
    private final BorderPane root = new BorderPane();
    private final VBox centerContainer = new VBox();
    private final HBox topContainer = new HBox();
    private final HBox bottomContainer = new HBox();

    Scene templateScene = new Scene(root, GUI_WIDTH, GUI_HEIGHT);

    // Constructor
    public TemplateScene(Stage stage) {
        // Layout properties
        topContainer.setSpacing(10);
        topContainer.setAlignment(Pos.CENTER);

        centerContainer.setSpacing(10);
        centerContainer.setAlignment(Pos.CENTER);

        bottomContainer.setSpacing(10);
        bottomContainer.setAlignment(Pos.CENTER);

        // Assign Layouts
        root.setTop(topContainer);
        root.setCenter(centerContainer);
        root.setBottom(bottomContainer);

    }

    // Return Scene
    public Scene getTemplateScene() {
        return templateScene;
    }

    // Return Layouts
    public HBox getTopContainer() { return topContainer; }
    public VBox getCenterContainer() { return centerContainer; }
    public HBox getBottomContainer() { return bottomContainer; }

    // Return Scene Dimensions
    public int getGuiWidth() { return GUI_WIDTH; }
    public int getGuiHeight() { return GUI_HEIGHT; }
}

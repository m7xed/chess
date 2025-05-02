package app.utilities;

import javafx.scene.Scene;

import java.util.Objects;

public class StyleManager {
    public static void applyStyles(Scene scene) {
        String cssPath = Objects.requireNonNull(StyleManager.class.getResource("/styles/base-styles.css")).toExternalForm();
        scene.getStylesheets().add(cssPath);
    }
}

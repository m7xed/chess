package app.views;

import app.utilities.StyleManager;
import app.utilities.TemplateScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import static app.utilities.SceneManager.switchScene;

public class TitleScene {

    public static Scene createTitleScene(Stage stage) {
        // Create Template Scene
        TemplateScene templateScene = new TemplateScene(stage);

        // Add Title
        Label title = new Label("Chess");

        // Play Button
        Button play = new Button("Play");
        play.setOnAction(e -> {
            switchScene(stage, new GameScene(stage).getScene());
        });

        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(3);
        slider.setValue(2);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setMaxWidth(templateScene.getGuiWidth() * 0.75);

        slider.setOnMouseReleased(event -> {
            double snappedValue = Math.round(slider.getValue());
            slider.setValue(snappedValue);
        });



        // Add Style
        title.getStyleClass().add("title");

        // Add elements
        templateScene.getTopContainer().getChildren().add(title);
        templateScene.getCenterContainer().getChildren().addAll(play, slider);


        // Convert TemplateScene to Scene
        Scene titleScene = templateScene.getTemplateScene();

        // Apply Stylesheets
        StyleManager.applyStyles(titleScene);

        // Return Scene
        return titleScene;
    }
}

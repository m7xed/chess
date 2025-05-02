package app;

import app.views.TitleScene;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Font Loading
        String fontPath = "/fonts/DSGabriele.ttf";
        Font.loadFont(getClass().getResourceAsStream(fontPath), 48);

        // Stage Properties
        stage.setTitle("Chess");
        stage.setResizable(false);


        stage.setScene(TitleScene.createTitleScene(stage));

        // Display Stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
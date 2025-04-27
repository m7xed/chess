package app;

import app.utilities.TemplateScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Stage Properties
        stage.setTitle("Chess");
        stage.setResizable(false);


        stage.setScene(new TemplateScene(stage).getTemplateScene());



        // Display Stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package app.views;

import app.utilities.StyleManager;
import app.utilities.TemplateScene;
import game.ChessBoard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScene {

    private TemplateScene templateScene;

    private static int turnTime = 300; // 5 minutes in seconds (300 seconds)

    private static boolean isWhiteTurn = true; // Track whose turn it is
    private static Timeline whiteTimer; // White player's clock
    private static Timeline blackTimer; // Black player's clock
    private int whiteTimeLeft = turnTime; // Time remaining for white
    private int blackTimeLeft = turnTime; // Time remaining for black

    private Label whiteClockLabel; // Label for the white player's clock
    private Label blackClockLabel; // Label for the black player's clock

    private ChessBoard chessBoard;
    private VBox rightPanel; // Changed from Region to VBox

    public GameScene(Stage stage) {
        templateScene = new TemplateScene(stage);
        StyleManager.applyStyles(templateScene.getTemplateScene());

        chessBoard = new ChessBoard();

        // Create clock labels
        whiteClockLabel = createClockLabel();
        blackClockLabel = createClockLabel();

        // Create clock labels with player names above
        Label whiteLabel = new Label("White's Clock");
        Label blackLabel = new Label("Black's Clock");

        // Style labels to make them prominent
        whiteLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        blackLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Right panel (clocks, etc.)
        rightPanel = new VBox(20); // Using VBox to hold the clocks vertically
        rightPanel.setPrefWidth(350); // Set the preferred width of the right panel
        rightPanel.setStyle("-fx-background-color: rgba(255,255,255,0);"); // Set background with transparency
        rightPanel.setPadding(new Insets(10, 10, 10, 0));

        // Add labels and clocks to the right panel
        rightPanel.getChildren().addAll(blackLabel, blackClockLabel, whiteLabel, whiteClockLabel);

        // Create the timeline for both clocks
        createTimers();

        // Wrap board in a StackPane
        StackPane boardWrapper = new StackPane(chessBoard.getGrid());
        boardWrapper.setPrefSize(chessBoard.getCellSize() * 8, chessBoard.getCellSize() * 8);
        boardWrapper.setMaxSize(chessBoard.getCellSize() * 8, chessBoard.getCellSize() * 8);
        boardWrapper.setMinSize(chessBoard.getCellSize() * 8, chessBoard.getCellSize() * 8);

        // Layout to contain both the board and side panel
        HBox centerLayout = new HBox(20); // Spacing between board and side content
        centerLayout.setAlignment(Pos.CENTER_LEFT); // Align to left
        centerLayout.setPadding(new Insets(20)); // Add padding around the content
        centerLayout.getChildren().addAll(boardWrapper, rightPanel);

        // Add the final layout to the scene
        templateScene.getCenterContainer().setAlignment(Pos.CENTER);
        templateScene.getCenterContainer().getChildren().add(centerLayout);

        // Start the game with white's turn
        startClock();
    }

    private Label createClockLabel() {
        Label clockLabel = new Label("5:00"); // Default start time (5 minutes)
        clockLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;"); // White text color
        return clockLabel;
    }

    private void createTimers() {
        // White player's timer
        whiteTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            whiteTimeLeft--;
            updateClockDisplay(whiteClockLabel, whiteTimeLeft);
            if (whiteTimeLeft <= 0) {
                // Time's up for white
                endTurn();
            }
        }));
        whiteTimer.setCycleCount(Timeline.INDEFINITE);

        // Black player's timer
        blackTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            blackTimeLeft--;
            updateClockDisplay(blackClockLabel, blackTimeLeft);
            if (blackTimeLeft <= 0) {
                // Time's up for black
                endTurn();
            }
        }));
        blackTimer.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateClockDisplay(Label clockLabel, int timeLeft) {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        clockLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private static void startClock() {
        if (isWhiteTurn) {
            whiteTimer.play();
            blackTimer.pause();
        } else {
            blackTimer.play();
            whiteTimer.pause();
        }
    }

    public static void endTurn() {
        // Switch turn
        isWhiteTurn = !isWhiteTurn;
        startClock(); // Start the other player's clock
    }

    public Scene getScene() {
        return templateScene.getTemplateScene();
    }
}

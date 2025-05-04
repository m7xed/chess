package game;

public class GameState {
    private static boolean isWhiteTurn = true; // Default to white (White moves first)

    // Return which players turn
    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    // Alternate players turns
    public static void swapTurn() {
        isWhiteTurn = !isWhiteTurn;
    }
}

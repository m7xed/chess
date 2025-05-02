package game;

import app.utilities.TemplateScene;
import app.views.GameScene;
import game.pieces.Bishop;
import game.pieces.Knight;
import game.pieces.Pawn;
import game.pieces.Rook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class ChessBoard {
    GridPane grid = new GridPane();
    TemplateScene temp = new TemplateScene(new Stage());
    private final StackPane root = new StackPane(); // this is now your main wrapper


    private ChessPiece[][] board = new ChessPiece[8][8];

    private ChessPiece selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private double cellSize = (double) (temp.getGuiWidth() * 0.8) / 8;


    public ChessBoard() {

        // Initialise pieces
        for (int col = 0; col < 8; col++) {
            // Add black pawns
            board[1][col] = new Pawn(false, 1, col);
            // Add white pawns
            board[6][col] = new Pawn(true, 6, col);
        }

        // Add white rooks
        board[7][0] = new Rook(true, 7, 0);
        board[7][7] = new Rook(true, 7, 7);

        // Add black rooks
        board[0][0] = new Rook(false, 0, 0);
        board[0][7] = new Rook(false, 0, 7);

        // Add white bishops
        board[7][1] = new Bishop(true, 7, 1);
        board[7][6] = new Bishop(true, 7, 6);

        // Add black bishops
        board[0][1] = new Bishop(false, 0, 1);
        board[0][6] = new Bishop(false, 0, 6);

        // Add white knights
        board[7][2] = new Knight(true, 7, 2);
        board[7][6] = new Knight(true, 7, 6);

        // Add black Knights
        board[0][2] = new Knight(false, 0, 2);
        board[0][6] = new Knight(false, 0, 6);


        boolean white = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(white ? Color.WHITE : Color.GREY);

                 // Wrap both the cell and the piece (if any) in a StackPane
                StackPane stack = new StackPane();
                stack.setPrefSize(cellSize, cellSize);
                stack.getChildren().add(cell); // add background first

                ChessPiece piece = board[row][col];
                if (piece != null) {
                    ImageView pieceImage = createPieceImage(piece);
                    pieceImage.setFitWidth(cellSize * 0.8);
                    pieceImage.setFitHeight(cellSize * 0.8);
                    stack.getChildren().add(pieceImage); // add piece on top
                }

                int finalRow = row;
                int finalCol = col;
                stack.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handleCellClick(finalRow, finalCol);
                });

                grid.add(stack, col, row);

                white = !white;
            }
            white = !white;
        }


        grid.setBorder(new Border(new BorderStroke(
                Color.GOLD,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(4)
        )));

        root.getChildren().add(grid);
    }

    private void handleCellClick(int row, int col) {
        ChessPiece piece = board[row][col];

        // Case 1: A piece is already selected
        if (selectedPiece != null) {
            List<int[]> validMoves = selectedPiece.getValidMoves(board);
            boolean isValidMove = validMoves.stream().anyMatch(m -> m[0] == row && m[1] == col);

            if (isValidMove) {
                ChessPiece targetPiece = board[row][col];
                if (targetPiece == null || targetPiece.isWhite() != selectedPiece.isWhite()) {
                    board[row][col] = selectedPiece;
                    board[selectedRow][selectedCol] = null;

                    int sourceRow = selectedRow;
                    int sourceCol = selectedCol;

                    selectedPiece.move(row, col); // Update internal coords

                    selectedPiece = null;
                    selectedRow = -1;
                    selectedCol = -1;

                    updateBoardCell(sourceRow, sourceCol);
                    updateBoardCell(row, col);
                    clearHighlight();
                    GameScene.endTurn();
                }
            } else {
                // Deselect on invalid move
                System.out.println("Invalid move: Piece can't move like that.");
                clearHighlight();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }

            // Case 2: No piece selected yet
        } else {
            if (piece != null && piece.isWhite()) {
                selectedPiece = piece;
                selectedRow = row;
                selectedCol = col;
                System.out.println("Piece selected at: " + row + ", " + col);
                highlightSelectedCell(row, col);
            } else {
                // Clicked on empty cell — reset selection just in case
                clearHighlight();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }



    private void clearHighlight() {
        if (selectedRow == -1 || selectedCol == -1) {
            return;  // Do nothing if no piece is selected
        }
        // Only attempt to clear the highlight if there was a valid selection
        System.out.println("Clearing highlight on cell: " + selectedRow + "," + selectedCol);

        // Get the corresponding StackPane for the selected cell
        StackPane cellStack = (StackPane) grid.getChildren().get(selectedRow * 8 + selectedCol);

        // Ensure that the first child is a Rectangle, which is the background.
        if (!cellStack.getChildren().isEmpty() && cellStack.getChildren().get(0) instanceof Rectangle cell) {
            // Clear the previous highlight by replacing the background rectangle
            cell.setFill((selectedRow + selectedCol) % 2 == 0 ? Color.WHITE : Color.GREY);
        }
    }


    private void highlightSelectedCell(int row, int col) {
        // Get the corresponding StackPane for the cell
        StackPane cellStack = (StackPane) grid.getChildren().get(row * 8 + col);

        // Change the background color to highlight
        Rectangle cell = new Rectangle(cellSize, cellSize);
        cell.setFill(Color.YELLOW);  // Highlight color (e.g., yellow)
        cellStack.getChildren().set(0, cell);  // Update the background color (index 0)
    }

    private void updateBoardCell(int row, int col) {

        System.out.println("Selected Row: " + selectedRow + " Selected Column: " + selectedCol);
        System.out.println("Target Row: " + row + " Target Column: " + col);
        // Get the corresponding StackPane for the cell
        StackPane cellStack = (StackPane) grid.getChildren().get(row * 8 + col);

        // Clear the previous piece (if any)
        cellStack.getChildren().clear();

        // Add the background
        Rectangle cell = new Rectangle(cellSize, cellSize);
        cell.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GREY);
        cellStack.getChildren().add(cell);

        // If there is a piece, add it to the stack
        ChessPiece piece = board[row][col];
        if (piece != null) {
            ImageView pieceImage = createPieceImage(piece);
            pieceImage.setFitWidth(cellSize * 0.8);
            pieceImage.setFitHeight(cellSize * 0.8);
            cellStack.getChildren().add(pieceImage);

            }
        }

    private ImageView createPieceImage(ChessPiece piece) {
        String colour = piece.isWhite() ? "white" : "black";
        String type = piece.getClass().getSimpleName().toLowerCase();
        String imagePath = "/images/" + type + "/" + colour + ".png"; // Make it absolute path

        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            throw new IllegalArgumentException("Image not found: " + imagePath);
        }

        Image pieceImage = new Image(imageUrl.toExternalForm());
        return new ImageView(pieceImage);
    }

    public GridPane getGrid() {
        return grid;
    }

    public double getCellSize() {
        return cellSize;
    }
}

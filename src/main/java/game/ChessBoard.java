package game;

import app.utilities.TemplateScene;
import app.views.GameScene;
import game.pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    GridPane grid = new GridPane();
    TemplateScene temp = new TemplateScene(new Stage());

    private List<ChessMove> moveHistory = new ArrayList<>();
    private final ChessPiece[][] board = new ChessPiece[8][8];

    private ChessPiece selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private final double CELL_SIZE = (temp.getGuiWidth() * 0.8) / 8;


    public ChessBoard() {

        // Initialise Pawns
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

        // Add white Knights
        board[7][1] = new Knight(true, 7, 1);
        board[7][6] = new Knight(true, 7, 6);

        // Add black Knights
        board[0][1] = new Knight(false, 0, 1);
        board[0][6] = new Knight(false, 0, 6);

        // Add white Bishops
        board[7][2] = new Bishop(true, 7, 2);
        board[7][5] = new Bishop(true, 7, 5);

        // Add black Bishops
        board[0][2] = new Bishop(false, 0, 2);
        board[0][5] = new Bishop(false, 0, 5);

        // Add Queens
        board[7][3] = new Queen(true, 7, 3);
        board[0][3] = new Queen(false, 0, 3);

        // Add Kings
        board[7][4] = new King(true, 7, 4);
        board[0][4] = new King(false, 0, 4);


        boolean white = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(white ? Color.WHITE : Color.GREY);

                StackPane stack = new StackPane();
                stack.setPrefSize(CELL_SIZE, CELL_SIZE);
                stack.getChildren().add(cell);

                ChessPiece piece = board[row][col];
                if (piece != null) {
                    ImageView pieceImage = createPieceImage(piece);
                    pieceImage.setFitWidth(CELL_SIZE * 0.8);
                    pieceImage.setFitHeight(CELL_SIZE * 0.8);
                    stack.getChildren().add(pieceImage);
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

        StackPane root = new StackPane();
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
                    // Capture or Move
                    ChessPiece target = board[row][col];
                    boolean isCapture = target != null && target.isWhite != selectedPiece.isWhite();

                    ChessMove move = new ChessMove(
                            // Selected piece attributes
                            selectedRow, selectedCol,
                            // Target square attributes
                            row, col,
                            // Piece objects
                            selectedPiece, targetPiece,
                            false,false,false,
                            "", moveHistory.size() + 1
                            );

                    makeMove(move, row, col);

                    addMove(move);

                    selectedPiece = null;
                    selectedRow = -1;
                    selectedCol = -1;

                    updateBoardCell(move.getFromRow(), move.getFromCol());
                    updateBoardCell(row, col);
                    clearHighlight();
                    GameState.swapTurn();
                }
            } else {
                // Space occupied by own piece
                System.out.println("Invalid move: Piece can't move like that.");
                clearHighlight();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }

            // Case 2: No piece selected yet
        } else {
            if (piece != null && piece.isWhite() == GameState.isWhiteTurn()) {

                selectedPiece = piece;
                selectedRow = row;
                selectedCol = col;
                System.out.println("Piece selected at: " + row + ", " + col);
                highlightSelectedCell(row, col);
            } else {
                // Clicked on empty cell - reset selection
                clearHighlight();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    private void clearHighlight() {
        if (selectedRow == -1 || selectedCol == -1) {
            return;
        }
        System.out.println("Clearing highlight on cell: " + selectedRow + "," + selectedCol);
        StackPane cellStack = (StackPane) grid.getChildren().get(selectedRow * 8 + selectedCol);
        if (!cellStack.getChildren().isEmpty() && cellStack.getChildren().get(0) instanceof Rectangle cell) {
            cell.setFill((selectedRow + selectedCol) % 2 == 0 ? Color.WHITE : Color.GREY);
        }
    }

    private void highlightSelectedCell(int row, int col) {
        StackPane cellStack = (StackPane) grid.getChildren().get(row * 8 + col);

        Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
        cell.setFill(Color.YELLOW);
        cellStack.getChildren().set(0, cell);
    }

    private void makeMove(ChessMove move, int row, int col) {
        ChessPiece piece = board[move.getFromRow()][move.getFromCol()];
        ChessPiece targetPiece = board[move.getToRow()][move.getToCol()];

        boolean isCapture = targetPiece != null && targetPiece.isWhite() != selectedPiece.isWhite();

        // Castling logic
        if (selectedPiece instanceof King && Math.abs(col - selectedCol) == 2) {
            System.out.println("Testing Castling");
            boolean kingSide = col > selectedCol;

            int rookCol = kingSide ? 7 : 0;
            ChessPiece rook = board[selectedRow][rookCol];

            if (rook instanceof Rook && rook.isWhite() == selectedPiece.isWhite()) {
                if (!((King) selectedPiece).hasMoved() && !((Rook) rook).hasMoved()) {
                    // Check squares between king and rook are empty
                    boolean pathClear = true;
                    int step = kingSide ? 1 : -1;
                    for (int c = selectedCol + step; c != rookCol; c += step) {
                        if (board[selectedRow][c] != null) {
                            pathClear = false;
                            break;
                        }
                    }

                    if (pathClear) {
                        int newRookCol = kingSide ? col - 1 : col + 1;

                        board[selectedRow][newRookCol] = rook;
                        board[selectedRow][rookCol] = null;
                        rook.move(selectedRow, newRookCol);

                        board[selectedRow][col] = selectedPiece;
                        board[selectedRow][selectedCol] = null;
                        selectedPiece.move(selectedRow, col);

                        updateBoardCell(selectedRow, rookCol);
                        updateBoardCell(selectedRow, newRookCol);
                        updateBoardCell(selectedRow, selectedCol);
                        updateBoardCell(selectedRow, col);

                        selectedPiece = null;
                        selectedRow = -1;
                        selectedCol = -1;

                        GameScene.endTurn();
                        return;
                    }
                }
            }
        }
        // Regular move or capture
        System.out.println("testing regular move");
        board[row][col] = piece;
        board[move.getFromRow()][move.getFromCol()] = null;
        piece.move(row, col);

        updateBoardCell(move.getFromRow(), move.getFromCol());
        updateBoardCell(row, col);

        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;

        GameScene.endTurn();
    }

    private void updateBoardCell(int row, int col) {
        System.out.println("Selected Row: " + selectedRow + " Selected Column: " + selectedCol);
        System.out.println("Target Row: " + row + " Target Column: " + col);
        StackPane cellStack = (StackPane) grid.getChildren().get(row * 8 + col);

        cellStack.getChildren().clear();

        Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
        cell.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GREY);
        cellStack.getChildren().add(cell);

        ChessPiece piece = board[row][col];
        if (piece != null) {
            ImageView pieceImage = createPieceImage(piece);
            pieceImage.setFitWidth(CELL_SIZE * 0.8);
            pieceImage.setFitHeight(CELL_SIZE * 0.8);
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

    public void addMove(ChessMove move) {
        moveHistory.add(move);
    }

    public List<ChessMove> getMoveHistory() {
        return moveHistory;
    }

    public GridPane getGrid() {
        return grid;
    }

    public double getCELL_SIZE() {
        return CELL_SIZE;
    }
}

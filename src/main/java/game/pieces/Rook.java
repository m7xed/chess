package game.pieces;

import game.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public List<int[]> getValidMoves(ChessPiece[][] board) {
        List<int[]> validMoves = new ArrayList<>();

        checkCardinalDirections(board, validMoves);

        return validMoves;
    }

    private void checkCardinalDirections(ChessPiece[][] board, List<int[]> validMoves) {
        checkDirection(board, validMoves, -1, 0);
        checkDirection(board, validMoves, 1, 0);
        checkDirection(board, validMoves, 0, -1);
        checkDirection(board, validMoves, 0, 1);
    }

    private void checkDirection(ChessPiece[][] board, List<int[]> validMoves, int rowChange, int colChange) {
        int currentRow = row + rowChange;
        int currentCol = col + colChange;

        while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
            ChessPiece piece = board[currentRow][currentCol];
            if (piece == null) {
                validMoves.add(new int[]{currentRow, currentCol});
            } else {
                if (piece.isWhite() != isWhite) {
                    validMoves.add(new int[]{currentRow, currentCol});
                }
                break;
            }
            currentRow += rowChange;
            currentCol += colChange;
        }
    }


    @Override
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}

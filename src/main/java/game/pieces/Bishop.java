package game.pieces;

import game.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public List<int[]> getValidMoves(ChessPiece[][] board) {
        List<int[]> validMoves = new ArrayList<>();

        checkDiagonalDirections(board, validMoves);

        return validMoves;
    }

    private void checkDiagonalDirections(ChessPiece[][] board, List<int[]> validMoves) {
        checkDirection(board, validMoves, -1, -1); // North West
        checkDirection(board, validMoves, 1, -1); // North East
        checkDirection(board, validMoves, -1, 1); // South West
        checkDirection(board, validMoves, 1, 1); // South East
    }

    @Override
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}

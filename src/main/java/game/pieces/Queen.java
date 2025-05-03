package game.pieces;

import game.ChessBoard;
import game.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {
    public Queen(Boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }




    @Override
    public List<int[]> getValidMoves(ChessPiece[][] board) {
        List<int[]> validMoves = new ArrayList<>();

        checkMoves(board, validMoves);

        return validMoves;
    }

    private void checkMoves(ChessPiece[][] board, List<int[]> validMoves) {
        // Check Diagonals
        checkDirection(board, validMoves, -1, -1); // North West
        checkDirection(board, validMoves, 1, -1); // North East
        checkDirection(board, validMoves, -1, 1); // South West
        checkDirection(board, validMoves, 1, 1); // South East

        // Check Cardinal Directions
        checkDirection(board, validMoves, -1, 0);
        checkDirection(board, validMoves, 1, 0);
        checkDirection(board, validMoves, 0, -1);
        checkDirection(board, validMoves, 0, 1);
    }


    @Override
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}

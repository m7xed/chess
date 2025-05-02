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

    @Override
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}

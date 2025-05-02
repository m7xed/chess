package game.pieces;

import game.ChessBoard;
import game.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Boolean isWhite, int row, int col) {
        super (isWhite,row,col);
    }



    @Override
    public List<int[]> getValidMoves(ChessPiece[][] board) {
        List<int[]> validMoves = new ArrayList<int[]>();

        checkLDirections(board, validMoves);

        return validMoves;
    }




    private void checkLDirections(ChessPiece[][] board, List<int[]> validMoves) {
        checkKnightDirection(board, validMoves, -2,-1);
        checkKnightDirection(board, validMoves, -2,1);
        checkKnightDirection(board, validMoves, -1,2);
        checkKnightDirection(board, validMoves, 1,2);
        checkKnightDirection(board, validMoves, 2,-1);
        checkKnightDirection(board, validMoves, 2,1);
        checkKnightDirection(board, validMoves, -1,-2);
        checkKnightDirection(board, validMoves, 1,-2);
    }


    protected void checkKnightDirection(ChessPiece[][] board, List<int[]> validMoves, int rowChange, int colChange) {
        int newRow = rowChange + row;
        int newCol = colChange + col;

        if (newRow < 8 && newRow >= 0 && newCol < 8 && newCol >= 0) {
            ChessPiece piece = board[newRow][newCol];
            if (piece == null) {
                validMoves.add(new int[] {newRow, newCol});
            } else {
                if (piece.isWhite() != isWhite) {
                    validMoves.add(new int[] {newRow, newCol});
                }
            }
        }
    }

    @Override
    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }


}

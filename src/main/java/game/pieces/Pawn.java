package game.pieces;

import game.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {
    private final int MAX_ROWS = 8;
    private final int MAX_COLUMNS = 8;
    private final int MIN_ROWS = 0;
    private final int MIN_COLUMNS = 0;

    private boolean hasMoved;

    public Pawn(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
        this.hasMoved = false;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }


    // Modify the valid moves method
    @Override
    public List<int[]> getValidMoves(ChessPiece[][] board) {
        List<int[]> validMoves = new ArrayList<>();
        int direction = isWhite() ? -1 : 1;  // White moves up (-1), Black moves down (+1)
        int startRow = isWhite() ? 6 : 1;  // White starts at row 6, Black at row 1

        // 1. One square forward
        if (board[getRow() + direction][getCol()] == null) {
            validMoves.add(new int[]{getRow() + direction, getCol()});
        }

        // 2. Two squares forward on the first move
        if (!hasMoved && board[getRow() + (2 * direction)][getCol()] == null) {
            validMoves.add(new int[]{getRow() + (2 * direction), getCol()});
        }

        // 3. Capture diagonally (if there's an opponent's piece)
        if (getCol() - 1 >= 0 && board[getRow() + direction][getCol() - 1] != null &&
                board[getRow() + direction][getCol() - 1].isWhite() != isWhite()) {
            validMoves.add(new int[]{getRow() + direction, getCol() - 1});
        }
        if (getCol() + 1 < 8 && board[getRow() + direction][getCol() + 1] != null &&
                board[getRow() + direction][getCol() + 1].isWhite() != isWhite()) {
            validMoves.add(new int[]{getRow() + direction, getCol() + 1});
        }

        return validMoves;
    }

    @Override
    public void move(int newRow, int newCol) {
        setRow(newRow);
        setCol(newCol);
        setHasMoved(true);  // Mark pawn as having moved
    }
}

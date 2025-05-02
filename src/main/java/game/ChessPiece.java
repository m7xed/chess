package game;

import java.util.List;

public abstract class ChessPiece {
    protected boolean isWhite;
    protected int row, col;

    public ChessPiece(Boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }


    public boolean isWhite() {return isWhite;}
    public int getRow() {return row;}
    public int getCol() {return col;}

    public void setRow(int row) {this.row = row;}
    public void setCol(int col) {this.col = col;}

    public List<int[]> getValidMoves(ChessPiece[][] board) {
        return null;
    }

    public abstract void move(int newRow, int newCol);
}

package game;

public class ChessMove {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;
    private final ChessPiece pieceMoved;
    private final ChessPiece pieceCaptured;
    private final String promotionPiece;
    private final boolean isCastling;
    private final boolean isEnPassant;
    private final boolean isPromotion;

    private final int moveNumber;

    public ChessMove(int fromRow, int fromCol, int toRow, int toCol,
                     ChessPiece pieceMoved, ChessPiece pieceCaptured,
                     boolean isCastling, boolean isEnPassant, boolean isPromotion,
                     String promotionPiece, int moveNumber
                     ) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.pieceMoved = pieceMoved;
        this.pieceCaptured = pieceCaptured;
        this.promotionPiece = promotionPiece;
        this.isCastling = isCastling;
        this.isEnPassant = isEnPassant;
        this.isPromotion = isPromotion;
        this.moveNumber = moveNumber;
    }

    public int getFromRow() { return fromRow; }
    public int getFromCol() { return fromCol; }
    public int getToRow() { return toRow; }
    public int getToCol() { return toCol; }
    public ChessPiece getPieceMoved() { return pieceMoved; }
    public ChessPiece getCapturedPiece() { return pieceCaptured; }
    public String getPromotionPiece() { return promotionPiece; }
    public boolean isCastling() { return isCastling; }
    public boolean isEnPassant() { return isEnPassant; }
    public boolean isPromotion() { return isPromotion; }
    public int getMoveNumber() { return moveNumber; }



















}

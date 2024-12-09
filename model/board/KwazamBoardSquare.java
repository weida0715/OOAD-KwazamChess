package model.board;

import model.piece.KwazamPiece;

public class KwazamBoardSquare {
    private KwazamPiece piece;
    
    public KwazamBoardSquare() {}

    public KwazamPiece getPiece() {
        return piece;
    }

    public void setPiece(KwazamPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
    
    public boolean isEmpty() {
        return this.piece == null;
    }
    
}

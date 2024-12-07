package model.pieces;

import model.utils.KwazamPieceType;

public class KwazamPiece {
    protected KwazamPieceType type;

    public KwazamPiece(KwazamPieceType type) {
        this.type = type;
    }

    public KwazamPieceType getType() {
        return type;
    }

    public void setType(KwazamPieceType type) {
        this.type = type;
    }
}

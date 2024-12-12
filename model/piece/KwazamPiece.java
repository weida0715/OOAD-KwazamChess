package model.piece;

import model.utils.KwazamPieceColor;
import model.utils.KwazamPieceType;

public class KwazamPiece {
    protected KwazamPieceColor color;
    protected KwazamPieceType type;
    protected int x, y;

    public KwazamPiece() {}

    public KwazamPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y) {
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public KwazamPieceColor getColor() {
        return color;
    }

    public KwazamPieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(KwazamPieceType type) {
        this.type = type;
    }
}

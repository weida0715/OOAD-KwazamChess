package model.piece;

import java.awt.image.BufferedImage;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class KwazamPiece {
    protected KwazamPieceColor color;
    protected KwazamPieceType type;
    protected BufferedImage img;

    public KwazamPiece(KwazamPieceColor color, KwazamPieceType type) {
        this.color = color;
        this.type = type;
    }

    public KwazamPieceColor getColor() {
        return color;
    }

    public KwazamPieceType getType() {
        return type;
    }

    public void setType(KwazamPieceType type) {
        this.type = type;
    }
}

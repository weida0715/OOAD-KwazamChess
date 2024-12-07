package model.pieces;

import java.awt.Color;
import model.utils.KwazamPieceType;

public class Biz extends KwazamPiece {
    public Biz(Color color) {
        super(color == Color.BLUE ? KwazamPieceType.B_BIZ : KwazamPieceType.R_BIZ);
    }
}

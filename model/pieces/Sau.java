package model.pieces;

import java.awt.Color;
import model.utils.KwazamPieceType;

public class Sau extends KwazamPiece {
    public Sau(Color color) {
        super(color == Color.BLUE ? KwazamPieceType.B_SAU : KwazamPieceType.R_SAU);
    }
}

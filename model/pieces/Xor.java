package model.pieces;

import java.awt.Color;
import model.utils.KwazamPieceType;

public class Xor extends KwazamPiece {
    public Xor(Color color) {
        super(color == Color.BLUE ? KwazamPieceType.B_XOR : KwazamPieceType.R_XOR);
    }
}

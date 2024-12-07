package model.pieces;

import java.awt.Color;
import model.utils.KwazamPieceType;

public class Ram extends KwazamPiece {
    public Ram(Color color) {
        super(color == Color.BLUE ? KwazamPieceType.B_RAM : KwazamPieceType.R_RAM);
    }
}

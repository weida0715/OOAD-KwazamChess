package model.pieces;

import java.awt.Color;
import model.utils.KwazamPieceType;

public class Tor extends KwazamPiece {
    public Tor(Color color) {
        super(color == Color.BLUE ? KwazamPieceType.B_TOR : KwazamPieceType.R_TOR);
    }
}

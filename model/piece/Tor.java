package model.piece;

import model.utils.KwazamPieceColor;
import model.utils.KwazamPieceType;

public class Tor extends KwazamPiece {
    public Tor(KwazamPieceColor color, int x, int y) {
        super(color, KwazamPieceType.TOR, x, y);
    }
}

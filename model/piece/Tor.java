package model.piece;

import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class Tor extends KwazamPiece {
    public Tor(KwazamPieceColor color, int x, int y) {
        super(color, KwazamPieceType.TOR, x, y);
    }
}

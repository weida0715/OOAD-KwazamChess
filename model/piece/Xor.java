package model.piece;

import model.utils.KwazamPieceColor;
import model.utils.KwazamPieceType;

public class Xor extends KwazamPiece {
    public Xor(KwazamPieceColor color, int x, int y) {
        super(color, KwazamPieceType.XOR, x, y);
    }
}

package model.pieces;

import model.movements.OrthogonalMovement;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public final class KwazamPieceFactory {
    private KwazamPieceFactory(){}

    public static KwazamPiece getPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y)
    {
        switch (type) {
            case BIZ:
                return new Biz(color, x, y, new OrthogonalMovement());
            case RAM:
                return new Ram(color, x, y, new OrthogonalMovement());
            case SAU:
                return new Sau(color, x, y, new OrthogonalMovement());
            case TOR:
                return new Tor(color, x, y, new OrthogonalMovement());
            case XOR:
                return new Xor(color, x, y, new OrthogonalMovement());
            default:
                return null;
        }
    }
}

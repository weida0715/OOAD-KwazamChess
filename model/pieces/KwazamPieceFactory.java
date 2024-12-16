package model.pieces;

import model.movements.BizMovement;
import model.movements.RamMovement;
import model.movements.SauMovement;
import model.movements.TorMovement;
import model.movements.XorMovement;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public final class KwazamPieceFactory {
    private KwazamPieceFactory(){}

    public static KwazamPiece getPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y)
    {
        switch (type) {
            case BIZ:
                return new Biz(color, x, y, new BizMovement());
            case RAM:
                return new Ram(color, x, y, new RamMovement());
            case SAU:
                return new Sau(color, x, y, new SauMovement());
            case TOR:
                return new Tor(color, x, y, new TorMovement());
            case XOR:
                return new Xor(color, x, y, new XorMovement());
            default:
                return null;
        }
    }
}

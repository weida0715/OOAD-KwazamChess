package model.pieces;

import model.movements.BizMovement;
import model.movements.RamMovement;
import model.movements.SauMovement;
import model.movements.TorMovement;
import model.movements.XorMovement;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Author(s):
 * 
 * Factory class for creating Kwazam pieces.
 * Provides a method to create pieces based on type, color, and position.
 */
public final class KwazamPieceFactory {
    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Private constructor to prevent instantiation.
     * This is a utility class with static methods only.
     */
    private KwazamPieceFactory() {
    }

    // =================================================================
    // FACTORY METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Creates a Kwazam piece of the specified type, color, and position.
     * 
     * @param color the color of the piece
     * @param type  the type of the piece
     * @param x     the x-coordinate of the piece
     * @param y     the y-coordinate of the piece
     * @return the created piece, or null if the type is invalid
     */
    public static KwazamPiece getPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y) {
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

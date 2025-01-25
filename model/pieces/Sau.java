package model.pieces;

import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Represents the Sau piece in Kwazam.
 * Inherits from KwazamPiece and uses a specific movement strategy.
 */
public class Sau extends KwazamPiece {
    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s): Lim Kar Joon
     * 
     * Constructs a Sau piece with the given color, position, and movement strategy.
     * 
     * @param color            the color of the piece
     * @param x                the x-coordinate of the piece
     * @param y                the y-coordinate of the piece
     * @param movementStrategy the movement strategy for the piece
     */
    public Sau(KwazamPieceColor color, int x, int y, MovementStrategy movementStrategy) {
        super(color, KwazamPieceType.SAU, x, y, movementStrategy);
    }
}

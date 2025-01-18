package model.pieces;

import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Author(s):
 * 
 * Represents the Ram piece in Kwazam.
 * Inherits from KwazamPiece and includes direction for movement.
 */
public class Ram extends KwazamPiece {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    int direction;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a Ram piece with the given color, position, and movement strategy.
     * Sets the initial direction based on the piece's color.
     * 
     * @param color            the color of the piece
     * @param x                the x-coordinate of the piece
     * @param y                the y-coordinate of the piece
     * @param movementStrategy the movement strategy for the piece
     */
    public Ram(KwazamPieceColor color, int x, int y, MovementStrategy movementStrategy) {
        super(color, KwazamPieceType.RAM, x, y, movementStrategy);
        this.direction = color == KwazamPieceColor.RED ? 1 : -1;
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets the current direction of the Ram piece.
     * 
     * @return the direction of the piece
     */
    public int getDirection() {
        return direction;
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Sets the direction of the Ram piece.
     * 
     * @param d the new direction
     */
    public void setDirection(int d) {
        this.direction = d;
    }
}

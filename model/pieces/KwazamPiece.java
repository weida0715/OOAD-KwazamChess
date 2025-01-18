package model.pieces;

import java.util.List;
import model.board.KwazamBoard;
import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Author(s):
 * 
 * Represents a generic piece in Kwazam.
 * Contains properties like color, type, position, and movement strategy.
 */
public class KwazamPiece {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    protected KwazamPieceColor color;
    protected KwazamPieceType type;
    protected int x, y;
    protected MovementStrategy movementStrategy;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a KwazamPiece with the given properties.
     * 
     * @param color            the color of the piece
     * @param type             the type of the piece
     * @param x                the x-coordinate of the piece
     * @param y                the y-coordinate of the piece
     * @param movementStrategy the movement strategy for the piece
     */
    public KwazamPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y, MovementStrategy movementStrategy) {
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
        this.movementStrategy = movementStrategy;
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets the color of the piece.
     * 
     * @return the color of the piece
     */
    public KwazamPieceColor getColor() {
        return color;
    }

    /**
     * Author(s):
     * 
     * Gets the type of the piece.
     * 
     * @return the type of the piece
     */
    public KwazamPieceType getType() {
        return type;
    }

    /**
     * Author(s):
     * 
     * Gets the x-coordinate of the piece.
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Author(s):
     * 
     * Gets the y-coordinate of the piece.
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Author(s):
     * 
     * Gets all valid moves for the piece.
     * 
     * @param board the game board
     * @return list of valid moves as [x, y] coordinates
     */
    public List<int[]> getValidMoves(KwazamBoard board) {
        return movementStrategy.getValidMoves(this, board);
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Sets the x-coordinate of the piece.
     * 
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Author(s):
     * 
     * Sets the y-coordinate of the piece.
     * 
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Author(s):
     * 
     * Sets the type of the piece.
     * 
     * @param type the new type
     */
    public void setType(KwazamPieceType type) {
        this.type = type;
    }
}

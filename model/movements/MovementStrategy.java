package model.movements;

import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Defines the movement strategy for Kwazam pieces.
 * Provides a method to calculate valid moves for a piece on the board.
 */
public interface MovementStrategy {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Polymorphic method to gets all valid moves for the piece.
     * 
     * @param piece the piece
     * @param board the game board
     * @return list of valid moves as [x, y] coordinates
     */
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board);
}

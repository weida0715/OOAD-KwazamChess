package model.movements;

import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Author(s):
 * 
 * Defines the movement strategy for Kwazam pieces.
 * Provides a method to calculate valid moves for a piece on the board.
 */
public interface MovementStrategy {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board);
}

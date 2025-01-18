package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Author(s):
 * 
 * Implements the movement strategy for the Biz piece.
 * Handles valid moves for the Biz in a Kwazam game.
 */
public class BizMovement implements MovementStrategy {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final int[][] directions;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    public BizMovement() {
        this.directions = new int[][] {
                { 2, 1 }, // Right 2, Up 1
                { 2, -1 }, // Right 2, Down 1
                { -2, 1 }, // Left 2, Up 1
                { -2, -1 }, // Left 2, Down 1
                { 1, 2 }, // Right 1, Up 2
                { 1, -2 }, // Right 1, Down 2
                { -1, 2 }, // Left 1, Up 2
                { -1, -2 } // Left 1, Down 2
        };
    }

    // =================================================================
    // MOVEMENT STRATEGY IMPLEMENTATION
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets all valid moves for the Biz piece.
     * 
     * @param piece the Biz piece
     * @param board the game board
     * @return list of valid moves as [x, y] coordinates
     */
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (isValidMove(newX, newY, board, piece)) {
                validMoves.add(new int[] { newX, newY });
            }
        }

        return validMoves;
    }

    // =================================================================
    // PRIVATE METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Checks if a move is valid for the Biz piece.
     * 
     * @param x     the target x-coordinate
     * @param y     the target y-coordinate
     * @param board the game board
     * @param piece the Biz piece
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int x, int y, KwazamBoard board, KwazamPiece piece) {
        return board.isWithinBounds(x, y) &&
                (board.getPiece(x, y) == null || board.getPiece(x, y).getColor() != piece.getColor());
    }
}

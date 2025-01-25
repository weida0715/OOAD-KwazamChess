package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;
import model.pieces.Ram;

/**
 * Author(s):
 * 
 * Implements the movement strategy for the Ram piece.
 * Handles valid moves for the Ram in a Kwazam game.
 */
public class RamMovement implements MovementStrategy {
    // =================================================================
    // MOVEMENT STRATEGY IMPLEMENTATION
    // =================================================================
    /**
     * Author(s): Lim Kar Joon
     * 
     * Gets all valid moves for the Ram piece.
     * 
     * @param piece the Ram piece
     * @param board the game board
     * @return list of valid moves as [x, y] coordinates
     */
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        Ram ram = (Ram) piece;
        int x = ram.getX();
        int y = ram.getY();

        // Update direction if the Ram reaches the top or bottom of the board
        updateDirection(ram, board);

        // Calculate the next position based on the current direction
        int targetY = y + ram.getDirection();
        if (isValidMove(x, targetY, board, piece)) {
            validMoves.add(new int[] { x, targetY });
        }

        return validMoves;
    }

    // =================================================================
    // PRIVATE METHODS
    // =================================================================
    /**
     * Author(s): Lim Kar Joon
     * 
     * Updates the Ram's direction if it reaches the top or bottom of the board.
     * 
     * @param ram   the Ram piece
     * @param board the game board
     */
    private void updateDirection(Ram ram, KwazamBoard board) {
        int y = ram.getY();
        int direction = ram.getDirection();

        if (y == 0 && direction == -1) { // Reached top
            ram.setDirection(1); // Change to downward movement
        } else if (y == board.getRows() - 1 && direction == 1) { // Reached bottom
            ram.setDirection(-1); // Change to upward movement
        }
    }

    /**
     * Author(s): Lim Kar Joon
     * 
     * Checks if a move is valid for the Ram piece.
     * 
     * @param x     the target x-coordinate
     * @param y     the target y-coordinate
     * @param board the game board
     * @param piece the Ram piece
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int x, int y, KwazamBoard board, KwazamPiece piece) {
        return board.isWithinBounds(x, y) &&
                (board.getPiece(x, y) == null || board.getPiece(x, y).getColor() != piece.getColor());
    }
}

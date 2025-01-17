package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Author(s):
 * 
 * Implements the movement strategy for the Sau piece.
 * Handles valid moves for the Sau in a Kwazam game.
 */
public class SauMovement implements MovementStrategy {
    private final int[][] directions;

    /**
     * Author(s):
     * 
     * Constructs a SauMovement.
     * Initializes the possible movement directions for the Sau piece.
     */
    public SauMovement() {
        this.directions = new int[][] {
                { 1, 0 }, // Right
                { -1, 0 }, // Left
                { 0, 1 }, // Up
                { 0, -1 }, // Down
                { 1, 1 }, // Top-right diagonal
                { 1, -1 }, // Bottom-right diagonal
                { -1, 1 }, // Top-left diagonal
                { -1, -1 } // Bottom-left diagonal
        };
    }

    /**
     * Author(s):
     * 
     * Gets all valid moves for the Sau piece.
     * 
     * @param piece the Sau piece
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

    /**
     * Author(s):
     * 
     * Checks if a move is valid for the Sau piece.
     * 
     * @param x     the target x-coordinate
     * @param y     the target y-coordinate
     * @param board the game board
     * @param piece the Sau piece
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int x, int y, KwazamBoard board, KwazamPiece piece) {
        return board.isWithinBounds(x, y) &&
                (board.getPiece(x, y) == null || board.getPiece(x, y).getColor() != piece.getColor());
    }
}

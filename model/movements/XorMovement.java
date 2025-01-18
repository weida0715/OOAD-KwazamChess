package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Author(s):
 * 
 * Implements the movement strategy for the Xor piece.
 * Handles valid moves for the Xor in a Kwazam game.
 */
public class XorMovement implements MovementStrategy {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final int[][] directions;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs an XorMovement.
     * Initializes the possible movement directions for the Xor piece.
     */
    public XorMovement() {
        this.directions = new int[][] {
                { 1, 1 }, // Top-right diagonal
                { 1, -1 }, // Bottom-right diagonal
                { -1, 1 }, // Top-left diagonal
                { -1, -1 } // Bottom-left diagonal
        };
    }

    // =================================================================
    // MOVEMENT STRATEGY IMPLEMENTATION
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets all valid moves for the Xor piece.
     * 
     * @param piece the Xor piece
     * @param board the game board
     * @return list of valid moves as [x, y] coordinates
     */
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        for (int[] direction : directions) {
            checkDirectionalMoves(x, y, board, piece, validMoves, direction[0], direction[1]);
        }

        return validMoves;
    }

    // =================================================================
    // PRIVATE METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Checks valid moves in a specific direction for the Xor piece.
     * 
     * @param x          the starting x-coordinate
     * @param y          the starting y-coordinate
     * @param board      the game board
     * @param piece      the Xor piece
     * @param validMoves list to store valid moves
     * @param dx         the x-direction (1 or -1)
     * @param dy         the y-direction (1 or -1)
     */
    private void checkDirectionalMoves(int x, int y, KwazamBoard board, KwazamPiece piece,
            List<int[]> validMoves, int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;

        while (board.isWithinBounds(newX, newY)) {
            KwazamPiece targetPiece = board.getPiece(newX, newY);

            if (targetPiece == null) {
                validMoves.add(new int[] { newX, newY });
            } else {
                if (targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { newX, newY });
                }
                break; // Stop after encountering any piece
            }
            newX += dx;
            newY += dy;
        }
    }
}

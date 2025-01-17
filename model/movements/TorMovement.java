package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

/**
 * Author(s):
 * 
 * Implements the movement strategy for the Tor piece.
 * Handles valid moves for the Tor in a Kwazam game.
 */
public class TorMovement implements MovementStrategy {
    private final int[][] directions;

    /**
     * Author(s):
     * 
     * Constructs a TorMovement.
     * Initializes the possible movement directions for the Tor piece.
     */
    public TorMovement() {
        this.directions = new int[][] {
                { 0, 1 }, // Up
                { 0, -1 }, // Down
                { 1, 0 }, // Right
                { -1, 0 } // Left
        };
    }

    /**
     * Author(s):
     * 
     * Gets all valid moves for the Tor piece.
     * 
     * @param piece the Tor piece
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

    /**
     * Author(s):
     * 
     * Checks valid moves in a specific direction for the Tor piece.
     * 
     * @param x          the starting x-coordinate
     * @param y          the starting y-coordinate
     * @param board      the game board
     * @param piece      the Tor piece
     * @param validMoves list to store valid moves
     * @param dx         the x-direction (1 or -1)
     * @param dy         the y-direction (1 or -1)
     */
    private void checkDirectionalMoves(int x, int y, KwazamBoard board, KwazamPiece piece, List<int[]> validMoves,
            int dx, int dy) {
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

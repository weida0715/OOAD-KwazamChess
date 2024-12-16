package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

public class SauMovement implements MovementStrategy {
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        // Moving one step in any direction (horizontally, vertically, or diagonally)
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Orthogonal moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal moves
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (board.isWithinBounds(newX, newY)) {
                KwazamPiece targetPiece = board.getPiece(newX, newY);
                if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { newX, newY });
                }
            }
        }

        return validMoves;
    }
}

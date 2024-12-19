package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;
import model.pieces.Ram;

public class RamMovement implements MovementStrategy {
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        Ram ram = (Ram) piece;
        int x = ram.getX();
        int y = ram.getY();
        int direction = ram.getDirection();

        // Reverse direction if at the end of the board
        if (y == 0 && direction == -1) { // Reached top
            ram.setDirection(1); // Change to downward movement
        } else if (y == board.getRows() - 1 && direction == 1) { // Reached bottom
            ram.setDirection(-1); // Change to upward movement
        }

        // Calculate the next position based on the current direction
        int targetY = y + direction;
        if (board.isWithinBounds(x, targetY)) {
            KwazamPiece targetPiece = board.getPiece(x, targetY);
            if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                validMoves.add(new int[] { x, targetY });
            }
        }

        return validMoves;
    }
}

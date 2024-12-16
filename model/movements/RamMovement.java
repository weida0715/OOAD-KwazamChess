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
        if (y == 0 || y == board.getRows() - 1) {
            direction *= -1;
            ram.setDirection(direction);
        }

        // Moving in the current direction (forward or backward)
        if (board.isWithinBounds(x, y + direction)) {
            KwazamPiece targetPiece = board.getPiece(x, y + direction);
            if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                validMoves.add(new int[] { x, y + direction });
            }
        }

        return validMoves;
    }
}

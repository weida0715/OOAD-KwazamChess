package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

public class XorMovement implements MovementStrategy {
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        // Check diagonal moves in all 4 directions (XOR moves)
        checkDiagonalMoves(x, y, board, piece, validMoves, 1, 1); // Top-right diagonal
        checkDiagonalMoves(x, y, board, piece, validMoves, 1, -1); // Bottom-right diagonal
        checkDiagonalMoves(x, y, board, piece, validMoves, -1, 1); // Top-left diagonal
        checkDiagonalMoves(x, y, board, piece, validMoves, -1, -1); // Bottom-left diagonal
        
        return validMoves;
    }

    private void checkDiagonalMoves(int x, int y, KwazamBoard board, KwazamPiece piece, 
                                     List<int[]> validMoves, int dx, int dy) {
        int i = 1;
        while (board.isWithinBounds(x + dx * i, y + dy * i)) {
            KwazamPiece targetPiece = board.getPiece(x + dx * i, y + dy * i);

            if (targetPiece == null) {
                validMoves.add(new int[] { x + dx * i, y + dy * i });
            } else if (targetPiece.getColor() != piece.getColor()) {
                validMoves.add(new int[] { x + dx * i, y + dy * i });
                break;  // Capturing an opponent's piece
            } else {
                break;  // Blocked by a piece of the same color
            }
            i++;
        }
    }
}

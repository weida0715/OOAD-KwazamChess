package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

public class TorMovement implements MovementStrategy {
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        // Check vertical moves
        // Moving up
        for (int i = 1; i < board.getRows(); i++) {
            if (board.isWithinBounds(x, y + i)) {
                KwazamPiece targetPiece = board.getPiece(x, y + i);
                if (targetPiece == null) {
                    validMoves.add(new int[] { x, y + i });
                } else if (targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { x, y + i });
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Moving down
        for (int i = 1; i < board.getRows(); i++) {
            if (board.isWithinBounds(x, y - i)) {
                KwazamPiece targetPiece = board.getPiece(x, y - i);
                if (targetPiece == null) {
                    validMoves.add(new int[] { x, y - i });
                } else if (targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { x, y - i });
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Check horizontal moves
        // Moving right
        for (int i = 1; i < board.getCols(); i++) {
            if (board.isWithinBounds(x + i, y)) {
                KwazamPiece targetPiece = board.getPiece(x + i, y);
                if (targetPiece == null) {
                    validMoves.add(new int[] { x + i, y });
                } else if (targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { x + i, y });
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // Moving left
        for (int i = 1; i < board.getCols(); i++) {
            if (board.isWithinBounds(x - i, y)) {
                KwazamPiece targetPiece = board.getPiece(x - i, y);
                if (targetPiece == null) {
                    validMoves.add(new int[] { x - i, y });
                } else if (targetPiece.getColor() != piece.getColor()) {
                    validMoves.add(new int[] { x - i, y });
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return validMoves;
    }
}

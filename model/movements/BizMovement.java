package model.movements;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

public class BizMovement implements MovementStrategy {
    @Override
    public List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board) {
        List<int[]> validMoves = new ArrayList<>();
        int x = piece.getX();
        int y = piece.getY();

        int[][] directions = {
                { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
                { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (board.isWithinBounds(newX, newY)) {
                KwazamPiece targetPiece = board.getPiece(newX, newY);
                if (targetPiece == null || targetPiece.getColor() != piece.getColor())
                    validMoves.add(new int[] { newX, newY });
            }
        }

        return validMoves;
    }
}

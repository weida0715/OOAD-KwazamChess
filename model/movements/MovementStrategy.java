package model.movements;

import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;

public interface MovementStrategy {
    List<int[]> getValidMoves(KwazamPiece piece, KwazamBoard board);
}

package model.pieces;


import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class Biz extends KwazamPiece {
    public Biz(KwazamPieceColor color, int x, int y, MovementStrategy movementStrategy) {
        super(color, KwazamPieceType.BIZ, x, y, movementStrategy);
    }
}

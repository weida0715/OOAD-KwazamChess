package model.pieces;

import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class Ram extends KwazamPiece {
    int direction;

    public Ram(KwazamPieceColor color, int x, int y, MovementStrategy movementStrategy) {
        super(color, KwazamPieceType.RAM, x, y, movementStrategy);
        this.direction = color == KwazamPieceColor.RED ? 1 : -1;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int d) {
        this.direction = d;
    }
}

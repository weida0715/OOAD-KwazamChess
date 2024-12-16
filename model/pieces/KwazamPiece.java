package model.pieces;

import java.util.List;

import model.board.KwazamBoard;
import model.movements.MovementStrategy;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class KwazamPiece {
    protected KwazamPieceColor color;
    protected KwazamPieceType type;
    protected int x, y;
    protected MovementStrategy movementStrategy;

    public KwazamPiece() {
    }

    public KwazamPiece(KwazamPieceColor color, KwazamPieceType type, int x, int y, MovementStrategy movementStrategy) {
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
        this.movementStrategy = movementStrategy;
    }

    public KwazamPieceColor getColor() {
        return color;
    }

    public KwazamPieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<int[]> getValidMoves(KwazamBoard board) {
        return movementStrategy.getValidMoves(this, board);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(KwazamPieceType type) {
        this.type = type;
    }
}

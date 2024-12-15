package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.piece.Biz;
import model.piece.KwazamPiece;
import model.piece.Ram;
import model.piece.Sau;
import model.piece.Tor;
import model.piece.Xor;
import utils.KwazamPieceColor;

public class KwazamBoard {
    public List<KwazamPiece> pieces;

    public KwazamBoard() {
        pieces = new ArrayList<>();
    }

    public void setupBoard() {
        addPiece(new Tor(KwazamPieceColor.RED, 0, 0));
        addPiece(new Biz(KwazamPieceColor.RED, 1, 0));
        addPiece(new Sau(KwazamPieceColor.RED, 2, 0));
        addPiece(new Biz(KwazamPieceColor.RED, 3, 0));
        addPiece(new Xor(KwazamPieceColor.RED, 4, 0));
        addPiece(new Ram(KwazamPieceColor.RED, 0, 1));
        addPiece(new Ram(KwazamPieceColor.RED, 1, 1));
        addPiece(new Ram(KwazamPieceColor.RED, 2, 1));
        addPiece(new Ram(KwazamPieceColor.RED, 3, 1));
        addPiece(new Ram(KwazamPieceColor.RED, 4, 1));

        addPiece(new Ram(KwazamPieceColor.BLUE, 0, 6));
        addPiece(new Ram(KwazamPieceColor.BLUE, 1, 6));
        addPiece(new Ram(KwazamPieceColor.BLUE, 2, 6));
        addPiece(new Ram(KwazamPieceColor.BLUE, 3, 6));
        addPiece(new Ram(KwazamPieceColor.BLUE, 4, 6));
        addPiece(new Xor(KwazamPieceColor.BLUE, 0, 7));
        addPiece(new Biz(KwazamPieceColor.BLUE, 1, 7));
        addPiece(new Sau(KwazamPieceColor.BLUE, 2, 7));
        addPiece(new Biz(KwazamPieceColor.BLUE, 3, 7));
        addPiece(new Tor(KwazamPieceColor.BLUE, 4, 7));
    }

    public List<KwazamPiece> getPieces() {
        return pieces;
    }

    public KwazamPiece getPiece(int x, int y) {
        for (KwazamPiece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    public void addPiece(KwazamPiece piece) {
        pieces.add(piece);
    }

    public void removePiece(KwazamPiece p) {
        // Use an iterator to safely remove elements during iteration
        for (Iterator<KwazamPiece> iterator = pieces.iterator(); iterator.hasNext();) {
            KwazamPiece piece = iterator.next();
            if (p == piece) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }

    public void removePiece(int x, int y) {
        // Use an iterator to safely remove elements during iteration
        for (Iterator<KwazamPiece> iterator = pieces.iterator(); iterator.hasNext();) {
            KwazamPiece piece = iterator.next();
            if (piece.getX() == x && piece.getY() == y) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }

    public void capturePiece(KwazamPiece piece, int targetX, int targetY) {
        removePiece(targetX, targetY);
        movePiece(piece, targetX, targetY);
    }

    public void movePiece(KwazamPiece piece, int targetX, int targetY) {
        // Update the game board's piece positions
        getPiece(piece.getX(), piece.getY()).setX(targetX);
        getPiece(piece.getX(), piece.getY()).setY(targetY);
    }
}

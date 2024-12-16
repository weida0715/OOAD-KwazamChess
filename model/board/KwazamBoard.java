package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.pieces.KwazamPiece;
import model.pieces.KwazamPieceFactory;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class KwazamBoard {
    private final int cols;
    private final int rows;
    private final List<KwazamPiece> pieces;

    public KwazamBoard() {
        pieces = new ArrayList<>();
        cols = KwazamConstants.BOARD_COLS;
        rows = KwazamConstants.BOARD_ROWS;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public List<KwazamPiece> getPieces() {
        return pieces;
    }

    public KwazamPiece getRedSau() {
        for (KwazamPiece piece : pieces) {
            if (piece.getColor() == KwazamPieceColor.RED && piece.getType() == KwazamPieceType.SAU)
                return piece;
        }
        return null;
    }

    public KwazamPiece getBlueSau() {
        for (KwazamPiece piece : pieces) {
            if (piece.getColor() == KwazamPieceColor.BLUE && piece.getType() == KwazamPieceType.SAU)
                return piece;
        }
        return null;
    }
    
    public KwazamPiece getPiece(int x, int y) {
        for (KwazamPiece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    public void setupBoard() {
        // // Red Pieces
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.TOR, 0, 0));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.BIZ, 1, 0));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.SAU, 2, 0));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.BIZ, 3, 0));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.XOR, 4, 0));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.RAM, 0, 1));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.RAM, 1, 1));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.RAM, 2, 1));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.RAM, 3, 1));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.RED, KwazamPieceType.RAM, 4, 1));

        // // Blue Pieces
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.RAM, 0, 6));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.RAM, 1, 6));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.RAM, 2, 6));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.RAM, 3, 6));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.RAM, 4, 6));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.XOR, 0, 7));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.BIZ, 1, 7));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.SAU, 2, 7));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.BIZ, 3, 7));
        addPiece(KwazamPieceFactory.getPiece(KwazamPieceColor.BLUE, KwazamPieceType.TOR, 4, 7));
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

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < cols &&
                y >= 0 && y < rows;
    }
}

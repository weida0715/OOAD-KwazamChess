package model.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.pieces.KwazamPiece;
import model.pieces.KwazamPieceFactory;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Author(s):
 * 
 * Represents the game board for Kwazam.
 * Manages pieces, their positions, and board operations.
 */
public class KwazamBoard {
    private final int cols;
    private final int rows;
    private final List<KwazamPiece> pieces;

    /**
     * Author(s):
     * 
     * Constructs a KwazamBoard with default dimensions.
     * Initializes the board and piece list.
     */
    public KwazamBoard() {
        pieces = new ArrayList<>();
        cols = KwazamConstants.BOARD_COLS;
        rows = KwazamConstants.BOARD_ROWS;
    }

    /**
     * Author(s):
     * 
     * Gets the number of columns on the board.
     * 
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Author(s):
     * 
     * Gets the number of rows on the board.
     * 
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Author(s):
     * 
     * Gets the list of pieces on the board.
     * 
     * @return list of pieces
     */
    public List<KwazamPiece> getPieces() {
        return pieces;
    }

    /**
     * Author(s):
     * 
     * Gets the red Sau piece.
     * 
     * @return red Sau piece, or null if not found
     */
    public KwazamPiece getRedSau() {
        for (KwazamPiece piece : pieces) {
            if (piece.getColor() == KwazamPieceColor.RED && piece.getType() == KwazamPieceType.SAU)
                return piece;
        }
        return null;
    }

    /**
     * Author(s):
     * 
     * Gets the blue Sau piece.
     * 
     * @return blue Sau piece, or null if not found
     */
    public KwazamPiece getBlueSau() {
        for (KwazamPiece piece : pieces) {
            if (piece.getColor() == KwazamPieceColor.BLUE && piece.getType() == KwazamPieceType.SAU)
                return piece;
        }
        return null;
    }

    /**
     * Author(s):
     * 
     * Gets the piece at the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return piece at (x, y), or null if no piece exists
     */
    public KwazamPiece getPiece(int x, int y) {
        for (KwazamPiece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Author(s):
     * 
     * Sets up the board with initial piece positions.
     * Adds red and blue pieces to their starting locations.
     */
    public void setupBoard() {
        // Red Pieces
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

        // Blue Pieces
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

    /**
     * Author(s):
     * 
     * Adds a piece to the board.
     * 
     * @param piece the piece to add
     */
    public void addPiece(KwazamPiece piece) {
        pieces.add(piece);
    }

    /**
     * Author(s):
     * 
     * Removes a specific piece from the board.
     * 
     * @param p the piece to remove
     */
    public void removePiece(KwazamPiece p) {
        // Use an iterator to safely remove elements during iteration
        for (Iterator<KwazamPiece> iterator = pieces.iterator(); iterator.hasNext();) {
            KwazamPiece piece = iterator.next();
            if (p == piece) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }

    /**
     * Author(s):
     * 
     * Removes the piece at the specified coordinates.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void removePiece(int x, int y) {
        // Use an iterator to safely remove elements during iteration
        for (Iterator<KwazamPiece> iterator = pieces.iterator(); iterator.hasNext();) {
            KwazamPiece piece = iterator.next();
            if (piece.getX() == x && piece.getY() == y) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }

    /**
     * Author(s):
     * 
     * Captures a piece at the target location and moves the capturing piece.
     * 
     * @param piece   the capturing piece
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     */
    public void capturePiece(KwazamPiece piece, int targetX, int targetY) {
        removePiece(targetX, targetY);
        movePiece(piece, targetX, targetY);
    }

    /**
     * Author(s):
     * 
     * Moves a piece to the specified coordinates.
     * 
     * @param piece   the piece to move
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     */
    public void movePiece(KwazamPiece piece, int targetX, int targetY) {
        piece.setX(targetX);
        piece.setY(targetY);
    }

    /**
     * Author(s):
     * 
     * Checks if the coordinates are within the board bounds.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if within bounds, false otherwise
     */
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < cols &&
                y >= 0 && y < rows;
    }
}

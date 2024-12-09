package model.board;

import model.piece.Biz;
import model.piece.KwazamPiece;
import model.piece.Ram;
import model.piece.Sau;
import model.piece.Tor;
import model.piece.Xor;
import utils.KwazamConstants;
import utils.KwazamPieceColor;

public class KwazamBoard {
    public KwazamBoardSquare[][] boardSquares;

    public KwazamBoard() {
        boardSquares = new KwazamBoardSquare[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                boardSquares[row][col] = new KwazamBoardSquare();
            }
        }
    }

    public KwazamBoardSquare getSquare(int x, int y) {
        return boardSquares[y][x];
    }

    public void setupBoard() {
        addPiece(new Tor(KwazamPieceColor.RED), 0, 0);
        addPiece(new Biz(KwazamPieceColor.RED), 1, 0);
        addPiece(new Sau(KwazamPieceColor.RED), 2, 0);
        addPiece(new Biz(KwazamPieceColor.RED), 3, 0);
        addPiece(new Xor(KwazamPieceColor.RED), 4, 0);
        addPiece(new Ram(KwazamPieceColor.RED), 0, 1);
        addPiece(new Ram(KwazamPieceColor.RED), 1, 1);
        addPiece(new Ram(KwazamPieceColor.RED), 2, 1);
        addPiece(new Ram(KwazamPieceColor.RED), 3, 1);
        addPiece(new Ram(KwazamPieceColor.RED), 4, 1);

        addPiece(new Ram(KwazamPieceColor.BLUE), 0, 6);
        addPiece(new Ram(KwazamPieceColor.BLUE), 1, 6);
        addPiece(new Ram(KwazamPieceColor.BLUE), 2, 6);
        addPiece(new Ram(KwazamPieceColor.BLUE), 3, 6);
        addPiece(new Ram(KwazamPieceColor.BLUE), 4, 6);
        addPiece(new Xor(KwazamPieceColor.BLUE), 0, 7);
        addPiece(new Biz(KwazamPieceColor.BLUE), 1, 7);
        addPiece(new Sau(KwazamPieceColor.BLUE), 2, 7);
        addPiece(new Biz(KwazamPieceColor.BLUE), 3, 7);
        addPiece(new Tor(KwazamPieceColor.BLUE), 4, 7);
    }

    public void addPiece(KwazamPiece piece, int x, int y) {
        boardSquares[y][x].setPiece(piece);
    }

    public void removePiece(int x, int y) {
        boardSquares[y][x].setPiece(null);
    }

    /**
     * Moves a piece from one position to another on the board.
     * 
     * @param startX Starting column of the piece.
     * @param startY Starting row of the piece.
     * @param endX   Destination column for the piece.
     * @param endY   Destination row for the piece.
     */
    public void movePiece(int startX, int startY, int endX, int endY) {
        // Check if there is a piece at the start position
        KwazamPiece piece = boardSquares[startY][startX].getPiece();
        if (piece == null) {
            throw new IllegalArgumentException("No piece at the starting position.");
        }

        // Move the piece to the new position
        addPiece(piece, endX, endY);

        // Remove the piece from the old position
        removePiece(startX, startY);
    }
}

package model;

import java.awt.Color;
import model.pieces.Biz;
import model.pieces.KwazamPiece;
import model.pieces.Ram;
import model.pieces.Sau;
import model.pieces.Tor;
import model.pieces.Xor;
import model.utils.KwazamConstants;
import model.utils.KwazamPieceType;

public class KwazamBoard {
    public KwazamPieceType[][] gameGrid;
    public KwazamBoardSquare[][] boardSquares;
    
    public KwazamBoard() {
        gameGrid = new KwazamPieceType[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        boardSquares = new KwazamBoardSquare[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                gameGrid[row][col] = null;
                boardSquares[row][col] = new KwazamBoardSquare();
            }
        }
    }

    public KwazamBoardSquare getSquare(int x, int y) {
        return boardSquares[y][x];
    }

    public KwazamPieceType[][] getGameGrid() {
        return gameGrid;
    }

    // public void printGameGrid() {
    //     for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
    //         for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
    //             if (!boardSquares[row][col].isEmpty())
    //                 System.out.println(gameGrid[row][col]);
    //         }
    //     }
    // }

    public void setupBoard() {
        addPiece(new Tor(Color.RED), 0,0);
        addPiece(new Biz(Color.RED), 1,0);
        addPiece(new Sau(Color.RED), 2,0);
        addPiece(new Biz(Color.RED), 3,0);
        addPiece(new Xor(Color.RED), 4,0);
        addPiece(new Ram(Color.RED), 0,1);
        addPiece(new Ram(Color.RED), 1,1);
        addPiece(new Ram(Color.RED), 2,1);
        addPiece(new Ram(Color.RED), 3,1);
        addPiece(new Ram(Color.RED), 4,1);

        addPiece(new Ram(Color.BLUE), 0,6);
        addPiece(new Ram(Color.BLUE), 1,6);
        addPiece(new Ram(Color.BLUE), 2,6);
        addPiece(new Ram(Color.BLUE), 3,6);
        addPiece(new Ram(Color.BLUE), 4,6);
        addPiece(new Xor(Color.BLUE), 0,7);
        addPiece(new Biz(Color.BLUE), 1,7);
        addPiece(new Sau(Color.BLUE), 2,7);
        addPiece(new Biz(Color.BLUE), 3,7);
        addPiece(new Tor(Color.BLUE), 4,7);
    }

    public void addPiece(KwazamPiece piece, int x, int y) {
        boardSquares[y][x].setPiece(piece);
        gameGrid[y][x] = piece.getType();
    }

    public void removePiece(int x, int y) {
        boardSquares[y][x].setPiece(null);
        gameGrid[y][x] = null;
    }
}

package model;

import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.piece.KwazamPiece;
import utils.KwazamConstants;
import utils.KwazamPieceColor;

public class KwazamGameManager {
    private final KwazamBoard gameBoard;
    public String[][] gameState;
    private KwazamPieceColor currentColor;
    private boolean running;

    public KwazamGameManager() {
        this.gameBoard = new KwazamBoard();
        this.gameState = new String[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        this.currentColor = KwazamPieceColor.BLUE;
        this.running = false;
    }

    public KwazamBoard getGameBoard() {
        return gameBoard;
    }

    public String[][] getGameState() {
        return gameState;
    }

    public KwazamPieceColor getCurrentColor() {
        return currentColor;
    }

    public boolean isRunning() {
        return running;
    }

    public void initGame() {
        running = true;

        // Setup Board
        gameBoard.setupBoard();
        updateGameState();
    }

    public boolean movePiece(KwazamPiece piece, int targetX, int targetY) {
        if (isValidMove(piece, targetX, targetY)) {
            if (hasOpponentPiece(targetX, targetY))
                gameBoard.capturePiece(piece, targetX, targetY);
            else
                gameBoard.movePiece(piece, targetX, targetY);

            updateGameState();
            return true;
        }

        return false;
    }

    public boolean isValidMove(KwazamPiece piece, int targetX, int targetY) {
        if (piece.getX() == targetX && piece.getY() == targetY)
            return false;

        List<int[]> validMoves = checkValidMoves(piece);
        for (int[] move : validMoves) {
            if (targetX == move[0] && targetY == move[1])
                return true;
        }

        return false;
    }

    public List<int[]> checkValidMoves(KwazamPiece piece) {
        List<int[]> validMoves = new ArrayList<>();

        if (piece == null || piece.getColor() != currentColor)
            return validMoves;

        int pieceX = piece.getX();
        int pieceY = piece.getY();

        int nextY = (piece.getColor() == KwazamPieceColor.BLUE ? pieceY - 1 : pieceY + 1);

        if (pieceY >= 0 && pieceY < KwazamConstants.BOARD_ROWS) {
            KwazamPiece targetPiece = gameBoard.getPiece(pieceX, nextY);
            if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                validMoves.add(new int[] { pieceX, nextY });
            }
        }

        return validMoves;
    }

    public boolean hasOpponentPiece(int x, int y) {
        return (gameBoard.getPiece(x, y) != null);
    }

    public void printGameState() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                System.out.print(gameState[row][col] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void updateGameState() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (gameBoard.getPiece(col, row) != null) {
                    String colorString = gameBoard.getPiece(col, row).getColor().name().substring(0, 1);
                    String typeString = gameBoard.getPiece(col, row).getType().name();
                    gameState[row][col] = colorString + "_" + typeString;
                } else
                    gameState[row][col] = ".....";
            }
        }
    }

    public void switchColor() {
        if (currentColor == KwazamPieceColor.BLUE)
            currentColor = KwazamPieceColor.RED;
        else
            currentColor = KwazamPieceColor.BLUE;
    }

    public void resetGame() {
        // Reset game state
        currentColor = KwazamPieceColor.BLUE;
        initGame();
    }

    public void stopGame() {
        running = false;
    }
}

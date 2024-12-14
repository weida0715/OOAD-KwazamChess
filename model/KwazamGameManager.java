package model;

import model.board.KwazamBoard;
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

    public void updateGameState() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (gameBoard.getPiece(col, row) != null) {
                    String colorString = gameBoard.getPiece(col, row).getColor().name().substring(0, 1);
                    String typeString = gameBoard.getPiece(col, row).getType().name();
                    gameState[row][col] = colorString + "_" + typeString;
                } else
                    gameState[row][col] = "";
            }
        }
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

package model;

public class KwazamGameManager {
    KwazamBoard gameBoard;
    private int currentPlayer;
    
    public KwazamGameManager(KwazamBoard board) {
        this.gameBoard = board;
        this.currentPlayer = 1;
    }

    public KwazamBoard getGameBoard() {
        return gameBoard;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void initGame() {
        // Setup Board
        gameBoard.setupBoard();
        // gameBoard.printGameGrid();
    }

    public void initGameBoard() {
    }

    public void resetGame() {
        // Reset game state
        currentPlayer = 1;
        initGame();
    }
}

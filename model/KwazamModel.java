package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.board.KwazamBoard;
import model.pieces.KwazamPiece;
import model.pieces.KwazamPieceFactory;
import model.pieces.Ram;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

/**
 * Author(s):
 * 
 * Manages the Kwazam game logic.
 * Handles game state, piece movement, saving, loading, and win conditions.
 */
public class KwazamModel {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final KwazamBoard gameBoard;
    public String[][] gameState;
    private KwazamPieceColor currentColor;
    private String player1Name;
    private String player2Name;
    private String winner;
    private boolean running;
    private float turn;
    private String currentFilename;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a KwazamModel.
     * Initializes the game board, state, and default settings.
     */
    public KwazamModel() {
        this.gameBoard = new KwazamBoard();
        this.gameState = new String[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        this.currentColor = KwazamPieceColor.BLUE;
        this.running = false;
        this.currentFilename = null;
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets the game board.
     * 
     * @return the game board
     */
    public KwazamBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Author(s):
     * 
     * Gets the current game state.
     * 
     * @return the game state as a 2D array
     */
    public String[][] getGameState() {
        return gameState;
    }

    /**
     * Author(s):
     * 
     * Gets the current player's color.
     * 
     * @return the current player's color
     */
    public KwazamPieceColor getCurrentColor() {
        return currentColor;
    }

    /**
     * Author(s):
     * 
     * Gets player 1's name.
     * 
     * @return player 1's name
     */
    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * Author(s):
     * 
     * Gets player 2's name.
     * 
     * @return player 2's name
     */
    public String getPlayer2Name() {
        return player2Name;
    }

    /**
     * Author(s):
     * 
     * Gets the winner of the game.
     * 
     * @return the winner's name, or null if no winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Author(s):
     * 
     * Checks if the game is running.
     * 
     * @return true if the game is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Author(s):
     * 
     * Gets the current filename for saving/loading.
     * 
     * @return the current filename
     */
    public String getCurrentFilename() {
        return currentFilename;
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Sets the names of player 1 and player 2.
     * 
     * @param p1 player 1's name
     * @param p2 player 2's name
     */
    public void setPlayerNames(String p1, String p2) {
        this.player1Name = p1;
        this.player2Name = p2;
    }

    /**
     * Author(s):
     * 
     * Sets the current filename for saving/loading.
     * 
     * @param filename the filename to set
     */
    public void setCurrentFilename(String filename) {
        this.currentFilename = filename;
    }

    // =================================================================
    // GAME INITIALIZATION
    // =================================================================
    /**
     * Author(s):
     * 
     * Initializes the game.
     * Resets the board, sets up pieces, and marks the game as running.
     */
    public void initGame() {
        // Clear the board and reset the game state
        gameBoard.getPieces().clear();

        // Set up the board with the default chess piece positions
        gameBoard.setupBoard();

        // Mark the game as running
        running = true;

        // Reset the turn counter
        turn = 0.0f;

        // Update the game state
        updateGameState();
    }

    // =================================================================
    // GAME LOGIC
    // =================================================================
    /**
     * Author(s):
     * 
     * Moves a piece to the target location.
     * 
     * @param piece   the piece to move
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     * @return true if the move is valid and executed, false otherwise
     */
    public boolean movePiece(KwazamPiece piece, int targetX, int targetY) {
        if (isValidMove(piece, targetX, targetY)) {
            if (hasOpponentPiece(targetX, targetY))
                gameBoard.capturePiece(piece, targetX, targetY);
            else
                gameBoard.movePiece(piece, targetX, targetY);

            // Increment turn count after a move
            turn += 0.5f;

            if (turn % 2 == 0) {
                swapXorTor();
            }

            // Check if the opponent's Sau is in check after the move
            if (isSauCaptured()) {
                this.winner = currentColor == KwazamPieceColor.BLUE ? player1Name : player2Name;
                stopGame();
            }

            updateGameState();

            return true;
        }

        return false;
    }

    /**
     * Author(s):
     * 
     * Swaps all Xor and Tor pieces on the board.
     * Updates the game state after swapping.
     */
    public void swapXorTor() {
        List<KwazamPiece> piecesToRemove = new ArrayList<>();

        // First, collect the pieces to be swapped
        for (KwazamPiece piece : gameBoard.getPieces()) {
            if (piece.getType() == KwazamPieceType.XOR || piece.getType() == KwazamPieceType.TOR) {
                piecesToRemove.add(piece);
            }
        }

        // Remove the pieces
        for (KwazamPiece piece : piecesToRemove) {
            gameBoard.getPieces().remove(piece);

            // Create the new piece and add it back
            KwazamPieceType newType = piece.getType() == KwazamPieceType.XOR ? KwazamPieceType.TOR
                    : KwazamPieceType.XOR;
            KwazamPiece newPiece = KwazamPieceFactory.getPiece(piece.getColor(), newType, piece.getX(), piece.getY());
            gameBoard.addPiece(newPiece);
        }

        updateGameState();
    }

    /**
     * Author(s):
     * 
     * Checks if a move is valid for the given piece.
     * 
     * @param piece   the piece to move
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(KwazamPiece piece, int targetX, int targetY) {
        if (piece.getX() == targetX && piece.getY() == targetY)
            return false;

        List<int[]> validMoves = piece.getValidMoves(gameBoard);
        for (int[] move : validMoves) {
            if (targetX == move[0] && targetY == move[1])
                return true;
        }

        return false;
    }

    /**
     * Author(s):
     * 
     * Checks if an opponent's piece is at the target location.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if an opponent's piece is present, false otherwise
     */
    public boolean hasOpponentPiece(int x, int y) {
        return (gameBoard.getPiece(x, y) != null);
    }

    /**
     * Author(s):
     * 
     * Checks if the Sau of the given color is in check.
     * 
     * @param color the color of the Sau
     * @return true if the Sau is in check, false otherwise
     */
    public boolean isSauInCheck(KwazamPieceColor color) {
        // Get the Sau for the current player
        KwazamPiece sau = getSau(color);
        if (sau == null) {
            return false; // No Sau found, which shouldn't happen in a valid game
        }

        // Iterate through all pieces of the opponent
        for (KwazamPiece piece : gameBoard.getPieces()) {
            if (piece.getColor() != color) { // Only consider opponent's pieces
                List<int[]> validMoves = piece.getValidMoves(gameBoard);
                for (int[] move : validMoves) {
                    if (move[0] == sau.getX() && move[1] == sau.getY()) {
                        return true; // Sau is under attack
                    }
                }
            }
        }

        return false; // Sau is safe
    }

    /**
     * Author(s):
     * 
     * Gets the Sau piece for the given color.
     * 
     * @param color the color of the Sau
     * @return the Sau piece, or null if not found
     */
    public KwazamPiece getSau(KwazamPieceColor color) {
        for (KwazamPiece piece : gameBoard.getPieces()) {
            if (piece.getType() == KwazamPieceType.SAU && piece.getColor() == color) {
                return piece;
            }
        }
        return null; // Sau not found
    }

    /**
     * Author(s):
     * 
     * Switches the current player's color.
     */
    public void switchColor() {
        if (currentColor == KwazamPieceColor.BLUE)
            currentColor = KwazamPieceColor.RED;
        else
            currentColor = KwazamPieceColor.BLUE;
    }

    /**
     * Author(s):
     * 
     * Checks if a Sau has been captured.
     * 
     * @return true if a Sau is captured, false otherwise
     */
    public boolean isSauCaptured() {
        return gameBoard.getRedSau() == null || gameBoard.getBlueSau() == null;
    }

    /**
     * Author(s):
     * 
     * Checks if a winner has been found.
     * 
     * @return true if a winner exists, false otherwise
     */
    public boolean isWinnerFound() {
        return winner != null;
    }

    // =================================================================
    // GAME STATE MANAGEMENT
    // =================================================================
    /**
     * Author(s):
     * 
     * Updates the game state based on the current board.
     */
    public void updateGameState() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                KwazamPiece piece = gameBoard.getPiece(col, row);
                if (piece != null) {
                    String colorString = piece.getColor().name().substring(0, 1); // 'R' or 'B'
                    String typeString = piece.getType().name(); // Piece type like 'SAU', 'TOR'

                    // If the piece is a Ram, append the direction to the string
                    if (piece.getType() == KwazamPieceType.RAM) {
                        Ram ramPiece = (Ram) piece;
                        String directionString = (ramPiece.getDirection() == 1) ? "_D" : "_U";
                        gameState[row][col] = colorString + "_" + typeString + directionString;
                    } else {
                        gameState[row][col] = colorString + "_" + typeString; // Regular piece without direction
                    }
                } else {
                    gameState[row][col] = "....."; // Empty cell
                }
            }
        }
    }

    /**
     * Author(s):
     * 
     * Saves the current game state to a file.
     * 
     * @param filename the name of the file to save
     */
    public void saveGame(String filename) {
        // If a filename is provided, update the currentFilename
        if (filename != null) {
            this.currentFilename = filename + ".txt";
        }

        // If no filename is set, return (this should not happen if the controller
        // handles it correctly)
        if (this.currentFilename == null)
            return;

        // Ensure the /data folder exists
        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir(); // Create the /data folder if it doesn't exist
        }

        // Prepend the /data folder path to the filename
        String filePath = "data/" + this.currentFilename;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write title for the save game file
            writer.write("===== Kwazam Game Save =====");
            writer.newLine(); // Blank line for separation

            // Write player names
            writer.write("Player 1 (BLUE): " + player1Name);
            writer.newLine();
            writer.write("Player 2 (RED): " + player2Name);
            writer.newLine(); // Blank line for separation

            // Write current turn color
            writer.write("Current Turn: " + currentColor);
            writer.newLine();
            writer.newLine(); // Blank line for separation

            // Write the game board header
            writer.write("===== Game Board =====");
            writer.newLine(); // Blank line for separation

            // Define the width for each cell, based on the longest string in your pieces
            int cellWidth = 10; // You can adjust this value depending on the longest piece string

            // Loop through the game state array and write each piece with consistent width
            for (int row = 0; row < gameState.length; row++) {
                for (int col = 0; col < gameState[row].length; col++) {
                    String cell = gameState[row][col];

                    // Pad each cell to ensure it's the same width for alignment
                    writer.write(String.format("%-" + cellWidth + "s", cell)); // Left-align the text within the cell
                                                                               // width
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Author(s):
     * 
     * Loads a saved game from a file.
     */
    public void loadGame(String filename) {
        this.currentFilename = filename;
        File saveFile = new File("data/" + filename);
        if (!saveFile.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String line;
            String[] playerNames = new String[2];
            int playerIndex = 0;

            // Read player names and current turn
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player 1")) {
                    playerNames[playerIndex++] = line.split(":")[1].trim();
                } else if (line.startsWith("Player 2")) {
                    playerNames[playerIndex++] = line.split(":")[1].trim();
                } else if (line.startsWith("Current Turn")) {
                    this.currentColor = KwazamPieceColor.valueOf(line.split(":")[1].trim());
                } else if (line.startsWith("===== Game Board =====")) {
                    break; // Skip the "Game Board:" line
                }
            }

            // Set player names
            this.player1Name = playerNames[0];
            this.player2Name = playerNames[1];

            // Read the game state (board)
            this.gameState = new String[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
            int row = 0;
            while ((line = reader.readLine()) != null) {
                // Split each line of board into cells
                String[] cells = line.trim().split("\\s+");
                for (int col = 0; col < cells.length; col++) {
                    gameState[row][col] = cells[col]; // Store the game state
                }
                row++;
            }

            // Reconstruct the game board
            this.gameBoard.getPieces().clear(); // Clear the current board state
            for (int i = 0; i < gameState.length; i++) {
                for (int j = 0; j < gameState[i].length; j++) {
                    String cell = gameState[i][j];
                    if (!cell.equals(".....")) { // Not an empty cell
                        String[] parts = cell.split("_");
                        KwazamPieceColor color = parts[0].equals("R") ? KwazamPieceColor.RED : KwazamPieceColor.BLUE;
                        KwazamPieceType type = KwazamPieceType.valueOf(parts[1]);

                        // Create the piece based on type and color
                        KwazamPiece piece = KwazamPieceFactory.getPiece(color, type, j, i);

                        // If the piece is a Ram, set its direction (U or D)
                        if (piece.getType() == KwazamPieceType.RAM) {
                            String direction = parts.length > 2 ? parts[2] : ""; // Check for direction (U or D)
                            Ram ramPiece = (Ram) piece;
                            if ("U".equals(direction)) {
                                ramPiece.setDirection(-1); // Up (-1)
                            } else if ("D".equals(direction)) {
                                ramPiece.setDirection(1); // Down (1)
                            }
                        }

                        gameBoard.addPiece(piece); // Add the piece to the game board
                    }
                }
            }

            updateGameState(); // Update the game state after loading the game board

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Author(s):
     * 
     * Checks if a saved game exists.
     * 
     * @return true if a saved game exists, false otherwise
     */
    public boolean hasSavedGame() {
        if (this.currentFilename == null) {
            return false; // No filename set, so no saved game exists
        }

        File saveFile = new File("data/" + this.currentFilename);
        return saveFile.exists() && saveFile.isFile() && saveFile.length() > 0;
    }

    /**
     * Author(s):
     * 
     * Clears the saved game file.
     */
    public void clearSavedGame() {
        if (this.currentFilename == null) {
            return; // No filename set, so nothing to delete
        }

        File saveFile = new File("data/" + this.currentFilename);
        if (saveFile.exists()) {
            saveFile.delete(); // Delete the saved game file
        }
    }

    /**
     * Author(s):
     * 
     * Resets the game to its initial state.
     */
    public void resetGame() {

        // Reset the current turn to the starting color (e.g., BLUE)
        currentColor = KwazamPieceColor.BLUE;

        // Reset other game state variables
        winner = null;

        // Reinitialize the game board with the default setup
        initGame();
    }

    /**
     * Author(s):
     * 
     * Stops the game.
     */
    public void stopGame() {
        running = false;
    }
}
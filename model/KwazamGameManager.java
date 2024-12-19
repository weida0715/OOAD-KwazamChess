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

public class KwazamGameManager {
    private final KwazamBoard gameBoard;
    public String[][] gameState;
    private KwazamPieceColor currentColor;
    private String player1Name;
    private String player2Name;
    private String winner;
    private boolean running;
    private float turn;

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

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getWinner() {
        return winner;
    }

    public void setPlayerNames(String p1, String p2) {
        this.player1Name = p1;
        this.player2Name = p2;
    }

    public boolean isRunning() {
        return running;
    }

    public void initGame() {
        running = true;
        turn = 0.0f;

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

            // Increment turn count after a move
            turn += 0.5f;

            if (turn % 1 == 0) {
                swapXorTor();
            }

            if (isSauCaptured()) {
                this.winner = currentColor == KwazamPieceColor.BLUE ? player1Name : player2Name;
                stopGame();
            }

            return true;
        }

        return false;
    }

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

    public void switchColor() {
        if (currentColor == KwazamPieceColor.BLUE)
            currentColor = KwazamPieceColor.RED;
        else
            currentColor = KwazamPieceColor.BLUE;
    }

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
                        String directionString = (ramPiece.getDirection() == 1) ? "_U" : "_D";
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

    public boolean isSauCaptured() {
        return gameBoard.getRedSau() == null || gameBoard.getBlueSau() == null;
    }

    public boolean isWinnerFound() {
        return winner != null;
    }

    public void saveGame() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/savegame.txt"))) {
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

    public void loadGame() {
        File saveFile = new File("data/savegame.txt");
        if (!saveFile.exists()) {
            System.out.println("No saved game found.");
            return;
        }

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
                                ramPiece.setDirection(1); // Up (1)
                            } else if ("D".equals(direction)) {
                                ramPiece.setDirection(-1); // Down (-1)
                            }
                        }

                        gameBoard.addPiece(piece); // Add the piece to the game board
                    }
                }
            }

            updateGameState(); // Update the game state after loading the game board

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }

    public boolean hasSavedGame() {
        File saveFile = new File("data/savegame.txt"); // Example file name
        return saveFile.exists() && saveFile.isFile() && saveFile.length() > 0;
    }

    public void clearSavedGame() {
        File saveFile = new File("data/savegame.txt");
        saveFile.delete();
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

package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author(s):
 * 
 * Handles saving and loading game states to/from files.
 * Provides utility methods for file operations.
 */
public class GameFileHandler {
    /**
     * Author(s):
     * 
     * Saves the game state to a file.
     * 
     * @param gameState the game state as a 2D array
     * @param fileName  the name of the file to save
     * @throws IOException if an I/O error occurs
     */
    public static void saveGameToFile(String[][] gameState, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] row : gameState) {
                writer.write(String.join(" ", row)); // Write each row as space-separated values
                writer.newLine(); // Move to the next line
            }
        }
    }

    /**
     * Author(s):
     * 
     * Loads the game state from a file.
     * 
     * @param fileName the name of the file to load
     * @return the game state as a 2D array
     * @throws IOException if an I/O error occurs
     */
    public static String[][] loadGameFromFile(String fileName) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.trim().split(" "); // Split each line by spaces
                rows.add(row);
            }
        }

        // Convert the List to a 2D array
        return rows.toArray(new String[0][0]);
    }
}

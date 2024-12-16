package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameFileHandler {

    /**
     * Saves the game state to a file.
     * @param gameState The 2D array representing the game state.
     * @param fileName The name of the file to save the game state.
     * @throws IOException If an I/O error occurs.
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
     * Loads the game state from a file.
     * @param fileName The name of the file to load the game state from.
     * @return The 2D array representing the game state.
     * @throws IOException If an I/O error occurs.
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

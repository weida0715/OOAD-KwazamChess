package view.dialogs;

import java.io.File;
import java.util.Optional;
import javax.swing.*;

/**
 * Displays dialogs for saving the game.
 * Handles filename input and validation.
 */
public class SaveGameDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s): Lam Rong Yi
     * 
     * Prompts the user to save the game before performing an action.
     * 
     * @param parent the parent frame for the dialog
     * @param action the action being performed (e.g., "quitting", "restarting")
     * @return true if the user chooses to save, false otherwise
     */
    public boolean promptSaveBeforeAction(JFrame parent, String action) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                "Do you want to save the current game before " + action + "?",
                "Save Game",
                JOptionPane.YES_NO_OPTION); // Only show Yes and No options

        return response == JOptionPane.YES_OPTION; // Return true if the user clicks Yes
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Displays a dialog to prompt the user for a filename to save the game.
     * If the file already exists in the /data folder, prompts the user to reenter a
     * filename.
     *
     * @param parent The parent component for the dialog (can be null).
     * @return An optional containing the filename if provided, otherwise empty.
     */

    public Optional<String> showDialog(JFrame parent) {
        while (true) {
            String filename = JOptionPane.showInputDialog(
                    parent,
                    "Enter a filename to save the game:",
                    "Save Game",
                    JOptionPane.PLAIN_MESSAGE);

            // If the user cancels the dialog, return empty
            if (filename == null) {
                return Optional.empty();
            }

            // Trim the filename and check if it's empty
            filename = filename.trim();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(
                        parent,
                        "Filename cannot be empty!",
                        "Invalid Filename",
                        JOptionPane.WARNING_MESSAGE);
                continue; // Prompt the user again
            }

            // Check if the file already exists in the /data folder
            File file = new File("data/" + filename);
            if (file.exists()) {
                JOptionPane.showMessageDialog(
                        parent,
                        "A file with this name already exists. Please enter a different filename.",
                        "File Exists",
                        JOptionPane.WARNING_MESSAGE);
                continue; // Prompt the user again
            }

            // If the filename is valid and doesn't exist, return it
            return Optional.of(filename);
        }
    }
}
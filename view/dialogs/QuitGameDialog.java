package view.dialogs;

import javax.swing.*;

/**
 * Author(s):
 * 
 * Displays a dialog to confirm quitting the game.
 */
public class QuitGameDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Shows the quit game confirmation dialog.
     * 
     * @param parent the parent frame for the dialog
     * @return true if the user confirms, false otherwise
     */
    public boolean showDialog(JFrame parent) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }
}

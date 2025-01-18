package view.dialogs;

import javax.swing.*;

/**
 * Author(s):
 * 
 * Displays a dialog to confirm starting a new game.
 */
public class NewGameDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Shows the new game confirmation dialog.
     * 
     * @param parent the parent frame for the dialog
     * @return true if the user confirms, false otherwise
     */
    public boolean showDialog(JFrame parent) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                "Do you want to start a new game?",
                "New Game",
                JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }

}

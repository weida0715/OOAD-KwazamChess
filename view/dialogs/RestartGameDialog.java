package view.dialogs;

import javax.swing.*;

/**
 * Author(s):
 * 
 * Displays a dialog to confirm restarting the game.
 */
public class RestartGameDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Shows the restart game confirmation dialog.
     * 
     * @param parent the parent frame for the dialog
     * @return true if the user confirms, false otherwise
     */
    public boolean showDialog(JFrame parent) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                "Do you want to restart the game?",
                "Restart Game",
                JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }

}

// In PostGameDialog.java

package view.dialogs;

import javax.swing.*;

/**
 * Displays a dialog for the next step after the user wins the game.
 * The user can choose to restart the game, start a new game, or quit.
 */
public class PostGameDialog {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    // Constants for the user's choice
    public static final int RESTART_GAME = 0;
    public static final int NEW_GAME = 1;
    public static final int QUIT = 2;

    /**
     * Author(s): Lam Rong Yi
     * 
     * @param parent The parent JFrame to which this dialog is attached.
     * @return An integer representing the user's choice (RESTART_GAME, NEW_GAME, or
     *         QUIT).
     */
    public int showDialog(JFrame parent) {
        // Options for the dialog
        String[] options = { "Restart Game", "New Game", "Quit" };

        // Show the dialog and get the user's choice
        int response = JOptionPane.showOptionDialog(
                parent,
                "What would you like to do next?",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        // Return the corresponding constant based on the user's choice
        if (response == 0) {
            return RESTART_GAME;
        } else if (response == 1) {
            return NEW_GAME;
        } else {
            return QUIT;
        }
    }
}
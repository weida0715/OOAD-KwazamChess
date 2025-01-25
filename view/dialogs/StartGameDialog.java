package view.dialogs;

import java.util.Optional;
import javax.swing.*;

/**
 * Displays a dialog to collect player names at the start of the game.
 */
public class StartGameDialog {

    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Displays the start game dialog to collect player names.
     *
     * @param parent The parent component for the dialog (can be null).
     * @return An optional array containing the two player names. Empty if the user
     *         cancels.
     */
    public Optional<String[]> showDialog(JFrame parent) {
        // Create input fields for player names
        JTextField player1Field = new JTextField();
        JTextField player2Field = new JTextField();
        Object[] message = {
                "Enter Player 1 Name:", player1Field,
                "Enter Player 2 Name:", player2Field
        };

        // Show the dialog
        int option = JOptionPane.showConfirmDialog(
                parent,
                message,
                "Start Game",
                JOptionPane.OK_CANCEL_OPTION);

        // Check the user's response
        if (option == JOptionPane.OK_OPTION) {
            String player1Name = player1Field.getText().trim();
            String player2Name = player2Field.getText().trim();

            // Validate names
            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        parent,
                        "Both player names must be provided!",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                return showDialog(parent); // Retry dialog
            }

            return Optional.of(new String[] { player1Name, player2Name });
        }

        return Optional.empty(); // User cancelled
    }
}

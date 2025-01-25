package view.dialogs;

import java.awt.*;
import javax.swing.*;

/**
 * Displays a dialog when the game ends.
 * Shows the winner and provides a close button.
 */
public class EndGameDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s): Ng Wei Da, Lam Rong Yi
     * 
     * Shows the end game dialog with the winner's name.
     * 
     * @param parent the parent frame for the dialog
     * @param winner the name of the winner
     */
    public void showDialog(Frame parent, String winner) {
        // Create a modal dialog
        JDialog dialog = new JDialog(parent, "Game Over", true); // 'true' makes it modal

        // Set the dialog size and layout
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent); // Center the dialog on the screen

        // Set the layout of the dialog
        dialog.setLayout(new BorderLayout());

        // Create a message panel to display the winner
        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("Game Over! " + winner + " wins!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messagePanel.add(messageLabel);

        // Create a button panel with a "Close" button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose()); // Close the dialog when the button is clicked
        buttonPanel.add(closeButton);

        // Add the panels to the dialog
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Display the dialog
        dialog.setVisible(true);
    }
}

package view.dialogs;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Author(s):
 * 
 * Displays a dialog with the rules of the Kwazam game.
 * Includes game introduction, winning conditions, and piece movement rules.
 */
public class RulesDialog {
    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Shows the rules dialog.
     * 
     * @param parent the parent frame for the dialog
     */
    public void showDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Game Rules", true); // 'true' makes it modal
        dialog.setSize(800, 800);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen

        // Create a panel to hold the rules and table
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add the "Game Introduction" heading as a bold JLabel
        JLabel introHeading = new JLabel("Game Introduction:");
        introHeading.setFont(new Font("Arial", Font.BOLD, 14)); // Set bold font
        introHeading.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        panel.add(introHeading);

        // Create a panel to hold the introText and board image side by side
        JPanel introPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Use FlowLayout with left alignment
        introPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left

        // Add the game introduction text in a JTextArea
        JTextArea introText = new JTextArea(
                """
                        1) This will be a 5 x 8 board

                        2) There will be a Total of 10 pieces on each of the board

                        3) There will be 5 Ram Pieces on each side of the board

                        4) There will be 2 Biz Pieces on each side of the board

                        5) There will be 1 Tor pieces on each side of the board

                        6) There will be 1 Xor pieces on each side of the board

                        7) There will be 1 Sau pieces on each side of the board

                        """);
        introText.setEditable(false); // Make the text area non-editable
        introText.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size and style
        introText.setBackground(dialog.getBackground()); // Match the background color
        introText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        introPanel.add(introText); // Add text to the panel

        // Load the board image
        try {
            BufferedImage boardImage = ImageIO.read(getClass().getResourceAsStream("/images/board.png"));
            if (boardImage != null) {
                // Resize the image to fit nicely
                Image resizedImage = boardImage.getScaledInstance(250, 350, Image.SCALE_SMOOTH);
                JLabel boardLabel = new JLabel(new ImageIcon(resizedImage));
                introPanel.add(boardLabel); // Add image to the panel
            }
        } catch (IOException e) {
            System.out.println("Error loading board image: " + e.getMessage());
        }

        // Add the introPanel to the main panel
        panel.add(introPanel);

        // Add the "How to Win the Game" heading as a bold JLabel
        JLabel winHeading = new JLabel("How to Win the Game:");
        winHeading.setFont(new Font("Arial", Font.BOLD, 14)); // Set bold font
        winHeading.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        panel.add(winHeading);

        // Add the "How to Win the Game" text in a JTextArea
        JTextArea winText = new JTextArea(
                "1) Once the Sau has been captured by any enemy pieces, the game will end\n");
        winText.setEditable(false); // Make the text area non-editable
        winText.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size and style
        winText.setBackground(dialog.getBackground()); // Match the background color
        winText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        winText.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        panel.add(winText);

        // Create a table model with three columns: Pieces, Name, Rules
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class; // First column contains ImageIcon
                }
                return String.class; // Other columns contain String
            }
        };
        tableModel.addColumn("Pieces");
        tableModel.addColumn("Name");
        tableModel.addColumn("Rules");

        // Add rows for each piece
        addPieceRow(tableModel, "Ram", "b_ram.png",
                "Moves 1 step forward. If it reaches the end of the board, it turns around and heads back.");
        addPieceRow(tableModel, "Biz", "b_biz.png", "Moves in a 3x2 L shape. Can skip over other pieces.");
        addPieceRow(tableModel, "Tor", "b_tor.png", "Moves orthogonally at any distance. After 2 turns, becomes Xor.");
        addPieceRow(tableModel, "Xor", "b_xor.png", "Moves diagonally at any distance. After 2 turns, becomes Tor.");
        addPieceRow(tableModel, "Sau", "b_sau.png",
                "Moves only one step in any direction. Game ends if Sau is captured by the other side.");

        // Create a JTable with the table model
        JTable table = new JTable(tableModel);
        table.setRowHeight(30); // Set smaller row height (default is 16)
        table.getColumnModel().getColumn(0).setPreferredWidth(40); // Set smaller width for the Pieces column
        table.getColumnModel().getColumn(1).setPreferredWidth(40); // Set smaller width for the Name column
        table.getColumnModel().getColumn(2).setPreferredWidth(400); // Set smaller width for the Rules column

        // Center the text in the "Name" column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Center-align the text
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Make the table header bold
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12)); // Set bold font for the header (smaller size)

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the panel
        panel.add(scrollPane);

        // Add the panel to the dialog
        dialog.add(panel);

        dialog.setVisible(true);
    }

    // =================================================================
    // PRIVATE METHODS
    // =================================================================
    /**
     * Author(s):
     * 
     * Adds a row to the rules table for a specific piece.
     * 
     * @param tableModel    the table model to add the row to
     * @param pieceName     the name of the piece
     * @param imageFileName the filename of the piece's image
     * @param pieceRules    the movement rules for the piece
     */
    private void addPieceRow(DefaultTableModel tableModel, String pieceName, String imageFileName, String pieceRules) {
        // Load the image
        BufferedImage pieceImage = null;
        try {
            pieceImage = ImageIO.read(getClass().getResourceAsStream("/images/" + imageFileName));
        } catch (IOException e) {
            System.out.println("Error loading image for " + pieceName + ": " + e.getMessage());
        }

        // Resize the image to a smaller size (e.g., 20x20 pixels)
        Image resizedImage = pieceImage != null ? pieceImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH) : null;

        // Create an ImageIcon from the resized image
        ImageIcon icon = resizedImage != null ? new ImageIcon(resizedImage) : null;

        // Add the image, name, and rules as a row in the table
        tableModel.addRow(new Object[] { icon, pieceName, pieceRules });
    }
}

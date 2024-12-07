package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.utils.KwazamConstants;
import model.utils.KwazamPieceType;

public class KwazamBoardPanel extends JPanel {
    private KwazamPieceType[][] renderGrid;
    private final Map<KwazamPieceType, Image> pieceImages;
    private int panelWidth, panelHeight;
    private int boardWidth, boardHeight;
    private int squareSize;
    private int squareWidth, squareHeight;
    private int xOffset, yOffset;

    public KwazamBoardPanel() {
        renderGrid = new KwazamPieceType[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        pieceImages = new HashMap<>();

        loadPieceImages();

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                renderGrid[row][col] = null;
            }
        }
    }

    public void printRenderGrid() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (renderGrid[row][col] != null)
                    System.out.println(renderGrid[row][col]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        panelWidth = getWidth();
        panelHeight = getHeight();

        boardWidth = (int) (panelWidth * 0.9);
        boardHeight = (int) (panelHeight * 0.9);

        squareWidth = boardWidth / KwazamConstants.BOARD_COLS;
        squareHeight = boardHeight / KwazamConstants.BOARD_ROWS;

        squareSize = Math.min(squareWidth, squareHeight);
        boardWidth = squareSize * KwazamConstants.BOARD_COLS;
        boardHeight = squareSize * KwazamConstants.BOARD_ROWS;

        xOffset = (panelWidth - boardWidth) / 2;
        yOffset = (panelHeight - boardHeight) / 2;

        drawBoard(g);
        drawPieces(g);
    }

    private void loadPieceImages() {
        pieceImages.put(KwazamPieceType.B_RAM, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.B_BIZ, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.B_TOR, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.B_XOR, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.B_SAU, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.R_RAM, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.R_BIZ, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.R_TOR, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.R_XOR, new ImageIcon("images/ram.png").getImage());
        pieceImages.put(KwazamPieceType.R_SAU, new ImageIcon("images/ram.png").getImage());
    }

    public void updateRenderBoard(KwazamPieceType[][] grid) {
        this.renderGrid = grid;
        printRenderGrid();
    }

    public void drawBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(KwazamConstants.BACKGROUND_COLOR);
        g2.fillRect(0, 0, panelWidth, panelHeight);

        g2.setStroke(new BasicStroke(KwazamConstants.BORDER_WIDTH));

        // Draw the grid squares
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                // Alternate colors for squares
                Color squareColor = (row + col) % 2 == 0 ? KwazamConstants.SQUARE_COLOR_1
                        : KwazamConstants.SQUARE_COLOR_2;
                g2.setColor(squareColor);
                g2.fillRect(xOffset + col * squareSize, yOffset + row * squareSize, squareSize, squareSize);

                // Draw borders around each square
                g2.setColor(KwazamConstants.BORDER_COLOR);
                g2.drawRect(xOffset + col * squareSize, yOffset + row * squareSize, squareSize, squareSize);
            }
        }

        // Draw coordinates
        g2.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 14);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);

        // Draw column letters (A to E)
        for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
            String text = String.valueOf((char) ('A' + col));
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();
            g2.drawString(text, xOffset + col * squareSize + (squareSize - textWidth) / 2, yOffset - textHeight / 2);
        }

        // Draw row numbers (1 to 8)
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            String text = String.valueOf(row + 1);
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();
            g2.drawString(text, xOffset - textWidth - 5,
                    yOffset + row * squareSize + (squareSize + textHeight) / 2 - 5);
        }

        // Draw the outer board border
        g2.setColor(KwazamConstants.BORDER_COLOR);
        g2.drawRect(xOffset, yOffset, boardWidth, boardHeight);
    }

    public void drawPieces(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Draw the pieces based on the renderGrid
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                KwazamPieceType piece = renderGrid[row][col];
                if (piece != null) {
                    Image pieceImage = pieceImages.get(piece);
                    if (pieceImage != null) {
                        // Instead of getScaledInstance, use drawImage with scaling
                        int scaledWidth = (int) (squareSize * 0.9);
                        int scaledHeight = (int) (squareSize * 0.9);

                        // Calculate the position to center the piece on the square
                        int x = xOffset + col * squareSize + (squareSize - scaledWidth) / 2;
                        int y = yOffset + row * squareSize + (squareSize - scaledHeight) / 2;

                        // Draw the scaled image directly
                        g2.drawImage(pieceImage, x, y, scaledWidth, scaledHeight, null);
                    }
                }
            }
        }
    }

}

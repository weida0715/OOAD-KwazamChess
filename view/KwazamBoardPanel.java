package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.utils.KwazamConstants;
import model.utils.KwazamPieceType;

public class KwazamBoardPanel extends JPanel {
    private KwazamPieceType[][] renderGrid;
    private final List<KwazamPiecePanel> piecePanels; // List of piece panels
    private int panelWidth, panelHeight;
    private int boardWidth, boardHeight;
    private int squareSize;
    private int squareWidth, squareHeight;
    private int xOffset, yOffset;
    private boolean boardFlipped = false;
    private Color opponentColor; // Added attribute to track the opponent's color

    public KwazamBoardPanel() {
        this.renderGrid = new KwazamPieceType[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        this.piecePanels = new ArrayList<>();
        this.opponentColor = Color.RED; // Initialize the opponent's color
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
        drawCoordinates(g);  // Added method to draw coordinates
        drawPieces(g);
    }

    public void updateRenderBoard(KwazamPieceType[][] grid) {
        this.renderGrid = grid;
        syncPiecePanels();
        repaint();
    }

    private void syncPiecePanels() {
        piecePanels.clear();

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                KwazamPieceType piece = renderGrid[row][col];
                if (piece != null) {
                    KwazamPiecePanel piecePanel = new KwazamPiecePanel(piece, row, col);
                    piecePanels.add(piecePanel);

                    // If the piece belongs to the opponent, flip it immediately
                    if (piecePanel.getPieceColor() == opponentColor) {
                        piecePanel.flipPiece();
                    }
                }
            }
        }
    }

    public void drawBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(KwazamConstants.BACKGROUND_COLOR);
        g2.fillRect(0, 0, panelWidth, panelHeight);

        g2.setStroke(new BasicStroke(KwazamConstants.BORDER_WIDTH));

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                int drawRow = boardFlipped ? KwazamConstants.BOARD_ROWS - 1 - row : row;
                int drawCol = boardFlipped ? KwazamConstants.BOARD_COLS - 1 - col : col;

                Color squareColor = (row + col) % 2 == 0 ? KwazamConstants.SQUARE_COLOR_1
                        : KwazamConstants.SQUARE_COLOR_2;
                g2.setColor(squareColor);
                g2.fillRect(xOffset + drawCol * squareSize, yOffset + drawRow * squareSize, squareSize, squareSize);

                g2.setColor(KwazamConstants.BORDER_COLOR);
                g2.drawRect(xOffset + drawCol * squareSize, yOffset + drawRow * squareSize, squareSize, squareSize);
            }
        }
    }

    public void drawPieces(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (KwazamPiecePanel piecePanel : piecePanels) {
            piecePanel.draw(g2, xOffset, yOffset, squareSize, boardFlipped);
        }
    }

    public void flipBoard() {
        boardFlipped = !boardFlipped;

        // Toggle the opponent color when the board is flipped
        if (opponentColor == Color.BLUE) {
            opponentColor = Color.RED; // Change to the other color
        } else {
            opponentColor = Color.BLUE; // Revert back to the initial color
        }

        // Optionally, flip all the pieces if necessary (you can keep this logic)
        for (KwazamPiecePanel piecePanel : piecePanels) {
            piecePanel.flipPiece();
        }
        repaint();
    }

    // Setter for opponent color in case it needs to be changed dynamically
    public void setOpponentColor(Color opponentColor) {
        this.opponentColor = opponentColor;
    }

    // Method to draw the coordinates on the board
    private void drawCoordinates(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, Math.max(squareSize / 4, 12));
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);

        // Draw column labels (A, B, C, ...)
        for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
            String text = String.valueOf((char) ('A' + col));
            int drawCol = boardFlipped ? KwazamConstants.BOARD_COLS - 1 - col : col;
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getAscent();
            g2.drawString(text, xOffset + drawCol * squareSize + (squareSize - textWidth) / 2, yOffset - textHeight / 2);
        }

        // Draw row labels (1, 2, 3, ...)
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            String text = String.valueOf(row + 1);
            int drawRow = boardFlipped ? KwazamConstants.BOARD_ROWS - 1 - row : row;
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getAscent();
            g2.drawString(text, xOffset - textWidth - 10,
                    yOffset + drawRow * squareSize + (squareSize + textHeight) / 2 - 10);
        }
    }
}

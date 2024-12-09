package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import utils.KwazamConstants;
import utils.KwazamPieceColor;

public class KwazamBoardPanel extends JPanel {
    private KwazamPiecePanel[][] piecePanels;
    private KwazamPieceColor currentColor;
    private int panelWidth, panelHeight;
    private int boardWidth, boardHeight;
    private int squareSize;
    private int squareWidth, squareHeight;
    private int xOffset, yOffset;
    private boolean boardFlipped;

    public KwazamBoardPanel() {
        this.piecePanels = new KwazamPiecePanel[KwazamConstants.BOARD_ROWS][KwazamConstants.BOARD_COLS];
        this.currentColor = KwazamPieceColor.BLUE;

        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                this.piecePanels[row][col] = new KwazamPiecePanel();
            }
        }
    }

    public KwazamPiecePanel[][] getPiecePanels() {
        return piecePanels;
    }

    public KwazamPieceColor getCurrentColor() {
        return currentColor;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public boolean isBoardFlipped() {
        return boardFlipped;
    }

    public void setOpponentColor(KwazamPieceColor currentColor) {
        this.currentColor = currentColor;
    }

    public void renderBoard(String[][] gameState) {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (gameState[row][col] != null) {
                    String[] parts = gameState[row][col].toLowerCase().split("_");
                    String color = parts[0];
                    String type = parts[1];
                    
                    piecePanels[row][col].setPiece(color, type, col, row, squareSize);
                } else {
                    piecePanels[row][col] = new KwazamPiecePanel();
                }
            }
        }

        flipOpponentPieces();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        panelWidth = getWidth();
        panelHeight = getHeight();
    
        boardWidth = (int) (panelWidth * 0.9);
        boardHeight = (int) (panelHeight * 0.9);
    
        // Dynamically adjust the square size based on the panel size
        squareWidth = boardWidth / KwazamConstants.BOARD_COLS;
        squareHeight = boardHeight / KwazamConstants.BOARD_ROWS;
    
        squareSize = Math.min(squareWidth, squareHeight);  // Ensure square size is consistent
        boardWidth = squareSize * KwazamConstants.BOARD_COLS;
        boardHeight = squareSize * KwazamConstants.BOARD_ROWS;
    
        xOffset = (panelWidth - boardWidth) / 2;
        yOffset = (panelHeight - boardHeight) / 2;
    
        drawBoard(g);
        drawCoordinates(g);
        drawPieces(g);
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
    
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (piecePanels[row][col] != null) {
                    piecePanels[row][col].draw(g2, xOffset, yOffset, squareSize, boardFlipped);
                }
            }
        }
    }    

    public void flipOpponentPieces() {
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (piecePanels[row][col] != null) {
                    if (piecePanels[row][col].getPieceColor() != currentColor) {
                        piecePanels[row][col].flipPiece();
                    }
                }
            }
        }
    }

    public void flipBoard() {
        boardFlipped = !boardFlipped;
        currentColor = (currentColor == KwazamPieceColor.BLUE ? KwazamPieceColor.RED : KwazamPieceColor.BLUE);

        // Optionally, flip all the pieces if necessary (you can keep this logic)
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                if (piecePanels[row][col] != null) {
                    piecePanels[row][col].flipPiece();
                }
            }
        }
    }

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
            g2.drawString(text, xOffset + drawCol * squareSize + (squareSize - textWidth) / 2,
                    yOffset - textHeight / 2);
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

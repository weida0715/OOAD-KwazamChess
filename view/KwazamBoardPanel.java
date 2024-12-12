package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;
import model.utils.KwazamConstants;

public class KwazamBoardPanel extends JPanel {
    private List<KwazamRenderPiece> renderPieces;
    private KwazamRenderPiece selectedPiece, draggingPiece;
    private int dragX, dragY;
    private int hoveredGridX, hoveredGridY;
    private int panelWidth, panelHeight;
    private int boardWidth, boardHeight;
    private int squareSize;
    private int squareWidth, squareHeight;
    private int xOffset, yOffset;
    private boolean boardFlipped;

    public KwazamBoardPanel() {
        this.dragX = -1;
        this.dragY = -1;
        this.hoveredGridX = -1;
        this.hoveredGridY = -1;
    }

    public List<KwazamRenderPiece> getRenderPieces() {
        return renderPieces;
    }
    
    public KwazamRenderPiece getSelectedPiece() {
        return selectedPiece;
    }
    
    public KwazamRenderPiece getDraggingPiece() {
        return draggingPiece;
    }
    
    public int getDragX() {
        return dragX;
    }
    
    public int getDragY() {
        return dragY;
    }
    
    public int getHoveredGridX() {
        return hoveredGridX;
    }
    
    public int getHoveredGridY() {
        return hoveredGridY;
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
    
    public void setRenderPieces(List<KwazamRenderPiece> renderPieces) {
        this.renderPieces = renderPieces;
        repaint();
    }

    public void setSelectedPiece(KwazamRenderPiece selectedPiece) {
        this.selectedPiece = selectedPiece;
        repaint();
    }

    public void setDraggingPiece(KwazamRenderPiece draggingPiece) {
        this.draggingPiece = draggingPiece;
        this.dragX = draggingPiece.getX();
        this.dragY = draggingPiece.getY();
        repaint();
    }

    public void clearDraggingPiece() {
        this.draggingPiece = null;
        repaint();
    }

    public void setHoveredGrid(int gridX, int gridY) {
        this.hoveredGridX = gridX;
        this.hoveredGridY = gridY;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    
        // Calculate panel dimensions
        panelWidth = getWidth();
        panelHeight = getHeight();
    
        // Calculate board size and square dimensions
        boardWidth = (int) (panelWidth * 0.9);
        boardHeight = (int) (panelHeight * 0.9);
        squareWidth = boardWidth / KwazamConstants.BOARD_COLS;
        squareHeight = boardHeight / KwazamConstants.BOARD_ROWS;
        squareSize = Math.min(squareWidth, squareHeight);
        boardWidth = squareSize * KwazamConstants.BOARD_COLS;
        boardHeight = squareSize * KwazamConstants.BOARD_ROWS;
    
        // Calculate offsets
        xOffset = (panelWidth - boardWidth) / 2;
        yOffset = (panelHeight - boardHeight) / 2;
    
        // Draw the board and coordinates
        drawBoard(g2);
        drawCoordinates(g2);
    
        // Highlight selected piece's grid
        if (selectedPiece != null) {
            g2.setColor(new Color(200, 200, 255)); // Purple color
            int x = xOffset + selectedPiece.getX() * squareSize;
            int y = yOffset + selectedPiece.getY() * squareSize;
            g2.fillRect(x, y, squareSize, squareSize);
        }
    
        // Highlight dragging piece's grid
        if (draggingPiece != null) {
            g2.setColor(new Color(200, 200, 255)); // Purple color
            int x = xOffset + draggingPiece.getX() * squareSize;
            int y = yOffset + draggingPiece.getY() * squareSize;
            g2.fillRect(x, y, squareSize, squareSize);
        }
    
        // Highlight hovered grid
        if (hoveredGridX >= 0 && hoveredGridY >= 0) {
            g2.setColor(new Color(200, 200, 255)); // Purple color
            g2.setStroke(new BasicStroke(5)); // Set border thickness
            int hoverX = xOffset + hoveredGridX * squareSize;
            int hoverY = yOffset + hoveredGridY * squareSize;
            g2.drawRect(hoverX, hoverY, squareSize, squareSize);
        }
    
        // Draw pieces (skip the dragging piece in its original position)
        if (renderPieces != null) {
            for (KwazamRenderPiece piece : renderPieces) {
                if (draggingPiece != null && piece.equals(draggingPiece)) {
                    continue;
                }
                int x = xOffset + piece.getX() * squareSize;
                int y = yOffset + piece.getY() * squareSize;
                drawPiece(g2, piece, x, y, squareSize);
            }
        }
    
        // Draw the dragging piece at its new position
        if (draggingPiece != null) {
            drawPiece(g2, draggingPiece, dragX - squareSize / 2, dragY - squareSize / 2, squareSize, 0.5f);
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

    public void drawPiece(Graphics2D g2, KwazamRenderPiece renderPiece, int x, int y, int size) {
        drawPiece(g2, renderPiece, x, y, size, 1.0f);
    }

    // Auxiliary function
    public void drawPiece(Graphics2D g2, KwazamRenderPiece renderPiece, int x, int y, int gridSize, float transparency) {
        // Calculate reduced size to 90% of grid size
        int reducedSize = (int) (gridSize * 0.9);
    
        // Center the piece within the square
        int offset = (gridSize - reducedSize) / 2;
    
        // Apply transparency
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
    
        // Draw the piece image
        g2.drawImage(renderPiece.getImage(), x + offset, y + offset, reducedSize, reducedSize, null);
    
        // Reset transparency
        g2.setComposite(originalComposite);
    }
    

    public void flipOpponentPieces() {
        // for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
        // for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
        // if (piecePanels[row][col] != null) {
        // if (piecePanels[row][col].getPieceColor() != currentColor) {
        // piecePanels[row][col].flipPiece();
        // }
        // }
        // }
        // }
    }

    public void flipBoard() {
        // boardFlipped = !boardFlipped;
        // currentColor = (currentColor == KwazamPieceColor.BLUE ? KwazamPieceColor.RED
        // : KwazamPieceColor.BLUE);

        // // Optionally, flip all the pieces if necessary (you can keep this logic)
        // for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
        // for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
        // if (piecePanels[row][col] != null) {
        // piecePanels[row][col].flipPiece();
        // }
        // }
        // }
    }
}
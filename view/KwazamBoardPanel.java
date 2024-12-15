package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import utils.KwazamConstants;

public class KwazamBoardPanel extends JPanel {
    private List<KwazamRenderPiece> renderPieces;
    private KwazamRenderPiece selectedPiece, draggingPiece;
    private List<int[]> availableMoves = null;
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
        this.availableMoves = new ArrayList<>();
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

    public void setDraggingPiece(KwazamRenderPiece draggingPiece, int x, int y) {
        this.draggingPiece = draggingPiece;
        this.dragX = x;
        this.dragY = y;
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
            int x = xOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - selectedPiece.getX()) : selectedPiece.getX())
                            * squareSize
                    + KwazamConstants.BORDER_WIDTH / 2;
            int y = yOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - selectedPiece.getY()) : selectedPiece.getY())
                            * squareSize
                    + KwazamConstants.BORDER_WIDTH / 2;
            int size = squareSize - KwazamConstants.BORDER_WIDTH;
            g2.fillRect(x, y, size, size);
        }

        // Highlight dragging piece's grid
        if (draggingPiece != null) {
            g2.setColor(new Color(200, 200, 255)); // Purple color
            int x = xOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - draggingPiece.getX()) : draggingPiece.getX())
                            * squareSize
                    + KwazamConstants.BORDER_WIDTH / 2;
            int y = yOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - draggingPiece.getY()) : draggingPiece.getY())
                            * squareSize
                    + KwazamConstants.BORDER_WIDTH / 2;
            int size = squareSize - KwazamConstants.BORDER_WIDTH;
            g2.fillRect(x, y, size, size);
        }

        // Highlight hovered grid
        if (hoveredGridX >= 0 && hoveredGridY >= 0 && draggingPiece != null) {
            g2.setColor(new Color(200, 200, 255)); // Purple color
            g2.setStroke(new BasicStroke(5)); // Set border thickness
            int hoverX = xOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - hoveredGridX) : hoveredGridX) * squareSize;
            int hoverY = yOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - hoveredGridY) : hoveredGridY) * squareSize;
            g2.drawRect(hoverX, hoverY, squareSize, squareSize);
        }

        // Draw pieces (skip the dragging piece in its original position)
        if (renderPieces != null) {
            for (KwazamRenderPiece piece : renderPieces) {
                int drawRow = piece.getY();
                int drawCol = piece.getX();

                // Adjust the coordinates for the flipped board
                if (boardFlipped) {
                    drawRow = KwazamConstants.BOARD_ROWS - 1 - piece.getY();
                    drawCol = KwazamConstants.BOARD_COLS - 1 - piece.getX();
                }

                if (draggingPiece != null && piece.getX() == draggingPiece.getX()
                        && piece.getY() == draggingPiece.getY()) {
                    continue;
                }

                int x = xOffset + drawCol * squareSize;
                int y = yOffset + drawRow * squareSize;
                drawPiece(g2, piece, x, y, squareSize);
            }
        }

        // Draw the dragging piece at its new position
        if (draggingPiece != null) {
            drawPiece(g2, draggingPiece, dragX - squareSize / 2, dragY - squareSize / 2, squareSize, 0.5f);
        }

        // Highlight available moves with medium-sized grey circles
        if (availableMoves != null) {
            g2.setColor(KwazamConstants.VALID_MOVE_COLOR);
            int circleRadius = squareSize / 5; // Medium size
            for (int[] move : availableMoves) {
                int moveX = move[0];
                int moveY = move[1];

                // Flip coordinates if the board is flipped
                if (boardFlipped) {
                    moveX = KwazamConstants.BOARD_COLS - 1 - moveX;
                    moveY = KwazamConstants.BOARD_ROWS - 1 - moveY;
                }

                // Calculate circle center
                int centerX = xOffset + moveX * squareSize + squareSize / 2;
                int centerY = yOffset + moveY * squareSize + squareSize / 2;

                // Draw the circle
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius, 2 * circleRadius);
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

                Color squareColor = (row + col) % 2 == 0 ? KwazamConstants.SQUARE_COLOR_1
                        : KwazamConstants.SQUARE_COLOR_2;
                g2.setColor(squareColor);
                g2.fillRect(xOffset + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - col) : col) * squareSize,
                        yOffset + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - row) : row) * squareSize,
                        squareSize, squareSize);

                g2.setColor(KwazamConstants.BORDER_COLOR);
                g2.drawRect(xOffset + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - col) : col) * squareSize,
                        yOffset + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - row) : row) * squareSize,
                        squareSize, squareSize);
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
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getAscent();
            g2.drawString(text,
                    xOffset + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - col) : col) * squareSize
                            + (squareSize - textWidth) / 2,
                    yOffset - textHeight / 2);
        }

        // Draw row labels (1, 2, 3, ...)
        for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
            String text = String.valueOf(row + 1);
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getAscent();
            g2.drawString(text, xOffset - textWidth - 10,
                    yOffset + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - row) : row) * squareSize
                            + (squareSize + textHeight) / 2 - 10);
        }
    }

    public void drawPiece(Graphics2D g2, KwazamRenderPiece renderPiece, int x, int y, int size) {
        drawPiece(g2, renderPiece, x, y, size, 1.0f);
    }

    // Auxiliary function
    public void drawPiece(Graphics2D g2, KwazamRenderPiece renderPiece, int x, int y, int gridSize,
            float transparency) {
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

    public void flipBoard() {
        boardFlipped = !boardFlipped;
    }

    public void setAvailableMoves(List<int[]> moves) {
        this.availableMoves = moves; // Store moves for rendering
    }

    public void clearAvailableMoves() {
        this.availableMoves = null;
    }
}

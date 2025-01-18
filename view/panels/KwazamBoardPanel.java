package view.panels;

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
import view.components.KwazamRenderPiece;

/**
 * Author(s):
 * 
 * Represents the game board panel for Kwazam.
 * Handles rendering of the board, pieces, and game state.
 */
public class KwazamBoardPanel extends JPanel {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
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
    private int sauInCheckX = -1; // X position of the Sau in check
    private int sauInCheckY = -1; // Y position of the Sau in check
    private boolean boardFlipped;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a KwazamBoardPanel.
     * Initializes the board and game state.
     */
    public KwazamBoardPanel() {
        this.dragX = -1;
        this.dragY = -1;
        this.hoveredGridX = -1;
        this.hoveredGridY = -1;
        this.availableMoves = new ArrayList<>();
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets the list of renderable pieces.
     * 
     * @return the list of renderable pieces
     */
    public List<KwazamRenderPiece> getRenderPieces() {
        return renderPieces;
    }

    /**
     * Author(s):
     * 
     * Gets the currently selected piece.
     * 
     * @return the selected piece
     */
    public KwazamRenderPiece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Author(s):
     * 
     * Gets the piece currently being dragged.
     * 
     * @return the dragging piece
     */
    public KwazamRenderPiece getDraggingPiece() {
        return draggingPiece;
    }

    /**
     * Author(s):
     * 
     * Gets the x-coordinate of the dragging piece.
     * 
     * @return the x-coordinate
     */
    public int getDragX() {
        return dragX;
    }

    /**
     * Author(s):
     * 
     * Gets the y-coordinate of the dragging piece.
     * 
     * @return the y-coordinate
     */
    public int getDragY() {
        return dragY;
    }

    /**
     * Author(s):
     * 
     * Gets the x-coordinate of the hovered grid.
     * 
     * @return the x-coordinate
     */
    public int getHoveredGridX() {
        return hoveredGridX;
    }

    /**
     * Author(s):
     * 
     * Gets the y-coordinate of the hovered grid.
     * 
     * @return the y-coordinate
     */
    public int getHoveredGridY() {
        return hoveredGridY;
    }

    /**
     * Author(s):
     * 
     * Gets the width of the panel.
     * 
     * @return the panel width
     */
    public int getPanelWidth() {
        return panelWidth;
    }

    /**
     * Author(s):
     * 
     * Gets the height of the panel.
     * 
     * @return the panel height
     */
    public int getPanelHeight() {
        return panelHeight;
    }

    /**
     * Author(s):
     * 
     * Gets the width of the board.
     * 
     * @return the board width
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Author(s):
     * 
     * Gets the height of the board.
     * 
     * @return the board height
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Author(s):
     * 
     * Gets the size of each square on the board.
     * 
     * @return the square size
     */
    public int getSquareSize() {
        return squareSize;
    }

    /**
     * Author(s):
     * 
     * Gets the width of each square on the board.
     * 
     * @return the square width
     */
    public int getSquareWidth() {
        return squareWidth;
    }

    /**
     * Author(s):
     * 
     * Gets the height of each square on the board.
     * 
     * @return the square height
     */
    public int getSquareHeight() {
        return squareHeight;
    }

    /**
     * Author(s):
     * 
     * Gets the x-offset of the board.
     * 
     * @return the x-offset
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Author(s):
     * 
     * Gets the y-offset of the board.
     * 
     * @return the y-offset
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Author(s):
     * 
     * Checks if the board is flipped.
     * 
     * @return true if the board is flipped, false otherwise
     */
    public boolean isBoardFlipped() {
        return boardFlipped;
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Sets the list of renderable pieces.
     * 
     * @param renderPieces the list of renderable pieces
     */
    public void setRenderPieces(List<KwazamRenderPiece> renderPieces) {
        this.renderPieces = renderPieces;
        repaint();
    }

    /**
     * Author(s):
     * 
     * Sets the currently selected piece.
     * 
     * @param selectedPiece the selected piece
     */
    public void setSelectedPiece(KwazamRenderPiece selectedPiece) {
        this.selectedPiece = selectedPiece;
        repaint();
    }

    /**
     * Author(s):
     * 
     * Sets the piece currently being dragged.
     * 
     * @param draggingPiece the dragging piece
     * @param x             the x-coordinate of the dragging piece
     * @param y             the y-coordinate of the dragging piece
     */
    public void setDraggingPiece(KwazamRenderPiece draggingPiece, int x, int y) {
        this.draggingPiece = draggingPiece;
        this.dragX = x;
        this.dragY = y;
        repaint();
    }

    /**
     * Author(s):
     * 
     * Sets the piece currently being dragged.
     * 
     * @param draggingPiece the dragging piece
     * @param x             the x-coordinate of the dragging piece
     * @param y             the y-coordinate of the dragging piece
     */
    public void clearDraggingPiece() {
        this.draggingPiece = null;
        repaint();
    }

    /**
     * Author(s):
     * 
     * Sets the hovered grid coordinates.
     * 
     * @param gridX the x-coordinate of the hovered grid
     * @param gridY the y-coordinate of the hovered grid
     */
    public void setHoveredGrid(int gridX, int gridY) {
        this.hoveredGridX = gridX;
        this.hoveredGridY = gridY;
        repaint();
    }

    /**
     * Author(s):
     * 
     * Highlights the Sau's position if it is in check.
     * 
     * @param x the x-coordinate of the Sau
     * @param y the y-coordinate of the Sau
     */
    public void setSauInCheck(int x, int y) {
        this.sauInCheckX = x;
        this.sauInCheckY = y;
        repaint(); // Trigger a repaint to update the board
    }

    /**
     * Author(s):
     * 
     * Clears the Sau in check highlight.
     */
    public void clearSauInCheck() {
        this.sauInCheckX = -1;
        this.sauInCheckY = -1;
        repaint(); // Trigger a repaint to clear the highlight
    }

    /**
     * Author(s):
     * 
     * Sets the available moves for the selected piece.
     * 
     * @param moves the list of available moves
     */
    public void setAvailableMoves(List<int[]> moves) {
        this.availableMoves = moves; // Store moves for rendering
    }

    /**
     * Author(s):
     * 
     * Clears the available moves.
     */
    public void clearAvailableMoves() {
        this.availableMoves = null;
    }

    // =================================================================
    // BOARD RENDERING
    // =================================================================
    /**
     * Author(s):
     * 
     * Renders the board, pieces, and game state.
     * 
     * @param g the graphics context
     */
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

        // Highlight the Sau's square if it is in check
        if (sauInCheckX != -1 && sauInCheckY != -1) {
            int x = xOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_COLS - 1 - sauInCheckX) : sauInCheckX) * squareSize;
            int y = yOffset
                    + (boardFlipped ? (KwazamConstants.BOARD_ROWS - 1 - sauInCheckY) : sauInCheckY) * squareSize;

            // Set the pink highlight color
            g2.setColor(KwazamConstants.SAU_CHECKED_COLOR);
            g2.fillRect(x, y, squareSize, squareSize);
        }
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

    /**
     * Author(s):
     * 
     * Draws the game board.
     * 
     * @param g the graphics context
     */
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

    /**
     * Author(s):
     * 
     * Draws the board coordinates (letters and numbers).
     * 
     * @param g the graphics context
     */
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

    /**
     * Author(s):
     * 
     * Draws a piece on the board with transparency.
     * 
     * @param g2           the graphics context
     * @param renderPiece  the piece to draw
     * @param x            the x-coordinate
     * @param y            the y-coordinate
     * @param gridSize     the size of the grid square
     * @param transparency the transparency level (0.0f to 1.0f)
     */
    public void drawPiece(Graphics2D g2, KwazamRenderPiece renderPiece, int x, int y, int size) {
        drawPiece(g2, renderPiece, x, y, size, 1.0f);
    }

    /**
     * Author(s):
     * 
     * Draws a piece on the board.
     * 
     * @param g2          the graphics context
     * @param renderPiece the piece to draw
     * @param x           the x-coordinate
     * @param y           the y-coordinate
     * @param size        the size of the piece
     */
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

    // =================================================================
    // BOARD ORIENTATION
    // =================================================================
    /**
     * Author(s):
     * 
     * Flips the board orientation.
     */
    public void flipBoard() {
        boardFlipped = !boardFlipped;
    }

    /**
     * Author(s):
     * 
     * Flips the board back to the default orientation.
     */
    public void flipBoardToDefault() {
        if (boardFlipped) {
            flipBoard(); // Flip the board back to the default orientation
        }
    }

}

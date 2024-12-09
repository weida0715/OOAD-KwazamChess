package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;

public class KwazamPiecePanel  {
    private KwazamPieceColor pieceColor;
    private KwazamPieceType pieceType;
    private BufferedImage pieceImg;
    private String colorString;
    private String typeString;
    private int originalRow;
    private int originalCol;
    private boolean flipped;
    private int x, y;  // Current screen position of the piece

    public KwazamPiecePanel() {
    }

    // Getter and Setter methods for piece properties and position
    public KwazamPieceColor getPieceColor() {
        return pieceColor;
    }

    public KwazamPieceType getPieceType() {
        return pieceType;
    }

    public String getColorString() {
        return colorString;
    }

    public String getTypeString() {
        return typeString;
    }

    public BufferedImage getImage() {
        return pieceImg;
    }

    public int getRow() {
        return originalRow;
    }

    public int getCol() {
        return originalCol;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setRow(int row) {
        this.originalRow = row;
    }

    public void setCol(int col) {
        this.originalCol = col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;  // Set the current x position
    }

    public void setY(int y) {
        this.y = y;  // Set the current y position
    }

    public void setPiece(String c, String t, int col, int row, int squareSize) {
        this.typeString = t;
        this.colorString = c;
        this.originalRow = row;
        this.originalCol = col;
        this.flipped = false;
    
        this.pieceColor = (this.colorString.equals("b") ? KwazamPieceColor.BLUE : KwazamPieceColor.RED);
    
        String imagePath;
        imagePath = "/images/" + colorString.toLowerCase() + "_"
                + this.typeString.toLowerCase() + ".png";
        try {
            pieceImg = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Recalculate the position based on the square size
        this.x = col * squareSize;
        this.y = row * squareSize;
    }
    

    public void draw(Graphics2D g2, int xOffset, int yOffset, int squareSize, boolean boardFlipped) {
        int drawRow = boardFlipped ? KwazamConstants.BOARD_ROWS - 1 - originalRow : originalRow;
        int drawCol = boardFlipped ? KwazamConstants.BOARD_COLS - 1 - originalCol : originalCol;

        int coorX = xOffset + drawCol * squareSize + (int) (squareSize * 0.05);
        int coorY = yOffset + drawRow * squareSize + (int) (squareSize * 0.05);

        int size = (int) (squareSize * 0.9);

        if (flipped) {
            g2.drawImage(pieceImg, coorX, coorY + size, size, -size, null);
        } else {
            g2.drawImage(pieceImg, coorX, coorY, size, size, null);
        }
    }

    // Flip the piece when needed (e.g., for a piece that changes orientation)
    public void flipPiece() {
        this.flipped = !this.flipped;
    }

    // Method to snap the piece back to the closest grid square when released
    public void snapToGrid(int squareSize) {
        int snappedCol = (int) Math.round((double) x / squareSize);
        int snappedRow = (int) Math.round((double) y / squareSize);

        // Update the grid position (this could be updated when the piece is dropped or when you check for legality)
        this.originalCol = snappedCol;
        this.originalRow = snappedRow;

        // Update the current position based on the snapped grid
        this.x = snappedCol * squareSize;
        this.y = snappedRow * squareSize;
    }
}

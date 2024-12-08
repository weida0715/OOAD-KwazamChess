package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import model.utils.KwazamConstants;
import model.utils.KwazamPieceType;

public class KwazamPiecePanel {
    private final KwazamPieceType pieceType;
    private final Color pieceColor;
    private final int originalRow;
    private final int originalCol;
    private boolean isFlipped; // Piece-specific flip state

    public KwazamPiecePanel(KwazamPieceType pieceType, int row, int col) {
        this.pieceType = pieceType;
        this.originalRow = row;
        this.originalCol = col;
        this.isFlipped = false;
        this.pieceColor = this.pieceType.name().startsWith("R_") ? Color.RED : Color.BLUE;
    }

    public void draw(Graphics2D g2, int xOffset, int yOffset, int squareSize, boolean boardFlipped) {
        int drawRow = boardFlipped ? KwazamConstants.BOARD_ROWS - 1 - originalRow : originalRow;
        int drawCol = boardFlipped ? KwazamConstants.BOARD_COLS - 1 - originalCol : originalCol;

        Image pieceImage = new ImageIcon("images/" + pieceType.name().toLowerCase() + ".png").getImage();
        int x = xOffset + drawCol * squareSize + (int) (squareSize * 0.05);
        int y = yOffset + drawRow * squareSize + (int) (squareSize * 0.05);
        int size = (int) (squareSize * 0.9);

        if (isFlipped) {
            g2.drawImage(pieceImage, x, y + size, size, -size, null);
        } else {
            g2.drawImage(pieceImage, x, y, size, size, null);
        }
    }

    public boolean getFlipped() {
        return isFlipped;
    }

    public void flipPiece() {
        this.isFlipped = !this.isFlipped;
    }

    public KwazamPieceType getPieceType() {
        return pieceType;
    }

    public Color getPieceColor() {
        return pieceColor;
    }
}

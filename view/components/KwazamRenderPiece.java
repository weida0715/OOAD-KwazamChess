package view.components;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a renderable piece in Kwazam.
 * Handles image loading, flipping, and position management.
 */
public class KwazamRenderPiece {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final String data;
    private int x;
    private int y;
    private BufferedImage img;
    private boolean flipped;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Constructs a KwazamRenderPiece with the given data and position.
     * 
     * @param data the piece data (e.g., "r_sau", "b_tor")
     * @param x    the x-coordinate of the piece
     * @param y    the y-coordinate of the piece
     */
    public KwazamRenderPiece(String data, int x, int y) {
        this.data = data.toLowerCase();
        this.x = x;
        this.y = y;
        this.flipped = false;

        String imagePath;
        imagePath = "/images/" + this.data + ".png";
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("Error loading image: " + imagePath);
        }
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the piece data.
     * 
     * @return the piece data
     */
    public String getData() {
        return data;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the x-coordinate of the piece.
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the y-coordinate of the piece.
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Checks if the piece is flipped.
     * 
     * @return true if the piece is flipped, false otherwise
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the image of the piece.
     * 
     * @return the piece image
     */
    public BufferedImage getImage() {
        return img;
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Sets the x-coordinate of the piece.
     * 
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
      * Author(s): Ng Wei Da
     * 
     * Sets the y-coordinate of the piece.
     * 
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    // =================================================================
    // IMAGE MANIPULATION
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Flips the piece image vertically.
     */
    public void flip() {
        if (img == null) {
            return; // If image is null, skip flipping
        }

        // Flip vertically
        AffineTransform transform = AffineTransform.getScaleInstance(1, -1); // Flip vertically
        transform.translate(0, -img.getHeight()); // Translate to avoid cutting off the image

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        img = op.filter(img, null); // Apply the transformation

        // Toggle the flipped state
        this.flipped = !this.flipped;
    }
}

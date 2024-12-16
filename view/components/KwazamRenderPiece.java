package view.components;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KwazamRenderPiece {
    private final String data;
    private int x;
    private int y;
    private BufferedImage img;
    private boolean flipped;

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

    public String getData() {
        return data;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImage() {
        return img;
    }

    /**
     * Flips the image vertically (up to down).
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

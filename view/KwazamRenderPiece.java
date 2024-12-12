package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KwazamRenderPiece {
    private final String data;
    private int x;
    private int y;
    private BufferedImage img;

    public KwazamRenderPiece(String data, int x, int y) {
        this.data = data.toLowerCase();
        this.x = x;
        this.y = y;

        String imagePath;
        imagePath = "/images/" + this.data + ".png";
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println(imagePath);
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

}
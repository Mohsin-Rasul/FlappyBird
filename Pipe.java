import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Pipe {
    private int x;
    private int width = 80;
    private int height;
    private int gap = 150;
    private int speed = 5;
    private int screenHeight;
    private int topHeight;
    private boolean scored = false;

    private static Image pipeImage;

    static {
        // Load the image once (you can adjust the path)
        pipeImage = new ImageIcon("C:\\Users\\PC\\OneDrive\\Desktop\\FlappyBird\\pipe4.png").getImage();
    }

    public Pipe(int x, int screenHeight) {
        this.x = x;
        this.screenHeight = screenHeight;
        generateRandomHeights();
    }

    private void generateRandomHeights() {
        this.topHeight = 50 + (int)(Math.random() * (screenHeight - gap - 200));
    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics g) {
        // Top pipe (flipped vertically)
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(pipeImage, x, topHeight, width, -topHeight, null); // flipped

        // Bottom pipe (normal)
        int bottomY = topHeight + gap;
        int bottomHeight = screenHeight - bottomY - 0; // 100 is ground height
        g.drawImage(pipeImage, x, bottomY, width, bottomHeight, null);
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, width, topHeight);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, topHeight + gap, width, screenHeight - topHeight - gap - 100);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public int getX() {
        return x;
    }

    public boolean passed(int birdX) {
        return birdX > x + width;
    }

    public boolean collidesWith(Bird bird) {
        Rectangle birdBounds = bird.getBounds();
        return getTopBounds().intersects(birdBounds) || getBottomBounds().intersects(birdBounds);
    }

    public int getWidth() {
        return width;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }
}

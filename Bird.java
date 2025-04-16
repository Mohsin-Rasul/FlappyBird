import java.awt.*;
import javax.swing.ImageIcon;

public class Bird {
    private int x, y;
    private int width, height;
    private int velocity;
    private final int GRAVITY = 1;
    private Image birdImage;

    // Constructor that accepts custom x, y, width, and height values
    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = 0;

        // Load the bird image from the path, specify the image file extension (e.g., .png)
        birdImage = new ImageIcon("C:\\Users\\PC\\OneDrive\\Desktop\\FlappyBird\\flappy4.png").getImage(); // Replace with your actual file path
        
        birdImage = birdImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    // Default constructor that uses default position and size
    public Bird() {
        this(200, 300, 40, 40); // Default position and size
    }

    // Update bird's position based on gravity
    public void update() {
        velocity += GRAVITY;
        y += velocity;
    }

    // Make the bird "flap" by changing its vertical velocity
    public void flap() {
        velocity = -10;  // Adjust flap strength here
    }

    // Reset bird's position and velocity to the initial state
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    // Draw the bird on the screen
    public void draw(Graphics g) {
        // Draw the bird image
        g.drawImage(birdImage, x, y, width, height, null);
    }

    // Get the bounding box for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Get the X coordinate
    public int getX() {
        return x;
    }
}

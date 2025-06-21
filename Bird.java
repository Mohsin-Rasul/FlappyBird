import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

public class Bird {
    private int x, y;
    private int width, height;
    private int velocity;
    private final int GRAVITY = 1;

    // Animation variables
    private List<Image> birdFrames;
    private int currentFrame;
    private long lastFrameTime;
    private long frameDuration = 100; // Time in milliseconds between frames

    // Constructor that accepts custom x, y, width, and height values
    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = 0;
        this.currentFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();

        // Load the bird animation frames
        birdFrames = new ArrayList<>();
        try {
            // Assuming images are named flappy1.png, flappy2.png, flappy3.png, and flappy4.png
            for (int i = 1; i <= 4; i++) {
                String imagePath = "C:\\Users\\PC\\OneDrive\\Desktop\\FlappyBird\\flappy" + i + ".png";
                Image frame = new ImageIcon(imagePath).getImage();
                birdFrames.add(frame.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to a single image if animation frames are not found
            String imagePath = "C:\\Users\\PC\\OneDrive\\Desktop\\FlappyBird\\flappy4.png";
            Image frame = new ImageIcon(imagePath).getImage();
            birdFrames.add(frame.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
    }

    // Default constructor that uses default position and size
    public Bird() {
        this(200, 300, 40, 40); // Default position and size
    }

    // Update bird's position and animation frame
    public void update() {
        velocity += GRAVITY;
        y += velocity;

        // Update animation frame based on time
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > frameDuration) {
            currentFrame = (currentFrame + 1) % birdFrames.size();
            lastFrameTime = currentTime;
        }
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
        this.currentFrame = 0;
    }

    // Draw the current frame of the bird on the screen
    public void draw(Graphics g) {
        g.drawImage(birdFrames.get(currentFrame), x, y, width, height, null);
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
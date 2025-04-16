// FlappyBird.java
import javax.swing.JFrame;

public class FlappyBird {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);

        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}

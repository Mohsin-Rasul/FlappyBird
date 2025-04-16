import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Timer gameTimer; // Declare gameTimer
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private GameState state = GameState.START;
    private int score = 0;
    private JButton replayButton;
    private int pipeSpawnCounter = 0;
    private int highScore = HighScoreManager.loadHighScore();  // Load saved high score
    private Image backgroundImage;
    private Image grassImage;



    public GamePanel() {
    this.setPreferredSize(new Dimension(800, 600));
    this.setBackground(Color.CYAN); // Sky blue background
    this.setFocusable(true);
    this.addKeyListener(this);

    // Initialize game objects
   // Setting width and height as 80px
    bird = new Bird();
    pipes = new ArrayList<>();
    score = 0;
    highScore = 0;
    state = GameState.START;

    backgroundImage = new ImageIcon("C:\\Users\\PC\\OneDrive\\Desktop\\FlappyBird\\background.jpg").getImage();
    grassImage = new ImageIcon("C:\\\\Users\\\\PC\\\\OneDrive\\\\Desktop\\\\FlappyBird\\ground.png").getImage();

    // Create and style the replay button
    replayButton = new JButton("Play Again");
    replayButton.setFont(new Font("Montserrat", Font.BOLD, 20));
    replayButton.setBackground(new Color(0, 123, 255));  // Blue color
    replayButton.setForeground(Color.WHITE);
    replayButton.setFocusPainted(false);
    replayButton.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 200), 2));
    replayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    replayButton.setPreferredSize(new Dimension(200, 60));
    replayButton.setVisible(false);  // Initially hidden until game over
    replayButton.addActionListener(e -> restartGame());
    replayButton.setBounds(getWidth() / 2 - 100, getHeight() / 2 + 0, 200, 60);
    this.add(replayButton);

    // Start the game timer
    gameTimer = new Timer(1000 / 60, this);  // 60 FPS
    gameTimer.start();
    }

    private void restartGame() {
        bird.reset(200, 300);
        pipes.clear();
        score = 0;
        state = GameState.START;
        replayButton.setVisible(false);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Draw background image first
        // Draw full-screen background (extends all the way down)
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

    
        // Draw bird
        bird.draw(g);
    
        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
    
        // Draw score and UI
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
    
        if (state == GameState.START) {
            g.drawString("Press SPACE to Start", 250, 250);
        } else if (state == GameState.PLAYING) {
            g.drawString("Score: " + score, getWidth() / 2 - 50, 50);
        } else if (state == GameState.GAMEOVER) {
            g.setColor(Color.RED);
            g.drawString("Game Over", 310, 200);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 330, 250);
            g.drawString("High Score: " + highScore, 290, 290);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (score > highScore) {
            highScore = score;
            HighScoreManager.saveHighScore(highScore);  // Save new high score
        }
        
        if (state == GameState.PLAYING) {
            bird.update();

            // Spawn pipes every ~100 frames
            pipeSpawnCounter++;
            if (pipeSpawnCounter >= 100) {
                pipes.add(new Pipe(800, getHeight()));
                pipeSpawnCounter = 0;
            }

            ArrayList<Pipe> toRemove = new ArrayList<>();
            for (Pipe pipe : pipes) {
                pipe.update();

                if (!pipe.isScored() && pipe.getX() + pipe.getWidth() < bird.getX()) {
                    score++;
                    pipe.setScored(true);
                }

                if (pipe.isOffScreen()) {
                    toRemove.add(pipe);
                }

                if (pipe.getTopBounds().intersects(bird.getBounds()) || pipe.getBottomBounds().intersects(bird.getBounds())) {
                    state = GameState.GAMEOVER;
                    replayButton.setVisible(true);
                }

                if (!pipe.passed(bird.getBounds().x) && bird.getBounds().x > pipe.getX() + 80) {
                    score++;
                    if (score > highScore) highScore = score;
                }
                if (pipe.collidesWith(bird)) {
                    setGameOver();
                }
                
            }
            pipes.removeAll(toRemove);

            if (bird.getBounds().y + bird.getBounds().height >= getHeight() - 100) {
                state = GameState.GAMEOVER;
                replayButton.setVisible(true);
            }
        }
        repaint();
    }

    private void setGameOver() {
        state = GameState.GAMEOVER;
        replayButton.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (state == GameState.START) {
                state = GameState.PLAYING;
            }
            if (state == GameState.PLAYING) {
                bird.flap();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_R && state == GameState.GAMEOVER) {
            restartGame();
        }
    }
  

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
} 

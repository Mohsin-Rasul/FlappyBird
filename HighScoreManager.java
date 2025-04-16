import java.io.*;

public class HighScoreManager {
    private static final String FILE_PATH = "highscore.txt";

    public static int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0;  // Default if file doesn't exist or is corrupted
        }
    }

    public static void saveHighScore(int highScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Scoreboard {
    private int turnCount;
    private int blueScore;
    private int greenScore;
    private int yellowScore;
    private int redScore;
    private ArrayList<Integer> ratTailPositions;
    private BufferedImage scoreboardImage;

    //  1 = Blue, 2 = Green, 3 = Yellow, 4 = Red
    public Scoreboard() {
        this.turnCount = 1;
        this.blueScore = 0;
        this.greenScore = 0;
        this.yellowScore = 0;
        this.redScore = 0;
        this.ratTailPositions = new ArrayList<>();
        setRatTailPositions();
        loadScoreboardImage("/images/scoreboard.png"); // change to image
    }

    private void loadScoreboardImage(String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is != null) {
                scoreboardImage = ImageIO.read(is);
                return;
            }
        } catch (Exception ignored) {
        }

        try {
            File fallback = new File("src" + resourcePath);
            if (fallback.exists()) {
                scoreboardImage = ImageIO.read(fallback);
            }
        } catch (Exception e) {
            System.out.println("Exception loading scoreboard image: " + e.getMessage());
        }
    }

    public void addPoints(int playerNum, int points) {
        if (playerNum == 1) {
            blueScore += points;
        } else if (playerNum == 2) {
            greenScore += points;
        } else if (playerNum == 3) {
            yellowScore += points;
        } else if (playerNum == 4) {
            redScore += points;
        }
    }

    public int getScore(int playerNum) {
        if (playerNum == 1) return blueScore;
        if (playerNum == 2) return greenScore;
        if (playerNum == 3) return yellowScore;
        if (playerNum == 4) return redScore;
        return 0;
    }

    public int getLeaderScore() {
        return Math.max(Math.max(blueScore, greenScore), Math.max(yellowScore, redScore));
    }

    public void setRatTailPositions() {
        ratTailPositions.clear();
        int[] spots = {
            1, 4, 7, 10, 12, 14, 16, 18, 20, 22, 24,
            26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48
        };
        for (int spot : spots) {
            ratTailPositions.add(spot);
        }
    }

    public int numRattailSpots(int leaderScore, int playerNum) {
        int playerScore = getScore(playerNum);
        if (playerScore >= leaderScore) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < ratTailPositions.size(); i++) {
            int tailBeforeSpace = ratTailPositions.get(i);
            if (tailBeforeSpace >= playerScore && tailBeforeSpace < leaderScore) {
                count++;
            }
        }
        return count;
    }

    public void drawScoreboard(Graphics g) {
        if (scoreboardImage != null) {
            g.drawImage(scoreboardImage, 0, 0, 1510, 1080, null);
        } else {
            g.setColor(new Color(52, 42, 34));
            g.fillRect(0, 0, 1510, 1080);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            g.drawString("Blue: " + blueScore + " | Green: " + greenScore
                    + " | Yellow: " + yellowScore + " | Red: " + redScore, 60, 80);
        }
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TurnIndicator {
    public static final int TOTAL_PLAYERS = 4;
    public static final int MAX_ROUNDS = 9;

    private int currentTurn;
    private int currentRound;

    public TurnIndicator(int currentTurn, int currentRound) {
        this.currentTurn = clampPlayer(currentTurn);
        this.currentRound = Math.max(1, Math.min(MAX_ROUNDS, currentRound));
    }

    public TurnIndicator() {
        this(1, 1);
    }

    private int clampPlayer(int turn) {
        if (turn < 1 || turn > TOTAL_PLAYERS) {
            return 1;
        }
        return turn;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int turn) {
        this.currentTurn = clampPlayer(turn);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void advanceTurn() {
        currentTurn = (currentTurn % TOTAL_PLAYERS) + 1;
    }

    public boolean nextRound() {
        if (currentRound < MAX_ROUNDS) {
            currentRound++;
            return true;
        }
        return false;
    }

    public String getActivePlayerName() {
        switch (currentTurn) {
            case 1:
                return "Blue";
            case 2:
                return "Green";
            case 3:
                return "Yellow";
            case 4:
                return "Red";
            default:
                return "Blue";
        }
    }

    private Color getActivePlayerColor() {
        switch (currentTurn) {
            case 1:
                return new Color(55, 130, 225);
            case 2:
                return new Color(45, 165, 85);
            case 3:
                return new Color(240, 200, 55);
            case 4:
                return new Color(215, 65, 65);
            default:
                return new Color(55, 130, 225);
        }
    }

    public void drawIndicator(Graphics g, int x, int y) {
        if (g == null) {
            return;
        }

        int width = 260;
        int height = 110;

        // Rounded turn banner on the right sidebar
        g.setColor(getActivePlayerColor());
        g.fillRoundRect(x, y, width, height, 36, 36);

        g.setColor(new Color(45, 32, 20));
        g.drawRoundRect(x, y, width, height, 36, 36);

        g.setColor(currentTurn == 3 ? Color.BLACK : Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        String label = getActivePlayerName() + "'s turn";
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (width - fm.stringWidth(label)) / 2;
        int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(label, textX, textY);

        // Day / Round label in bottom-left of the sidebar
        g.setColor(new Color(35, 25, 15));
        g.setFont(new Font("Serif", Font.BOLD, 34));
        g.drawString("Day " + currentRound, 1530, 1020);
    }
}

import java.awt.Graphics;
import java.util.Random;

public class Player {
    private static final int RUBY_ACTION_COST = 2;

    private int playerNum;
    private int playerScore;
    private Pot pot;
    private Bag bag;
    private Random random;

    public Player(int playerNum) {
        if (playerNum < 1 || playerNum > 4) {
            throw new IllegalArgumentException("Player number must be 1-4");
        }
        this.playerNum = playerNum;
        this.playerScore = 0;
        this.pot = new Pot();
        this.bag = new Bag();
        this.random = new Random();
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int rollDice() {
        int roll = random.nextInt(6) + 1;
        System.out.println("Player " + playerNum + " rolled: " + roll);
        return roll;
    }

    public void addPoints(int points) {
        this.playerScore += points;
    }

    public boolean buyChipWithCost(String color, int val, int cost) {
        if (color == null || val <= 0 || cost < 0) {
            return false;
        }
        if (pot.getCoinsCount() < cost) {
            return false;
        }
        pot.setCoinsCount(pot.getCoinsCount() - cost);
        bag.addChip(new IngredientChip(color, val));
        return true;
    }

    public boolean buyOrange() {
        return buyChipWithCost("orange", 1, 3);
    }

    public boolean buyGreen(int val) {
        int cost;
        if (val == 1) {
            cost = 4;
        } else if (val == 2) {
            cost = 8;
        } else if (val == 4) {
            cost = 14;
        } else {
            return false;
        }
        return buyChipWithCost("green", val, cost);
    }

    public boolean buyRed(int val) {
        int cost;
        if (val == 1) {
            cost = 6;
        } else if (val == 2) {
            cost = 10;
        } else if (val == 4) {
            cost = 16;
        } else {
            return false;
        }
        return buyChipWithCost("red", val, cost);
    }

    public boolean buyBlue(int val) {
        int cost;
        if (val == 1) {
            cost = 5;
        } else if (val == 2) {
            cost = 10;
        } else if (val == 4) {
            cost = 19;
        } else {
            return false;
        }
        return buyChipWithCost("blue", val, cost);
    }

    public boolean buyBlack(int val) {
        if (val != 1) {
            return false;
        }
        return buyChipWithCost("black", 1, 10);
    }

    public boolean buyYellow(int val) {
        int cost;
        if (val == 1) {
            cost = 8;
        } else if (val == 2) {
            cost = 12;
        } else if (val == 4) {
            cost = 18;
        } else {
            return false;
        }
        return buyChipWithCost("yellow", val, cost);
    }

    public boolean buyPink(int val) {
        if (val != 1) {
            return false;
        }
        return buyChipWithCost("pink", 1, 12);
    }

    public boolean buyDroplet() {
        if (pot.getRubyCount() < RUBY_ACTION_COST) {
            return false;
        }
        pot.removeRubies(RUBY_ACTION_COST);
        moveDropForward();
        return true;
    }

    public void moveDropForward() {
        pot.moveDropletForward();
    }

    public boolean buyFlask() {
        if (pot.getRubyCount() < RUBY_ACTION_COST || pot.isFlaskFull()) {
            return false;
        }
        pot.removeRubies(RUBY_ACTION_COST);
        pot.setFlaskFull(true);
        return true;
    }

    public void resetPots() {
        pot.resetRoundPot();
    }

    public Pot getPot() {
        return pot;
    }

    public Bag getBag() {
        return bag;
    }

    public void drawPlayer(Graphics g) {
        int col = (playerNum - 1) % 2;
        int row = (playerNum - 1) / 2;
        int x = col * 720 + 40;
        int y = row * 540 + 45;

        g.drawString("Player " + playerNum, x, y);
        g.drawString("Score: " + playerScore, x, y + 20);
    }
}

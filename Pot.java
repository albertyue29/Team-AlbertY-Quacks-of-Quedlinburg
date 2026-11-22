import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Pot {
    public static final int POT_CAPACITY = 54;
    public static final int WHITE_EXPLOSION_LIMIT = 7;

    private int rubyCount;
    private int coinsCount;
    private boolean flaskFull;
    private int dropletLoc;
    private boolean isExploded;
    private int ratStoneLoc;
    private int[] potArray;
    private IngredientChip[] chipArray;
    private ArrayList<IngredientChip> redsSaved;

    private int whiteChipSum;
    private int currentPotPosition;
    private int lastPlacedIndex;
    private IngredientChip lastDrawnChip;
    private BufferedImage potImage;

    public Pot() {
        rubyCount = 1;
        coinsCount = 0;
        flaskFull = true;
        dropletLoc = 0;
        isExploded = false;
        ratStoneLoc = -1;

        potArray = new int[POT_CAPACITY];
        chipArray = new IngredientChip[POT_CAPACITY];
        redsSaved = new ArrayList<>();

        whiteChipSum = 0;
        currentPotPosition = 0;
        lastPlacedIndex = -1;
        lastDrawnChip = null;

        loadPotImage(""); // replace "" with  pot image file path 
    }

    private void loadPotImage(String path) {
        if (path == null || path.isEmpty()) {
            potImage = null;
            return;
        }
        try {
            potImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Exception Error loading pot image: " + e.getMessage());
            potImage = null;
        }
    }

    public void resetRoundPot() {
        isExploded = false;
        ratStoneLoc = -1;
        coinsCount = 0;
        whiteChipSum = 0;
        currentPotPosition = dropletLoc;
        lastPlacedIndex = -1;
        lastDrawnChip = null;
        redsSaved.clear();

        for (int i = 0; i < POT_CAPACITY; i++) {
            potArray[i] = 0;
            chipArray[i] = null;
        }
    }

    public int getRubyCount() {
        return rubyCount;
    }

    public void addRuby() {
        rubyCount++;
    }

    public void removeRuby() {
        if (rubyCount > 0) {
            rubyCount--;
        }
    }

    public void removeRubies(int amount) {
        if (amount > 0) {
            rubyCount = Math.max(0, rubyCount - amount);
        }
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public void setCoinsCount(int coins) {
        coinsCount = Math.max(0, coins);
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coinsCount += amount;
        }
    }

    public boolean isFlaskFull() {
        return flaskFull;
    }

    public void setFlaskFull(boolean full) {
        flaskFull = full;
    }

    public int getDropletLoc() {
        return dropletLoc;
    }

    public void moveDropletForward() {
        if (dropletLoc < POT_CAPACITY - 1) {
            dropletLoc++;
        }
    }

    public int getRatStoneLoc() {
        return ratStoneLoc;
    }

    public void setRatStoneLoc(int spacesAheadOfDroplet) {
        int clamped = Math.max(0, spacesAheadOfDroplet);
        ratStoneLoc = Math.min(POT_CAPACITY - 1, dropletLoc + clamped);
        currentPotPosition = Math.max(currentPotPosition, ratStoneLoc);
    }

    public int getCurrentPotPosition() {
        return currentPotPosition;
    }

    public int getWhiteChipSum() {
        return whiteChipSum;
    }

    public IngredientChip getLastDrawnChip() {
        return lastDrawnChip;
    }

    public ArrayList<IngredientChip> getRedsSaved() {
        return redsSaved;
    }

    public boolean potExploded() {
        return isExploded;
    }

    public boolean greenAction() {
        for (int i = POT_CAPACITY - 1; i >= 0; i--) {
            if (chipArray[i] != null) {
                return "green".equalsIgnoreCase(chipArray[i].getColor());
            }
        }
        return false;
    }

    public boolean blackAction() {
        for (IngredientChip c : chipArray) {
            if (c != null && "black".equalsIgnoreCase(c.getColor())) {
                return true;
            }
        }
        return false;
    }

    public boolean pinkAction() {
        for (IngredientChip c : chipArray) {
            if (c != null && "pink".equalsIgnoreCase(c.getColor())) {
                return true;
            }
        }
        return false;
    }

    public boolean addChip(IngredientChip chip) {
        if (chip == null || isExploded) {
            return false;
        }

        int startBase = (lastPlacedIndex >= 0)
                ? lastPlacedIndex
                : Math.max(dropletLoc, Math.max(0, ratStoneLoc));

        int targetIndex = Math.min(POT_CAPACITY - 1, startBase + chip.getVal());
        chipArray[targetIndex] = chip;
        potArray[targetIndex] = chip.getVal();
        lastPlacedIndex = targetIndex;
        currentPotPosition = targetIndex;
        lastDrawnChip = chip;

        if ("white".equalsIgnoreCase(chip.getColor())) {
            whiteChipSum += chip.getVal();
            if (whiteChipSum > WHITE_EXPLOSION_LIMIT) {
                isExploded = true;
            }
        }

        return true;
    }

    public IngredientChip useFlaskOnLastChip() {
        if (!flaskFull || isExploded || lastDrawnChip == null) {
            return null;
        }
        if (!"white".equalsIgnoreCase(lastDrawnChip.getColor())) {
            return null;
        }

        IngredientChip returnedChip = lastDrawnChip;
        whiteChipSum = Math.max(0, whiteChipSum - returnedChip.getVal());

        if (lastPlacedIndex >= 0 && lastPlacedIndex < POT_CAPACITY) {
            chipArray[lastPlacedIndex] = null;
            potArray[lastPlacedIndex] = 0;
        }

        int fallbackIndex = Math.max(dropletLoc, Math.max(0, ratStoneLoc));
        int prevIndex = -1;
        for (int i = lastPlacedIndex - 1; i >= 0; i--) {
            if (chipArray[i] != null) {
                prevIndex = i;
                break;
            }
        }

        lastPlacedIndex = prevIndex;
        currentPotPosition = (prevIndex >= 0) ? prevIndex : fallbackIndex;
        lastDrawnChip = (prevIndex >= 0) ? chipArray[prevIndex] : null;
        flaskFull = false;

        return returnedChip;
    }

    public void drawPot(Graphics g, int playerNum) {
        int safeNum = Math.max(1, Math.min(4, playerNum));
        int col = (safeNum - 1) % 2;
        int row = (safeNum - 1) / 2;
        int x = col * 720 + 20;
        int y = row * 540 + 20;
        int w = 680;
        int h = 500;

        if (potImage != null) {
            g.drawImage(potImage, x, y, w, h, null);
        } else {
            g.setColor(new Color(45, 36, 30));
            g.fillRoundRect(x, y, w, h, 24, 24);
            g.setColor(new Color(212, 175, 55));
            g.drawRoundRect(x, y, w, h, 24, 24);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Player " + safeNum + " | Pos: " + currentPotPosition
                + " | White: " + whiteChipSum + "/" + WHITE_EXPLOSION_LIMIT
                + (isExploded ? " [EXPLODED]" : ""), x + 20, y + 30);

        int count = 0;
        for (int i = 0; i < POT_CAPACITY; i++) {
            if (chipArray[i] != null) {
                int cx = x + 25 + (count % 11) * 56;
                int cy = y + 55 + (count / 11) * 56;
                chipArray[i].drawChip(g, cx, cy);
                count++;
            }
        }
    }
}

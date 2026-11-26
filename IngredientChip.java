import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class IngredientChip {
    private String color;
    private int val;
    private String name;
    private BufferedImage chipImage;

    public IngredientChip(String color, int val) {
        this.color = (color == null) ? "" : color.trim();
        this.val = val;
        this.name = buildChipName(this.color, val);
        this.chipImage = loadChipImage(this.color, val);
    }

    private String buildChipName(String color, int val) {
        switch (color.toLowerCase()) {
            case "white":
                return "Cherry Bomb " + val;
            case "orange":
                return "Pumpkin " + val;
            case "green":
                return "Garden Spider " + val;
            case "red":
                return "Toadstool " + val;
            case "blue":
                return "Crow Skull " + val;
            case "black":
                return "Head Hawkmoth " + val;
            case "yellow":
                return "Mandrake " + val;
            case "pink":
            case "purple":
                return "Ghost's Breath " + val;
            default:
                return (color.isEmpty() ? "Chip" : color) + " " + val;
        }
    }

    private BufferedImage loadChipImage(String color, int val) {
        String lower = color.toLowerCase();
        String[] candidatePaths = {
            "/chips/" + lower + "_" + val + ".png",
            "/chips/" + lower + val + ".png",
            "/chips/" + lower + "_" + val + ".jpg",
            "/ingredientChips/" + lower + "_" + val + ".png"
        };

        for (String path : candidatePaths) {
            try (InputStream is = IngredientChip.class.getResourceAsStream(path)) {
                if (is != null) {
                    return ImageIO.read(is);
                }
            } catch (Exception ignored) {
            }

            File localFile = new File("src" + path);
            if (localFile.exists()) {
                try {
                    return ImageIO.read(localFile);
                } catch (Exception ignored) {
                }
            }
        }

        return null;
    }

    public String getColor() {
        return color;
    }

    public int getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    public void drawChip(Graphics g, int x, int y) {
        if (g == null) {
            return;
        }

        int size = 46;

        if (chipImage != null) {
            g.drawImage(chipImage, x, y, size, size, null);
            return;
        }

        g.setColor(resolveChipColor());
        g.fillOval(x, y, size, size);

        g.setColor(new Color(35, 25, 20));
        g.drawOval(x, y, size, size);

        g.setColor(needsLightText() ? Color.WHITE : Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        String label = String.valueOf(val);
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (size - fm.stringWidth(label)) / 2;
        int textY = y + ((size - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(label, textX, textY);
    }

    private Color resolveChipColor() {
        switch (color.toLowerCase()) {
            case "white":
                return new Color(245, 245, 240);
            case "orange":
                return new Color(235, 130, 35);
            case "green":
                return new Color(55, 165, 75);
            case "red":
                return new Color(205, 45, 45);
            case "blue":
                return new Color(45, 105, 210);
            case "black":
                return new Color(45, 45, 50);
            case "yellow":
                return new Color(240, 205, 45);
            case "pink":
            case "purple":
                return new Color(225, 125, 175);
            default:
                return Color.LIGHT_GRAY;
        }
    }

    private boolean needsLightText() {
        String lower = color.toLowerCase();
        return lower.equals("blue") || lower.equals("black") || lower.equals("red") || lower.equals("green");
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Card {
    private String cardName;
    private String description;
    private String color;
    private BufferedImage image;

    public Card(String name, String color, String description) {
        this.cardName = name;
        this.color = color;
        this.description = description;
        this.image = loadCardImage(name);
    }

    public Card(String name, String color) {
        this(name, color, "");
    }

    private BufferedImage loadCardImage(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String[] extensions = {".png", ".jpg", ".jpeg"};
        for (String ext : extensions) {
            String resourcePath = "/fortuneCard/" + name + ext;
            try (InputStream is = Card.class.getResourceAsStream(resourcePath)) {
                if (is != null) {
                    return ImageIO.read(is);
                }
            } catch (Exception ignored) {
            }

            File localFile = new File("src/fortuneCard/" + name + ext);
            if (localFile.exists()) {
                try {
                    return ImageIO.read(localFile);
                } catch (Exception ignored) {
                }
            }
        }

        try (InputStream exactStream = Card.class.getResourceAsStream("/fortuneCard/" + name)) {
            if (exactStream != null) {
                return ImageIO.read(exactStream);
            }
        } catch (Exception e) {
            System.out.println("Exception Error loading card [" + name + "]: " + e.getMessage());
        }

        return null;
    }

    public String getName() {
        return cardName;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public void cardAction(Player player) {
        if (player == null || cardName == null) {
            return;
        }
    }

    public void drawCard(Graphics g, boolean viewing) {
        if (g == null) {
            return;
        }

        int x;
        int y;
        int w;
        int h;

        if (viewing) {
            w = 420;
            h = 620;
            x = (1920 - w) / 2;
            y = (1080 - h) / 2;
        } else {
            w = 210;
            h = 310;
            x = 1560;
            y = 140;
        }

        if (image != null) {
            g.drawImage(image, x, y, w, h, null);
        } else {
            g.setColor(resolveCardThemeColor());
            g.fillRoundRect(x, y, w, h, 24, 24);
            g.setColor(new Color(225, 190, 90));
            g.drawRoundRect(x, y, w, h, 24, 24);

            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, viewing ? 26 : 16));
            g.drawString(cardName != null ? cardName : "Fortune Card", x + 20, y + (viewing ? 60 : 35));

            if (viewing && description != null && !description.isEmpty()) {
                g.setFont(new Font("SansSerif", Font.PLAIN, 20));
                drawWrappedText(g, description, x + 24, y + 120, w - 48, 28);
            }
        }

        if (viewing) {
            g.setColor(new Color(255, 235, 160));
            g.setFont(new Font("SansSerif", Font.ITALIC, 22));
            g.drawString("click to continue", x + (w / 2) - 85, y + h - 24);
        }
    }

    private Color resolveCardThemeColor() {
        if ("Purple".equalsIgnoreCase(color) || "P".equalsIgnoreCase(color)) {
            return new Color(75, 35, 105);
        }
        if ("Blue".equalsIgnoreCase(color) || "B".equalsIgnoreCase(color)) {
            return new Color(30, 65, 125);
        }
        return new Color(55, 45, 40);
    }

    private void drawWrappedText(Graphics g, String text, int x, int y, int maxWidth, int lineHeight) {
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        int currentY = y;

        for (String word : words) {
            String candidate = line.length() == 0 ? word : line + " " + word;
            if (g.getFontMetrics().stringWidth(candidate) > maxWidth && line.length() > 0) {
                g.drawString(line.toString(), x, currentY);
                line = new StringBuilder(word);
                currentY += lineHeight;
            } else {
                line = new StringBuilder(candidate);
            }
        }
        if (line.length() > 0) {
            g.drawString(line.toString(), x, currentY);
        }
    }
}

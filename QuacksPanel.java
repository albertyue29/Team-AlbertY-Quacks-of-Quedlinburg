import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class QuacksPanel extends JPanel implements MouseListener {
    public static final int NUM_PLAYERS = 4;
    public static final int PANEL_WIDTH = 1920;
    public static final int PANEL_HEIGHT = 1080;

    private Player[] players;
    private Scoreboard scoreboard;
    private Deck deck;
    private TurnIndicator turn;
    private int activePlayer;
    private String gameState;

    private boolean[] playerFinishedRound;
    private boolean viewingCard;
    private int rulesPage;
    private String previousStateBeforeRules;
    private String statusMessage;

    private BufferedImage startScreenImage;
    private BufferedImage[] rulesImages;

    public QuacksPanel() {
        this("Quacks of Quedlinburg");
    }

    public QuacksPanel(String title) {
        addMouseListener(this);
        initGame();
        loadAssets();
    }

    private void initGame() {
        this.players = new Player[NUM_PLAYERS];
        this.playerFinishedRound = new boolean[NUM_PLAYERS];

        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i] = new Player(i + 1);
            setupStartingBag(players[i]);
        }

        this.scoreboard = new Scoreboard();
        this.deck = new Deck();
        this.turn = new TurnIndicator(1, 1);
        this.activePlayer = 1;
        this.gameState = "START";
        this.viewingCard = false;
        this.rulesPage = 1;
        this.previousStateBeforeRules = "START";
        this.statusMessage = "";
    }

    private void setupStartingBag(Player player) {
        Bag bag = player.getBag();
        bag.getChips().clear();

        // Standard starting bag: White (1, 1, 1, 2, 2, 3), Green (1), Orange (1)
        bag.addChip(new IngredientChip("white", 1));
        bag.addChip(new IngredientChip("white", 1));
        bag.addChip(new IngredientChip("white", 1));
        bag.addChip(new IngredientChip("white", 2));
        bag.addChip(new IngredientChip("white", 2));
        bag.addChip(new IngredientChip("white", 3));
        bag.addChip(new IngredientChip("green", 1));
        bag.addChip(new IngredientChip("orange", 1));
    }

    private void loadAssets() {
        startScreenImage = loadImage("/images/start_screen.png");
        rulesImages = new BufferedImage[3];
        rulesImages[0] = loadImage("/images/rules_1.png");
        rulesImages[1] = loadImage("/images/rules_2.png");
        rulesImages[2] = loadImage("/images/rules_3.png");
    }

    private BufferedImage loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (Exception ignored) {
        }

        try {
            File file = new File("src" + path);
            if (file.exists()) {
                return ImageIO.read(file);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void nextRound() {
        int currentRound = turn.getCurrentRound();

        if (currentRound == 2) {
            statusMessage = "Day 2: Yellow Mandrake book unlocked!";
        } else if (currentRound == 3) {
            statusMessage = "Day 3: Pink Ghost's Breath book unlocked!";
        } else if (currentRound == 6) {
            for (Player p : players) {
                p.getBag().addChip(new IngredientChip("white", 1));
            }
            statusMessage = "Day 6: Each player adds a White 1 chip to their bag!";
        } else {
            statusMessage = "";
        }

        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i].resetPots();
            playerFinishedRound[i] = false;
        }

        // Apply rat-tail catch-up starting on Round 2
        if (currentRound >= 2) {
            int leaderScore = scoreboard.getLeaderScore();
            for (int i = 0; i < NUM_PLAYERS; i++) {
                int ratTails = scoreboard.numRattailSpots(leaderScore, i + 1);
                players[i].getPot().setRatStoneLoc(ratTails);
            }
        }

        activePlayer = turn.getCurrentTurn();
        deck.drawCard();
        viewingCard = true;
        gameState = "ROUND";
        repaint();
    }

    public void displayRules() {
        if (!"RULES".equals(gameState)) {
            previousStateBeforeRules = gameState;
            rulesPage = 1;
            gameState = "RULES";
            repaint();
        }
    }

    public void endRound() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            Pot pot = players[i].getPot();
            int basePoints = Math.max(1, pot.getCurrentPotPosition() / 5);
            int baseCoins = Math.max(2, pot.getCurrentPotPosition() / 2);

            if (pot.potExploded()) {
                // Exploded pot: take either victory points or coins (defaulting to coins here)
                pot.setCoinsCount(baseCoins);
            } else {
                players[i].addPoints(basePoints);
                scoreboard.addPoints(i + 1, basePoints);
                pot.setCoinsCount(baseCoins);
            }
        }

        if (turn.getCurrentRound() == TurnIndicator.MAX_ROUNDS) {
            gameState = "DAY_9_EXCHANGE";
        } else {
            gameState = "BUYING";
        }
        activePlayer = 1;
        turn.setCurrentTurn(1);
        repaint();
    }

    public void checkWinner() {
        if (turn.nextRound()) {
            nextRound();
        } else {
            gameState = "GAME_OVER";
            repaint();
        }
    }

    private int countActiveBrewers() {
        int active = 0;
        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (!playerFinishedRound[i] && !players[i].getPot().potExploded()) {
                active++;
            }
        }
        return active;
    }

    private void advanceToNextBrewerOrEndRound() {
        if (countActiveBrewers() == 0) {
            gameState = "ROUND_OVER";
            repaint();
            return;
        }

        for (int step = 1; step <= NUM_PLAYERS; step++) {
            int nextIdx = (activePlayer - 1 + step) % NUM_PLAYERS;
            if (!playerFinishedRound[nextIdx] && !players[nextIdx].getPot().potExploded()) {
                activePlayer = nextIdx + 1;
                turn.setCurrentTurn(activePlayer);
                repaint();
                return;
            }
        }

        gameState = "ROUND_OVER";
        repaint();
    }

    private void handleActionCommand(String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return;
        }

        if ("RULES".equals(cmd)) {
            displayRules();
            return;
        }

        switch (cmd) {
            case "START":
                nextRound();
                break;

            case "STIR": {
                Player current = players[activePlayer - 1];
                IngredientChip drawn = current.getBag().getRandomChip();
                if (drawn != null) {
                    current.getPot().addChip(drawn);
                    statusMessage = "Player " + activePlayer + " drew " + drawn.getName();
                } else {
                    playerFinishedRound[activePlayer - 1] = true;
                    statusMessage = "Player " + activePlayer + " has no chips left in bag.";
                }
                advanceToNextBrewerOrEndRound();
                break;
            }

            case "WAIT": {
                if (countActiveBrewers() > 1) {
                    statusMessage = "Player " + activePlayer + " waited.";
                    advanceToNextBrewerOrEndRound();
                }
                break;
            }

            case "FINISH": {
                playerFinishedRound[activePlayer - 1] = true;
                statusMessage = "Player " + activePlayer + " finished brewing.";
                advanceToNextBrewerOrEndRound();
                break;
            }

            case "FLASK": {
                Pot pot = players[activePlayer - 1].getPot();
                IngredientChip returned = pot.useFlaskOnLastChip();
                if (returned != null) {
                    players[activePlayer - 1].getBag().addChip(returned);
                    statusMessage = "Player " + activePlayer + " used flask to return " + returned.getName();
                }
                repaint();
                break;
            }

            case "FINISH_BUYING":
            case "NO_PURCHASE": {
                if (activePlayer < NUM_PLAYERS) {
                    activePlayer++;
                    turn.setCurrentTurn(activePlayer);
                } else {
                    gameState = "NEXT_DAY";
                }
                repaint();
                break;
            }

            case "INCREASE_DROPLET": {
                Player p = players[activePlayer - 1];
                if (p.buyDroplet()) {
                    statusMessage = "Player " + activePlayer + " moved droplet forward!";
                }
                repaint();
                break;
            }

            case "REFILL_FLASK": {
                Player p = players[activePlayer - 1];
                if (p.buyFlask()) {
                    statusMessage = "Player " + activePlayer + " refilled flask!";
                }
                repaint();
                break;
            }

            case "EXCHANGE_RUBIES_VP": {
                Player p = players[activePlayer - 1];
                Pot pot = p.getPot();
                if (pot.getRubyCount() >= 2) {
                    pot.removeRubies(2);
                    p.addPoints(1);
                    scoreboard.addPoints(activePlayer, 1);
                }
                repaint();
                break;
            }

            case "EXCHANGE_COINS_VP": {
                Player p = players[activePlayer - 1];
                Pot pot = p.getPot();
                if (pot.getCoinsCount() >= 5) {
                    pot.setCoinsCount(pot.getCoinsCount() - 5);
                    p.addPoints(1);
                    scoreboard.addPoints(activePlayer, 1);
                }
                repaint();
                break;
            }

            case "NEXT_DAY":
                checkWinner();
                break;

            default:
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (g == null) {
            return;
        }

        if ("RULES".equals(gameState)) {
            drawRulesScreen(g);
            return;
        }

        if ("START".equals(gameState)) {
            drawStartScreen(g);
            return;
        }

        if ("GAME_OVER".equals(gameState)) {
            drawGameOverScreen(g);
            return;
        }

        if ("ROUND_OVER".equals(gameState)) {
            g.setColor(new Color(35, 25, 20));
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            g.setColor(new Color(240, 195, 65));
            g.setFont(new Font("Serif", Font.BOLD, 56));
            g.drawString("ROUND OVER! click to continue", 530, 520);
            return;
        }

        // Board & 4 player pots
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i].getPot().drawPot(g, i + 1);
            players[i].drawPlayer(g);
        }

        // Right-hand parchment sidebar
        g.setColor(new Color(228, 205, 162));
        g.fillRect(1510, 0, 410, PANEL_HEIGHT);

        // Top-right help '?' icon
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 54));
        g.drawString("?", 1850, 70);

        turn.drawIndicator(g, 1575, 120);
        deck.drawDeck(g);

        drawRightSidebarControls(g);

        if (viewingCard && deck.getCurrentCard() != null) {
            deck.getCurrentCard().drawCard(g, true);
        }

        if (statusMessage != null && !statusMessage.isEmpty()) {
            g.setColor(new Color(35, 25, 15));
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString(statusMessage, 1530, 960);
        }
    }

    private void drawStartScreen(Graphics g) {
        if (startScreenImage != null) {
            g.drawImage(startScreenImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        } else {
            g.setColor(new Color(45, 32, 55));
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            g.setColor(new Color(245, 200, 70));
            g.setFont(new Font("Serif", Font.BOLD, 64));
            g.drawString("The Quacks of Quedlinburg", 540, 340);
        }

        g.setColor(new Color(235, 185, 55));
        g.fillRoundRect(760, 720, 400, 110, 30, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 36));
        g.drawString("CLICK TO START", 815, 788);

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 54));
        g.drawString("?", 1850, 70);
    }

    private void drawRulesScreen(Graphics g) {
        int idx = Math.max(0, Math.min(2, rulesPage - 1));
        if (rulesImages != null && rulesImages[idx] != null) {
            g.drawImage(rulesImages[idx], 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        } else {
            g.setColor(new Color(235, 215, 175));
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 42));
            g.drawString("Rules - Page " + rulesPage + " of 3 (Click to continue)", 520, 200);
        }
    }

    private void drawRightSidebarControls(Graphics g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        if ("ROUND".equals(gameState)) {
            drawPillButton(g, 1545, 310, 150, 110, "Stir");
            if (countActiveBrewers() > 1) {
                drawPillButton(g, 1725, 310, 150, 110, "Wait");
            }
            drawPillButton(g, 1545, 450, 150, 110, "Finish");
            Pot p = players[activePlayer - 1].getPot();
            if (p.isFlaskFull() && p.getLastDrawnChip() != null) {
                drawPillButton(g, 1725, 450, 150, 110, "Flask");
            }
        } else if ("BUYING".equals(gameState)) {
            drawPillButton(g, 810, 25, 300, 75, "Finish buying");
        } else if ("RUBY_BUYING".equals(gameState)) {
            drawPillButton(g, 1560, 400, 310, 95, "Increase droplet");
            drawPillButton(g, 1560, 520, 310, 95, "Refill Flask");
            drawPillButton(g, 1560, 640, 310, 95, "No purchase");
        } else if ("DAY_9_EXCHANGE".equals(gameState)) {
            drawPillButton(g, 1560, 400, 310, 95, "2 Rubies -> 1 VP");
            drawPillButton(g, 1560, 640, 310, 95, "5 Coins -> 1 VP");
        } else if ("NEXT_DAY".equals(gameState)) {
            drawPillButton(g, 1565, 440, 270, 105, "Next Day");
        }
    }

    private void drawPillButton(Graphics g, int x, int y, int w, int h, String label) {
        g.setColor(new Color(235, 185, 55));
        g.fillRoundRect(x, y, w, h, 35, 35);
        g.setColor(new Color(45, 30, 15));
        g.drawRoundRect(x, y, w, h, 35, 35);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString(label, x + 22, y + (h / 2) + 8);
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(new Color(30, 22, 28));
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        int bestScore = -1;
        ArrayList<Integer> winners = new ArrayList<>();
        for (int i = 0; i < NUM_PLAYERS; i++) {
            int s = players[i].getPlayerScore();
            if (s > bestScore) {
                bestScore = s;
                winners.clear();
                winners.add(i + 1);
            } else if (s == bestScore) {
                winners.add(i + 1);
            }
        }

        g.setColor(new Color(245, 200, 70));
        g.setFont(new Font("Serif", Font.BOLD, 60));
        String headline = (winners.size() > 1)
                ? "Tie!"
                : "Player " + winners.get(0) + " wins the game!";
        g.drawString(headline, 580, 280);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 34));
        for (int i = 0; i < NUM_PLAYERS; i++) {
            g.drawString("Player " + (i + 1) + ": " + players[i].getPlayerScore() + " pts", 760, 400 + i * 55);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e == null) {
            return;
        }
        int mx = e.getX();
        int my = e.getY();

        if ("RULES".equals(gameState)) {
            if (rulesPage < 3) {
                rulesPage++;
            } else {
                gameState = previousStateBeforeRules;
            }
            repaint();
            return;
        }

        if (viewingCard) {
            viewingCard = false;
            repaint();
            return;
        }

        if ("ROUND_OVER".equals(gameState)) {
            endRound();
            return;
        }

        Buttons btn = new Buttons(mx, my, gameState);
        handleActionCommand(btn.action());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

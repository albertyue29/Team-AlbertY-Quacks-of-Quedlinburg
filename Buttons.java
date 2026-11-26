import java.awt.Rectangle;

public class Buttons {
    private int x;
    private int y;
    private String gameState;

    // Start Screen
    private static final Rectangle START_BUTTON = new Rectangle(760, 720, 400, 110);
    private static final Rectangle HELP_BUTTON = new Rectangle(1830, 15, 75, 75);

    // Round Action buttons
    private static final Rectangle STIR_BUTTON = new Rectangle(1545, 310, 150, 110);
    private static final Rectangle WAIT_BUTTON = new Rectangle(1725, 310, 150, 110);
    private static final Rectangle FINISH_BUTTON = new Rectangle(1545, 450, 150, 110);
    private static final Rectangle FLASK_BUTTON = new Rectangle(1725, 450, 150, 110);

    // Scoring, Dice 
    private static final Rectangle ROLL_DICE_BUTTON = new Rectangle(1585, 520, 230, 110);

    // Buying phase 
    private static final Rectangle FINISH_BUYING_BUTTON = new Rectangle(810, 25, 300, 75);
    private static final Rectangle RUBY_OPTION_1 = new Rectangle(1560, 400, 310, 95);
    private static final Rectangle RUBY_OPTION_2 = new Rectangle(1560, 520, 310, 95);
    private static final Rectangle RUBY_OPTION_3 = new Rectangle(1560, 640, 310, 95);

    // End of rounf
    private static final Rectangle NEXT_DAY_BUTTON = new Rectangle(1565, 440, 270, 105);

    public Buttons(int x, int y, String gameState) {
        this.x = x;
        this.y = y;
        this.gameState = (gameState == null) ? "" : gameState;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getGameState() {
        return gameState;
    }

    public String action() {
        if (HELP_BUTTON.contains(x, y)) {
            return "RULES";
        }

        String state = gameState.toUpperCase();
        switch (state) {
            case "START":
            case "STARTSCREEN":
                if (START_BUTTON.contains(x, y)) {
                    return "START";
                }
                break;

            case "ROUND":
            case "PLAYING":
                if (STIR_BUTTON.contains(x, y)) {
                    return "STIR";
                }
                if (WAIT_BUTTON.contains(x, y)) {
                    return "WAIT";
                }
                if (FINISH_BUTTON.contains(x, y)) {
                    return "FINISH";
                }
                if (FLASK_BUTTON.contains(x, y)) {
                    return "FLASK";
                }
                break;

            case "SCORING":
            case "DICE":
                if (ROLL_DICE_BUTTON.contains(x, y)) {
                    return "ROLL_DICE";
                }
                return "CONTINUE";

            case "BUYING":
                if (FINISH_BUYING_BUTTON.contains(x, y)) {
                    return "FINISH_BUYING";
                }
                break;

            case "RUBY_BUYING":
            case "END_TURN_RUBIES":
                if (RUBY_OPTION_1.contains(x, y)) {
                    return "INCREASE_DROPLET";
                }
                if (RUBY_OPTION_2.contains(x, y)) {
                    return "REFILL_FLASK";
                }
                if (RUBY_OPTION_3.contains(x, y)) {
                    return "NO_PURCHASE";
                }
                break;

            case "DAY_9_EXCHANGE":
                if (RUBY_OPTION_1.contains(x, y)) {
                    return "EXCHANGE_RUBIES_VP";
                }
                if (RUBY_OPTION_3.contains(x, y)) {
                    return "EXCHANGE_COINS_VP";
                }
                break;

            case "NEXT_DAY":
            case "ROUND_OVER":
                if (NEXT_DAY_BUTTON.contains(x, y)) {
                    return "NEXT_DAY";
                }
                break;

            default:
                break;
        }

        return "";
    }
}

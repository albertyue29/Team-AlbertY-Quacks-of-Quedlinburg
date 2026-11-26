import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> fortuneDeck;
    private Card currentCard;

    public Deck() {
        this.fortuneDeck = new ArrayList<>();
        this.currentCard = null;
        initializeCards();
        shuffle();
    }

    private void initializeCards() {
        // Purple Fortune Cards
        fortuneDeck.add(new Card("A Good Start", "Purple", "Choose: Use your rat stone normally OR pass up on 1-3 rat tails and take that many rubies (1-3) instead."));
        fortuneDeck.add(new Card("An Opportunistic Moment", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Rat Infestation", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Choose Wisely", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("You Only Get To Choose One", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Roll The Die", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Beginner's Bonus", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Rats Are Your Friends", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Wheel And Deal", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Less Is More", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Just In Time", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("The Pot Is Filling Up", "Purple", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Charity", "Purple", "Apply round fortune effect."));

        // Blue Fortune Cards 
        fortuneDeck.add(new Card("The Pot is Full", "Blue", "Any player who gets to roll the die this round rolls twice."));
        fortuneDeck.add(new Card("Well Stirred", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Living In Luxury", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("A Second Chance", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Strong Ingredient", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Seasoned Perfectly", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Schadenfreude", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("It's Shining Extra Bright", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Lucky Devil", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Pumpkin Patch Party", "Blue", "Apply round fortune effect."));
        fortuneDeck.add(new Card("Magic Potion", "Blue", "Apply round fortune effect."));
    }

    public void shuffle() {
        Collections.shuffle(fortuneDeck);
    }

    public Card drawCard() {
        if (fortuneDeck.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * fortuneDeck.size());
        currentCard = fortuneDeck.remove(randomIndex);
        return currentCard;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void drawDeck(Graphics g) {
        if (g == null || currentCard == null) {
            return;
        }
        currentCard.drawCard(g, false);
    }
}

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class Deck {
	private ArrayList<Card> fortuneDeck;
	private Card currentCard;
	
	public Deck () {
		
		
		fortuneDeck.add(new Card("A Good Start", "P"));
		fortuneDeck.add(new Card("An Opportnistic Moment", "P"));
		fortuneDeck.add(new Card("Rat Infestation", "P"));
		fortuneDeck.add(new Card("Choose Wisely", "P"));
		fortuneDeck.add(new Card("You Only Get To Choose One", "P"));
		fortuneDeck.add(new Card("Roll The Die", "P"));
		fortuneDeck.add(new Card("Beginner's Bonus", "P"));
		fortuneDeck.add(new Card("Rats Are Your Friends", "P"));
		fortuneDeck.add(new Card("Wheel And Deal", "P"));
		fortuneDeck.add(new Card("Less Is More", "P"));
		fortuneDeck.add(new Card("Just In Time", "P"));
		fortuneDeck.add(new Card("The Pot Is Filling Up", "P"));
		fortuneDeck.add(new Card("Charity", "P"));
		
		fortuneDeck.add(new Card("The Pot is Full", "B"));
		fortuneDeck.add(new Card("Well Stirred", "B"));
		fortuneDeck.add(new Card("Living In Luxury", "B") );
		fortuneDeck.add(new Card("A Second Chance", "B"));
		fortuneDeck.add(new Card("Strong Ingredient", "B"));
		fortuneDeck.add(new Card("Seasoned Perfectly", "B"));
		fortuneDeck.add(new Card("Schadenfreude", "B"));
		fortuneDeck.add(new Card("It's Shining Extra Bright", "B"));
		fortuneDeck.add(new Card("Lucky Devil", "B"));
		fortuneDeck.add(new Card("Pumpkin Patch Party", "B"));
		fortuneDeck.add(new Card("Magic Potion", "B"));
		
		
		
		
			/*	//Card goodStart = new Card("A Good Start", "P")
				//Card opportunistic = new Card("An Opportnistic Moment", "P");
				//Card ratInfestation = new Card("Rat Infestation", "P");
				//Card chooseWisely = new Card("Choose Wisely", "P");
				//Card onlyChooseOne = new Card("You Only Get To Choose One", "P");
				//Card rollDie = new Card("Roll The Die", "P");
				//Card beginnerBonus = new Card("Beginner's Bonus", "P");
				Card ratsAreFriends = new Card("Rats Are Your Friends", "P");
				Card wheelDeal = new Card("Wheel And Deal", "P");
				Card lessMore = new Card("Less Is More", "P");
				Card justInTime = new Card("Just In Time", "P");
				Card thePotIsFillingUp = new Card("The Pot Is Filling Up", "P");
				Card charity = new Card("Charity", "P");
				
				Card thePotIsFull = new Card("The Pot is Full", "B");
				Card wellStirred = new Card("Well Stirred", "B");
				Card livingLuxury = new Card("Living In Luxury", "B");
				Card secondChance = new Card("A Second Chance", "B");
				Card strongIngredient = new Card("Strong Ingredient", "B");
				Card seasonePerfectly = new Card("Seasoned Perfectly", "B");
				Card schadenfreude = new Card("Schadenfreude", "B");
				Card shiningBright = new Card("It's Shining Extra Bright", "B");
				Card luckyDevil = new Card("Lucky Devil", "B");
				Card pumpkinParty = new Card("Pumpkin Patch Party", "B");
				Card magicPotion = new Card("Magic Potion", "B");
		
		*/
	}
	
	public Card drawCard() {
		int randomIndex = (int) (Math.random() * fortuneDeck.size());
		currentCard = fortuneDeck.get(randomIndex);
		fortuneDeck.remove(randomIndex);
		
		return currentCard;
	}
	
	//public void shuffle() {
		
	//}
	
	public Card getCurrentCard() {
		
		return currentCard;
	}
	
	public void drawDeck(Graphics g) {
		
	}
}

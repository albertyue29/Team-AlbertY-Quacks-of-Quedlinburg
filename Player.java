
import java.awt.Graphics;
import java.util.Random;

public class Player {

		private int playerNum;
		private int playerScore;
		private Pot pot;//i need the pot and bag classes 
		private Bag bag;

       public Player(int playerNum) {
           this.playerNum = playerNum;
           this.playerScore = 0;
           this.pot = new Pot();
           this.bag = new Bag();
           
           if (playerNum < 1 || playerNum > 4) {
        	    throw new IllegalArgumentException("Player number must be 1-4");
        	}
       }

    	public void rollDice() {
    		Random random = new Random();
    		int roll = random.nextInt(6) + 1;

    		System.out.println("Player " + playerNum + " rolled: " + roll);
    }

        public void addPoints(int points) {
            playerScore += points;
    }

    private boolean buyChip(String color, int val) {
        int cost = val; 

        if (bag.getCoins() >= cost) {
            bag.addChip(color, val);
            bag.removeCoins(cost);
            return true;
        }

        return false;
    }

    public boolean buyOrange() {
       
    	return buyChip("orange", 1);
    }

    public boolean buyGreen(int val) {
        
    	return buyChip("green", val);
    }

    public boolean buyRed(int val) {

        return buyChip("red", val);
    }

    public boolean buyBlue(int val) {
       
    	return buyChip("blue", val);
    }

    public boolean buyBlack(int val) {
       
    	return buyChip("black", val);
    }

    public boolean buyYellow(int val) {
        
    	return buyChip("yellow", val);
    }

    public boolean buyPink(int val) {
        
    	return buyChip("pink", val);
    }

    
    public boolean buyDroplet() {
        
    	return bag.getRubies() >= 2;
    }

    public void moveDropForward() {
        
    	pot.moveDropForward();
    }

    public boolean buyFlask() {
       
    	return bag.getRubies() >= 2;
    }

    public void resetPots() {
      
    	pot.reset();
    }

    public Pot getPot() {
       
    	return pot;
    }

    public Bag getBag() {
        
    	return bag;
    }

    public void drawPlayer(Graphics g) {
      
        g.drawString("Player " + playerNum, 20, 30);
        g.drawString("Score: " + playerScore, 20, 50);
    }
    
}

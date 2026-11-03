import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Card {
	private String cardName;
	private String description;
	private String color;
	private BufferedImage image;
	
	
	public Card (String name, String color){
		cardName = name;
		this.color=color;
		this.description=description;
		
		try {
			image= ImageIO.read(Card.class.getResource("/fortuneCards/"+name));
			
		} 
		catch(Exception e) {
            System.out.println("Exception Error");
            return;
        }
	}
	
	public String getName() {
		return cardName;
		
	}
	
	public String getColor() {
		return color;
		
	}
	
	//public String getDescription() {
	//	return description;
		
	//}
	
	//public void cardAction(Player player) {
		
		
	//}
	
	public BufferedImage drawCard() { //(Graphics g???){
		
		
		return image;
	}
	
	
}

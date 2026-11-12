
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class Pot {
	private int rubyCount;
	private int coinsCount;
	private boolean flaskFull;
	private int dropletLoc;
	private boolean isExploded;
	private int ratStoneLoc;
	private int[] potArray;
	private IngredientChip[] chipArray;
	private ArrayList<IngredientChip> redsSaved;
	
	public Pot()
	{
		rubyCount = 1;
		coinsCount = 0;
		flaskFull = true;
		dropletLoc = 0;
		isExploded = false;
		ratStoneLoc = -1;
		
		// numerous of one type of number in the pot ??
		// albert said to make new class 
		
		// potArray = new int[]
		// chipArray = new IngredientChip[];
		redsSaved = new ArrayList<>();
		
		
	}
	
	public int getRubyCount()
	{
		return rubyCount;
	}
	
	public void addRuby()
	{
		rubyCount++;
	}
	
	public void removeRuby()
	{
		rubyCount--;
	}
	
	public boolean isFlaskFull()
	{
		return flaskFull;
	}
	
	public int getDropletLoc()
	{
		return dropletLoc;
	}
	
	public int getRatStoneLoc()
	{
		return ratStoneLoc;
	}
	
	public boolean potExploded()
	{
		return isExploded;
	}
	
	// more variables needed?
	// if action used boolean object
	// black neighbors
	// count of each color chip
	
	
	public boolean greenAction()
	{
		return false;
	}
	
	public boolean blackAction()
	{
		return false;
	}
	
	public boolean pinkAction()
	{
		return false;
	}
	
	public boolean addchip(IngredientChip chip)
	{
		return false;
	}
	
	public void drawPot(Graphics g, int playerNum)
	{
		BufferedImage pot;
		
		try
		{
			pot = ImageIO.read(new File("")); 
		}
		
		// draws pot image
		
		catch(Exception E)
		{
			System.out.println("Exception Error");
			return;
		}
		
		g.drawImage(pot, 0, 0, 1920, 1080, null);
	}
	
}


import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class ScoreBoard {
	private int turnCount;
	private int blueScore = 0;
	private int redScore = 0;
	private int greenScore = 0;
	private int orangeScore = 0; 
	private ArrayList<Integer> ratTailPositions = new ArrayList<>();
	
	// blue - 1
	// red - 2
	// green - 3
	// orange - 4
	
	public void addPoints(int playerNum, int points)
	{
		if (playerNum == 1)
		{
			blueScore += points;
		}
		
		else if (playerNum == 2)
		{
			redScore += points;
		}
		
		else if (playerNum == 3)
		{
			greenScore += points;
		}
		
		else
		{
			orangeScore += points;
		}
	}
	
	public void setRatTailPositions()
	{
		ratTailPositions.add(1);
		ratTailPositions.add(4);
		ratTailPositions.add(10);
		ratTailPositions.add(12);
		ratTailPositions.add(14);
		ratTailPositions.add(16);
		ratTailPositions.add(18);
		ratTailPositions.add(20);
		ratTailPositions.add(22);
		ratTailPositions.add(24);
		ratTailPositions.add(26);
		ratTailPositions.add(29);
		ratTailPositions.add(30);
		ratTailPositions.add(32);
		ratTailPositions.add(34);
		ratTailPositions.add(36);
		ratTailPositions.add(38);
		ratTailPositions.add(40);
		ratTailPositions.add(42);
		ratTailPositions.add(44);
		ratTailPositions.add(48);

	}
	
	public int numRattailSpots(int leaderScore, int playerNum)
	{
		int playerScore;
		
		if (playerNum == 1)
		{
			playerScore = blueScore;
		}
		
		else if (playerNum == 2)
		{
			playerScore = redScore;
		}
		
		else if (playerNum == 3)
		{
			playerScore = greenScore;
		}
		
		else
		{
			playerScore = orangeScore;
		}
		
		
		if (playerScore >= leaderScore)
		{
			return 0;
		}
		
		
		int count = 0;
		
		for (int i = 0; i <= 20; i++)
		{
			if (ratTailPositions.get(i) < leaderScore && ratTailPositions.get(i) >= playerScore)
			{
				count++;
			}
		}
		
		return count;

	}
	
	public void drawScoreboard(Graphics g)
	{
		BufferedImage scoreboard;
		
		try
		{
			scoreboard = ImageIO.read(new File("")); 	// should be jarable format
		}
		
		// need to draw the score board image still
		
		catch(Exception E)
		{
			System.out.println("Exception Error");
			return;
		}
		
		g.drawImage(scoreboard, 0, 0, 1920, 1080, null);
		
	}
	
}

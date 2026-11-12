
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class IngredientChip {
	
	private String color;
	private int val;
	private String name;
	
	public IngredientChip(String c, int v)
	{
		color = c;
		val = v;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public int getVal()
	{
		return val;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void drawchip(Graphics g, int x, int y)
	{
		// draw each of the chips
	}
}

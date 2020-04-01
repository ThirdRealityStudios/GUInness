package core.tools.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.io.Path;
import core.tools.image.Manipulation;

public class FontLoader
{
	private final ArrayList<Character> specialChars;
	
	private final BufferedImage pattern;

	public FontLoader()
	{
		specialChars = new ArrayList<Character>();

		specialChars.add(' ');
		specialChars.add('.');
		specialChars.add(':');
		specialChars.add(';');
		specialChars.add(',');
		
		pattern = loadPattern();
	}

	private int getAlphabeticIndex(char letter)
	{
		char upperCase = new String(new char[]
		{ letter }).toUpperCase().toCharArray()[0];

		int index = upperCase - 65 + 1;

		return index;
	}

	private int getSpecialSymbolIndex(char symbol)
	{
		int fontIndex = specialChars.indexOf((symbol));

		int relativeIndex = fontIndex + 37;

		return relativeIndex;
	}

	private boolean isValidSpecialChar(char symbol)
	{
		for (char c : specialChars)
		{
			if (c == symbol)
			{
				return true;
			}
		}

		return false;
	}

	private int getDigitIndex(char symbol)
	{
		int offsetIndex = 48;

		int relativeIndex = symbol - offsetIndex + 27;

		return relativeIndex;
	}

	private boolean isAlphabetic(char symbol)
	{
		return Character.isAlphabetic(symbol) && ((int) symbol) >= 65 && ((int) symbol) <= 90;
	}

	public boolean isValid(char symbol)
	{
		char upperCase = (symbol + "").toUpperCase().charAt(0);

		symbol = upperCase;

		return (isAlphabetic(symbol) || Character.isDigit(symbol) || isValidSpecialChar(symbol));
	}

	private int getSymbolIndex(char symbol)
	{
		if (Character.isAlphabetic(symbol))
		{
			return getAlphabeticIndex(symbol);
		} else if (Character.isDigit(symbol))
		{
			return getDigitIndex(symbol);
		} else if (isValidSpecialChar(symbol))
		{
			return getSpecialSymbolIndex(symbol);
		} else
		{
			// Character not found.
			return -1;
		}
	}

	private BufferedImage loadPattern()
	{
		BufferedImage imgLoaded = null;

		try
		{
			String path = Path.CORE_MEDIA + "\\StandardFont.png";

			imgLoaded = ImageIO.read(new File(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return imgLoaded;
	}

	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	public void display(Graphics g, char letter, int xPos, int yPos, int fontSize, Color fontColor)
	{
		int index = getSymbolIndex(letter);

		if (index == -1)
			return;

		int x = (index - 1) * 30 + index, y = 1;

		int dim = 30;
		
		BufferedImage img = pattern.getSubimage(x, y, dim, dim);

		Image colorized = Manipulation.colorize(img, fontColor).getScaledInstance(fontSize, fontSize, pattern.SCALE_SMOOTH);
		
		g.drawImage(colorized, xPos, yPos, null);
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified font size.
	public Dimension display(Graphics g, String text, int xPos, int yPos, int fontSize, Color fontColor)
	{
		char[] converted = text.toCharArray();

		int offset = 0;

		for(char c : converted)
		{
			display(g, c, xPos + fontSize * offset, yPos, fontSize, fontColor);

			offset++;
		}

		return new Dimension(fontSize * text.length(), fontSize);
	}

	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	// Uses the specified scale relative to the fonts original size (30px).
	public void display(Graphics g, char letter, int xPos, int yPos, float scale)
	{
		int dim = 30, scaled = (int) (dim * scale);

		display(g, letter, xPos, yPos, scaled);
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified value.
	public Dimension display(Graphics g, String text, int xPos, int yPos, float scale)
	{
		char[] converted = text.toCharArray();

		int dim = 30, scaled = (int) (dim * scale);

		int offset = 0;

		for (char c : converted)
		{
			display(g, c, xPos + scaled * offset, yPos, scale);

			offset++;
		}

		return new Dimension((int) (scale * dim) * text.length(), (int) (scale * dim));
	}
}

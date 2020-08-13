package core.gui.font;

import java.util.ArrayList;

public class FontLoader
{
	private final ArrayList<Character> specialChars;

	public FontLoader()
	{
		specialChars = new ArrayList<Character>();

		specialChars.add(' ');
		specialChars.add('.');
		specialChars.add(':');
		specialChars.add(';');
		specialChars.add(',');
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
			if(c == symbol)
			{
				return true;
			}
		}

		return false;
	}

	public int getDigitIndex(int symbol)
	{
		int offsetIndex = 48;

		int relativeIndex = symbol - offsetIndex + 27;

		return relativeIndex;
	}

	private boolean isAlphabetic(int symbol)
	{
		return Character.isAlphabetic(symbol) && symbol >= 65 && symbol <= 90;
	}
	
	public boolean isImplemented(char symbol)
	{
		char upperCase = (symbol + "").toUpperCase().charAt(0);

		symbol = upperCase;

		return (isAlphabetic(symbol) || Character.isDigit(symbol) || isValidSpecialChar(symbol));
	}

	public int getSymbolIndex(char symbol)
	{
		if(Character.isAlphabetic(symbol))
		{
			return getAlphabeticIndex(symbol);
		}
		else if(Character.isDigit(symbol))
		{
			return getDigitIndex(symbol);
		}
		else if(isValidSpecialChar(symbol))
		{
			return getSpecialSymbolIndex(symbol);
		}
		else
		{
			// Character not found.
			return -1;
		}
	}
}

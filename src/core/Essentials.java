package core;

import java.util.ArrayList;
import java.util.Arrays;

public class Essentials
{
	// This array contains a few of the accepted special chars.
	// Checking every single number would be a bad approach as of bad structure,
	// though more efficient.
	private static Character[] toList = {' ','.', ':', ',', ';'};
	
	private static ArrayList<Character> acceptedChars = new ArrayList<Character>(Arrays.asList(toList));
	
	// Returns the type of an object.
	// Returns an empty String if the given reference is null.
	public static String typeof(Object o)
	{		
		return o != null ? o.getClass().getSuperclass().getSimpleName() : "";
	}
	
	public static boolean isAlphanumeric(char c)
	{
		return acceptedChars.contains(c) || (c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
	}
}

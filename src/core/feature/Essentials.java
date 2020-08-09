package core.feature;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

import core.gui.component.EDComponent;

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
	
	// Returns the type of an EDComponent.
	// Returns an empty String if the given reference is null.
	public static String typeof(EDComponent c)
	{
		return c != null ? c.getType() : "";
	}
	
	public static boolean isAlphanumeric(char c)
	{
		return acceptedChars.contains(c) || (c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
	}
	
	public static Shape getLocalizedShape(Point loc)
	{
		return new Rectangle(loc);
	}
	
	public static Shape getLocalizedShape(Point loc, Dimension dim)
	{
		Shape s = getLocalizedShape(loc);
		
		s.getBounds().setSize(dim);
		
		return s;
	}
}

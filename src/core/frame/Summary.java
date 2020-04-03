package core.frame;

import java.awt.Point;

import core.gui.EDComponent;

public class Summary
{
	public static String typeof(EDComponent comp)
	{
		String type = comp.getClass().getGenericSuperclass().getTypeName();
		int last = type.lastIndexOf('.') + 1;
		String simpleType = type.substring(last, type.length()).toUpperCase();
		
		return simpleType;
	}
	
	public static Point getFrameLocation(Point location)
	{
		final int xDiff = -8, yDiff = -31;
		
		return new Point(location.x + xDiff, location.y + yDiff);
	}
}

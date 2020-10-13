package org.thirdreality.guinness.feature;

import java.awt.Point;
import java.awt.Rectangle;

public class GIRectangle extends Rectangle
{
	public void add(Point pt)
	{
		super.add(pt);
	}
	
	@Override
	public Point getLocation()
	{
		return new Point(super.getLocation());
	}
	
	public boolean contains(Point p)
	{
		return super.contains(new Point(p.x, p.y));
	}
}

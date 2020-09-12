package core.feature.shape;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class ShapeMaker
{
	public static Polygon createRectangle(int x, int y, int width, int height)
	{
		Polygon polygon = new Polygon();
		
		polygon.addPoint(x, y);
		polygon.addPoint(x + width, y);
		polygon.addPoint(x + width, y + height);
		polygon.addPoint(x, y + height);
		
		return polygon;
	}
	
	public static Polygon createRectangle(Point location, Dimension dimension)
	{
		return createRectangle(location.x, location.y, dimension.width, dimension.height);
	}
	
	public static Polygon createRectangleFrom(Rectangle rect)
	{
		return createRectangle(rect.x, rect.y, rect.width, rect.height);
	}
}

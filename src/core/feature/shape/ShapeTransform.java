package core.feature.shape;

import java.awt.Point;
import java.awt.Polygon;

public class ShapeTransform
{
	public static Polygon movePolygonTo(Polygon p, int x, int y)
	{
		int xDiff = x - p.getBounds().x;
		int yDiff = y - p.getBounds().y;
		
		int[] xpoints = new int[p.npoints];
		int[] ypoints = new int[p.npoints];
		
		for(int iX = 0; iX < p.npoints; iX++)
		{
			xpoints[iX] = p.xpoints[iX] + xDiff;
		}
		
		for(int iY = 0; iY < p.npoints; iY++)
		{
			ypoints[iY] = p.ypoints[iY] + yDiff;
		}
		
		Polygon returnable = new Polygon(xpoints, ypoints, p.npoints);
		
		return returnable;
	}
	
	public static Polygon movePolygonTo(Polygon p, Point point)
	{
		return movePolygonTo(p, point.x, point.y);
	}
}

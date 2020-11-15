package org.thirdreality.guinness.feature.shape;

import java.awt.Point;
import java.awt.Polygon;

import org.thirdreality.guinness.feature.GIPoint;

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
	
	public static Polygon scalePolygon(Polygon p, float k)
	{
		// If k is 1, there is no change in the polygon except it returns a copy of it only.
		// This prevents the loops below from being ran to reduce performance lacks.
		if(k == 1)
		{
			return new Polygon(p.xpoints, p.ypoints, p.npoints);
		}
		
		int[] xpoints = new int[p.npoints];
		int[] ypoints = new int[p.npoints];
		
		for(int iX = 0; iX < p.npoints; iX++)
		{
			xpoints[iX] = (int) (p.xpoints[iX] * k);
		}
		
		for(int iY = 0; iY < p.npoints; iY++)
		{
			ypoints[iY] = (int) (p.ypoints[iY] * k);
		}
		
		Polygon returnable = new Polygon(xpoints, ypoints, p.npoints);
		
		return returnable;
	}
	
	public static Polygon inverseOrderFrom(Polygon p)
	{
		Polygon inversed = new Polygon();
		
		for(int i = p.npoints-1; i >= 0; i--)
		{
			inversed.addPoint(p.xpoints[i], p.ypoints[i]);
		}

		return inversed;
	}
	
	public static Polygon invertXFromPolygon(Polygon p)
	{
		int[] xpoints = new int[p.npoints];
		int[] ypoints = new int[p.npoints];
		
		for(int iX = 0; iX < p.npoints; iX++)
		{
			xpoints[iX] = -p.xpoints[iX];
			ypoints[iX] = p.ypoints[iX];
		}
		
		Polygon returnable = new Polygon(xpoints, p.ypoints, p.npoints);
		
		return returnable;
	}
	
	public static Polygon invertYFromPolygon(Polygon p)
	{
		int[] xpoints = new int[p.npoints];
		int[] ypoints = new int[p.npoints];
		
		for(int i = 0; i < p.npoints; i++)
		{
			xpoints[i] = p.xpoints[i];
			ypoints[i] = -p.ypoints[i];
		}
		
		Polygon returnable = new Polygon(p.xpoints, ypoints, p.npoints);
		
		return returnable;
	}
	
	public static Polygon invertPolygon(Polygon p)
	{
		return invertYFromPolygon(invertXFromPolygon(p));
	}
	
	public static Polygon flipVertically(Polygon p)
	{
		return movePolygonTo(invertXFromPolygon(p), p.getBounds().x, p.getBounds().y);
	}
	
	public static Polygon flipHorizontically(Polygon p)
	{
		return movePolygonTo(invertYFromPolygon(p), p.getBounds().x, p.getBounds().y);
	}
	
	public static Polygon flip(Polygon p)
	{
		Point formerLocation = p.getBounds().getLocation();
		
		return movePolygonTo(invertPolygon(p), formerLocation);
	}
	
	// Use this method to retrieve a copy of a polygon which fits the scale and offset given by the Viewport.
	public static Polygon getPolygonRelativeToViewport(Polygon p, Point offset, float scale)
	{
		return ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(p, new GIPoint(p.getBounds().getLocation()).add(offset).toPoint()), scale);
	}
	
	// Use this method to retrieve a location which considers the scale and offset given by this Viewport.
	public static Point getLocationRelativeToViewport(Point location, Point offset, float scale)
	{
		return new Point((int) ((location.x + offset.x) * scale), (int) ((location.y + offset.y) * scale));
	}
}

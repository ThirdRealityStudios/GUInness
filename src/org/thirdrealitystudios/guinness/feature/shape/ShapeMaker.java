package org.thirdrealitystudios.guinness.feature.shape;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.thirdrealitystudios.guinness.gui.component.decoration.GRectangle;

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
	
	public static Polygon addPointsTo(Polygon destination, Polygon toBeAdded)
	{
		Polygon fed = new Polygon(destination.xpoints, destination.ypoints, destination.npoints);
		
		for(int i = 0; i < toBeAdded.npoints; i++)
		{
			fed.addPoint(toBeAdded.xpoints[i], toBeAdded.ypoints[i]);
		}

		return fed;
	}
	
	public static Polygon createRectangleFrom(int x, int y, int width, int height, int borderRadiusPx)
	{
		Polygon borderRadius = createCircle(borderRadiusPx, 1/4f, 1f);
		
		Polygon rectangle = createRectangleFrom(new Rectangle(x, y, width, height));
		
		Polygon topLeftRadius = ShapeTransform.flip(borderRadius);
		
		Polygon bottomLeftRadius = ShapeTransform.inverseOrderFrom(ShapeTransform.flipVertically(borderRadius));
		bottomLeftRadius.translate(0, rectangle.getBounds().height - borderRadiusPx);
		
		Polygon bottomRightRadius = ShapeTransform.inverseOrderFrom(ShapeTransform.flipVertically(bottomLeftRadius));
		bottomRightRadius.translate(rectangle.getBounds().width - borderRadiusPx, 0);
		
		Polygon topRightRadius = ShapeTransform.inverseOrderFrom(ShapeTransform.flipHorizontically(bottomRightRadius));
		topRightRadius.translate(0, - (rectangle.getBounds().height - borderRadiusPx));
		
		Polygon roundedRectangle = new Polygon();
		
		roundedRectangle = addPointsTo(roundedRectangle, topLeftRadius);
		roundedRectangle = addPointsTo(roundedRectangle, bottomLeftRadius);
		roundedRectangle = addPointsTo(roundedRectangle, bottomRightRadius);
		roundedRectangle = addPointsTo(roundedRectangle, topRightRadius);
		
		return ShapeTransform.movePolygonTo(roundedRectangle, x, y);
	}
	
	public static Polygon createRectangleFrom(Rectangle rect, int borderRadiusPx)
	{
		return createRectangleFrom(rect.x, rect.y, rect.width, rect.height, borderRadiusPx);
	}
	
	public static Polygon createCircle(int circleRadius, float circlePercentage, float quality) throws IllegalArgumentException
	{
		if(quality > 1f)
		{
			throw new IllegalArgumentException("The given precision (quality) was above 100%! Enter a valid quality between 0% and 100% for a circle.");
		}

		Polygon borderRadius = new Polygon();

		int maxPossiblePrecision = 1000;

		int maxPrecision = (int) (quality * maxPossiblePrecision);

		for(int i = 0; i < maxPrecision; i++)
		{
			double x = Math.sin(((float) i / maxPrecision) * Math.PI / 0.5 * circlePercentage) * circleRadius;
			double y = Math.cos(((float) i / maxPrecision) * Math.PI / 0.5 * circlePercentage) * circleRadius;

			borderRadius.addPoint((int) x, (int) y);
		}

		return borderRadius;
	}
}

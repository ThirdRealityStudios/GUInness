package org.thirdreality.guinness.feature.shape;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.thirdreality.guinness.gui.component.style.property.GBorderProperty;

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
	
	private static Polygon getLowerLeftRadius(GBorderProperty borderProperties, Rectangle rect)
	{
		int borderRadiusLLPx = borderProperties.getLowerLeftBorderRadiusPx() == null ? borderProperties.getBorderRadiusPx() : borderProperties.getLowerLeftBorderRadiusPx();
		Polygon borderRadiusLL = createCircle(borderRadiusLLPx, 1/4f, 1f);
		
		borderRadiusLL = ShapeTransform.flipVertically(borderRadiusLL);
		borderRadiusLL = ShapeTransform.inverseOrderFrom(borderRadiusLL);
		borderRadiusLL.translate(0, rect.height - borderRadiusLLPx);
		
		return borderRadiusLL;
	}
	
	private static Polygon getLowerRightRadius(GBorderProperty borderProperties, Rectangle rect)
	{
		int borderRadiusLRPx = borderProperties.getLowerRightBorderRadiusPx() == null ? borderProperties.getBorderRadiusPx() : borderProperties.getLowerRightBorderRadiusPx();
		Polygon borderRadiusLR = createCircle(borderRadiusLRPx, 1/4f, 1f);
		
		borderRadiusLR.translate(rect.width - borderRadiusLRPx, rect.height - borderRadiusLRPx);
		
		return borderRadiusLR;
	}
	
	private static Polygon getUpperLeftRadius(GBorderProperty borderProperties)
	{
		int borderRadiusTLPx = borderProperties.getUpperLeftBorderRadiusPx() == null ? borderProperties.getBorderRadiusPx() : borderProperties.getUpperLeftBorderRadiusPx();
		Polygon borderRadiusTL = createCircle(borderRadiusTLPx, 1/4f, 1f);
		
		borderRadiusTL = ShapeTransform.flip(borderRadiusTL);
		
		return borderRadiusTL;
	}
	
	private static Polygon getUpperRightRadius(GBorderProperty borderProperties, Rectangle rect)
	{		
		int borderRadiusTRPx = borderProperties.getUpperRightBorderRadiusPx() == null ? borderProperties.getBorderRadiusPx() : borderProperties.getUpperRightBorderRadiusPx();
		Polygon borderRadiusTR = createCircle(borderRadiusTRPx, 1/4f, 1f);

		borderRadiusTR = ShapeTransform.flipHorizontically(borderRadiusTR);
		borderRadiusTR = ShapeTransform.inverseOrderFrom(borderRadiusTR);
		borderRadiusTR.translate(rect.width - borderRadiusTRPx, 0);

		return borderRadiusTR;
	}
	
	public static Polygon createRectangleFrom(Rectangle rect, GBorderProperty borderProperties)
	{
		Polygon roundedRectangle = new Polygon();
		
		Polygon borderRadiusTL = getUpperLeftRadius(borderProperties);
		Polygon borderRadiusLL = getLowerLeftRadius(borderProperties, rect);
		Polygon borderRadiusLR = getLowerRightRadius(borderProperties, rect);
		Polygon borderRadiusTR = getUpperRightRadius(borderProperties, rect);
		
		roundedRectangle = addPointsTo(roundedRectangle, borderRadiusTL);
		roundedRectangle = addPointsTo(roundedRectangle, borderRadiusLL);
		roundedRectangle = addPointsTo(roundedRectangle, borderRadiusLR);
		roundedRectangle = addPointsTo(roundedRectangle, borderRadiusTR);

		return ShapeTransform.movePolygonTo(roundedRectangle, rect.getLocation());
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

package org.thirdreality.guinness.feature.path;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.feature.shape.ShapeMaker;

public class Path2DMaker
{
	public static Path2D.Double makeRectangle(int x, int y, int width, int height)
	{
		return new Path2D.Double(ShapeMaker.createRectangle(x, y, width, height));
	}

	public static Path2D.Double makeRectangle(GIPoint.Double location, int width, int height)
	{
		Path2D.Double moved = makeRectangle((int) location.x, (int) location.y, width, height);

		return moved;
	}
}

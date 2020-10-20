package org.thirdreality.guinness.feature;

import java.awt.Point;
import java.awt.geom.Point2D;

// A Point class which provides more features than Point2D.Float from the Java libraries.
public class GIPoint extends Point2D.Float
{
	public GIPoint()
	{
		super();
	}
	
	public GIPoint(Point point)
	{
		super(point.x, point.y);
	}
	
	public GIPoint(Point2D.Float point)
	{
		super(point.x, point.y);
	}
	
	public GIPoint(float x, float y)
	{
		super(x, y);
	}
	
	public void setLocation(Point location)
	{
		x = location.x;
		y = location.y;
	}
	
	public void setLocation(Point2D.Float location)
	{
		x = location.x;
		y = location.y;
	}
	
	public GIPoint add(Point2D.Float point)
	{
		x += point.x;
		y += point.y;
		
		return this;
	}
	
	// Adds a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint add(Point2D.Float point, boolean condition)
	{
		return condition ? add(point) : this;
	}
	
	public GIPoint add(float num)
	{
		x += num;
		y += num;
		
		return this;
	}
	
	// Adds a number to this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint add(float num, boolean condition)
	{
		return condition ? add(num) : this;
	}
	
	public GIPoint addX(float num)
	{
		x += num;
		
		return this;
	}
	
	// Adds a number to the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint addX(float num, boolean condition)
	{
		return condition ? addX(num) : this;
	}
	
	public GIPoint addY(float num)
	{
		y += num;
		
		return this;
	}
	
	// Adds a number to the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint addY(float num, boolean condition)
	{
		return condition ? addY(num) : this;
	}
	
	public GIPoint sub(Point2D.Float point)
	{
		x -= point.x;
		y -= point.y;
		
		return this;
	}
	
	// Subtracts a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint sub(Point2D.Float point, boolean condition)
	{
		return condition ? sub(point) : this;
	}
	
	public GIPoint sub(float num)
	{
		x -= num;
		y -= num;
		
		return this;
	}
	
	// Subtracts a number from this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint sub(float num, boolean condition)
	{
		return condition ? sub(num) : this;
	}
	
	public GIPoint subX(float num)
	{
		x -= num;
		
		return this;
	}
	
	// Subtracts a number from the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint subX(float num, boolean condition)
	{
		return condition ? subX(num) : this;
	}	
	
	public GIPoint subY(float num)
	{
		y -= num;
		
		return this;
	}
	
	// Subtracts a number from the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint subY(float num, boolean condition)
	{
		return condition ? subY(num) : this;
	}
	
	public GIPoint mul(Point2D.Float point)
	{
		x *= point.x;
		y *= point.y;
		
		return this;
	}
	
	// Multiplies a point with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(Point2D.Float point, boolean condition)
	{
		return condition ? mul(point) : this;
	}
	
	public GIPoint mul(float num)
	{
		x *= num;
		y *= num;
		
		return this;
	}
	
	// Multiplies a number with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(float num, boolean condition)
	{
		return condition ? mul(num) : this;
	}
	
	public GIPoint mulX(float num)
	{
		x *= num;
		
		return this;
	}
	
	// Multiplies a number with the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulX(float num, boolean condition)
	{
		return condition ? mulX(num) : this;
	}
	
	// Multiplies a number with the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulY(float num, boolean condition)
	{
		return condition ? mulY(num) : this;
	}
	
	public GIPoint mulY(float num)
	{
		y *= num;
		
		return this;
	}
	
	public GIPoint div(Point2D.Float point)
	{
		x /= point.x;
		y /= point.y;
		
		return this;
	}
	
	// Divides this point with another point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(Point2D.Float point, boolean condition)
	{
		return condition ? div(point) : this;
	}
	
	public GIPoint div(float num)
	{
		x /= num;
		y /= num;
		
		return this;
	}
	
	// Divides this point with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(float num, boolean condition)
	{
		return condition ? div(num) : this;
	}
	
	public GIPoint divX(float num)
	{
		x /= num;
		
		return this;
	}
	
	// Divides the x value with a number if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divX(float num, boolean condition)
	{
		return condition ? divX(num) : this;
	}
	
	public GIPoint divY(float num)
	{
		y /= num;
		
		return this;
	}
	
	// Divides the y value with a number if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divY(float num, boolean condition)
	{
		return condition ? divY(num) : this;
	}

	/*
	 * 
	 * Here below are the "native" methods which you can use with normal integers as parameters.
	 * 
	 */

	public GIPoint add(Point point)
	{
		x += point.x;
		y += point.y;
		
		return this;
	}

	// Adds a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint add(Point point, boolean condition)
	{
		return condition ? add(point) : this;
	}

	public GIPoint sub(Point point)
	{
		x -= point.x;
		y -= point.y;
		
		return this;
	}

	// Subtracts a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint sub(Point point, boolean condition)
	{
		return condition ? sub(point) : this;
	}

	public GIPoint mul(Point point)
	{
		x *= point.x;
		y *= point.y;
		
		return this;
	}

	// Multiplies a point with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(Point point, boolean condition)
	{
		return condition ? mul(point) : this;
	}

	public GIPoint div(Point point)
	{
		x /= point.x;
		y /= point.y;
		
		return this;
	}

	// Divides this point with another point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(Point point, boolean condition)
	{
		return condition ? div(point) : this;
	}

	/*
	 * 
	 * Here are some type methods which you can use to convert point data
	 * 
	 */

	public Point toPoint()
	{
		return new Point((int) x, (int) y);
	}

	public Point.Float toPointFloat()
	{
		return new Point.Float(x, y);
	}

	// Copies this GIPoint by creating a new instance.
	public GIPoint copy()
	{
		return new GIPoint(x, y);
	}
	
	// Checks whether the point an origin point (at 0|0).
	public boolean isEmpty()
	{
		return x == 0 && y == 0;
	}
	
	// Alias method for the isEmpty() method.
	public boolean isAtOrigin()
	{
		return isEmpty();
	}
}

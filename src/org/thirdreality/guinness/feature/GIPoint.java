package org.thirdreality.guinness.feature;

import java.awt.Dimension;
import java.awt.Point;

// A Point class which provides more features than Point from the Java libraries.
public class GIPoint extends Point
{
	public GIPoint()
	{
		super();
	}
	
	public GIPoint(Point point)
	{
		super(point);
	}
	
	public GIPoint(int x, int y)
	{
		super(x, y);
	}
	
	public void setLocation(Point location)
	{
		x = location.x;
		y = location.y;
	}
	
	public GIPoint add(Point point)
	{
		x += point.x;
		y += point.y;
		
		return new GIPoint(this);
	}
	
	// Adds a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint add(Point point, boolean condition)
	{
		return condition ? add(point) : this;
	}
	
	public GIPoint add(int num)
	{
		x += num;
		y += num;
		
		return new GIPoint(this);
	}
	
	// Adds a number to this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint add(int num, boolean condition)
	{
		return condition ? add(num) : this;
	}
	
	public GIPoint addX(int num)
	{
		x += num;
		
		return new GIPoint(this);
	}
	
	// Adds a number to the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint addX(int num, boolean condition)
	{
		return condition ? addX(num) : this;
	}
	
	public GIPoint addY(int num)
	{
		y += num;
		
		return new GIPoint(this);
	}
	
	// Adds a number to the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint addY(int num, boolean condition)
	{
		return condition ? addY(num) : this;
	}
	
	public GIPoint sub(Point point)
	{
		x -= point.x;
		y -= point.y;
		
		return new GIPoint(this);
	}
	
	// Subtracts a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint sub(Point point, boolean condition)
	{
		return condition ? sub(point) : this;
	}
	
	public GIPoint sub(int num)
	{
		x -= num;
		y -= num;
		
		return new GIPoint(this);
	}
	
	// Subtracts a number from this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint sub(int num, boolean condition)
	{
		return condition ? sub(num) : this;
	}
	
	public GIPoint subX(int num)
	{
		x -= num;
		
		return new GIPoint(this);
	}
	
	// Subtracts a number from the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint subX(int num, boolean condition)
	{
		return condition ? subX(num) : this;
	}	
	
	public GIPoint subY(int num)
	{
		y -= num;
		
		return new GIPoint(this);
	}
	
	// Subtracts a number from the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint subY(int num, boolean condition)
	{
		return condition ? subY(num) : this;
	}
	
	public GIPoint mul(Point point)
	{
		x *= point.x;
		y *= point.y;
		
		return new GIPoint(this);
	}
	
	// Multiplies a point with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(Point point, boolean condition)
	{
		return condition ? mul(point) : this;
	}
	
	public GIPoint mul(float num)
	{
		x *= num;
		y *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type float) with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(float num, boolean condition)
	{
		return condition ? mul(num) : this;
	}
	
	public GIPoint mul(int num)
	{
		x *= num;
		y *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type integer) with this point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mul(int num, boolean condition)
	{
		return condition ? mul(num) : this;
	}
	
	public GIPoint mulX(int num)
	{
		x *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type integer) with the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulX(int num, boolean condition)
	{
		return condition ? mulX(num) : this;
	}
	
	public GIPoint mulX(float num)
	{
		x *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type float) with the x value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulX(float num, boolean condition)
	{
		return condition ? mulX(num) : this;
	}
	
	public GIPoint mulY(int num)
	{
		y *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type integer) with the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulY(int num, boolean condition)
	{
		return condition ? mulY(num) : this;
	}
	
	public GIPoint mulY(float num)
	{
		y *= num;
		
		return new GIPoint(this);
	}
	
	// Multiplies a number (of type float) with the y value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint mulY(float num, boolean condition)
	{
		return condition ? mulY(num) : this;
	}
	
	public GIPoint div(Point point)
	{
		x /= point.x;
		y /= point.y;
		
		return new GIPoint(this);
	}
	
	// Divides this point with another point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(Point point, boolean condition)
	{
		return condition ? div(point) : this;
	}
	
	public GIPoint div(float num)
	{
		x /= num;
		y /= num;
		
		return new GIPoint(this);
	}
	
	// Divides this point with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(float num, boolean condition)
	{
		return condition ? div(num) : this;
	}
	
	public GIPoint div(int num)
	{
		x /= num;
		y /= num;
		
		return new GIPoint(this);
	}
	
	// Divides this point with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint div(int num, boolean condition)
	{
		return condition ? div(num) : this;
	}
	
	public GIPoint divX(int num)
	{
		x /= num;
		
		return new GIPoint(this);
	}
	
	// Divides the x value with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divX(int num, boolean condition)
	{
		return condition ? divX(num) : this;
	}
	
	public GIPoint divX(float num)
	{
		x /= num;
		
		return new GIPoint(this);
	}
	
	// Divides the x value with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divX(float num, boolean condition)
	{
		return condition ? divX(num) : this;
	}
	
	public GIPoint divY(int num)
	{
		y /= num;
		
		return new GIPoint(this);
	}
	
	// Divides the y value with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divY(int num, boolean condition)
	{
		return condition ? divY(num) : this;
	}
	
	public GIPoint divY(float num)
	{
		y /= num;
		
		return new GIPoint(this);
	}
	
	// Divides the y value with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIPoint divY(float num, boolean condition)
	{
		return condition ? divY(num) : this;
	}
}

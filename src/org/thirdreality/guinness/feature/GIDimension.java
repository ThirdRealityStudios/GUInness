package org.thirdreality.guinness.feature;

import java.awt.Dimension;

import org.thirdreality.guinness.Meta;

public class GIDimension extends Dimension
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	public GIDimension()
	{
		super();
	}
	
	public GIDimension(Dimension dimension)
	{
		super(dimension);
	}
	
	public GIDimension(int width, int height)
	{
		super(width, height);
	}
	
	public void setDimension(Dimension dimension)
	{
		super.setSize(dimension);
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public GIDimension add(Dimension dimension)
	{
		width += dimension.width;
		height += dimension.height;
		
		return new GIDimension(this);
	}
	
	// Adds a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension add(Dimension dimension, boolean condition)
	{
		return condition ? add(dimension) : this;
	}
	
	public GIDimension add(int num)
	{
		width += num;
		height += num;
		
		return new GIDimension(this);
	}
	
	// Adds a number to this dimension if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension add(int num, boolean condition)
	{
		return condition ? add(num) : this;
	}
	
	public GIDimension addWidth(int num)
	{
		width += num;
		
		return new GIDimension(this);
	}
	
	// Adds a number to the width value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension addWidth(int num, boolean condition)
	{
		return condition ? addWidth(num) : this;
	}
	
	public GIDimension addHeight(int num)
	{
		height += num;
		
		return new GIDimension(this);
	}
	
	// Adds a number to the height value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension addHeight(int num, boolean condition)
	{
		return condition ? addHeight(num) : this;
	}
	
	public GIDimension sub(Dimension dimension)
	{
		width -= dimension.width;
		height -= dimension.height;
		
		return new GIDimension(this);
	}
	
	// Subtracts a point only if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension sub(Dimension dimension, boolean condition)
	{
		return condition ? sub(dimension) : this;
	}
	
	public GIDimension sub(int num)
	{
		width -= num;
		height -= num;
		
		return new GIDimension(this);
	}
	
	// Subtracts a number from this dimension if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension sub(int num, boolean condition)
	{
		return condition ? sub(num) : this;
	}
	
	public GIDimension subWidth(int num)
	{
		width -= num;
		
		return new GIDimension(this);
	}
	
	// Subtracts a number from the width value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension subWidth(int num, boolean condition)
	{
		return condition ? subWidth(num) : this;
	}	
	
	public GIDimension subHeight(int num)
	{
		height -= num;
		
		return new GIDimension(this);
	}
	
	// Subtracts a number from the height value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension subHeight(int num, boolean condition)
	{
		return condition ? subHeight(num) : this;
	}
	
	public GIDimension mul(Dimension dimension)
	{
		width *= dimension.width;
		height *= dimension.height;
		
		return new GIDimension(this);
	}
	
	// Multiplies a point with this dimension if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mul(Dimension dimension, boolean condition)
	{
		return condition ? mul(dimension) : this;
	}
	
	public GIDimension mul(float num)
	{
		width *= num;
		height *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type float) with this dimension if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mul(float num, boolean condition)
	{
		return condition ? mul(num) : this;
	}
	
	public GIDimension mul(int num)
	{
		width *= num;
		height *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type integer) with this dimension if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mul(int num, boolean condition)
	{
		return condition ? mul(num) : this;
	}
	
	public GIDimension mulWidth(int num)
	{
		width *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type integer) with the width value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mulWidth(int num, boolean condition)
	{
		return condition ? mulWidth(num) : this;
	}
	
	public GIDimension mulWidth(float num)
	{
		width *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type float) with the width value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mulWidth(float num, boolean condition)
	{
		return condition ? mulWidth(num) : this;
	}
	
	public GIDimension mulHeight(int num)
	{
		height *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type integer) with the height value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mulHeight(int num, boolean condition)
	{
		return condition ? mulHeight(num) : this;
	}
	
	public GIDimension mulHeight(float num)
	{
		height *= num;
		
		return new GIDimension(this);
	}
	
	// Multiplies a number (of type float) with the height value if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension mulHeight(float num, boolean condition)
	{
		return condition ? mulHeight(num) : this;
	}
	
	public GIDimension div(Dimension dimension)
	{
		width /= dimension.width;
		height /= dimension.height;
		
		return new GIDimension(this);
	}
	
	// Divides this dimension with another point if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension div(Dimension dimension, boolean condition)
	{
		return condition ? div(dimension) : this;
	}
	
	public GIDimension div(float num)
	{
		width /= num;
		height /= num;
		
		return new GIDimension(this);
	}
	
	// Divides this dimension with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension div(float num, boolean condition)
	{
		return condition ? div(num) : this;
	}
	
	public GIDimension div(int num)
	{
		width /= num;
		height /= num;
		
		return new GIDimension(this);
	}
	
	// Divides this dimension with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension div(int num, boolean condition)
	{
		return condition ? div(num) : this;
	}
	
	public GIDimension divWidth(int num)
	{
		width /= num;
		
		return new GIDimension(this);
	}
	
	// Divides the width value with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension divWidth(int num, boolean condition)
	{
		return condition ? divWidth(num) : this;
	}
	
	public GIDimension divWidth(float num)
	{
		width /= num;
		
		return new GIDimension(this);
	}
	
	// Divides the width value with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension divWidth(float num, boolean condition)
	{
		return condition ? divWidth(num) : this;
	}
	
	public GIDimension divHeight(int num)
	{
		height /= num;
		
		return new GIDimension(this);
	}
	
	// Divides the height value with a number (of type integer) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension divHeight(int num, boolean condition)
	{
		return condition ? divHeight(num) : this;
	}
	
	public GIDimension divHeight(float num)
	{
		height /= num;
		
		return new GIDimension(this.width, this.height);
	}
	
	// Divides the height value with a number (of type float) if the condition is given. Otherwise it makes no changes and only returns this object.
	public GIDimension divHeight(float num, boolean condition)
	{
		return condition ? divHeight(num) : this;
	}
}

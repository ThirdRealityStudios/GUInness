package core.gui;

import java.awt.Graphics;
import java.awt.Shape;

public abstract class EDComponent
{
	// Contains the location and shape of the component.
	private Shape shape;

	private boolean visible = true;

	public EDComponent(Shape shape, boolean visible)
	{
		this.shape = shape;
		this.visible = visible;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public Shape getShape()
	{
		return shape;
	}
	
	public void setShape(Shape shape)
	{
		this.shape = shape;
	}
	
	public abstract void draw(Graphics g);
}

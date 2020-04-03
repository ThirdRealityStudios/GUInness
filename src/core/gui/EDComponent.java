package core.gui;

import java.awt.Rectangle;

public abstract class EDComponent
{
	// Contains the location and dimension of the component.
	private Rectangle rect;

	private boolean visible = true;

	public EDComponent(Rectangle rect, boolean visible)
	{
		this.rect = rect;
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

	public Rectangle getRectangle()
	{
		return rect;
	}
}

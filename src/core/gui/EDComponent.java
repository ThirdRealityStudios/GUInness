package core.gui;

import java.awt.Point;

public abstract class EDComponent
{
	private Point location = null;
	
	private boolean visible = true;
	
	public EDComponent(Point location, boolean visible)
	{
		this.location = location;
		this.visible = visible;
	}

	public Point getLocation()
	{
		return location;
	}

	public void setLocation(Point location)
	{
		this.location = location;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}

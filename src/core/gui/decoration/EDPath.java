package core.gui.decoration;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import core.gui.EDComponent;

public class EDPath extends EDComponent
{
	private Path2D.Double path;

	private Color drawColor;

	private boolean fill;

	public EDPath(Path2D.Double path, Color drawColor, boolean fill, Point location, boolean visible)
	{
		super(new Rectangle(location), visible);
		
		this.path = path;

		this.drawColor = drawColor;
		this.fill = fill;
	}

	public Path2D.Double getPath()
	{
		return path;
	}

	public void setPath(Path2D.Double path)
	{
		this.path = path;
	}

	public Color getDrawColor()
	{
		return drawColor;
	}

	public void setDrawColor(Color drawColor)
	{
		this.drawColor = drawColor;
	}

	public boolean isFill()
	{
		return fill;
	}

	public void setFill(boolean fill)
	{
		this.fill = fill;
	}

}

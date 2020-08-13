package core.gui.special;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Path2D;

import core.Meta;
import core.gui.component.GComponent;

public class GPath extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private Path2D.Double path;
	private boolean fill;

	public GPath(Path2D.Double path, Color drawColor, boolean fill, Point location, boolean visible)
	{
		super("path", location, null, 0, null, null, visible);
		
		Rectangle bounds = path.getBounds();
		Rectangle rect = new Rectangle(location.x, location.y, bounds.width, bounds.height);
		
		getStyle().setShape(rect);
		
		setPath(path);
		getStyle().setPrimaryColor(drawColor); // The "primary color" of GComponent is used as the "draw color".
		setFill(fill);
	}

	public Path2D.Double getPath()
	{
		return path;
	}

	public void setPath(Path2D.Double path)
	{
		this.path = path;
	}

	public boolean isFill()
	{
		return fill;
	}

	public void setFill(boolean fill)
	{
		this.fill = fill;
	}

	@Override
	public void onClick()
	{
		// Not implemented for paths yet.
	}

	@Override
	public void onHover()
	{
		// Not implemented for paths yet.
	}

	@Override
	public void setValue(String val)
	{
		// Unused
	}

}

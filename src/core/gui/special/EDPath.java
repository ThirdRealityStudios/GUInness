package core.gui.special;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Path2D;

import core.frame.LayeredRenderFrame;
import core.gui.EDComponent;
import core.gui.design.Design;

public class EDPath extends EDComponent
{
	private Path2D.Double path;
	private boolean fill;

	public EDPath(LayeredRenderFrame rF, Path2D.Double path, Color drawColor, boolean fill, Point location, boolean visible)
	{
		super(rF, "path", location, null, 0, null, 0, visible);
		
		Rectangle bounds = path.getBounds();
		Rectangle rect = new Rectangle(location.x, location.y, bounds.width, bounds.height);
		
		setShape(rect);
		
		setPath(path);
		setPrimaryColor(drawColor); // The "primary color" of EDComponent is used as the "draw color".
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

}

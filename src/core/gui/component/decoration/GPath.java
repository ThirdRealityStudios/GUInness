package core.gui.component.decoration;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import core.Meta;
import core.feature.shape.ShapeMaker;
import core.gui.component.GComponent;

public class GPath extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private Polygon path;
	private boolean fill;

	public GPath(Polygon path, Color drawColor, boolean fill, Point location)
	{
		super("path", location, null, 0, null, null);
		
		getStyle().setPrimaryLook(ShapeMaker.createRectangleFrom(path.getBounds()));
		
		setPath(path);
		getStyle().setPrimaryColor(drawColor); // The "primary color" of GComponent is used as the "draw color".
		setFill(fill);
	}

	public Polygon getPath()
	{
		return path;
	}

	public void setPath(Polygon path)
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

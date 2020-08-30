package core.gui.component.decoration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import core.Meta;
import core.gui.component.GComponent;

public class GRectangle extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GRectangle(int x, int y, Dimension size, Color color, float opacity)
	{
		super("rectangle", new Rectangle(x, y, size.width, size.height), 0, null, null);
		
		if(opacity != 0)
		{
			// First, the primary color needs to be set.
			// When applying the opacity below it uses the primary color.
			// That's why you need to set a primary color first because
			// otherwise it would cause a NullPointerException.
			getStyle().setPrimaryColor(color);
			
			// Applies the given opacity to the primary color.
			getStyle().setOpacity(opacity);
		}
		else
		{
			getStyle().setPrimaryColor(null);
		}
	}

	@Override
	public void onClick()
	{
		// Not implemented for rectangles.
	}

	@Override
	public void onHover()
	{
		// Not implemented for rectangles.
		
	}

	@Override
	public void setValue(String val)
	{
		// Does nothing..
	}
}

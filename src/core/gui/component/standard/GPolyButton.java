package core.gui.component.standard;

import java.awt.Point;
import java.awt.Polygon;
import core.Meta;
import core.gui.component.GComponent;
import core.gui.font.Font;

public abstract class GPolyButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	// Info: The polygon is closed automatically later..
	public GPolyButton(Point location, String title, Font font, Polygon polygon)
	{
		super("polybutton", location, polygon, title.length(), title, font);
		
		// Make sure, the polygon is at the correct position.
		// This way, you can also independently create polygons regardless of the Viewports measurements.
		// Anyway, scaling polygons and other transformations are up to the user still..
		polygon.translate(location.x, location.y);
	}

	public String getTitle()
	{
		return getValue();
	}

	public void setTitle(String title)
	{
		setValue(title);
	}

	public abstract void onClick();

	public abstract void onHover();
	
	@Override
	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;
		
		getStyle().setLength(getValue().length());
	}
}

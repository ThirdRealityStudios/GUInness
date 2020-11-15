package org.thirdreality.guinness.gui.component.standard;

import java.awt.Point;
import java.awt.Polygon;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.optional.GValueManager;
import org.thirdreality.guinness.gui.font.Font;

public class GPolyButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;
	
	// Info: The polygon is closed automatically later..
	public GPolyButton(Point location, String title, Font font, Polygon polygon)
	{
		super("polybutton", location, polygon, font);
		
		// Make sure, the polygon is at the correct position.
		// This way, you can also independently create polygons regardless of the Viewports measurements.
		// Anyway, scaling polygons and other transformations are up to the user still..
		polygon.translate(location.x, location.y);
		
		valueManager = new GValueManager()
		{
			@Override
			public void setValue(String value)
			{
				if(value == null)
				{
					return;
				}

				this.value = value;
				
				setMaxLength(getValue().length());
			}
		};
		
		setTitle(title);
	}
	
	private GValueManager getValueManager()
	{
		return valueManager;
	}

	public String getTitle()
	{
		return getValueManager().getValue();
	}

	public void setTitle(String title)
	{
		getValueManager().setValue(title);
	}
}

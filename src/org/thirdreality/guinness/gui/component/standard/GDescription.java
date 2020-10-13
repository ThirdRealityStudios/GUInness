package org.thirdreality.guinness.gui.component.standard;

import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	public GDescription(Point location, String title, Font font)
	{
		super("description");
		
		setTitle(title);
		getStyle().setLength(title.length());
		
		getStyle().setFont(font);
		
		updateDefaultShape();
		
		getStyle().setLocation(location);
	}

	public String getTitle()
	{
		return getValue();
	}

	public void setTitle(String title)
	{
		setValue(title);
	}

	public void onClick()
	{
		// Does nothing.
	}

	public void onHover()
	{
		// Does nothing.
	}

	public boolean isInteractionEnabled()
	{
		return interaction;
	}

	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;
		
		updateDefaultShape();
	}
}

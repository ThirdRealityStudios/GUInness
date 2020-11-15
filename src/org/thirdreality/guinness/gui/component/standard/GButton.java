package org.thirdreality.guinness.gui.component.standard;

import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.font.Font;

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GButton(Point location, String title, Font font)
	{
		super("button");

		setTitle(title);
		getStyle().setLength(title.length());
		getStyle().setFont(font);
		getStyle().getBorderProperties().setBorderRadiusPx(4);

		// This method is always called after the base values have been set, e.g. font size.
		updateDefaultShape();

		// Is always executed after having set the default shape because it transforms it directly to the given location.
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

	@Override
	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;

		getStyle().setLength(getValue().length());

		updateDefaultShape();
	}
}

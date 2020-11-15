package org.thirdreality.guinness.gui.component.standard;

import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.optional.GValueManager;
import org.thirdreality.guinness.gui.font.Font;

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;
	
	public GButton(Point location, String title, Font font)
	{
		super("button");
		
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

				setMaxLength(value.length());

				updateDefaultShape();
			}
		};

		setTitle(title);
		//setLength(title.length());
		getStyle().setFont(font);
		getStyle().getBorderProperties().setBorderRadiusPx(4);

		// This method is always called after the base values have been set, e.g. font size.
		updateDefaultShape();

		// Is always executed after having set the default shape because it transforms it directly to the given location.
		getStyle().setLocation(location);
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

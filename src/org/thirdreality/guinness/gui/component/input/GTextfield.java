package org.thirdreality.guinness.gui.component.input;

import java.awt.Color;
import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.font.Font;

public class GTextfield extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean active = false;

	private Color clicked;

	public GTextfield(Point location, String title, int maxInput, Font font)
	{
		super("textfield");

		if(maxInput > 0)
		{
			getStyle().setLength(maxInput);
		}
		else
		{
			throw new IllegalArgumentException("Maximum length must be 1 or greater!");
		}
		
		boolean isValidTextfield = title.length() <= getStyle().getLength();

		if(isValidTextfield)
		{
			// Will set the title or text which is contained yet.
			// Will also update the "default shape".
			setValue(title);
			
			// Is always executed after having set the default shape from the setValue(String title)-method above.
			// The setLocation(...)-method is dependent on a look available in order to transform it to the given location.
			getStyle().setLocation(location);
			
			getStyle().setPrimaryColor(Color.WHITE);
			getStyle().getBorderProperties().setBorderRadiusPx(3);

			getStyle().setFont(font);
		}
		else
		{
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
		}
	}

	public void onClick(){}
	
	public void setActive()
	{
		if(getStyle().getPrimaryColor() == null)
		{
			return;
		}
		
		getStyle().setBufferedColor(getStyle().getPrimaryColor());
		
		getStyle().setPrimaryColor(getColorClicked());
		
		active = true;
	}
	
	public void setInactive()
	{
		if(getStyle().getBufferedColor() == null)
		{
			return;
		}
		
		getStyle().setPrimaryColor(getStyle().getBufferedColor());
		
		getStyle().setBufferedColor(null);
		
		active = false;
	}

	public boolean isActive()
	{
		return active;
	}

	public Color getColorClicked()
	{
		return clicked;
	}

	public void setClicked(Color clicked)
	{
		this.clicked = clicked;
	}
	
	@Override
	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;

		// This method is (in general) always called after some base values have changed, e.g. the font size or like here it is the title.
		updateDefaultShape();
	}
}
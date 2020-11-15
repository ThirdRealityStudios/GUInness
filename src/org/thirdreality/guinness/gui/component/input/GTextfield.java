package org.thirdreality.guinness.gui.component.input;

import java.awt.Color;
import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.optional.GValueManager;
import org.thirdreality.guinness.gui.font.Font;

public class GTextfield extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean active = false;

	private Color clicked;
	
	private GValueManager valueManager;

	public GTextfield(Point location, String title, int maxInput, Font font)
	{
		super("textfield");
		
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

				// This method is (in general) always called after some base values have changed, e.g. the font size or like here it is the title.
				updateDefaultShape();
			}
		};

		if(maxInput > 0)
		{
			getValueManager().setMaxLength(maxInput);
		}
		else
		{
			throw new IllegalArgumentException("Maximum length must be 1 or greater!");
		}
		
		boolean isValidTextfield = title.length() <= getValueManager().getMaxLength();

		if(isValidTextfield)
		{
			// Will set the title or text which is contained yet.
			// Will also update the "default shape".
			getValueManager().setValue(title);
			
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
	
	public GValueManager getValueManager()
	{
		return valueManager;
	}

	protected void setActive()
	{
		if(getStyle().getPrimaryColor() == null)
		{
			return;
		}
		
		getStyle().setBufferedColor(getStyle().getPrimaryColor());
		
		getStyle().setPrimaryColor(getColorClicked());
		
		active = true;
	}
	
	protected void setInactive()
	{
		if(getStyle().getBufferedColor() == null)
		{
			return;
		}
		
		getStyle().setPrimaryColor(getStyle().getBufferedColor());
		
		getStyle().setBufferedColor(null);
		
		active = false;
	}

	protected boolean isActive()
	{
		return active;
	}

	protected Color getColorClicked()
	{
		return clicked;
	}

	protected void setClicked(Color clicked)
	{
		this.clicked = clicked;
	}
	
	public String getInputValue()
	{
		return getValueManager().getValue();
	}
}
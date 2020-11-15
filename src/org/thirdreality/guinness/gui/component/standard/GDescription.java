package org.thirdreality.guinness.gui.component.standard;

import java.awt.Point;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.optional.GValueManager;
import org.thirdreality.guinness.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	private GValueManager valueManager;
	
	public GDescription(Point location, String title, Font font)
	{
		super("description");
		
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
				
				updateDefaultShape();
			}
		};
		
		setTitle(title);
		
		getValueManager().setMaxLength(title.length());
		
		getStyle().setFont(font);
		
		updateDefaultShape();
		
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

	public boolean isInteractionEnabled()
	{
		return interaction;
	}
}

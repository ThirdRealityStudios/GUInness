package core.gui.component.standard;

import java.awt.Point;

import core.Meta;
import core.gui.component.GComponent;
import core.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	public GDescription(Point location, String title, Font font)
	{
		super("description", location, null, title.length(), title, font);
		
		updateDefaultShape();
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

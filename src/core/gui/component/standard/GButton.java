package core.gui.component.standard;

import java.awt.Point;

import core.Meta;
import core.gui.component.GComponent;

public abstract class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GButton(Point location, String title, int fontSize, boolean visible)
	{
		super("button", location, null, title.length(), title, fontSize, visible);
		
		// This method is always called after the base values have been set, e.g. font size.
		updateShape();
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

		updateShape();
	}
}

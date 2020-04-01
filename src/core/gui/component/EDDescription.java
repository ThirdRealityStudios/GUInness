package core.gui.component;

import java.awt.Color;
import java.awt.Point;

import core.gui.EDText;

public class EDDescription extends EDText
{
	private boolean interaction = true;
	
	public EDDescription(Point location, String title, int fontSize, Color fontColor, boolean visible)
	{		
		super(title.length(), new Color(0,0,0,0), new Color(0,0,0,0), new Color(0,0,0,0), location, title, fontColor, fontSize, 0, 0, new Color(0,0,0,0), visible);
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
		
	}

	public void onHover()
	{
		
	}

	public boolean isInteractionEnabled()
	{
		return interaction;
	}
}

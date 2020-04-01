package core.gui.component;

import java.awt.Color;
import java.awt.Point;

import core.gui.EDComponent;
import core.gui.EDText;

public abstract class EDTextfield extends EDText
{
	private boolean active = false;

	public EDTextfield(Color background, Color active, Point location, String title, int maxInput, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(-1, background, active, background, location, title, fontColor, fontSize, innerThickness, borderThickness, border, visible);

		if (maxInput > 0)
			setLength(maxInput);
		else
			throw new IllegalArgumentException("Maximum length must be 1 or longer!");

		if (title.length() <= getLength())
			setValue(title);
		else
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
	}

	public void onClick()
	{

	}

	public void setInactive()
	{
		active = false;
	}

	public boolean isActive()
	{
		return active;
	}

	public abstract void onHover();

	public void setActive()
	{
		active = true;
	}
}

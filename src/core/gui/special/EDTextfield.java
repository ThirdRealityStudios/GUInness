package core.gui.special;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import core.gui.component.EDComponent;

public abstract class EDTextfield extends EDComponent
{
	private boolean active = false;

	private Color clicked;

	public EDTextfield(Point location, String title, int maxInput, int fontSize, boolean visible)
	{
		super("textfield", location, null, -1, title, fontSize, visible);
		
		if (maxInput > 0)
			setLength(maxInput);
		else
			throw new IllegalArgumentException("Maximum length must be 1 or longer!");

		if (title.length() <= getLength())
			setValue(title);
		else
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
		
		// This method is always called after the base values have been set, e.g. font size.
		Shape s = getDesign().generateDefaultShape(this);
		setShape(s);
	}

	public void onClick(){}
	
	public void setActive()
	{
		if(getPrimaryColor() == null)
		{
			return;
		}
		
		setBufferedColor(getPrimaryColor());
		
		setPrimaryColor(getColorClicked());
		
		active = true;
	}
	
	public void setInactive()
	{
		if(getBufferedColor() == null)
		{
			return;
		}
		
		setPrimaryColor(getBufferedColor());
		
		setBufferedColor(null);
		
		active = false;
	}

	public boolean isActive()
	{
		return active;
	}

	public abstract void onHover();

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
		
		getDesign().updateDefaultShape(this);
	}
}

package core.gui.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import core.gui.EDText;

public abstract class EDTextfield extends EDText
{
	private boolean active = false;

	public String value = null;

	public String bufferedValue = null;
	
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
	
	public synchronized void write(char key)
	{		
		boolean noSafeCopy = bufferedValue == null;
		
		boolean noOverflow = (getValue().length() + 1) <= getLength();
		
		if(noSafeCopy)
		{
			bufferedValue = getValue();
		}
		else if(noOverflow)
		{
				writeDirectly(key);
		}
	}
	
	private synchronized void writeDirectly(char key)
	{
		if(key != KeyEvent.VK_UNDEFINED)
			setValue(getValue() + key);
	}
	
	public synchronized void save()
	{
		bufferedValue = null;
	}
	
	public synchronized void revert()
	{
		setValue(bufferedValue);
	}
	
	public void setActive()
	{
		if(backgroundColor == null)
		{
			return;
		}
		
		bufferedColor = backgroundColor;
		
		backgroundColor = activeColor;
		
		active = true;
	}
	
	public void setInactive()
	{
		if(bufferedColor == null)
		{
			return;
		}
		
		backgroundColor = bufferedColor;
		
		bufferedColor = null;
		
		active = false;
	}

	public boolean isActive()
	{
		return active;
	}

	public abstract void onHover();
}

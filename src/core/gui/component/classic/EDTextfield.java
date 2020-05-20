package core.gui.component.classic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import core.gui.EDText;
import core.tools.gui.FontLoader;

public abstract class EDTextfield extends EDText
{
	private boolean active = false;

	public String value = null;

	public String bufferedValue = null;
	
	private FontLoader fL;
	
	public EDTextfield(Color background, Color active, Point location, String title, int maxInput, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(-1, background, active, background, location, title, fontColor, fontSize, innerThickness, borderThickness, border, visible);

		fL = new FontLoader();
		
		if (maxInput > 0)
			setLength(maxInput);
		else
			throw new IllegalArgumentException("Maximum length must be 1 or longer!");

		if (title.length() <= getLength())
			setValue(title);
		else
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
	}

	public void onClick(){}
	
	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
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
	
	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseLastChar()
	{
		// Checking whether deleting one more char is still possible due to the length of 'value'.
		if(getValue().length() > 0)
		{
			setBufferedValue(getBufferedValue());
			
			char[] charValues = getValue().toCharArray();
			
			setValue(getValue().valueOf(charValues, 0, charValues.length-1));
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

	public void draw(Graphics g)
	{
		if(isVisible())
		{
			Rectangle bounds = getShape().getBounds();
			
			g.setColor(getBorder());
			g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);
			
			int titleWidth = getFontSize() * getValue().length();

			g.setColor(getBackground());
			g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), getFontSize() + 2 * getInnerThickness());

			fL.display(g, getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), getFontSize(), getFontColor());
		}
	}
}

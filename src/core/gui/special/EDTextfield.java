package core.gui.special;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import core.gui.EDComponent;
import core.gui.design.Design;

public abstract class EDTextfield extends EDComponent
{
	private boolean active = false;

	private Color clicked;

	public EDTextfield(Design design, Point location, String title, int maxInput, int fontSize, boolean visible)
	{
		super(design, "textfield", location, null, -1, title, fontSize, visible);

		// This method is always called after the base values have been set, e.g. font size.
		Shape s = design.generateDefaultShape(this);
		s.getBounds().setLocation(location);
		setShape(s);
		
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
		boolean noSafeCopy = getBufferedValue() == null;

		boolean noOverflow = (getValue().length() + 1) <= getLength();

		if(noSafeCopy)
		{
			setBufferedValue(getValue());
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
		setBufferedValue(null);
	}
	
	public synchronized void revert()
	{
		setValue(getBufferedValue());
	}
	
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

	public void draw(Graphics g)
	{
		if(isVisible())
		{
			
		}
	}

	public Color getColorClicked()
	{
		return clicked;
	}

	public void setClicked(Color clicked)
	{
		this.clicked = clicked;
	}
}

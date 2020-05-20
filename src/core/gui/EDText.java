package core.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class EDText extends EDComponent
{
	private volatile String value = "";

	private volatile String bufferedValue = null;

	private int length;

	private int fontSize, innerThickness, borderThickness;

	protected volatile Color borderColor, backgroundColor, activeColor, hoverColor, bufferedColor, fontColor;

	public EDText(int length, Color background, Color inactive, Color hover, Point location, String value, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(new Rectangle(location), visible);
		
		// Set all important attributes below:
		this.length = length;
		this.backgroundColor = background;
		this.activeColor = inactive;
		this.hoverColor = hover;
		this.value = value;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.innerThickness = innerThickness;
		this.borderThickness = borderThickness;
		this.borderColor = border;
		
		// After knowing all necessary attributes:
		setSize(); // Calculates the correct size of the rectangle for the text component.
	}

	public synchronized String getValue()
	{
		return value;
	}

	public synchronized void setValue(String title)
	{
		if(title == null)
		{
			return;
		}
		
		this.value = title;
		setSize();
	}

	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
		setSize();
	}

	public int getInnerThickness()
	{
		return innerThickness;
	}

	public void setInnerThickness(int innerThickness)
	{
		this.innerThickness = innerThickness;
		setSize();
	}

	public int getBorderThickness()
	{
		return borderThickness;
	}

	public void setBorderThickness(int borderThickness)
	{
		this.borderThickness = borderThickness;
		setSize();
	}

	public Color getBorder()
	{
		return borderColor;
	}

	public void setBorder(Color border)
	{
		this.borderColor = border;
	}

	public Color getBackground()
	{
		return backgroundColor;
	}

	public void setBackground(Color background)
	{
		this.backgroundColor = background;
	}

	public Color getFontColor()
	{
		return fontColor;
	}

	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
		setSize();
	}

	// Calculates the correct size of the rectangle for the text component.
	private void setSize()
	{
		Dimension backgroundSize = new Dimension(length * fontSize + 2 * innerThickness + 2 * borderThickness, fontSize + 2 * innerThickness + 2 * borderThickness);

		Rectangle rect = new Rectangle(backgroundSize);
		rect.setLocation(getShape().getBounds().getLocation());
		setShape(rect);
	}

	public synchronized void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public synchronized String getBufferedValue()
	{
		return bufferedValue;
	}

	public Color getActiveColor()
	{
		return activeColor;
	}

	public void setActiveColor(Color inactive)
	{
		this.activeColor = inactive;
	}
	
	public Color getHoverColor()
	{
		return hoverColor;
	}

	public void setHoverColor(Color hover)
	{
		this.hoverColor = hover;
	}

	public abstract void onClick();

	public abstract void onHover();

	public Color getBufferedColor()
	{
		return bufferedColor;
	}

	public void setBufferedColor(Color bufferedColor)
	{
		this.bufferedColor = bufferedColor;
	}
}

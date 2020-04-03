package core.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class EDText extends EDComponent
{
	private String value;

	private String bufferedValue;

	private int length;

	private int fontSize, innerThickness, borderThickness;

	private Color border, background, active, hover, bufferedColor, fontColor;

	public EDText(int length, Color background, Color inactive, Color hover, Point location, String value, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(new Rectangle(location), visible);
		
		this.length = length;
		this.background = background;
		this.active = inactive;
		this.hover = hover;
		this.value = value;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
		this.innerThickness = innerThickness;
		this.borderThickness = borderThickness;
		this.border = border;
		
		setSize(); // Calculates the correct size of the rectangle for the text component.
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String title)
	{
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
		return border;
	}

	public void setBorder(Color border)
	{
		this.border = border;
	}

	public Color getBackground()
	{
		return background;
	}

	public void setBackground(Color background)
	{
		this.background = background;
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

		getRectangle().setSize(backgroundSize);
	}

	public void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public String getBufferedValue()
	{
		return bufferedValue;
	}

	public Color getActiveColor()
	{
		return active;
	}

	public void setAtiveColor(Color inactive)
	{
		this.active = inactive;
	}
	
	public Color getHoverColor()
	{
		return hover;
	}

	public void setHoverColor(Color hover)
	{
		System.out.println("Changed hover color to: " + hover);
		
		this.hover = hover;
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

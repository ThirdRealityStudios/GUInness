package org.thirdreality.guinness.gui.design;

import java.awt.Color;

public class DesignColor
{
	private Color borderColor, backgroundColor, activeColor, hoverColor, fontColor;

	public DesignColor(Color borderColor, Color backgroundColor, Color activeColor, Color hoverColor, Color fontColor)
	{
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
		this.activeColor = activeColor;
		this.hoverColor = hoverColor;
		this.fontColor = fontColor;
	}

	public Color getBorderColor() 
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor) 
	{
		this.borderColor = borderColor;
	}

	public Color getBackgroundColor() 
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) 
	{
		this.backgroundColor = backgroundColor;
	}

	public Color getActiveColor() 
	{
		return activeColor;
	}

	public void setActiveColor(Color activeColor) 
	{
		this.activeColor = activeColor;
	}
	
	public Color getHoverColor()
	{
		return hoverColor;
	}
	
	public void setHoverColor(Color hoverColor)
	{
		this.hoverColor = hoverColor;
	}

	public Color getFontColor() 
	{
		return fontColor;
	}

	public void setFontColor(Color fontColor) 
	{
		this.fontColor = fontColor;
	}
}

package org.thirdreality.guinness.gui.design;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serializable;

import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.font.FontLoader;

public abstract class Design implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Color borderColor, backgroundColor, activeColor, hoverColor, fontColor;
	
	private int innerThickness, borderThickness;
	
	private FontLoader fL = new FontLoader();
	
	public Design(Color borderColor, Color backgroundColor, Color activeColor, Color hoverColor, Color fontColor, int innerThickness, int borderThickness)
	{
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
		this.activeColor = activeColor;
		this.hoverColor = hoverColor;
		this.fontColor = fontColor;
		this.innerThickness = innerThickness;
		this.borderThickness = borderThickness;
	}

	public int getInnerThickness()
	{
		return innerThickness;
	}

	public void setInnerThickness(int pInnerThickness)
	{
		innerThickness = pInnerThickness;
	}

	public int getBorderThickness()
	{
		return borderThickness;
	}

	public void setBorderThickness(int pBorderThickness)
	{
		borderThickness = pBorderThickness;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color pBorderColor)
	{
		borderColor = pBorderColor;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color pBackground)
	{
		backgroundColor = pBackground;
	}

	public Color getFontColor()
	{
		return fontColor;
	}

	public void setFontColor(Color pFontColor)
	{
		fontColor = pFontColor;
	}
	
	public Color getActiveColor()
	{
		return activeColor;
	}

	public void setActiveColor(Color pActiveColor)
	{
		activeColor = pActiveColor;
	}
	
	public Color getHoverColor()
	{
		return hoverColor;
	}

	public void setHoverColor(Color pHoverColor)
	{
		hoverColor = pHoverColor;
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
	
	public abstract void drawContext(Graphics g, GComponent c, Point offset, float scale);
	
	abstract void drawGeneralField(Graphics g, GComponent c);
	
	public abstract Polygon generateDefaultShape(GComponent c);
	
	public abstract void updateDefaultShape(GComponent c);
}
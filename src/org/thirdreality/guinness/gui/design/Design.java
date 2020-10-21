package org.thirdreality.guinness.gui.design;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.guinness.gui.component.style.property.GPaddingProperty;
import org.thirdreality.guinness.gui.font.FontLoader;

public abstract class Design implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private DesignColor designColor;
	
	private GBorderProperty borderProperty;
	
	private GPaddingProperty paddingProperty;
	
	private FontLoader fL = new FontLoader();
	
	public Design(DesignColor designColor, GBorderProperty borderProperties, GPaddingProperty paddingProperty)
	{
		this.designColor = designColor;
		this.borderProperty = borderProperties;
		this.paddingProperty = paddingProperty;
	}

	public DesignColor getDesignColor()
	{
		return designColor;
	}

	public void setDesignColor(DesignColor designColor)
	{
		this.designColor = designColor;
	}

	public GBorderProperty getBorderProperty()
	{
		return borderProperty;
	}

	public void setBorderProperty(GBorderProperty borderProperty)
	{
		this.borderProperty = borderProperty;
	}

	public GPaddingProperty getPaddingProperty()
	{
		return paddingProperty;
	}

	public void setPaddingProperty(GPaddingProperty paddingProperty)
	{
		this.paddingProperty = paddingProperty;
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
	
	public abstract void drawContext(Graphics g, GComponent c, Point origin, Point offset, float scale);
	
	public abstract Polygon generateDefaultShape(GComponent c);
	
	public abstract void updateDefaultShape(GComponent c);
}
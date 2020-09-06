package core.gui.component;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;

import core.Meta;
import core.gui.design.Design;
import core.gui.font.Font;

public class GStyle implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Will tell the render method how to render this component.
	private Design design;
	
	// Contains the shape of the component.
	protected Shape shape;

	// Tells whether the context or component is visible or not.
	// If 'null', a value will be automatically assigned later.
	// Having 'null' in the beginning only helps the program to know
	// if a value was assigned already.
	// Having this possibility, it prevents already set values to be overwritten when
	// adding new components to a layer.
	// A layer would otherwise just overwrite the already set value with default values.
	private Boolean visible = null;
	
	/*
	 * WARNING! This property might not be supported for all components.
	 * If not supported, it is anyway mostly the case that the upper-left corner is taken by default..
	 * 
	 * 0 = normal (text or title will  appear at the upper-left corner of the component).
	 * 1 = center (appears in the center of the component.
	 */
	private int textAlign = 0;
	
	// If you want the actual text to appear somewhere else than in the center or upper-left corner,
	// you can tell the program to translate or reposition the text by any x and y value.
	// The translated text is then not recognized later as a component itself.
	// Translating your text is because of that just for pure decoration purposes.
	private Point textTransition = new Point();

	private int length = 0;
	
	private Font font;
	
	private int paddingTop = 0, paddingBottom = 0;
	
	private float opacity = 1f;
	
	// For specific component types, such as GRectangle, you can define a border-radius (just like in CSS for HTML).
	private int borderRadiusPx = 0;

	private Color primaryColor = null, bufferedColor = null;

	private Point location = new Point();

	// Just contains an image in case it is wanted.
	// If you want the GComponent to be rendered as an image,
	// you need to clarify it in the variable "type" above (String value needs to be
	// "image" then).
	private Image img;

	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design d)
	{
		this.design = d;
	}

	public Shape getShape()
	{
		return shape;
	}

	public void setShape(Shape shape)
	{
		this.shape = shape;
	}

	public Boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getPrimaryColor()
	{
		return primaryColor;
	}

	public void setPrimaryColor(Color primaryColor)
	{
		this.primaryColor = primaryColor;
	}

	public Color getBufferedColor()
	{
		return bufferedColor;
	}

	public void setBufferedColor(Color bufferedColor)
	{
		this.bufferedColor = bufferedColor;
	}

	public Point getLocation()
	{
		return location;
	}

	public void setLocation(Point location)
	{
		this.location = location;
	}

	public Image getImage()
	{
		return img;
	}

	public void setImage(Image img)
	{
		this.img = img;
	}

	public int getPaddingTop()
	{
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop)
	{
		this.paddingTop = paddingTop;
	}

	public int getPaddingBottom()
	{
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom)
	{
		this.paddingBottom = paddingBottom;
	}

	public float getOpacity()
	{
		return opacity;
	}

	// Applies the given opacity to the primary color.
	public void setOpacity(float opacity)
	{
		if(opacity <= 1 && opacity >= 0)
		{
			int maxRGB = 255;
			
			float r = (float) getPrimaryColor().getRed() / maxRGB;
			float g = (float) getPrimaryColor().getGreen() / maxRGB;
			float b = (float) getPrimaryColor().getBlue() / maxRGB;
			
			Color rgba = new Color(r, g, b, opacity);
			
			setPrimaryColor(rgba);
			
			this.opacity = opacity;
		}
	}

	public int getBorderRadiusPx()
	{
		return borderRadiusPx;
	}

	public void setBorderRadiusPx(int borderRadiusPx)
	{
		this.borderRadiusPx = borderRadiusPx;
	}

	public int getTextAlign()
	{
		return textAlign;
	}

	public void setTextAlign(int textAlign)
	{
		this.textAlign = textAlign;
	}

	public Point getTextAlignTransition()
	{
		return textTransition;
	}

	public void setTextTransition(Point textAlignTransition)
	{
		this.textTransition = textAlignTransition;
	}
}
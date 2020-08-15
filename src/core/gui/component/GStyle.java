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
	private Shape shape;

	private boolean visible = true;

	private int length;
	
	private Font font;
	
	private int paddingTop, paddingBottom;

	private Color primaryColor = null, bufferedColor = null;

	private Point location;

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

	public boolean isVisible()
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
}
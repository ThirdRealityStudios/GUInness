package core.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

import core.gui.design.Design;

public abstract class EDComponent
{
	// Will tell the render method how to render this component.
	private Design design;
	
	// Determines the type of the EDComponent, e.g. image, path or default.
	// This will determine the render method later.
	private String type;
	
	private volatile boolean interaction = true, isHovered = false, actionOnClick = true, actionOnHover = true;

	// Contains the shape of the component.
	private Shape shape;

	private boolean visible = true;

	private volatile String value = "";

	private volatile String bufferedValue = null;

	private int length;

	private int fontSize;

	private Color primaryColor = null, bufferedColor = null;

	private Point location;

	// Just contains an image in case it is wanted.
	// If you want the EDComponent to be rendered as an image,
	// you need to clarify it in the variable "type" above (String value needs to be "image" then).
	private Image img;

	public EDComponent(Design design, String type, Point location, Shape shape, int length, String val, int fontSize, boolean visible)
	{
		setDesign(design);
		setPrimaryColor(design.getBackgroundColor());
		
		setType(type);
		setLocation(location);
		setShape(shape);
		
		setFontSize(fontSize);
		
		setVisible(visible);
		
		// Set all important attributes below:
		setLength(length);
		setValue(val);
		
		// Checks whether it needs to adjust the design values for the current type.
		if(getType().contentEquals("default"))
		{
			// After knowing all necessary attributes:
			design.updateDefaultShape(this); // Calculates the correct size of the rectangle for an EDComponent of type "default". Will not apply to "image" or "path".
		}
	}
	
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	// Returns whether the component contains an image.
	// Could affect the way the component is rendered.
	public boolean containsImage()
	{
		return img != null;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public Shape getShape()
	{
		return shape;
	}
	
	public void setShape(Shape shape)
	{
		this.shape = shape;
	}
	
	// Part of "EDText"
	
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
		design.updateDefaultShape(this);
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public synchronized void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public synchronized String getBufferedValue()
	{
		return bufferedValue;
	}
	
	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	public void setBufferedColor(Color pBufferedColor)
	{
		this.bufferedColor = pBufferedColor;
	}
	
	public Color getBufferedColor()
	{
		return this.bufferedColor;
	}
	
	public void setPrimaryColor(Color pPrimaryColor)
	{
		this.primaryColor = pPrimaryColor;
	}
	
	public Color getPrimaryColor()
	{
		return this.primaryColor;
	}
	
	public Point getLocation()
	{
		return location;
	}

	public void setLocation(Point location)
	{
		this.location = location;
	}
	
	public boolean isInteractionEnabled()
	{
		return interaction;
	}

	public synchronized void setInteraction(boolean interaction)
	{
		this.interaction = interaction;
	}

	public synchronized boolean isHovered()
	{
		return isHovered;
	}

	protected void setHovered(boolean isHovered)
	{
		this.isHovered = isHovered;
	}

	public boolean actsOnClick()
	{
		return actionOnClick;
	}

	public void actsOnClick(boolean actsOnHover)
	{
		this.actionOnClick = actsOnHover;
	}

	public boolean actsOnHover()
	{
		return actionOnHover;
	}

	public void actsOnHover(boolean actsOnHover)
	{
		this.actionOnHover = actsOnHover;
	}

	public abstract void onClick();

	public abstract void onHover();

	public String getType()
	{
		return type;
	}

	private void setType(String type)
	{
		this.type = type;
	}

	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design d)
	{
		this.design = d;
	}
	
	public void print()
	{
		System.out.println();
		System.out.println(this);
	}
	
	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType() + "\"):\ndesign = " + design.getClass().getSimpleName() + "\nshape = " + shape + "\nlength = " + length + "\nvalue = \"" + value + "\"\nfontSize = " + fontSize + "\nvisible = " + visible;
	}
}

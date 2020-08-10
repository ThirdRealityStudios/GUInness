package core.gui.component;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;

import core.Meta;
import core.gui.Display;
import core.gui.design.Design;
import core.gui.design.Sample;

public abstract class GComponent implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	// Will tell the render method how to render this component.
	private Design design;

	// Determines the type of the GComponent, e.g. image, path or default.
	// This will determine the render method later.
	private String type;

	private volatile boolean interaction = true, actionOnClick = true, actionOnHover = true;

	// "Realtime execution" means that if you interact with an GComponent it will
	// execute as fast as it can the corresponding method,
	// e.g. you click a button for one second which means the onClick() method is
	// executed as often as possible depending the computer.
	// If disabled the corresponding method is called only once in a time which
	// prevents too much execution and load.
	// Anyway, you can define a component specific delay below before the method may
	// be called again (which is actually not real-time anymore but yeah, you know
	// what I mean..).
	private volatile boolean realtimeExecution = false;

	// This means, in best case scenario you are able to interact with the component
	// 4 times a second or 4 Hz.
	// How you can interact with it depends of course on the type of the component.
	// Not all components support this feature, e.g. text-fields.
	// Also, from this point the delay is currently only compatible with buttons and
	// similar components which will probably follow in future.
	private int delayMs = 0;
	
	// Decide whether it is allowed to run the same method multiple times when the component is clicked a specific time..
	// Is not compatible with all GComponents.
	private boolean doubleClickingAllowed = false;

	// Contains the shape of the component.
	private Shape shape;

	private boolean visible = true;

	protected volatile String value = "";

	private volatile String bufferedValue = null;

	private int length;

	private int fontSize;

	private Color primaryColor = null, bufferedColor = null;

	private Point location;

	// Just contains an image in case it is wanted.
	// If you want the GComponent to be rendered as an image,
	// you need to clarify it in the variable "type" above (String value needs to be
	// "image" then).
	private Image img;

	// The main reference to all major functions of this whole program.
	private Display display;

	public GComponent(String type, Point location, Shape shape, int length, String val,
			int fontSize, boolean visible)
	{		
		setType(type);
		setLocation(location);

		// When created apply the default design first.
		this.setDesign(Sample.classic);
 
		setPrimaryColor(getDesign().getBackgroundColor());
		setShape(shape);

		setFontSize(fontSize);

		setVisible(visible);

		// Set all important attributes below:
		setLength(length);
		setValue(val);
	}

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getLength();

		if(noOverflow)
		{
			setValue(getValue() + key);
		}
	}

	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseLastChar()
	{
		// Checking whether deleting one more char is still possible due to the length
		// of 'value'.
		if(getValue().length() > 0)
		{
			setBufferedValue(getBufferedValue());

			char[] charValues = getValue().toCharArray();

			setValue(getValue().valueOf(charValues, 0, charValues.length - 1));
		}
	}
	
	// Tells you whether the cursor of 'value' is at the beginning,
	// meaning 'value' is empty.
	public boolean isCursorAtBeginning()
	{
		return getValue().length() == 0;
	}
	
	// Tells you whether the cursor of 'value' is at the end,
	// meaning 'value' is full.
	public boolean isCursorAtEnd()
	{
		return (getValue().length() + 1) > getLength();
	}

	public synchronized void revert()
	{
		setValue(getBufferedValue());
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

	public synchronized String getValue()
	{
		return value;
	}

	// The implementation depends on the type,
	// e.g. a text-field is treated differently than an image.
	public abstract void setValue(String val);

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
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType()
				+ "\"):\ndesign = " + getDesign().getClass().getSimpleName() + "\nshape = " + shape + "\nlength = "
				+ length + "\nvalue = \"" + value + "\"\nfontSize = " + fontSize + "\nvisible = " + visible;
	}

	public Display getDisplay()
	{
		return display;
	}

	public void setDisplay(Display display)
	{
		this.display = display;
	}

	public boolean isRealtimeExecutionOn()
	{
		return realtimeExecution;
	}

	public void setRealtimeExecution(boolean realtimeExecution)
	{
		this.realtimeExecution = realtimeExecution;
	}

	public int getDelayMilliseconds()
	{
		return delayMs;
	}

	public void setDelayMilliseconds(int delayMs)
	{
		this.delayMs = delayMs;
	}

	// Decide whether it is allowed to run the same method multiple times when the button is pressed..
	public void setDoubleClickingAllowed(boolean allowed)
	{
		this.doubleClickingAllowed = allowed;
	}

	public boolean isDoubleClickingAllowed()
	{
		return doubleClickingAllowed;
	}

	// Updates the shape if possible,
	// meaning if a design available already.
	// Otherwise the component needs to be updated internally with one.
	protected void updateShape()
	{
		if(getDesign() != null)
		{
			getDesign().updateDefaultShape(this);
		}
	}
}
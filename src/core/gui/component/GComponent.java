package core.gui.component;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.io.Serializable;

import core.Meta;
import core.feature.Path;
import core.feature.shape.ShapeTransform;
import core.gui.Display;
import core.gui.design.Sample;
import core.gui.font.Font;

public abstract class GComponent implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Determines the type of the GComponent, e.g. image, path or default.
	// This will determine the render method later.
	private String type;
	
	/* Determines whether the component should be enabled or not.
	 * If it's disabled, it is not just invisible but also you cannot interact with it anymore.
	 * If 'null', a value will be automatically assigned later.
	 * Having 'null' in the beginning only helps the program to know
	 * if a value was assigned already.
	 * Having this possibility, it prevents already set values to be overwritten when
	 * adding new components to a layer.
	 * A layer would otherwise just overwrite the already set value with default values.
	 */
	private Boolean enabled = null;
	
	private boolean scalable = true;
	
	// Relates to the offset.
	private boolean movable = true;

	protected volatile String value = "";

	private volatile String bufferedValue = null;

	// The main reference to all major functions of this whole program.
	private Display display;
	
	private GStyle style;
	
	private GLogic logic;

	public GComponent(String type, Point location)
	{
		style = new GStyle();
		
		logic = new GLogic();
		
		setType(type);
		getStyle().setLocation(location);
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONT + File.separator + "StandardFont.png", 18));

		// When created apply the default design first.
		this.getStyle().setDesign(Sample.classic);
 
		getStyle().setPrimaryColor(getStyle().getDesign().getBackgroundColor());
	}
	
	public GComponent(String type, Point location, Shape shape, int length, String val, Font font)
	{
		this(type, location);
		
		getStyle().setShape(shape);
		getStyle().setFont(font);

		// Set all important attributes below:
		getStyle().setLength(length);
		setValue(val);
	}
	
	public GComponent(String type, Shape shape, int length, String val, Font font)
	{
		this(type, shape.getBounds().getLocation(), shape, length, val, font);
	}
	
	public GComponent(String type, Point location, Shape shape, String val, Font font)
	{
		this(type, location);

		getStyle().setShape(shape);
		getStyle().setFont(font);

		// Set all important attributes below:
		getStyle().setLength(val.length());
		setValue(val);
	}

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getStyle().getLength();

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
		return (getValue().length() + 1) > getStyle().getLength();
	}

	public synchronized void revert()
	{
		setValue(getBufferedValue());
	}

	public synchronized String getValue()
	{
		return value;
	}

	// The implementation depends on the type,
	// e.g. a text-field is treated differently than an image.
	public abstract void setValue(String val);

	public synchronized void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public synchronized String getBufferedValue()
	{
		return bufferedValue;
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

	public void print()
	{
		System.out.println();
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType()
				+ "\"):\ndesign = " + getStyle().getDesign().getClass().getSimpleName() + "\nshape = " + getStyle().getShape() + "\nlength = "
				+ getStyle().getLength() + "\nvalue = \"" + value + "\"\nfontSize = " + getStyle().getFont().getFontSize() + "\nvisible = " + getStyle().isVisible();
	}

	public Display getDisplay()
	{
		return display;
	}

	public void setDisplay(Display display)
	{
		this.display = display;
	}
	
	
	public Boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public GStyle getStyle()
	{
		return style;
	}
	
	public void setStyle(GStyle style)
	{
		this.style = style;
	}
	
	public GLogic getLogic()
	{
		return logic;
	}
	
	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}
	
	// Updates the shape if possible,
	// meaning if a design available already.
	// Otherwise the component needs to be updated internally with one.
	protected void updateDefaultShape()
	{
		if(getStyle().getDesign() != null)
		{
			getStyle().getDesign().updateDefaultShape(this);
		}
	}

	public boolean isScalable() {
		return scalable;
	}

	public void setScalable(boolean scalable) {
		this.scalable = scalable;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
}
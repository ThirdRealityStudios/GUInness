package org.thirdreality.guinness.gui.component;

import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.Serializable;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.feature.Path;
import org.thirdreality.guinness.feature.shape.ShapeTransform;
import org.thirdreality.guinness.gui.Display;
import org.thirdreality.guinness.gui.component.optional.GActionListener;
import org.thirdreality.guinness.gui.component.style.GStyle;
import org.thirdreality.guinness.gui.design.Sample;
import org.thirdreality.guinness.gui.font.Font;

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

	// The main reference to all major functions of this whole program.
	private Display display;
	
	private GStyle style;
	
	private GLogic logic;
	
	// This contains the onClick() and onHover() methods to be run on this component.
	private GActionListener actions;

	public GComponent(String type)
	{
		style = new GStyle()
		{
			@Override
			public void setLocation(Point location)
			{
				this.location = location;
				
				setPrimaryLook(ShapeTransform.movePolygonTo(getPrimaryLook(), location));
			}
		};
		
		logic = new GLogic();
		
		setType(type);
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONT_FOLDER + File.separator + "StandardFont.png", 18));

		// When created apply the default design first.
		this.getStyle().setDesign(Sample.classic);
 
		getStyle().setPrimaryColor(getStyle().getDesign().getDesignColor().getBackgroundColor());
	}
	
	public GComponent(String type, Point location, Polygon look, Font font)
	{
		this(type);
		
		getStyle().setPrimaryLook(look);
		
		// Is always executed after having set the look because it transforms the shape directly to the given location.
		getStyle().setLocation(location);
		
		getStyle().setFont(font);
	}
	
	public GComponent(String type, Polygon look, Font font)
	{
		this(type, new Point(look.getBounds().getLocation()), look, font);
	}
	
	public GComponent(String type, Point location, Polygon look, String val, Font font)
	{
		this(type);

		getStyle().setPrimaryLook(look);
		
		// Is always executed after having set the look because it transforms the shape directly to the given location.
		getStyle().setLocation(location);
		
		getStyle().setFont(font);

		// Set all important attributes below:
		// getStyle().setLength(val.length());
		// setValue(val);
	}

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
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType()
				+ "\"):\ndesign = " + getStyle().getDesign().getClass().getSimpleName() + "\nshape = " + getStyle().getPrimaryLook() + "\nlength = "
				+ "\nvalue = \"" + "\nvisible = " + getStyle().isVisible();
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

	public void setActionListener(GActionListener actions)
	{
		this.actions = actions;
	}
	
	public GActionListener getActionListener()
	{
		return actions;
	}
	
	public boolean hasActionListener()
	{
		return actions != null;
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
}
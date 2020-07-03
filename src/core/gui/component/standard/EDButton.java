package core.gui.component.standard;

import java.awt.Point;
import java.awt.Shape;

import core.gui.EDComponent;
import core.gui.design.Design;

public abstract class EDButton extends EDComponent
{	
	public EDButton(Design design, Point location, String title, int fontSize, boolean visible)
	{
		super(design, "button", location, null, title.length(), title, fontSize, visible);
		
		// This method is always called after the base values have been set, e.g. font size.
		Shape s = design.generateDefaultShape(this);
		setShape(s);
		
		System.out.println(this);
	}

	public String getTitle()
	{
		return getValue();
	}

	public void setTitle(String title)
	{
		setValue(title);
	}

	public abstract void onClick();

	public abstract void onHover();
}

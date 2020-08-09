package core.gui.component.standard;

import java.awt.Point;
import java.awt.Shape;

import core.gui.Display;
import core.gui.component.EDComponent;
import core.gui.design.Design;

public abstract class EDButton extends EDComponent
{	
	public EDButton(Display display, Point location, String title, int fontSize, boolean visible)
	{
		super(display, "button", location, null, title.length(), title, fontSize, visible);
		
		// This method is always called after the base values have been set, e.g. font size.
		Shape s = getDesign().generateDefaultShape(this);
		setShape(s);
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

package core.gui.component.standard;

import java.awt.Point;
import java.awt.Rectangle;

import core.frame.LayeredRenderFrame;
import core.gui.EDComponent;
import core.gui.design.Design;

public class EDDescription extends EDComponent
{
	private boolean interaction = true;
	
	public EDDescription(LayeredRenderFrame rF, Point location, String title, int fontSize, boolean visible)
	{
		super(rF, "description", location, null, title.length(), title, fontSize, visible);
		
		Rectangle rect = getDesign().generateDefaultShape(this);
		setShape(rect);
	}

	public String getTitle()
	{
		return getValue();
	}

	public void setTitle(String title)
	{
		setValue(title);
	}

	public void onClick()
	{
		// Does nothing.
	}

	public void onHover()
	{
		// Does nothing.
	}

	public boolean isInteractionEnabled()
	{
		return interaction;
	}
}

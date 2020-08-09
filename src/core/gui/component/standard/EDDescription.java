package core.gui.component.standard;

import java.awt.Point;
import java.awt.Rectangle;

import core.gui.Display;
import core.gui.component.EDComponent;
import core.gui.design.Design;

public class EDDescription extends EDComponent
{
	private boolean interaction = true;
	
	public EDDescription(Point location, String title, int fontSize, boolean visible)
	{
		super("description", location, null, title.length(), title, fontSize, visible);
		
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

	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;
		
		// Should not be applied to images.. !
		getDesign().updateDefaultShape(this);
	}
}

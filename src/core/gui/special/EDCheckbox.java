package core.gui.special;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import core.frame.LayeredRenderFrame;
import core.gui.EDComponent;

public class EDCheckbox extends EDComponent
{
	public EDCheckbox(LayeredRenderFrame rF, String type, Point location, Shape shape, int length, String val, int fontSize, boolean visible)
	{
		super(rF, "checkbox", location, new Rectangle(new Point(location), new Dimension(50,50)), 0, "", 0, visible);
		
		// The 'value' attribute is used as the marker, meaning if 'value' is null the checkbox is unchecked and otherwise true.
		
	}

	@Override
	public void onClick()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover()
	{
		// TODO Auto-generated method stub
		
	}

}

package core.gui.special;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import core.Meta;
import core.feature.Path;
import core.feature.image.ImageToolkit;
import core.gui.component.GComponent;
import core.gui.font.Font;

public class GCheckbox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	public GCheckbox(String type, Point location, Shape shape, int length, String val, Font font, boolean visible)
	{
		super("checkbox", location, new Rectangle(new Point(location), new Dimension(50,50)), 0, "", null, visible);
		
		getStyle().setImage(ImageToolkit.loadImage(Path.CORE_PATH));
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

	@Deprecated
	@Override
	// This method is used to set the value (true (!= null) or false (null)) for the check-box.
	// rather use the method below as it saves more memory with a boolean parameter and thus is more efficient.
	public void setValue(String val)
	{
		value = (val != null) ? "" : null;
	}
	
	public boolean isUnchecked()
	{
		return getValue() == null;
	}
	
	public boolean isChecked()
	{
		return !isUnchecked();
	}
	
	// The 'value' attribute is changed here, meaning if 'value' is null then the checkbox is unchecked and otherwise true.
	public void setChecked(boolean checked)
	{
		value = checked ? "" : null;
	}
}

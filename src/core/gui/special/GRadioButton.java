package core.gui.special;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import core.gui.component.GComponent;
import core.gui.font.Font;

public abstract class GRadioButton extends GComponent
{
	// The 'isDefault' value tells whether this radio-button is set as the first option to be active (!) in a radio button box / list (GSelectionBox).
	private boolean isDefault = false;
	
	public GRadioButton(Point location, String val, Font font, boolean visible)
	{
		super("radiobutton", location, new Rectangle(new Dimension(font.getFontSize() + val.length() * font.getFontSize(), font.getFontSize())), val.length(), val, font, visible);
	
		getStyle().setPaddingTop(4);
		getStyle().setPaddingBottom(4);
	}

	@Deprecated
	@Override
	// This method is used to set the value (true (!= null) or false (null)) for the radio-button.
	// rather use the method below as it saves more memory with a boolean parameter and thus is more efficient.
	public void setValue(String val)
	{
		setChecked((val != null));
	}
	
	public boolean isUnchecked()
	{
		return getValue() == null;
	}
	
	public boolean isChecked()
	{
		return !isUnchecked();
	}
	
	// The 'value' attribute is changed here, meaning if 'value' is null then the radio-button is unchecked and otherwise true.
	public void setChecked(boolean checked)
	{
		value = checked ? "" : null;
	}
	
	public boolean isDefaultRadioButton()
	{
		return isDefault;
	}

	public void setDefaultRadioButton(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	@Override
	public abstract void onClick();

	@Override
	public abstract void onHover();

}

package core.gui.special.selection;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import core.feature.Path;
import core.feature.image.ImageToolkit;
import core.gui.component.GComponent;
import core.gui.font.Font;

public class GRadioButton extends GComponent
{
	// The 'isDefault' value tells whether this radio-button is set as the first option to be active (!) in a radio button box / list (GSelectionBox).
	private boolean isDefault = false;
	
	public GRadioButton(Point location, String val, Font font)
	{
		super("radiobutton", location, new Rectangle(new Dimension(font.getFontSize() + val.length() * font.getFontSize(), font.getFontSize())), val.length(), val, font);

		// By default, a "space" beneath and above the radio button is placed,
		// so it has a larger clicking area and to keep more distance (#Corona) from other radio buttons in a GSelectionBox.
		// The unit is in pixels.
		getStyle().setPaddingTop(4);
		getStyle().setPaddingBottom(4);
		
		getStyle().setImage(ImageToolkit.loadImage(Path.GUI_PATH + "\\special\\image\\check_sign.png"));
		
		// Decreased size of the loaded symbol, so the radio button is actually a little smaller because of its drawn borders.
		// Otherwise, borders would be drawn on the symbol which is not what you want actually.
		int size_scaled = getStyle().getShape().getBounds().width - 4*getStyle().getDesign().getBorderThickness();
		
		getStyle().setImage(getStyle().getImage().getScaledInstance(size_scaled, size_scaled, Image.SCALE_SMOOTH));
	}
	
	public GRadioButton(Point location, String val, Font font, boolean visible)
	{
		this(location, val, font);
		
		getStyle().setVisible(visible);
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
	public void onClick()
	{
		// Unused so far..
	}

	@Override
	public void onHover()
	{
		// Unused so far..
	}

}
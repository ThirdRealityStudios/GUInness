package org.thirdreality.guinness.gui.component.selection;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.feature.Path;
import org.thirdreality.guinness.feature.image.ImageToolkit;
import org.thirdreality.guinness.feature.shape.ShapeMaker;
import org.thirdreality.guinness.gui.component.GComponent;

public abstract class GCheckbox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	public GCheckbox(Point location, boolean checked)
	{
		super("checkbox", location, ShapeMaker.createRectangle(location, new Dimension(20, 20)), 0, null, null);
		
		init(checked);
	}

	public GCheckbox(Point location, boolean checked, int sizePx)
	{
		super("checkbox", location, ShapeMaker.createRectangle(location, new Dimension(sizePx, sizePx)), 0, null, null);
		
		init(checked);
	}

	// Just some values to be set which are equally set in both constructors.
	private void init(boolean checked)
	{
		setChecked(checked);
		
		getStyle().setImage(ImageToolkit.loadImage(Path.ICON + File.separator + "check_sign.png"));
		
		int size_scaled = getStyle().getPrimaryLook().getBounds().width - 4*getStyle().getDesign().getBorderThickness();
		
		getStyle().setImage(getStyle().getImage().getScaledInstance(size_scaled, size_scaled, Image.SCALE_SMOOTH));
	}
	
	@Override
	public abstract void onClick();

	@Override
	public abstract void onHover();

	@Deprecated
	@Override
	// This method is used to set the value (true (!= null) or false (null)) for the check-box.
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
	
	// The 'value' attribute is changed here, meaning if 'value' is null then the checkbox is unchecked and otherwise true.
	public void setChecked(boolean checked)
	{
		value = checked ? "" : null;
	}
}

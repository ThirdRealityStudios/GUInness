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

public class GCheckbox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean checked;

	public GCheckbox(Point location, boolean checked)
	{
		super("checkbox", location, ShapeMaker.createRectangle(location, new Dimension(20, 20)), null);
		
		init(checked);
	}

	public GCheckbox(Point location, boolean checked, int sizePx)
	{
		super("checkbox", location, ShapeMaker.createRectangle(location, new Dimension(sizePx, sizePx)), null);
		
		init(checked);
	}

	// Just some values to be set which are equally set in both constructors.
	private void init(boolean checked)
	{
		setChecked(checked);
		
		getStyle().setImage(ImageToolkit.loadImage(Path.ICON_FOLDER + File.separator + "check_sign.png"));
		
		int size_scaled = getStyle().getPrimaryLook().getBounds().width - 4*getStyle().getDesign().getBorderProperty().getBorderThicknessPx();
		
		getStyle().setImage(getStyle().getImage().getScaledInstance(size_scaled, size_scaled, Image.SCALE_SMOOTH));
	}
	
	public boolean isChecked()
	{
		return checked;
	}
	
	// Sets the check-box checked or unchecked (false).
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
}

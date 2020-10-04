package org.thirdreality.guinness.gui.component.placeholder.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.feature.shape.ShapeMaker;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.decoration.GImage;
import org.thirdreality.guinness.gui.component.style.property.GBorder;

public abstract class GWindowButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GImage icon;

	public GWindowButton(Rectangle rect, Color background, float opacity, GBorder borderProperties, GImage icon)
	{
		super("window_button");

		getStyle().setBorderProperties(borderProperties);

		getStyle().setPrimaryLook(ShapeMaker.createRectangleFrom(new Rectangle(rect.getSize()), getStyle().getBorderProperties()));
		getStyle().setLocation(rect.getLocation());

		getStyle().setPrimaryColor(background);
		getStyle().setOpacity(opacity);

		this.icon = icon;
	}
	
	public GImage getIcon()
	{
		return icon;
	}
	
	@Override
	public void setValue(String val){}
	
	// People can define here what happens when clicking a window button..
	public abstract void onClick();
	
	// Nothing happens here..
	public void onHover(){}
}

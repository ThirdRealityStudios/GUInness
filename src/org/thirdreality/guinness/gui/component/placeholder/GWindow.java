package org.thirdreality.guinness.gui.component.placeholder;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import org.thirdreality.guinness.feature.Path;
import org.thirdreality.guinness.feature.shape.ShapeMaker;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.GLogic;
import org.thirdreality.guinness.gui.component.GStyle;
import org.thirdreality.guinness.gui.font.Font;

public class GWindow extends GComponent
{
	private GStyle style;

	private GLogic logic;

	private String value;

	private ArrayList<GComponent> components;

	public GWindow(String title, Font titleFont, Point location, Dimension size, int borderRadiusPx, int borderThicknessPx, ArrayList<GComponent> components)
	{
		super("window");

		this.components = components;
		
		setTitle(title);

		setStyle(new GStyle());
		setLogic(new GLogic());

		getStyle().setPrimaryLook(ShapeMaker.createRectangleFrom(location.x, location.y, size.width, size.height, borderRadiusPx));
		
		int titleAreaHeightPx = titleFont.getFontSize() * 2;
		
		getStyle().setSecondaryLook(ShapeMaker.createRectangleFrom(location.x + borderRadiusPx, location.y + borderRadiusPx + titleAreaHeightPx, size.width - 2 * borderRadiusPx, size.height - 2 * borderRadiusPx - titleAreaHeightPx, borderRadiusPx));

		getStyle().setLocation(location);
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONT_FOLDER + File.separator + "StandardFont.png", 18));
	}

	public void setComponents(ArrayList<GComponent> components)
	{
		this.components = components;
	}

	public void setTitle(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	// Prefer setTitle(...) instead!
	@Deprecated
	@Override
	public void setValue(String val)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick()
	{
		System.out.println("asdasdasd!");
	}

	@Override
	public void onHover()
	{
		System.out.println("Hover babe!");
	}
}

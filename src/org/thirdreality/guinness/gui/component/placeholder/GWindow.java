package org.thirdreality.guinness.gui.component.placeholder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.thirdreality.guinness.feature.Path;
import org.thirdreality.guinness.feature.shape.ShapeMaker;
import org.thirdreality.guinness.feature.shape.ShapeTransform;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.GLogic;
import org.thirdreality.guinness.gui.component.placeholder.window.GWindowButton;
import org.thirdreality.guinness.gui.component.style.GStyle;
import org.thirdreality.guinness.gui.component.style.property.GBorder;
import org.thirdreality.guinness.gui.font.Font;

public abstract class GWindow extends GComponent
{
	private GStyle style;

	private GLogic logic;

	private String title;

	private ArrayList<GComponent> components;

	private GWindowButton minimizeButton, exitButton;

	public GWindow(String title, Font titleFont, Rectangle window, GBorder borderProperties, ArrayList<GComponent> components)
	{
		super("window");

		this.components = components;

		initStyle(window, borderProperties);

		setTitle(title);

		setLogic(new GLogic());

		// Here, the buttons are created, e.g. exit and minimize buttons for the window.
		createWindowButtons(window, borderProperties);
	}

	private void initStyle(Rectangle window, GBorder borderProperties)
	{
		setStyle(new GStyle()
		{
			@Override
			public void setLocation(Point location)
			{
				this.location = location;

				setPrimaryLook(ShapeTransform.movePolygonTo(getPrimaryLook(), location));

				updateWindowButtons(window);
			}
		});

		getStyle().setBorderProperties(borderProperties);

		getStyle().setPrimaryLook(ShapeMaker.createRectangleFrom(window, getStyle().getBorderProperties()));

		// The font which is used for the window title.
		getStyle().setFont(new Font("GWindow.title", Path.FONT_FOLDER + File.separator + "StandardFont.png", 17));

		int titleAreaHeightPx = getStyle().getFont().getFontSize() * 2;

		Point movedInnerArea = new Point(window.x + borderProperties.getBorderThicknessPx(), window.y + borderProperties.getBorderThicknessPx() + titleAreaHeightPx);
		Dimension sizeInnerArea = new Dimension(window.width - 2 * borderProperties.getBorderThicknessPx(), window.height - 2 * borderProperties.getBorderThicknessPx() - titleAreaHeightPx);
		
		Rectangle innerArea = new Rectangle(movedInnerArea, sizeInnerArea);
		
		GBorder innerAreaBorders = new GBorder(10);
		
		getStyle().setSecondaryLook(ShapeMaker.createRectangleFrom(innerArea, innerAreaBorders));
	}
	
	// Updates the window buttons when something has changed, e.g. the location of the GWindow or its dimensions (re-sized or scaled).
	private void updateWindowButtons(Rectangle window)
	{
		Point exitLocation = new Point(window.x + window.width - exitButton.getStyle().getPrimaryLook().getBounds().width, window.y);
		
		exitButton.getStyle().setPrimaryLook(exitButton.getStyle().getPrimaryLook());
		exitButton.getStyle().setLocation(exitLocation);
		
		Point minimizeLocation = new Point(window.x + 2 * window.width - minimizeButton.getStyle().getPrimaryLook().getBounds().width, window.y);
		
		minimizeButton.getStyle().setPrimaryLook(minimizeButton.getStyle().getPrimaryLook());
		minimizeButton.getStyle().setLocation(minimizeLocation);
	}
	
	// This is the part where the window gets initialized with its button areas, such as exit buttons etc.
	private void createWindowButtons(Rectangle window, GBorder borderProperties)
	{
		Dimension buttonSize = new Dimension(44, 20);

		Point exitLocation = new Point(window.x + window.width - buttonSize.width, window.y);

		Rectangle exitButtonRect = new Rectangle(exitLocation, buttonSize);

		GBorder exitBorderStyle = new GBorder(borderProperties.getBorderRadiusPx());

		exitBorderStyle.setUpperLeftBorderRadiusPx(0);
		exitBorderStyle.setLowerLeftBorderRadiusPx(0);
		exitBorderStyle.setLowerRightBorderRadiusPx(0);

		exitButton = new GWindowButton(exitButtonRect, Color.BLUE, 0.8f, exitBorderStyle, null)
		{
			@Override
			public void onClick()
			{
				System.out.println("Exit");
			}
		};

		Point minimizeLocation = new Point(window.x + window.width - 2*buttonSize.width, window.y);

		Rectangle minimizeButtonRect = new Rectangle(minimizeLocation, buttonSize);

		GBorder minimizeBorderStyle = new GBorder(borderProperties.getBorderRadiusPx());

		minimizeBorderStyle.setUpperLeftBorderRadiusPx(0);
		minimizeBorderStyle.setLowerRightBorderRadiusPx(0);
		minimizeBorderStyle.setUpperRightBorderRadiusPx(0);

		minimizeButton = new GWindowButton(minimizeButtonRect, Color.GRAY, 0.9f, minimizeBorderStyle, null)
		{
			@Override
			public void onClick()
			{
				System.out.println("Minimize");
			}
		};
	}

	public void setComponents(ArrayList<GComponent> components)
	{
		this.components = components;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}

	@Deprecated
	public String getValue()
	{
		return title;
	}

	// Prefer setTitle(...) instead!
	@Deprecated
	@Override
	public void setValue(String val)
	{
		// TODO Auto-generated method stub
	}
	
	public GWindowButton getExitButton()
	{
		return exitButton;
	}
	
	public GWindowButton getMinimizeButton()
	{
		return minimizeButton;
	}
}

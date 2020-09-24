package org.thirdrealitystudios.guinness.gui.component.selection.list;

import java.awt.Image;
import java.io.File;

import org.thirdrealitystudios.guinness.feature.Path;
import org.thirdrealitystudios.guinness.gui.component.GLogic;
import org.thirdrealitystudios.guinness.gui.component.GStyle;
import org.thirdrealitystudios.guinness.gui.font.Font;

public class GSelectionOption
{
	private GStyle style;
	
	private GLogic logic;
	
	private boolean isChecked = false;
	
	private boolean isDefaultOption = false;
	
	private String value;
	
	private Image[] icon;
	
	public GSelectionOption(String title, boolean isDefaultOption)
	{
		this.isDefaultOption = isDefaultOption;

		setTitle(title);
		
		setStyle(new GStyle());
		
		setLogic(new GLogic());
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONT + File.separator + "StandardFont.png", 18));
	}
	
	public GSelectionOption(GStyle style, GLogic logic, boolean isDefaultOption)
	{
		this("", isDefaultOption);
		
		this.style = style;
		this.logic = logic;
	}
	
	// The option is being updated with new icons from the GSelectionBox or similar instances.
	// This way, abstractness is being kept by handling all scaling and other stuff by outer instances.
	// Doing so, you don't have to take care about applying an icon for each option (including scaling manually).
	public void setIcon(Image[] icons, int width, int height)
	{
		icon[0] = icons[0].getScaledInstance(width, height, Image.SCALE_SMOOTH); // "Unselected" state
		icon[1] = icons[1].getScaledInstance(width, height, Image.SCALE_SMOOTH); // "Selected" state
	}
	
	public Image[] getIcon()
	{
		return icon;
	}

	public GStyle getStyle()
	{
		return style;
	}

	public void setStyle(GStyle style)
	{
		this.style = style;
	}

	public GLogic getLogic()
	{
		return logic;
	}

	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}

	public boolean isChecked()
	{
		return isChecked;
	}

	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}

	public boolean isDefaultOption()
	{
		return isDefaultOption;
	}

	public void setDefaultOption(boolean isDefaultOption)
	{
		this.isDefaultOption = isDefaultOption;
	}
	
	public void setTitle(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}

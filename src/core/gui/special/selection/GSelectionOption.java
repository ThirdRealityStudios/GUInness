package core.gui.special.selection;

import java.io.File;

import core.feature.Path;
import core.gui.component.GLogic;
import core.gui.component.GStyle;
import core.gui.design.Classic;
import core.gui.font.Font;

// new Rectangle(new Dimension(font.getFontSize() + val.length() * font.getFontSize(), font.getFontSize()))

public class GSelectionOption
{
	private GStyle style;
	
	private GLogic logic;
	
	private boolean isChecked = false;
	
	private boolean isDefaultOption = false;
	
	private String value;
	
	public GSelectionOption(String title, boolean isDefaultOption)
	{
		this.isDefaultOption = isDefaultOption;
		
		setTitle(title);
		
		setStyle(new GStyle());
		setLogic(new GLogic());
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONTS + File.separator + "StandardFont.png", 18));
	}
	
	public GSelectionOption(GStyle style, GLogic logic, boolean isDefaultOption)
	{
		this("", isDefaultOption);
		
		this.style = style;
		this.logic = logic;
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

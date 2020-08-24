package core.gui.special.selection;

import core.gui.component.GLogic;
import core.gui.component.GStyle;

// new Rectangle(new Dimension(font.getFontSize() + val.length() * font.getFontSize(), font.getFontSize()))

public class GSelectionOption
{
	private GStyle style;
	
	private GLogic logic;
	
	private boolean isChecked = false;
	
	private boolean isDefaultOption = false;
	
	private String value;
	
	public GSelectionOption(boolean isChecked, boolean isDefaultOption)
	{		
		this.isChecked = isChecked;
		this.isDefaultOption = isDefaultOption;
		
		style = new GStyle();
		logic = new GLogic();
	}
	
	public GSelectionOption(GStyle style, GLogic logic, boolean isChecked, boolean isDefaultOption)
	{
		this(isChecked, isDefaultOption);
		
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
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}

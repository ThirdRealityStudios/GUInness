package org.thirdreality.guinness.gui.component.style.property;

// Mainly a container to indicate all different kinds of border properties in pixels (px)
// and to improve the readibility of the GStyle class.
public class GBorder
{
	// For specific component types, such as GRectangle, you can define the thickness of the border.
	private int borderThicknessPx = 0;

	// For specific component types, such as GRectangle, you can define a border-radius (just like in CSS).
	private int borderRadiusPx = 0;

	/*
	 *  Defines various border radiuses in pixels.
	 *
	 *  Legend:
	 *
	 *  L = Left, R = Right, T = Top, B = Bottom
	 *
	 *  Notice!
	 *
	 *  If you specify a general border radius above (borderRadiusPx variable) the radiuses below are preferred (if something is specified, meaning not null (!= null) ).
	 *
	 *	Also: if you want the setting to be ignored you can set it to 'null' again!
	 */
	private Integer borderRadiusPxLT, borderRadiusPxRT, borderRadiusPxBL, borderRadiusPxBR;

	public GBorder(){}

	public GBorder(int borderRadiusPx)
	{
		this.borderRadiusPx = borderRadiusPx;
	}
	
	public GBorder(int borderRadiusPx, int borderThicknessPx)
	{
		this(borderRadiusPx);
		
		this.borderThicknessPx = borderThicknessPx;
	}
	
	public int getBorderRadiusPx()
	{
		return borderRadiusPx;
	}
	
	public void setBorderRadiusPx(int borderRadiusPx)
	{
		this.borderRadiusPx = borderRadiusPx;
	}
	
	public Integer getUpperLeftBorderRadiusPx()
	{
		return borderRadiusPxLT;
	}

	public void setUpperLeftBorderRadiusPx(int borderRadiusPxLT)
	{
		this.borderRadiusPxLT = borderRadiusPxLT;
	}

	public Integer getUpperRightBorderRadiusPx()
	{
		return borderRadiusPxRT;
	}

	public void setUpperRightBorderRadiusPx(int borderRadiusPxRT)
	{
		this.borderRadiusPxRT = borderRadiusPxRT;
	}

	public Integer getLowerLeftBorderRadiusPx()
	{
		return borderRadiusPxBL;
	}

	public void setLowerLeftBorderRadiusPx(int borderRadiusPxBL)
	{
		this.borderRadiusPxBL = borderRadiusPxBL;
	}

	public Integer getLowerRightBorderRadiusPx()
	{
		return borderRadiusPxBR;
	}

	public void setLowerRightBorderRadiusPx(int borderRadiusPxBR)
	{
		this.borderRadiusPxBR = borderRadiusPxBR;
	}
	
	public void setBorderThicknessPx(int borderThicknessPx)
	{
		this.borderThicknessPx = borderThicknessPx;
	}
	
	public int getBorderThicknessPx()
	{
		return borderThicknessPx;
	}
	
	// Creates a copy of these border properties.
	public GBorder copy()
	{
		GBorder border = new GBorder(getBorderRadiusPx(), getBorderThicknessPx());
		
		border.setLowerLeftBorderRadiusPx(getLowerLeftBorderRadiusPx());
		border.setLowerRightBorderRadiusPx(getLowerRightBorderRadiusPx());
		border.setUpperLeftBorderRadiusPx(getUpperLeftBorderRadiusPx());
		border.setUpperRightBorderRadiusPx(getUpperRightBorderRadiusPx());
		
		return border;
	}
}

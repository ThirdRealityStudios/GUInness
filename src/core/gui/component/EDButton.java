package core.gui.component;

import java.awt.Color;
import java.awt.Point;

import core.gui.EDText;

public abstract class EDButton extends EDText
{
	private volatile boolean interaction = true, isHovered = false, actionOnClick = true, actionOnHover = true;
	
	public EDButton(Color background, Color active, Color hover, Point location, String title, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(title.length(), background, hover, active, location, title, fontColor, fontSize, innerThickness, borderThickness, border, visible);
	}

	public String getTitle()
	{
		return getValue();
	}

	public void setTitle(String title)
	{
		setValue(title);
	}

	public abstract void onClick();

	public abstract void onHover();

	public boolean isInteractionEnabled()
	{
		return interaction;
	}

	public synchronized void setInteraction(boolean interaction)
	{
		this.interaction = interaction;
	}

	public synchronized boolean isHovered()
	{
		return isHovered;
	}

	protected void setHovered(boolean isHovered)
	{
		this.isHovered = isHovered;
	}

	public boolean actsOnClick()
	{
		return actionOnClick;
	}

	public void actsOnClick(boolean actsOnHover)
	{
		this.actionOnClick = actsOnHover;
	}

	public boolean actsOnHover()
	{
		return actionOnHover;
	}

	public void actsOnHover(boolean actsOnHover)
	{
		this.actionOnHover = actsOnHover;
	}
}

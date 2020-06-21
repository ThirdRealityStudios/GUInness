package core.gui.component.standard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import core.gui.EDText;
import core.tools.gui.FontLoader;

public abstract class EDButton extends EDText
{
	private volatile boolean interaction = true, isHovered = false, actionOnClick = true, actionOnHover = true;
	
	private FontLoader fL;
	
	public EDButton(Color background, Color active, Color hover, Point location, String title, Color fontColor, int fontSize, int innerThickness, int borderThickness, Color border, boolean visible)
	{
		super(title.length(), background, hover, active, location, title, fontColor, fontSize, innerThickness, borderThickness, border, visible);
		
		fL = new FontLoader();
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
	
	public void draw(Graphics g)
	{
		if(isVisible())
		{
			g.setColor(getBorder());
			
			Rectangle bounds = getShape().getBounds();
			
			g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);

			int titleWidth = getFontSize() * getValue().length();

			g.setColor(getBackground());
			g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), getFontSize() + 2 * getInnerThickness());

			fL.display(g, getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), getFontSize(), getFontColor());
		}
	}
}

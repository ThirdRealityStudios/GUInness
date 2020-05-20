package core.gui.component.standard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import core.gui.EDText;
import core.tools.gui.FontLoader;

public class EDDescription extends EDText
{
	private FontLoader fL;
	
	private boolean interaction = true;
	
	public EDDescription(Point location, String title, int fontSize, Color fontColor, boolean visible)
	{		
		super(title.length(), new Color(0,0,0,0), new Color(0,0,0,0), new Color(0,0,0,0), location, title, fontColor, fontSize, 0, 0, new Color(0,0,0,0), visible);
		
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

	public void onClick()
	{
		
	}

	public void onHover()
	{
		
	}

	public boolean isInteractionEnabled()
	{
		return interaction;
	}

	@Override
	public void draw(Graphics g)
	{
		if(isVisible())
		{
			Rectangle bounds = getShape().getBounds();
			
			g.setColor(getBorder());
			g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);
			
			int titleWidth = getFontSize() * getValue().length();

			g.setColor(getBackground());
			g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), getFontSize() + 2 * getInnerThickness());

			fL.display(g, getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), getFontSize(), getFontColor());
		}
	}
}

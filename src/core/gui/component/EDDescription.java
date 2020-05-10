package core.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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
			g.setColor(getBorder());
			g.fillRect(getRectangle().getLocation().x, getRectangle().getLocation().y, getRectangle().getSize().width, getRectangle().getSize().height);
			
			int titleWidth = getFontSize() * getValue().length();

			g.setColor(getBackground());
			g.fillRect(getRectangle().getLocation().x + getBorderThickness(), getRectangle().getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), getFontSize() + 2 * getInnerThickness());

			fL.display(g, getValue(), getRectangle().getLocation().x + getInnerThickness() + getBorderThickness(), getRectangle().getLocation().y + getInnerThickness() + getBorderThickness(), getFontSize(), getFontColor());
		}
	}
}

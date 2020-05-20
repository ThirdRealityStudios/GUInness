package core.tools.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import core.gui.EDText;

public class UICreator
{
	private FontLoader fL = new FontLoader();

	public void createText(Graphics g, EDText text)
	{
		Rectangle bounds = text.getShape().getBounds();
		
		g.setColor(text.getBorder());
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		int titleWidth = text.getFontSize() * text.getValue().length();

		g.setColor(text.getBackground());
		g.fillRect(bounds.x + text.getBorderThickness(), bounds.y + text.getBorderThickness(), titleWidth + 2 * text.getInnerThickness(), text.getFontSize() + 2 * text.getInnerThickness());

		fL.display(g, text.getValue(), bounds.x + text.getInnerThickness() + text.getBorderThickness(), bounds.y + text.getInnerThickness() + text.getBorderThickness(), text.getFontSize(), text.getFontColor());
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
}

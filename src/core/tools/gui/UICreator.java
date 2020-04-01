package core.tools.gui;

import java.awt.Graphics;

import core.gui.EDText;

public class UICreator
{
	private FontLoader fL = new FontLoader();

	public void createText(Graphics g, EDText text)
	{
		g.setColor(text.getBorder());
		g.fillRect(text.getLocation().x, text.getLocation().y, text.getSize().width, text.getSize().height);

		int titleWidth = text.getFontSize() * text.getValue().length();

		g.setColor(text.getBackground());
		g.fillRect(text.getLocation().x + text.getBorderThickness(), text.getLocation().y + text.getBorderThickness(), titleWidth + 2 * text.getInnerThickness(), text.getFontSize() + 2 * text.getInnerThickness());

		//g.setColor(text.getFontColor());
		fL.display(g, text.getValue(), text.getLocation().x + text.getInnerThickness() + text.getBorderThickness(), text.getLocation().y + text.getInnerThickness() + text.getBorderThickness(), text.getFontSize(), text.getFontColor());
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
}

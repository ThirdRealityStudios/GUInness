package core.tools.gui;

import java.awt.Graphics;

import core.gui.EDText;

public class UICreator
{
	private FontLoader fL = new FontLoader();

	public void createText(Graphics g, EDText text)
	{
		g.setColor(text.getBorder());
		g.fillRect(text.getRectangle().getLocation().x, text.getRectangle().getLocation().y, text.getRectangle().getSize().width, text.getRectangle().getSize().height);

		int titleWidth = text.getFontSize() * text.getValue().length();

		g.setColor(text.getBackground());
		g.fillRect(text.getRectangle().getLocation().x + text.getBorderThickness(), text.getRectangle().getLocation().y + text.getBorderThickness(), titleWidth + 2 * text.getInnerThickness(), text.getFontSize() + 2 * text.getInnerThickness());

		fL.display(g, text.getValue(), text.getRectangle().getLocation().x + text.getInnerThickness() + text.getBorderThickness(), text.getRectangle().getLocation().y + text.getInnerThickness() + text.getBorderThickness(), text.getFontSize(), text.getFontColor());
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
}

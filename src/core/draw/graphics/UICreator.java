package core.draw.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

import core.gui.component.GComponent;
import core.gui.design.Design;
import core.gui.font.FontLoader;

public class UICreator
{
	private FontLoader fL = new FontLoader();

	private Design design;

	public UICreator(Design design)
	{
		this.setDesign(design);
	}

	public void createText(Graphics g, GComponent c)
	{
		Rectangle bounds = c.getShape().getBounds();
		
		g.setColor(design.getBorderColor());
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		int titleWidth = c.getFontSize() * c.getValue().length();

		g.setColor(design.getBackgroundColor());
		g.fillRect(bounds.x + design.getBorderThickness(), bounds.y + design.getBorderThickness(), titleWidth + 2 * design.getInnerThickness(), c.getFontSize() + 2 * design.getInnerThickness());

		fL.display(g, c.getValue(), bounds.x + design.getInnerThickness() + design.getBorderThickness(), bounds.y + design.getInnerThickness() + design.getBorderThickness(), c.getFontSize(), design.getFontColor());
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}

	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design design)
	{
		this.design = design;
	}
}

package core.gui.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import core.gui.EDComponent;
import core.gui.special.EDPath;

// The classic design without which looks very retro-stylish or ugly.
public class Classic extends Design
{
	public Classic(Color borderColor, Color backgroundColor, Color activeColor, Color hoverColor, Color fontColor, int innerThickness, int borderThickness)
	{
		super(borderColor, backgroundColor, activeColor, hoverColor, fontColor, innerThickness, borderThickness);
	}

	// Every design has its own draw method in order to know how to draw each component.
	// This is a "pre-method".
	public void drawContext(Graphics g, EDComponent c)
	{
		Rectangle bounds = c.getShape().getBounds();
		
		// For the case there is an image supplied to the EDComponent object,
		// it is considered to be rendered.
		// The programmer needs to know how to use the features EDComponent delivers and has to ensure
		// a supplied image will not get in conflict with other settings.
		switch(c.getType())
		{
			case "image":
			{
				g.drawImage(c.getImage(), bounds.getLocation().x, bounds.getLocation().y, bounds.width, bounds.height, null);
				
				break;
			}
		
			case "path":
			{
				EDPath path = (EDPath) c;
				
				Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(path.getPrimaryColor());

				if(path.isFill())
				{
					g2d.fill(path.getPath());
				}
				else
				{
					g2d.draw(path.getPath());
				}
				
				break;
			}
			
			case "textfield":
			{
				g.setColor(getBorderColor());
				g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);
				
				int titleWidth = c.getFontSize() * c.getValue().length();

				g.setColor(getBackgroundColor());
				g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getFontSize() + 2 * getInnerThickness());

				getFontLoader().display(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getFontSize(), getFontColor());
			
				break;
			}
			
			default:
			{
				drawDefault(g, c);
			}
		}
	}
	
	protected void drawDefault(Graphics g, EDComponent c)
	{
		Rectangle bounds = c.getShape().getBounds();
		
		g.setColor(getBorderColor());

		g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);

		int titleWidth = c.getFontSize() * c.getValue().length();

		g.setColor(c.getPrimaryColor());
		g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getFontSize() + 2 * getInnerThickness());

		getFontLoader().display(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getFontSize(), getFontColor());
	}

	// Returns a determined shape which uses the design defined in this class.
	public Rectangle generateDefaultShape(EDComponent c)
	{
		// Calculates the correct size of the rectangle for the text component.
		Dimension backgroundSize = new Dimension(c.getLength() * c.getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness(), c.getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness());

		Rectangle rect = new Rectangle(c.getLocation(), backgroundSize);
		// rect.setLocation(c.getShape().getBounds().getLocation());
		
		return rect;
	}

	// Updates the context component with its corresponding values.
	// This is a post-method.
	public void updateDefaultShape(EDComponent c)
	{
		Shape recalculated = generateDefaultShape(c);
		
		c.setShape(recalculated);
	}
}

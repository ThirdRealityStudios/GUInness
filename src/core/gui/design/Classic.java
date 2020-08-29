package core.gui.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import core.Meta;
import core.draw.DrawToolkit;
import core.gui.component.GComponent;
import core.gui.special.GCheckbox;
import core.gui.special.GPath;
import core.gui.special.selection.GSelectionBox;
import core.gui.special.selection.GSelectionOption;

// The classic design without which looks very retro-stylish or ugly.
public class Classic extends Design
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	public Classic(Color borderColor, Color backgroundColor, Color activeColor, Color hoverColor, Color fontColor, int innerThickness, int borderThickness)
	{
		super(borderColor, backgroundColor, activeColor, hoverColor, fontColor, innerThickness, borderThickness);
	}

	// Every design has its own draw method in order to know how to draw each component.
	// This is a "pre-method".
	public void drawContext(Graphics g, GComponent c)
	{
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
		// For the case there is an image supplied to the GComponent object,
		// it is considered to be rendered.
		// The programmer needs to know how to use the features GComponent delivers and has to ensure
		// a supplied image will not get in conflict with other settings.
		switch(c.getType())
		{
			case "description":
			{
				DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());
		
				break;
			}
		
			case "image":
			{				
				g.drawImage(c.getStyle().getImage(), bounds.getLocation().x, bounds.getLocation().y, bounds.width, bounds.height, null);
				
				break;
			}
		
			case "path":
			{
				GPath path = (GPath) c;
				
				Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(path.getStyle().getPrimaryColor());

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

				int titleWidth = c.getStyle().getFont().getFontSize() * c.getStyle().getLength();

				g.setColor(Color.WHITE);
				g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness());

				DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());
			
				break;
			}
			
			case "checkbox":
			{
				// It wouldn't matter if you use 'height' or 'width' because both values are the same.
				int size = bounds.width;
				
				GCheckbox checkbox = (GCheckbox) c;
				
				g.setColor(getBorderColor());
				g.fillRect(bounds.getLocation().x, bounds.getLocation().y, size + getInnerThickness(), size + getInnerThickness());
				
				g.setColor(Color.WHITE);
				g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), size, size);

				if(checkbox.isChecked())
				{
					g.drawImage(c.getStyle().getImage() , bounds.getLocation().x + 2*getBorderThickness(), bounds.getLocation().y + 2*getBorderThickness(), null);
				}

				break;
			}
			
			case "selectionbox":
			{
				GSelectionBox selectionBox = (GSelectionBox) c;
				
				bounds = selectionBox.getStyle().getShape().getBounds();
				
				g.setColor(Color.GRAY);
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
				
				ArrayList<Rectangle[]> shapeTable = selectionBox.getShapeTable();
				
				// Draws every single option from the GSelectionBox.
				for(int i = 0; i < shapeTable.size(); i++)
				{
					GSelectionOption option = selectionBox.getOptions().get(i);
					
					Rectangle optionShape = shapeTable.get(i)[0];
					Rectangle titleShape = shapeTable.get(i)[1];
					
					g.setColor(option.isChecked() ? Color.GREEN : Color.RED);
					g.fillRect(optionShape.x, optionShape.y, optionShape.width, optionShape.height);
					
					g.setColor(Color.YELLOW);
					g.fillRect(titleShape.x, titleShape.y, titleShape.width, titleShape.height);
					
					DrawToolkit.drawString(g, option.getValue(), titleShape.x, titleShape.y, option.getStyle().getFont());
				}

				break;
			}
			
			default:
			{
				drawDefault(g, c);
			}
		}
	}

	protected void drawDefault(Graphics g, GComponent c)
	{
		Rectangle bounds = c.getStyle().getShape().getBounds();

		g.setColor(getBorderColor());

		g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);

		int titleWidth = c.getStyle().getFont().getFontSize() * c.getValue().length();

		g.setColor(c.getStyle().getPrimaryColor());
		g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness());

		DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());
	}

	// Returns a determined shape which uses the design defined in this class.
	public Rectangle generateDefaultShape(GComponent c)
	{		
		// Calculates the correct size of the rectangle for the text component.
		Dimension backgroundSize = new Dimension(c.getStyle().getLength() * c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness());

		Rectangle rect = new Rectangle(c.getStyle().getLocation(), backgroundSize);
		
		return rect;
	}

	// Updates the context component with its corresponding values.
	// This is a post-method.
	public void updateDefaultShape(GComponent c)
	{
		Shape recalculated = generateDefaultShape(c);
		
		c.getStyle().setShape(recalculated);
	}
}
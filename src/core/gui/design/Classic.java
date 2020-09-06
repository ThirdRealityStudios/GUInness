package core.gui.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import core.Meta;
import core.draw.DrawToolkit;
import core.gui.component.GComponent;
import core.gui.component.decoration.GPath;
import core.gui.component.decoration.GRectangle;
import core.gui.component.selection.GCheckbox;
import core.gui.component.selection.list.GSelectionBox;
import core.gui.component.selection.list.GSelectionOption;

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
		// For the case there is an image supplied to the GComponent object,
		// it is considered to be rendered.
		// The programmer needs to know how to use the features GComponent delivers and has to ensure
		// a supplied image will not get in conflict with other settings.
		switch(c.getType())
		{
			case "polybutton":
			{
				drawPolyButton(g, c);
	
				break;
			}
		
			case "description":
			{
				drawDescription(g, c);
		
				break;
			}
		
			case "image":
			{				
				drawImage(g, c);
				
				break;
			}
		
			case "path":
			{
				drawPath(g, c);
				
				break;
			}
			
			case "textfield":
			{
				drawTextfield(g, c);
				
				break;
			}
			
			case "checkbox":
			{
				drawCheckbox(g, c);

				break;
			}
			
			case "selectionbox":
			{
				drawSelectionBox(g, c);

				break;
			}
			
			case "rectangle":
			{
				drawRectangle(g, c);

				break;
			}
			
			default:
			{
				drawDefault(g, c);
			}
		}
	}
	
	private void drawRectangle(Graphics g, GComponent c)
	{
		// A GRectangle can do more than a usual GComponent.
		// You can define border-radiuses and more.
		if(c.getType().contentEquals("rectangle"))
		{
			GRectangle rect = (GRectangle) c;
			
			Rectangle shape = rect.getStyle().getShape().getBounds();
			
			g.setColor(rect.getStyle().getPrimaryColor() == null ? Color.BLACK : rect.getStyle().getPrimaryColor());
			
			if(shape != null)
			{
				g.fillRect(shape.x, shape.y, shape.width, shape.height);
			}
		}
		// If it's not a GRectangle just draw the shape if there is one. Anyway, you can do less things here..
		else if(c.getStyle().getShape() != null)
		{
			Rectangle shape = c.getStyle().getShape().getBounds();
			
			g.setColor(c.getStyle().getPrimaryColor() == null ? Color.BLACK : c.getStyle().getPrimaryColor());
			
			if(shape != null)
			{
				g.fillRect(shape.x, shape.y, shape.width, shape.height);
			}
		}
	}
	
	private void drawDescription(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
		DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());
	}
	
	private void drawImage(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
		g.drawImage(c.getStyle().getImage(), bounds.getLocation().x, bounds.getLocation().y, bounds.width, bounds.height, null);
	}
	
	private void drawPath(Graphics g, GComponent c)
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
	}
	
	private void drawTextfield(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
		g.setColor(getBorderColor());
		g.fillRect(bounds.getLocation().x, bounds.getLocation().y, bounds.getSize().width, bounds.getSize().height);

		int titleWidth = c.getStyle().getFont().getFontSize() * c.getStyle().getLength();

		g.setColor(Color.WHITE);
		g.fillRect(bounds.getLocation().x + getBorderThickness(), bounds.getLocation().y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness());

		DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + getInnerThickness() + getBorderThickness(), bounds.getLocation().y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());

	}
	
	private void drawCheckbox(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
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
	}
	
	private void drawSelectionBox(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();
		
		GSelectionBox selectionBox = (GSelectionBox) c;
		
		bounds = selectionBox.getStyle().getShape().getBounds();
		
		drawRectangle(g, selectionBox);
		
		ArrayList<Rectangle[]> shapeTable = selectionBox.getShapeTable();
		
		// Draws every single option from the GSelectionBox.
		for(int i = 0; i < shapeTable.size(); i++)
		{
			GSelectionOption option = selectionBox.getOptions().get(i);
			
			Rectangle optionShape = shapeTable.get(i)[0];
			Rectangle titleShape = shapeTable.get(i)[2];
			
			if(option.isChecked())
			{
				g.drawImage(selectionBox.getIcons()[1], optionShape.x, optionShape.y, optionShape.width, optionShape.height, null);
			}
			else
			{
				g.drawImage(selectionBox.getIcons()[0], optionShape.x, optionShape.y, optionShape.width, optionShape.height, null);
			}
			
			// Every option can have a background color..
			Color optionColor = option.getStyle().getPrimaryColor();
			
			// But if there is no background color, then no background will be drawn just..
			if(optionColor != null)
			{
				g.setColor(optionColor);
				g.fillRect(titleShape.x, titleShape.y, titleShape.width, titleShape.height);
			}
			
			DrawToolkit.drawString(g, option.getValue(), titleShape.x, titleShape.y, option.getStyle().getFont());
		}
	}
	
	protected void drawPolyButton(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getShape().getBounds();

		g.setColor(c.getStyle().getPrimaryColor());

		g.fillPolygon((Polygon) c.getStyle().getShape());

		// If text should be displayed in the center of the component.
		if(c.getStyle().getTextAlign() == 1)
		{
			int textLength = c.getStyle().getFont().getFontSize() * c.getValue().length();
			
			int centerX = bounds.getLocation().x + bounds.width / 2 - textLength / 2;
			int centerY = bounds.getLocation().y + bounds.height / 2 - c.getStyle().getFont().getFontSize() / 2;
			
			DrawToolkit.drawString(g, c.getValue(), centerX + c.getStyle().getTextAlignTransition().x, centerY + c.getStyle().getTextAlignTransition().y, c.getStyle().getFont());
		}
		else // If text should be displayed normally (upper-left corner of the component).
		{
			DrawToolkit.drawString(g, c.getValue(), bounds.getLocation().x + c.getStyle().getTextAlignTransition().x, bounds.getLocation().y + c.getStyle().getTextAlignTransition().y, c.getStyle().getFont());
		}
	}

	protected void drawDefault(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
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
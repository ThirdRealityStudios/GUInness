package core.gui.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.Meta;
import core.draw.DrawToolkit;
import core.feature.shape.ShapeMaker;
import core.feature.shape.ShapeTransform;
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
	
	private Point offset;
	
	private float scale;

	public Classic(Color borderColor, Color backgroundColor, Color activeColor, Color hoverColor, Color fontColor, int innerThickness, int borderThickness)
	{
		super(borderColor, backgroundColor, activeColor, hoverColor, fontColor, innerThickness, borderThickness);
	}

	// Every design has its own draw method in order to know how to draw each component.
	// This is a "pre-method".
	public void drawContext(Graphics g, GComponent c, Point offset, float scale)
	{
		this.offset = offset;
		this.scale = scale;
		
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
			
			Rectangle shape = rect.getStyle().getLook().getBounds();
			
			g.setColor(rect.getStyle().getPrimaryColor() == null ? Color.BLACK : rect.getStyle().getPrimaryColor());
			
			if(shape != null)
			{
				g.fillRect(c.isMovable() ? shape.x + getOffset().x : shape.x, c.isMovable() ? shape.y + getOffset().y : shape.y, shape.width, shape.height);
			}
		}
		// If it's not a GRectangle just draw the shape if there is one. Anyway, you can do less things here..
		else if(c.getStyle().getLook() != null)
		{
			Rectangle shape = c.getStyle().getLook().getBounds();
			
			g.setColor(c.getStyle().getPrimaryColor() == null ? Color.BLACK : c.getStyle().getPrimaryColor());
			
			if(shape != null)
			{
				g.fillRect(c.isMovable() ? shape.x + getOffset().x : shape.x, c.isMovable() ? shape.y + getOffset().y : shape.y, shape.width, shape.height);
			}
		}
	}
	
	private void drawDescription(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();
		
		int x = bounds.getLocation().x + getInnerThickness() + getBorderThickness();
		int y = bounds.getLocation().y + getInnerThickness() + getBorderThickness();
		
		DrawToolkit.drawString(g, c.getValue(), c.isMovable() ? x + getOffset().x : x, c.isMovable() ? y + getOffset().y : y, c.getStyle().getFont());
	}
	
	private void drawImage(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();
		
		int x = bounds.getLocation().x;
		int y = bounds.getLocation().y;
		
		g.drawImage(c.getStyle().getImage(), c.isMovable() ? x + getOffset().x : x, c.isMovable() ? y + getOffset().y : y, bounds.width, bounds.height, null);
	}
	
	// Needs to be updated with offset and scale ability from the Viewports settings.
	@Deprecated
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
		Rectangle bounds = c.getStyle().getLook().getBounds();

		g.setColor(getBorderColor());
		
		int x = bounds.getLocation().x;
		int y = bounds.getLocation().y;
		
		g.fillRect(c.isMovable() ? x + getOffset().x : x, c.isMovable() ? y + getOffset().y : y, bounds.getSize().width, bounds.getSize().height);

		int titleWidth = c.getStyle().getFont().getFontSize() * c.getStyle().getLength();

		g.setColor(Color.WHITE);
		
		int xBorder = x + getBorderThickness();
		int yBorder = y + getBorderThickness();
		
		g.fillRect(c.isMovable() ? xBorder + getOffset().x : xBorder, c.isMovable() ? yBorder + getOffset().y : yBorder, titleWidth + 2 * getInnerThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness());

		int xInner = xBorder + getInnerThickness();
		int yInner = yBorder + getInnerThickness();
		
		DrawToolkit.drawString(g, c.getValue(), c.isMovable() ? xInner + getOffset().x : xInner, c.isMovable() ? yInner + getOffset().y : yInner, c.getStyle().getFont());
	}
	
	private void drawCheckbox(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();
		
		// It wouldn't matter if you use 'height' or 'width' because both values are the same.
		int size = bounds.width;
		
		GCheckbox checkbox = (GCheckbox) c;

		g.setColor(getBorderColor());
		
		int x = c.isMovable() ? bounds.getLocation().x + getOffset().x : bounds.getLocation().x;
		int y = c.isMovable() ? bounds.getLocation().y + getOffset().y : bounds.getLocation().y;
		
		g.fillRect(x, y, size + getInnerThickness(), size + getInnerThickness());

		g.setColor(Color.WHITE);
		
		int xBorder = x + getBorderThickness();
		int yBorder = y + getBorderThickness();
		
		g.fillRect(xBorder, yBorder, size, size);

		if(checkbox.isChecked())
		{
			g.drawImage(c.getStyle().getImage() , xBorder + getBorderThickness(), yBorder + getBorderThickness(), null);
		}
	}
	
	private void drawSelectionBox(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();
		
		GSelectionBox selectionBox = (GSelectionBox) c;
		
		bounds = selectionBox.getStyle().getLook().getBounds();
		
		drawRectangle(g, selectionBox);
		
		ArrayList<Polygon[]> shapeTable = selectionBox.getShapeTable();
		
		// Draws every single option from the GSelectionBox.
		for(int i = 0; i < shapeTable.size(); i++)
		{
			GSelectionOption option = selectionBox.getOptions().get(i);
			
			Polygon optionShape = shapeTable.get(i)[0];
			Polygon titleShape = shapeTable.get(i)[2];
			
			int xOption = c.isMovable() ? optionShape.getBounds().x + getOffset().x : optionShape.getBounds().x;
			int yOption = c.isMovable() ? optionShape.getBounds().y + getOffset().y : optionShape.getBounds().y;
			
			if(option.isChecked())
			{
				g.drawImage(selectionBox.getIcons()[1], xOption, yOption, optionShape.getBounds().width, optionShape.getBounds().height, null);
			}
			else
			{
				g.drawImage(selectionBox.getIcons()[0], xOption, yOption, optionShape.getBounds().width, optionShape.getBounds().height, null);
			}
			
			// Every option can have a background color..
			Color optionColor = option.getStyle().getPrimaryColor();
			
			int xTitle = c.isMovable() ? titleShape.getBounds().x + getOffset().x : titleShape.getBounds().x;
			int yTitle = c.isMovable() ? titleShape.getBounds().y + getOffset().y : titleShape.getBounds().y;
			
			// But if there is no background color, then no background will just be drawn..
			if(optionColor != null)
			{
				g.setColor(optionColor);
				g.fillRect(xTitle, yTitle, titleShape.getBounds().width, titleShape.getBounds().height);
			}
			
			DrawToolkit.drawString(g, option.getValue(), xTitle, yTitle, option.getStyle().getFont());
		}
	}
	
	protected void drawPolyButton(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();
		
		Polygon look = c.getStyle().getLook();

		g.setColor(c.getStyle().getPrimaryColor());

		// Here it is only working with a copy in order not to modify the original object (polygon and Polybutton).
		Polygon transformedCopy = ShapeTransform.movePolygonTo(look, look.getBounds().x + getOffset().x, look.getBounds().y + getOffset().y);
		g.fillPolygon(transformedCopy);

		// If text should be displayed in the center of the component.
		if(c.getStyle().getTextAlign() == 1)
		{
			int textLength = c.getStyle().getFont().getFontSize() * c.getValue().length();

			int centerX = bounds.getLocation().x + bounds.width / 2 - textLength / 2;
			int centerY = bounds.getLocation().y + bounds.height / 2 - c.getStyle().getFont().getFontSize() / 2;
			
			int x = c.isMovable() ? centerX + c.getStyle().getTextAlignTransition().x + getOffset().x : centerX + c.getStyle().getTextAlignTransition().x;
			int y = c.isMovable() ? centerY + c.getStyle().getTextAlignTransition().y + getOffset().y : centerY + c.getStyle().getTextAlignTransition().y;

			DrawToolkit.drawString(g, c.getValue(), x, y, c.getStyle().getFont());
		}
		else // If text should be displayed normally (upper-left corner of the component).
		{
			int x = c.isMovable() ? bounds.x + c.getStyle().getTextAlignTransition().x + getOffset().x : bounds.x + c.getStyle().getTextAlignTransition().x;
			int y = c.isMovable() ? bounds.y + c.getStyle().getTextAlignTransition().y + getOffset().y : bounds.y + c.getStyle().getTextAlignTransition().y;
			
			DrawToolkit.drawString(g, c.getValue(), x, y, c.getStyle().getFont());
		}
	}

	protected void drawDefault(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getLook().getBounds();

		g.setColor(getBorderColor());

		int x = c.isMovable() ? bounds.getLocation().x + getOffset().x : bounds.getLocation().x;
		int y = c.isMovable() ? bounds.getLocation().y + getOffset().y : bounds.getLocation().y;

		g.fillRect(x, y, bounds.getSize().width, bounds.getSize().height);

		int titleWidth = c.getStyle().getFont().getFontSize() * c.getValue().length();

		g.setColor(c.getStyle().getPrimaryColor());
		g.fillRect(x + getBorderThickness(), y + getBorderThickness(), titleWidth + 2 * getInnerThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness());

		DrawToolkit.drawString(g, c.getValue(), x + getInnerThickness() + getBorderThickness(), y + getInnerThickness() + getBorderThickness(), c.getStyle().getFont());
	}

	// Returns a determined shape which uses the design defined in this class.
	public Polygon generateDefaultShape(GComponent c)
	{
		// Calculates the correct size of the rectangle for the text component.
		Dimension backgroundSize = new Dimension(c.getStyle().getLength() * c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness());

		// Creates a rectangle actually.
		Polygon polygon = ShapeMaker.createRectangle(c.getStyle().getLocation(), backgroundSize);

		return polygon;
	}

	// Updates the context component with its corresponding values.
	// This is a post-method.
	public void updateDefaultShape(GComponent c)
	{
		Polygon recalculated = generateDefaultShape(c);
		
		c.getStyle().setLook(recalculated);
	}

	private Point getOffset()
	{
		return offset;
	}

	private float getScale()
	{
		return scale;
	}
}
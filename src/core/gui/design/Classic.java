package core.gui.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import core.gui.font.Font;

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
			
			case "button":
			{
				Color temp = getBorderColor();
				
				setBorderColor(c.getStyle().getPrimaryColor().darker().darker());
				
				drawGeneralField(g, c, false);
				
				setBorderColor(temp);

				break;
			}
			
			default:
			{
				drawGeneralField(g, c, true);
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

			// Polygon rectangle = ShapeMaker.createRectangle(rect.getStyle().getLook().getBounds().getLocation(), rect.getStyle().getLook().getBounds().getSize());
			Polygon rectangle = ShapeMaker.createRectangleFrom(rect.getStyle().getPrimaryLook().getBounds(), rect.getStyle().getBorderRadiusPx());

			g.setColor(rect.getStyle().getPrimaryColor() == null ? Color.BLACK : rect.getStyle().getPrimaryColor());

			// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
			float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
			
			int x = (int) ((c.getStyle().isMovableForViewport() ? rectangle.getBounds().x + getOffset().x : rectangle.getBounds().x) * scale);
			int y = (int) ((c.getStyle().isMovableForViewport() ? rectangle.getBounds().y + getOffset().y : rectangle.getBounds().y) * scale);

			if(rectangle.getBounds() != null)
			{
				g.fillPolygon(ShapeTransform.movePolygonTo(ShapeTransform.scalePolygon(rectangle, scale), x, y));
			}
		}
		// If it's not a GRectangle just draw the shape if there is one. Anyway, you can do less things here..
		else if(c.getStyle().getPrimaryLook() != null)
		{
			Rectangle shape = c.getStyle().getPrimaryLook().getBounds();
			
			g.setColor(c.getStyle().getPrimaryColor() == null ? Color.BLACK : c.getStyle().getPrimaryColor());
			
			// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
			float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
			
			int x = (int) ((c.getStyle().isMovableForViewport() ? shape.x + getOffset().x : shape.x) * scale);
			int y = (int) ((c.getStyle().isMovableForViewport() ? shape.y + getOffset().y : shape.y) * scale);
			
			if(shape != null)
			{
				g.fillRect(x, y, (int) (shape.width * scale), (int) (shape.height * scale));
			}
		}
	}
	
	private void drawDescription(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();
		
		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		int x = (int) ((bounds.getLocation().x + getInnerThickness() + getBorderThickness() + (c.getStyle().isMovableForViewport() ? getOffset().x : 0)) * scale);
		int y = (int) ((bounds.getLocation().y + getInnerThickness() + getBorderThickness() + (c.getStyle().isMovableForViewport() ? getOffset().y : 0)) * scale);
		
		Font original = c.getStyle().getFont();
		Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));
		
		DrawToolkit.drawString(g, c.getValue(), x, y, scaledFont);
	}
	
	private void drawImage(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();
		
		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		int x = (int) ((bounds.getLocation().x + (c.getStyle().isMovableForViewport() ? getOffset().x : 0)) * scale);
		int y = (int) ((bounds.getLocation().y + (c.getStyle().isMovableForViewport() ? getOffset().y : 0)) * scale);
		
		g.drawImage(c.getStyle().getImage(), x, y, (int) (bounds.width * scale), (int) (bounds.height * scale), null);
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
		drawGeneralField(g, c, false);
	}
	
	private void drawCheckbox(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();
		
		// It wouldn't matter if you use 'height' or 'width' because both values are the same.
		int size = bounds.width;
		
		GCheckbox checkbox = (GCheckbox) c;

		g.setColor(getBorderColor());
		
		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		int x = (int) (((c.getStyle().isMovableForViewport() ? getOffset().x : 0) + bounds.getLocation().x) * scale);
		int y = (int) (((c.getStyle().isMovableForViewport() ? getOffset().y : 0) + bounds.getLocation().y) * scale);
		
		int outerSize = (int) ((size + getInnerThickness()) * scale);
		
		g.fillRect(x, y, outerSize, outerSize);

		g.setColor(Color.WHITE);
		
		int rectX = x + (int) (getBorderThickness() * scale);
		int rectY = y + (int) (getBorderThickness() * scale);

		int innerSize = (int) (size * scale);
		
		g.fillRect(rectX, rectY, innerSize, innerSize);

		int imgX = x + (int) (2 * getBorderThickness() * scale);
		int imgY = y + (int) (2 * getBorderThickness() * scale);
		
		if(checkbox.isChecked())
		{
			Image checkSymbol = c.getStyle().getImage();

			// Simply the square size of the image.
			// The image is saved with square dimensions,
			// so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
			int sizePx = (int) (checkSymbol.getWidth(null) * scale);
			
			g.drawImage(checkSymbol, imgX, imgY, sizePx, sizePx, null);
		}
	}
	
	private void drawSelectionBox(Graphics g, GComponent c)
	{
		GSelectionBox selectionBox = (GSelectionBox) c;

		drawRectangle(g, selectionBox);
		
		ArrayList<Polygon[]> shapeTable = selectionBox.getShapeTable();
		
		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		// Draws every single option from the GSelectionBox.
		for(int i = 0; i < shapeTable.size(); i++)
		{
			GSelectionOption option = selectionBox.getOptions().get(i);
			
			Polygon optionShape = ShapeTransform.scalePolygon(shapeTable.get(i)[0], scale);
			Polygon titleShape = ShapeTransform.scalePolygon(shapeTable.get(i)[2], scale);
			
			int xOption = c.getStyle().isMovableForViewport() ? optionShape.getBounds().x + (int) (getOffset().x  * scale) : optionShape.getBounds().x;
			int yOption = c.getStyle().isMovableForViewport() ? optionShape.getBounds().y + (int) (getOffset().y * scale) : optionShape.getBounds().y;
			
			int optionShapeWidth = optionShape.getBounds().width;
			int optionShapeHeight = optionShape.getBounds().height;
			
			if(option.isChecked())
			{
				g.drawImage(selectionBox.getIcons()[1], xOption, yOption, optionShapeWidth, optionShapeHeight, null);
			}
			else
			{
				g.drawImage(selectionBox.getIcons()[0], xOption, yOption, optionShapeWidth, optionShapeHeight, null);
			}
			
			// Every option can have a background color..
			Color optionColor = option.getStyle().getPrimaryColor();
			
			int xTitle = c.getStyle().isMovableForViewport() ? titleShape.getBounds().x + (int) (getOffset().x * scale) : titleShape.getBounds().x;
			int yTitle = c.getStyle().isMovableForViewport() ? titleShape.getBounds().y + (int) (getOffset().y * scale) : titleShape.getBounds().y;
			
			int titleShapeWidth = titleShape.getBounds().width;
			int titleShapeHeight = titleShape.getBounds().height;
			
			// But if there is no background color, then no background will just be drawn..
			if(optionColor != null)
			{
				g.setColor(optionColor);
				g.fillRect(xTitle, yTitle, titleShapeWidth, titleShapeHeight);
			}
			
			Font original = c.getStyle().getFont();
			Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));
			
			DrawToolkit.drawString(g, option.getValue(), xTitle, yTitle, scaledFont);
		}
	}
	
	protected void drawPolyButton(Graphics g, GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();

		Polygon look = c.getStyle().getPrimaryLook();

		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		g.setColor(c.getStyle().getPrimaryColor());

		int xButton = (int) (look.getBounds().x + (c.getStyle().isMovableForViewport() ? getOffset().x : 0));
		int yButton = (int) (look.getBounds().y + (c.getStyle().isMovableForViewport() ? getOffset().y : 0));

		// Here it is only working with a copy in order not to modify the original object (polygon and Polybutton).
		Polygon transformedCopy = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(look, xButton, yButton), scale);
		g.fillPolygon(transformedCopy);

		// If text should be displayed in the center of the component.
		if(c.getStyle().getTextAlign() == 1)
		{
			int textLength = c.getStyle().getFont().getFontSize() * c.getValue().length();

			int centerX = bounds.getLocation().x + bounds.width / 2 - textLength / 2;
			int centerY = bounds.getLocation().y + bounds.height / 2 - c.getStyle().getFont().getFontSize() / 2;

			int x = c.getStyle().isMovableForViewport() ? centerX + c.getStyle().getTextAlignTransition().x + getOffset().x : centerX + c.getStyle().getTextAlignTransition().x;
			int y = c.getStyle().isMovableForViewport() ? centerY + c.getStyle().getTextAlignTransition().y + getOffset().y : centerY + c.getStyle().getTextAlignTransition().y;

			Font original = c.getStyle().getFont();
			Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));
			
			DrawToolkit.drawString(g, c.getValue(), (int) (x * scale), (int) (y * scale), scaledFont);
		}
		else // If text should be displayed normally (upper-left corner of the component).
		{
			int x = c.getStyle().isMovableForViewport() ? bounds.x + c.getStyle().getTextAlignTransition().x + getOffset().x : bounds.x + c.getStyle().getTextAlignTransition().x;
			int y = c.getStyle().isMovableForViewport() ? bounds.y + c.getStyle().getTextAlignTransition().y + getOffset().y : bounds.y + c.getStyle().getTextAlignTransition().y;
			
			DrawToolkit.drawString(g, c.getValue(), (int) (x * scale), (int) (y * scale), c.getStyle().getFont());
		}
	}

	protected void drawGeneralField(Graphics g, GComponent c, boolean backgroundFitsTextLength)
	{
		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
		
		int xBackground = (int) ((c.getStyle().isMovableForViewport() ? c.getStyle().getLocation().getLocation().x + getOffset().x : c.getStyle().getLocation().getLocation().x) * scale);
		int yBackground = (int) ((c.getStyle().isMovableForViewport() ? c.getStyle().getLocation().getLocation().y + getOffset().y : c.getStyle().getLocation().getLocation().y) * scale);
		
		Polygon scaledBackground = ShapeTransform.scalePolygon(c.getStyle().getPrimaryLook(), scale);
		
		Polygon movedBackground = ShapeTransform.movePolygonTo(scaledBackground, xBackground, yBackground);
		
		g.setColor(getBorderColor());
		g.fillPolygon(movedBackground);

		int xInnerArea = xBackground + (int) (getBorderThickness() * scale);
		int yInnerArea = yBackground + (int) (getBorderThickness() * scale);
		
		int titleWidth = c.getStyle().getFont().getFontSize() * c.getValue().length();

		int innerWidth = (int) (((backgroundFitsTextLength ? titleWidth : c.getStyle().getLength() * c.getStyle().getFont().getFontSize()) + 2 * getInnerThickness()) * scale);
		int innerHeight = (int) ((c.getStyle().getFont().getFontSize() + 2 * getInnerThickness()) * scale);
		
		Polygon innerArea = ShapeMaker.createRectangleFrom(xInnerArea, yInnerArea, innerWidth, innerHeight, c.getStyle().getBorderRadiusPx());

		Polygon movedInnerArea = ShapeTransform.movePolygonTo(innerArea, xInnerArea, yInnerArea);

		g.setColor(c.getStyle().getPrimaryColor());
		g.fillPolygon(movedInnerArea);

		int textX = xBackground + (int) ((getInnerThickness() + getBorderThickness()) * scale);
		int textY = yBackground + (int) ((getInnerThickness() + getBorderThickness()) * scale);

		Font original = c.getStyle().getFont();
		Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));

		DrawToolkit.drawString(g, c.getValue(), textX, textY, scaledFont);
	}

	// Returns a determined shape which uses the design defined in this class.
	public Polygon generateDefaultShape(GComponent c)
	{
		// Calculates the correct size of the rectangle for the default button component.
		Dimension backgroundSize = new Dimension(c.getStyle().getLength() * c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness(), c.getStyle().getFont().getFontSize() + 2 * getInnerThickness() + 2 * getBorderThickness());

		Rectangle rectangle = new Rectangle(c.getStyle().getLocation(), backgroundSize);
		
		// Creates a rectangle actually.
		Polygon polygon = ShapeMaker.createRectangleFrom(rectangle, c.getStyle().getBorderRadiusPx());
		
		return polygon;
	}

	// Updates the context component with its corresponding values.
	// This is a post-method.
	public void updateDefaultShape(GComponent c)
	{
		Polygon recalculated = generateDefaultShape(c);
		
		c.getStyle().setPrimaryLook(recalculated);
	}

	// Will return the offset of the Viewport.
	// If no offset was defined or assigned, it will return an empty point (0|0).
	private Point getOffset()
	{
		return offset != null ? offset : new Point();
	}

	private float getScale()
	{
		return scale;
	}
}
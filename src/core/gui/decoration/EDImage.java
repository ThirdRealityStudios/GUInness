package core.gui.decoration;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import core.gui.EDComponent;

public class EDImage extends EDComponent
{
	private Image content;

	public EDImage(Point location, Image content, boolean visible)
	{
		super(new Rectangle(new Point(location), new Dimension((int) content.getWidth(null), (int) content.getHeight(null))), visible);
		
		Rectangle bounds = getShape().getBounds();
		
		bounds.setSize(new Dimension(content.getWidth(null), content.getHeight(null)));
		
		this.content = content;
	}

	public EDImage(Point location, float scale, Image content, boolean visible)
	{
		super(new Rectangle(new Point(location), new Dimension((int) (scale * content.getWidth(null)), (int) (scale * content.getHeight(null)))), visible);

		this.content = content;
	}

	public EDImage(Point location, Dimension size, Image content, boolean visible)
	{
		super(new Rectangle(new Point(location), new Dimension(size)), visible);

		this.content = content;
	}

	public EDImage(Point location, int length, boolean useAsWidth, Image content, boolean visible)
	{
		super(new Rectangle(location, useAsWidth ? new Dimension(length, (int) (((float) length / content.getWidth(null)) * content.getHeight(null))) : new Dimension((int) (((float) length / content.getHeight(null)) * content.getWidth(null)), length)), visible);
		
		this.content = content;
	}

	public Image getContent()
	{
		return content;
	}

	public void setContent(Image content)
	{
		this.content = content;
	}

	@Override
	public void draw(Graphics g)
	{
		if(isVisible())
		{
			Rectangle bounds = getShape().getBounds();
			
			g.drawImage(getContent(), bounds.getLocation().x, bounds.getLocation().y, bounds.width, bounds.height, null);
		}
	}
}

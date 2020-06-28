package core.gui.special;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import core.gui.EDComponent;
import core.gui.design.Design;

public class EDImage extends EDComponent
{
	public EDImage(Design design, Point location, Image content, boolean visible)
	{
		super(design, "image", location, null, 0, null, 0, visible);
		
		Rectangle rect = new Rectangle(location.x, location.y, content.getWidth(null), content.getHeight(null));
		
		setShape(rect);
		setImage(content);
	}

	public EDImage(Design design, Point location, float scale, Image content, boolean visible)
	{
		super(design, "image", location, null, 0, null, 0, visible);
		
		Dimension scaled = new Dimension((int) (scale * content.getWidth(null)), (int) (scale * content.getHeight(null)));
		
		Rectangle rect = new Rectangle(location.x, location.y, scaled.width, scaled.height);
		
		setShape(rect);
		setImage(content);
	}

	public EDImage(Design design, Point location, Dimension size, Image content, boolean visible)
	{
		super(design, "image", location, null, 0, null, 0, visible);
		
		Rectangle rect = new Rectangle(location.x, location.y, size.width, size.height);
		
		setShape(rect);
		setImage(content);
	}

	public EDImage(Design design, Point location, int length, boolean useAsWidth, Image content, boolean visible)
	{
		super(design, "image", location, null, 0, null, 0, visible);
		
		Dimension scaled = useAsWidth ? new Dimension(length, (int) (((float) length / content.getWidth(null)) * content.getHeight(null))) : new Dimension((int) (((float) length / content.getHeight(null)) * content.getWidth(null)), length);
		
		Rectangle rect = new Rectangle(location.x, location.y, scaled.width, scaled.height);
		
		setShape(rect);
		setImage(content);
	}

	@Override
	public void onClick()
	{
		// Not implemented for images
	}

	@Override
	public void onHover()
	{
		// Not implemented for images
		
	}
}

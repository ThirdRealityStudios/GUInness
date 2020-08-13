package core.gui.special;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import core.Meta;
import core.gui.component.GComponent;

public class GImage extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GImage(Point location, Image content, boolean visible)
	{
		super("image", location, null, 0, null, null, visible);
		
		Rectangle rect = new Rectangle(location.x, location.y, content.getWidth(null), content.getHeight(null));
		
		getStyle().setShape(rect);
		getStyle().setImage(content);
	}

	public GImage(Point location, float scale, Image content, boolean visible)
	{
		super("image", location, null, 0, null, null, visible);
		
		Dimension scaled = new Dimension((int) (scale * content.getWidth(null)), (int) (scale * content.getHeight(null)));
		
		Rectangle rect = new Rectangle(location.x, location.y, scaled.width, scaled.height);
		
		getStyle().setShape(rect);
		getStyle().setImage(content);
	}

	public GImage(Point location, Dimension size, Image content, boolean visible)
	{
		super("image", location, null, 0, null, null, visible);
		
		Rectangle rect = new Rectangle(location.x, location.y, size.width, size.height);
		getStyle().setShape(rect);
		
		getStyle().setImage(content);
	}

	public GImage(Point location, int length, boolean useAsWidth, Image content, boolean visible)
	{
		super("image", location, null, 0, null, null, visible);
		
		Dimension scaled = useAsWidth ? new Dimension(length, (int) (((float) length / content.getWidth(null)) * content.getHeight(null))) : new Dimension((int) (((float) length / content.getHeight(null)) * content.getWidth(null)), length);
		
		Rectangle rect = new Rectangle(location.x, location.y, scaled.width, scaled.height);
		
		getStyle().setShape(rect);
		getStyle().setImage(content);
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

	@Override
	public void setValue(String val)
	{
		// Does nothing..
	}
}

package core.gui.decoration;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import core.gui.EDComponent;

public class EDImage extends EDComponent
{
	private Dimension size;

	private Image content;

	public EDImage(Point location, Image content, boolean visible)
	{
		super(location, visible);
		
		this.size = new Dimension(content.getWidth(null), content.getHeight(null));
		this.content = content;
	}

	public EDImage(Point location, float scale, Image content, boolean visible)
	{
		super(location, visible);

		this.size = new Dimension((int) (content.getWidth(null) * scale), (int) (content.getHeight(null) * scale));
		this.content = content;
	}

	public EDImage(Point location, Dimension size, Image content, boolean visible)
	{
		super(location, visible);

		this.size = size;
		this.content = content;
	}

	public EDImage(Point location, int length, boolean useAsWidth, Image content, boolean visible)
	{
		super(location, visible);

		int width = content.getWidth(null), height = content.getHeight(null);

		// float scale = useAsWidth ? (length/width) : (length/height);

		// this.size = new Dimension((int) (useAsWidth ? length : (width*scale)), (int)
		// (useAsWidth ? (height*scale) : height));

		Dimension widthDependent = new Dimension(length, (int) (((float) length / width) * height));

		Dimension heightDependent = new Dimension((int) (((float) length / height) * width), length);

		this.size = useAsWidth ? widthDependent : heightDependent;
		this.content = content;
	}

	public Dimension getSize()
	{
		return size;
	}

	public void setSize(Dimension size)
	{
		this.size = size;
	}

	public Image getContent()
	{
		return content;
	}

	public void setContent(Image content)
	{
		this.content = content;
	}

}

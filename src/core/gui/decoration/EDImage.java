package core.gui.decoration;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import core.gui.EDComponent;
import core.tools.gui.FontLoader;

public class EDImage extends EDComponent
{
	private Image content;
	
	private FontLoader fL;

	public EDImage(Point location, Image content, boolean visible)
	{
		super(new Rectangle(location), visible);
		
		fL = new FontLoader();
		
		getRectangle().setSize(new Dimension(content.getWidth(null), content.getHeight(null)));
		
		this.content = content;
	}

	public EDImage(Point location, float scale, Image content, boolean visible)
	{
		super(new Rectangle(location), visible);

		getRectangle().setSize(new Dimension((int) (content.getWidth(null) * scale), (int) (content.getHeight(null) * scale)));
		this.content = content;
	}

	public EDImage(Point location, Dimension size, Image content, boolean visible)
	{
		super(new Rectangle(location), visible);

		getRectangle().setSize(size);
		this.content = content;
	}

	public EDImage(Point location, int length, boolean useAsWidth, Image content, boolean visible)
	{
		super(new Rectangle(location), visible);

		int width = content.getWidth(null), height = content.getHeight(null);

		// float scale = useAsWidth ? (length/width) : (length/height);

		// this.size = new Dimension((int) (useAsWidth ? length : (width*scale)), (int)
		// (useAsWidth ? (height*scale) : height));

		Dimension widthDependent = new Dimension(length, (int) (((float) length / width) * height));

		Dimension heightDependent = new Dimension((int) (((float) length / height) * width), length);

		getRectangle().setSize(useAsWidth ? widthDependent : heightDependent);
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
			g.drawImage(getContent(), getRectangle().getLocation().x, getRectangle().getLocation().y, getRectangle().getSize().width, getRectangle().getSize().height, null);
	}
}

package org.thirdreality.guinness.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import org.thirdreality.guinness.handler.EventHandler;

public class Display extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Viewport viewport;

	private volatile EventHandler eH;
	
	public Display()
	{
		System.gc(); // This should just make up more space for this application.

		setTitle("GUInness - Default Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);

		eH = new EventHandler(this);

		eH.start();
	}

	public void center()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xScreenMiddle = (screen.width / 2), yScreenMiddle = (screen.height / 2);
		int xWindowMiddle = (getWidth() / 2), yWindowMiddle = (getHeight() / 2);
		
		int xMiddle = xScreenMiddle - xWindowMiddle, yMiddle = yScreenMiddle - yWindowMiddle;
		
		setLocation(xMiddle, yMiddle);
	}
	
	public EventHandler getEventHandler()
	{
		return eH;
	}
	
	public Viewport getViewport()
	{
		return viewport;
	}
	
	public void setViewport(Viewport viewport)
	{
		this.viewport = viewport;
		
		int size = getComponentCount();
		
		// Make sure the first component (assumed another JPanel by default, but NOT a viewport) is not removed from the Display (JFrame).
		if(size > 1)
		{
			remove(size - 1);
		}
		
		add(viewport);
		
		System.out.println("> " + getSize());
		
		viewport.setClippingArea(getSize());
	}
	
	@Override
	public void setSize(Dimension size)
	{
		super.setSize(size);
		
		if(hasViewport())
		{
			viewport.setClippingArea(size);
		}
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		if(hasViewport())
		{
			viewport.setClippingArea(new Dimension(width, height));
		}
	}
	
	public boolean hasViewport()
	{
		return viewport != null;
	}
}

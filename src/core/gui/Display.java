package core.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import core.gui.design.Classic;
import core.gui.design.Design;
import core.handler.EventHandler;

public class Display extends JFrame
{
	private Viewport viewport;

	private volatile EventHandler eH;
	
	public Display()
	{
		System.gc(); // This should just make up more space for this application.

		setTitle("Standard Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);

		eH = new EventHandler(this);

		eH.start();
		
		eH.enableKeyboardDriver();
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
		
		// Make sure the first component which is by default already added is not removed.
		if(size > 1)
		{
			remove(size - 1);
		}
		
		add(viewport);
	}
}

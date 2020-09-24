package org.thirdrealitystudios.guinness.handler;

import org.thirdrealitystudios.guinness.gui.Display;
import org.thirdrealitystudios.guinness.gui.adapter.KeyAdapter;
import org.thirdrealitystudios.guinness.gui.adapter.MouseAdapter;

public class EventHandler
{
	// All instances run from here generate ~30% of overall CPU usage
	
	private Display display = null;


	private ComponentHandler componentHandler = null;

	// Used to receive detailed information about the mouse movement.
	private MouseAdapter mouseAdapter = null;

	// Used to receive detailed information about the key interaction activity.
	private KeyAdapter keyAdapter = null;

	public EventHandler(Display display)
	{
		if (display != null)
			this.display = display;
		else
			throw new NullPointerException("Passed Display is null!");

		// Initialize the MouseAdapter with the Display context.
		// The Display context is needed for calculating front-end-window-related mouse data.
		mouseAdapter = new MouseAdapter(display);

		// After starting the driver (thread) you can receive movement data in real-time.
		mouseAdapter.getThread().start();

		// Initialize the KeyAdapter with the Display context.
		// The Display context is needed for getting front-end-window-related keyboard data.
		keyAdapter = new KeyAdapter(display);

		componentHandler = new ComponentHandler(display);
	}

	public void start()
	{
		componentHandler.getHandlingThread().getThread().start();
	}

	public MouseAdapter getMouseAdapter()
	{
		return mouseAdapter;
	}

	public KeyAdapter getKeyAdapter()
	{
		return keyAdapter;
	}
	
	public boolean isNoKeylistenerActive()
	{
		return display.getKeyListeners().length == 0;
	}

	public ComponentHandler getComponentHandler()
	{
		return componentHandler;
	}

	public Display getLayeredDisplay()
	{
		return display;
	}
}

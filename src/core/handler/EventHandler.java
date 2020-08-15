package core.handler;

import java.util.concurrent.CopyOnWriteArrayList;

import core.feature.Timer;
import core.gui.Display;
import core.gui.adapter.KeyAdapter;
import core.gui.adapter.MouseAdapter;
import core.gui.layer.GLayer;

public class EventHandler
{
	// ~30% overall CPU usage
	
	private Display display = null;

	private CopyOnWriteArrayList<GLayer> registeredLayers;

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

		registeredLayers = new CopyOnWriteArrayList<GLayer>();

		componentHandler = new ComponentHandler(display);
	}

	public synchronized void registerLayer(GLayer edL)
	{
		registeredLayers.add(edL);
	}

	public void unregisterLayer(String uuid)
	{
		int index = 0;

		for (GLayer current : registeredLayers)
		{
			if (current.getUUID().toString().equals(uuid))
				break;

			index++;
		}

		registeredLayers.remove(index);
	}

	public synchronized void unregisterAllLayers()
	{
		registeredLayers = new CopyOnWriteArrayList<GLayer>();
	}

	public void start()
	{
		componentHandler.getHandlingThread().getThread().start();
	}

	private void stopComponentHandler()
	{
		System.out.println("[EventHandler]: Stopping ComponentHandler");

		// Tell the component handler to stop (does not stop it directly).
		componentHandler.getHandlingThread().breakLoop();

		// Wait until the component handler has finished and stopped.
		while(componentHandler.getHandlingThread().getThread().isAlive())
		{
			System.out.println("[EventHandler]: ComponentHandler not responding.. [Breaking? " + componentHandler.getHandlingThread().tryingBreak() + "]");
			Timer.pauseSecond(1);
		};

		System.out.println("[EventHandler]: ComponentHandler successfully closed!");
	}

	private void stopMouseDriver()
	{
		System.out.println("[EventHandler]: Stopping MouseAdapter");

		// Tell the MouseAdapter to stop (does not stop it directly).
		mouseAdapter.breakLoop();

		// Wait until the MouseAdapter has finished and stopped.
		while(mouseAdapter.getThread().isAlive())
		{
			System.out.println("[EventHandler]: MouseAdapter not responding..");
			Timer.pauseSecond(1);
		};

		System.out.println("[EventHandler]: MouseAdapter successfully closed!");
	}

	// Stops the whole mechanism behind this which handles events of all kind of things, e.g. component events or periphery devices.
	public void stop()
	{
		// First need to stop the component handler before the dependent services can be stopped.
		stopComponentHandler();

		// After that the underlying services can be stopped first (because the dependence of these services is not given anymore).
		// Reason: The ComponentHandler steadily retrieves data from the drivers, so it has to be stopped first to avoid conflicts.
		stopMouseDriver();
	}

	public CopyOnWriteArrayList<GLayer> getRegisteredLayers()
	{
		return registeredLayers;
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

	public void disableKeyboardDriver()
	{
		if(!isNoKeylistenerActive())
		{
			display.removeKeyListener(keyAdapter);
		}
	}

	public void enableKeyboardDriver()
	{
		if(isNoKeylistenerActive())
		{
			display.addKeyListener(keyAdapter);
		}
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

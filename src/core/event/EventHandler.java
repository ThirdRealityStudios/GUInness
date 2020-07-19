package core.event;

import java.util.ArrayList;

import core.driver.KeyboardDriver;
import core.driver.MouseDriver;
import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.design.Design;
import core.io.Interrupt;

public class EventHandler
{
	// ~30% overall CPU usage
	
	private LayeredRenderFrame rF = null;

	private ArrayList<EDLayer> registeredLayers;

	private ComponentHandler componentHandler = null;

	// Used to receive detailed information about the mouse movement.
	private MouseDriver mouseDriver = null;

	// Used to receive detailed information about the keyboard activity.
	private KeyboardDriver keyboardDriver = null;

	public EventHandler(Design design, LayeredRenderFrame rF)
	{
		if (rF != null)
			this.rF = rF;
		else
			throw new NullPointerException("Passed RenderFrame is null!");

		// Initialize the mouse driver with the RenderFrame context.
		// The RenderFrame context is needed for calculating front-end-window-related mouse data.
		mouseDriver = new MouseDriver(rF);

		// After starting the driver (thread) you can receive movement data in real-time.
		mouseDriver.getThread().start();

		// Initialize the keyboard driver with the RenderFrame context.
		// The RenderFrame context is needed for getting front-end-window-related keyboard data.
		keyboardDriver = new KeyboardDriver(rF);

		registeredLayers = new ArrayList<EDLayer>();

		componentHandler = new ComponentHandler(design, this);
	}

	public synchronized void registerLayer(EDLayer edL)
	{
		registeredLayers.add(edL);
	}

	public void unregisterLayer(String uuid)
	{
		int index = 0;

		for (EDLayer current : registeredLayers)
		{
			if (current.getUUID().toString().equals(uuid))
				break;

			index++;
		}

		registeredLayers.remove(index);
	}

	public synchronized void unregisterAllLayers()
	{
		registeredLayers = new ArrayList<EDLayer>();
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
			Interrupt.pauseSecond(1);
		};

		System.out.println("[EventHandler]: ComponentHandler successfully closed!");
	}

	private void stopMouseDriver()
	{
		System.out.println("[EventHandler]: Stopping MouseDriver");

		// Tell the mouse driver to stop (does not stop it directly).
		mouseDriver.breakLoop();

		// Wait until the mouse driver has finished and stopped.
		while(mouseDriver.getThread().isAlive())
		{
			System.out.println("[EventHandler]: MouseDriver not responding..");
			Interrupt.pauseSecond(1);
		};

		System.out.println("[EventHandler]: MouseDriver successfully closed!");
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

	public ArrayList<EDLayer> getRegisteredLayers()
	{
		return registeredLayers;
	}

	public MouseDriver getMouseDriver()
	{
		return mouseDriver;
	}

	public KeyboardDriver getKeyboardDriver()
	{
		return keyboardDriver;
	}

	public ComponentHandler getComponentHandler()
	{
		return componentHandler;
	}

	public LayeredRenderFrame getLayeredRenderFrame()
	{
		return rF;
	}
}

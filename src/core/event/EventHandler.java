package core.event;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import core.Essentials;
import core.driver.MouseDriver;
import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.EDTextfield;
import core.gui.component.logic.DefaultButtonLogic;
import core.io.Interrupt;

public class EventHandler
{
	private LayeredRenderFrame rF = null;

	private ArrayList<EDLayer> registeredLayers;

	private ComponentHandler buttonHandler = null;
	
	// Used to receive detailed information about the mouse movement.
	private MouseDriver mouseDriver = null;

	public EventHandler(LayeredRenderFrame rF)
	{
		if (rF != null)
			this.rF = rF;
		else
			throw new NullPointerException("Passed RenderFrame is null!");
		
		// Initialize the mouse driver with the RenderFrame context.
		// The RenderFrame context is needed for calculating front-end-window-related mouse data.
		mouseDriver = new MouseDriver(rF);
		
		// After starting the driver you can receive movement information in real-time.
		mouseDriver.getThread().start();

		registeredLayers = new ArrayList<EDLayer>();
		
		buttonHandler = new ComponentHandler(this);
	}
	
	public void reset(EDTextfield text)
	{
		if(text.getBufferedValue() != null)
			text.setValue(text.getBufferedValue());

		text.setBackground(text.getBufferedColor());
		text.setBufferedColor(null);

		text.setBackground(text.getBackground());
		text.setInactive();
	}
	
	private void enableInput(EDTextfield edT)
	{
		if(edT.getBufferedColor() == null)
		{
			edT.setBufferedColor(edT.getBackground());
			edT.setBackground(edT.getActiveColor());
		}

		edT.setActive();
		edT.setBufferedValue(edT.getValue()); /* 
											   * Save current value to restore it if there
		 									   * will be
		  									   * no changes saved.
		  									   */

		edT.onClick();
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
		buttonHandler.getHandlingThread().getThread().start();
	}

	public void stop()
	{
		System.out.println("[EventHandler]: Stopping ButtonHandler");
		
		// Tell the button handler to stop (does not stop it directly).
		buttonHandler.getHandlingThread().breakLoop();
		
		// Wait until the button handler has finished and stopped.
		while(buttonHandler.getHandlingThread().getThread().isAlive())
		{
			System.out.println("[EventHandler]: ButtonHandler not responding..");
			Interrupt.pauseSecond(1);
		};
		
		System.out.println("[EventHandler]: ButtonHandler successfully closed!");
	}

	public ArrayList<EDLayer> getRegisteredLayers()
	{
		return registeredLayers;
	}
	
	public MouseDriver getMouseDriver()
	{
		return mouseDriver;
	}
	
	public LayeredRenderFrame getLayeredRenderFrame()
	{
		return rF;
	}
}

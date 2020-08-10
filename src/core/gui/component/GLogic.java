package core.gui.component;

import java.io.Serializable;

import core.Meta;

public class GLogic implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private volatile boolean interactable = true, actsOnClick = true, actsOnHover = true;

	// When having this activated, all click and hover actions will be executed using the concept of multithreading.
	// Anyway, if you have specified a delay it will be recognized too.
	// Also, using multithreading only makes sense at big tasks for example.
	private volatile boolean multithreading = false;
	
	// Decide whether it is allowed to run the same method multiple times when the component is clicked a specific time..
	// Is not compatible with all GComponents.
	private boolean doubleClickingAllowed = false;
	
	// This means, in best case scenario you are able to interact with the component
	// 4 times a second or 4 Hz.
	// How you can interact with it depends of course on the type of the component.
	// Not all components support this feature, e.g. text-fields.
	// Also, from this point the delay is currently only compatible with buttons and
	// similar components which will probably follow in future.
	private int delayMs = 0;
	
	public synchronized void setInteractable(boolean interactable)
	{
		this.interactable = interactable;
	}
	
	public boolean isInteractionAllowed()
	{
		return interactable;
	}
	
	public void setActionOnClick(boolean acts)
	{
		this.actsOnClick = acts;
	}
	
	public boolean isActingOnClick()
	{
		return actsOnClick;
	}
	
	public void setActionOnHover(boolean acts)
	{
		this.actsOnHover = acts;
	}
	
	public boolean isActingOnHover()
	{
		return actsOnHover;
	}
	
	public void setMultithreading(boolean multithreading)
	{
		this.multithreading = multithreading;
	}
	
	public boolean isMultithreadingOn()
	{
		return multithreading;
	}
	
	public void setDoubleClickingAllowed(boolean allowed)
	{
		doubleClickingAllowed = allowed;
	}
	
	public boolean isDoubleClickingAllowed()
	{
		return doubleClickingAllowed;
	}
	
	public void setDelayMs(int delay)
	{
		if(delay >= 0)
		{
			delayMs = delay;
		}
	}
	
	public int getDelayMs()
	{
		return delayMs;
	}
}

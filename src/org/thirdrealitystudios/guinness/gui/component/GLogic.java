package org.thirdrealitystudios.guinness.gui.component;

import java.io.Serializable;

import org.thirdrealitystudios.Meta;

public class GLogic implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	// Tells you whether the component is recognized when focused by the mouse.
	// For example:
	// if you click on a button but it is unfocusable then no click will be recognized on it.
	// It is very useful to disable if you intend to ignore the priority system or the layers priorities respectively.
	// Also an example:
	// You want to put a slightly transparent GRectangle in front of a button to darken its appearance.
	// Now if you want to click the button this is normally not possible unless you tell the system not to recognize the GRectangle.
	// The thing is, the GRectangle has a higher priority than the button and it is on a different layer.
	// That means, the GRectangle is recognized first before the button even would have a "chance" to be detected.
	// Component focusing logic also works the way "The winner takes it all" which means all other components at the same cursor position are unimportant in the end.
	private boolean focusable = true;
	
	// You might find the interactability similar to the ability of an component to be focusable but there are big differences!
	// The interactiblity here describes only whether a component is focused related to its its user defined actions which should be run.
	// For example:
	// you click on a button whereas the user-defined "onClick()"-method is executed.
	// That means in the end that the "focusable" variable (above!) only treats the general ability if it is focusable but the "interactable" variable on the other hand relates to the user-defined actions (whether they can be run),
	// such as "onClick()" or "onHover()".
	private boolean interactable = true;
	
	private boolean actsOnClick = true, actsOnHover = true;

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

	public boolean isFocusable()
	{
		return focusable;
	}

	public void setFocusable(boolean isFocusable)
	{
		this.focusable = isFocusable;
	}
}

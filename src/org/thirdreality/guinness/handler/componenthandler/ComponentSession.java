package org.thirdreality.guinness.handler.componenthandler;

import org.thirdreality.guinness.gui.Viewport;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.input.GTextfield;

// A ComponentSession is a session which keeps track of all necessary data, e.g. which was the last component which was clicked on a Display or GWindow?
public class ComponentSession
{
	// If there was a text-field selected, it will be stored here for a time.
	private GTextfield textfield;

	// Tells whether a component was clicked before.
	private GComponent clickedYet = null;

	// Tells by using 'clickedYet' whether the checked component was double clicked.
	private boolean doubleClicked = false;

	// This is the lastly focused component from the previous cycle always.
	private GComponent lastlyFocused;

	private boolean doubleHovered;

	private GComponent hoveredYet;
	
	// Tracking helps you to differ multiple GComponentSessions from each other if there are multiple Viewports..
	private final Viewport trackedViewport;
	
	public ComponentSession()
	{
		// No Viewport is being tracked.
		// This is a "general purpose" session, a.k.a the "main session".
		trackedViewport = null;
	}
	
	public ComponentSession(Viewport trackedViewport)
	{
		// You track a Viewport if you pass one here to the constructor.
		this.trackedViewport = trackedViewport;
	}
	
	public boolean isOwnedBy(Viewport checked)
	{
		return trackedViewport == checked;
	}

	public GTextfield getFocusedTextfield()
	{
		return textfield;
	}

	public void setFocusedTextfield(GTextfield textfield)
	{
		this.textfield = textfield;
	}

	public GComponent getYetClickedComponent()
	{
		return clickedYet;
	}

	public void setYetClickedComponent(GComponent component)
	{
		this.clickedYet = component;
	}

	public boolean isFocusedComponentDoubleClicked()
	{
		return doubleClicked;
	}

	public void setFocusedComponentDoubleClicked(boolean doubleClicked)
	{
		this.doubleClicked = doubleClicked;
	}

	public GComponent getLastlyFocusedComponent()
	{
		return lastlyFocused;
	}

	public void setLastlyFocusedComponent(GComponent component)
	{
		this.lastlyFocused = component;
	}

	public boolean isFocusedComponentDoubleHovered()
	{
		return doubleHovered;
	}

	public void setFocusedComponentDoubleHovered(boolean doubleHovered)
	{
		this.doubleHovered = doubleHovered;
	}

	public GComponent getYetHoveredComponent()
	{
		return hoveredYet;
	}

	public void setYetHoveredComponent(GComponent component)
	{
		this.hoveredYet = component;
	}

	// This simply returns the tracked Viewport.
	public Viewport getTrackedViewport()
	{
		return trackedViewport;
	}
}

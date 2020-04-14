package core.event;

import java.awt.MouseInfo;
import java.awt.Point;

import core.Essentials;
import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.logic.DefaultButtonLogic;

public class ComponentHandler
{
	private LoopedThread handler = null;
	
	private DefaultButtonLogic defaultButtonLogic = null;
	
	private EventHandler eventHandler = null;
	
	private EDText focusedComponent, remember;
	
	public ComponentHandler(EventHandler eventHandler)
	{
		// Used to tell the ComponentHandler how to evaluate all buttons.
		// The mouse driver is used to provide the DefaultButtonLogic with all necessary information.
		this.defaultButtonLogic = new DefaultButtonLogic(eventHandler.getMouseDriver());
		
		this.eventHandler = eventHandler;
		
		handler = new LoopedThread()
		{
			@Override
			public void loop()
			{
				triggerComponent();
			}
		};
	}
	
	public LoopedThread getHandlingThread()
	{
		return handler;
	}
	
	// Tests whether the mouse cursor (relative to the RenderFrame) is inside the given component.
	// Returns 'false' if target is 'null'.
	public boolean isCursorInsideComponent(EDText target)
	{
		// If there is no component given,
		// this method assumes no component was found,
		// so the cursor is not over a component.
		if(target == null)
			return false;
		
		LayeredRenderFrame rF = eventHandler.getLayeredRenderFrame();

		// The current absolute mouse position on screen.
		Point desktopCursor = MouseInfo.getPointerInfo().getLocation();
		
		// Frame offset for the relative cursor position.
		Point frameOffset = new Point(-8, -31);
		
		// The current mouse position relative to the JFrame.
		Point frameCursor = new Point(desktopCursor.x - rF.getLocation().x + frameOffset.x, desktopCursor.y - rF.getLocation().y + frameOffset.y);
		
		return target.getRectangle().contains(frameCursor);
	}
	
	// Returns the first component which is focused by the cursor.
	// Makes the UI more efficient by breaking at the first component already.
	// Returns null if there is no such component.
	public EDText getFocusedComponent()
	{
		EDText firstMatch = null;
		
		for(EDLayer layer : eventHandler.getRegisteredLayers())
		{
			for(EDText selected : layer.getTextBuffer())
			{
				boolean insideComponent = isCursorInsideComponent(selected);
				
				// Returns the first component which is focused by the mouse cursor.
				if(insideComponent)
				{
					firstMatch = selected;
					
					break;
				}
				else // Otherwise when the cursor is not inside a component it keeps looking..
				{
					continue;
				}
			}
		}
		
		// Returns the first component which is focused by the mouse cursor.
		return firstMatch;
	}
	
	// Mainly triggers or resets components if the conditions are given. The given 'logic' is applied to the components.
	protected void triggerComponent()
	{
		/*
		 * Looks whether the user left the area of a component.
		 * In such a case,
		 * the component needs to be reseted,
		 * e.g. the color of the background etc. 
		 */
		if(!isCursorInsideComponent(focusedComponent) && remember != null)
		{
			reset(remember);
			
			remember = null;
		}
		
		// After that it will just focus the component at the position of the cursor, if there is one actually.
		focusedComponent = getFocusedComponent();
		
		// It will look if the 'remember' variable can be updated with a newly focused component.
		// The 'remember' variable is then used later to restore the components original values if the user exits it.
		if(remember == null && focusedComponent != null)
		{
			remember = focusedComponent;
		}
		
		// If there is no focused component by the cursor, return directly.
		// It would make no sense to make further evaluations which could cost the performance of the application.
		if(focusedComponent == null)
		{
			return;
		}
		// For the case, there is a component, it will just continue below with evaluations, component logic etc...
		
		
		switch(Essentials.typeof(focusedComponent))
		{
		case "EDButton":
			{
				EDButton button = (EDButton) focusedComponent;
				
				defaultButtonLogic.exec(button);
			}
		break;
		}
	}
	
	public void reset(EDText target)
	{
		if(target.getBufferedColor() != null)
		{
			target.setBackground(target.getBufferedColor());
			target.setBufferedColor(null);
		}
	}
}

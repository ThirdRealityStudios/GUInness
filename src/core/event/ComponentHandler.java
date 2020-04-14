package core.event;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;

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
	
	private EDText focusedComponent, store;
	
	private boolean clickedBefore = false;
	
	private EDText focusedComponentBefore = null;
	
	// This will determine what EasyDraw component types can be reseted.
	// Normally,
	// this is just the EDButton type.
	// So, when the user leaves the area of a button,
	// it will reset the color etc.
	private ArrayList<String> resetableTypes = new ArrayList<String>();
	
	public ComponentHandler(EventHandler eventHandler)
	{
		initResetTypes(); // Adds all resetable EasyDraw component types.
		
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
	
	// Checks whether the cursor is over any EasyDraw component.
	public boolean isCursorInsideAnyComponent()
	{
		for(EDLayer layer : eventHandler.getRegisteredLayers())
		{
			for(EDText selected : layer.getTextBuffer())
			{
				boolean insideComponent = isCursorInsideComponent(selected);
				
				// If there was on match at least, you will know that there is a component which is focused by the cursor.
				if(insideComponent)
				{
					return true;
				}
			}
		}
		
		return false;
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
			}
		}
		
		// Returns the first component which is focused by the mouse cursor.
		return firstMatch;
	}
	
	// This method will reset the given types by reverting the background colors.
	// Adding types, will only affect the background color.
	private void initResetTypes()
	{
		resetableTypes.add("EDButton");
	}
	
	// Before another component can be focused, there is some pre-processing neccessary.
	// Until now, this is mainly treating the buttons.
	// For example,
	// it will look if the background colors can be restored.
	private void preprocess()
	{
		// Retrieve the type of the focused component (if there is one component focused actually => otherwise it is 'null')
		String type = store != null ? Essentials.typeof(store) : "";
		
		// Self-explaining but this is just the evaluation whether the previously focused component can be reseted.
		// For example,
		// you want a button to be reseted with all its colors when the user exits the area.
		// For a text-field, on the other hand,
		// that is something you do not actually want because it is treated differently.
		boolean isFocusedTypeResetable = store != null && resetableTypes.contains(type);
		
		// Explained directly below in the block-comment.
		boolean isResetNecessary = !isCursorInsideComponent(focusedComponent) && isFocusedTypeResetable;
		
		/*
		 * Looks whether the user left the area of a component.
		 * In such a case,
		 * the component needs to be reseted,
		 * e.g. the color of the background etc.
		 * It knows the 'reset event' by looking whether there was a change in the focused component.
		 */
		if(isResetNecessary)
		{
			// Resets the previously focused component after the user left its area.
			reset(store);
			
			// Now there is no previous component anymore when it was reseted before.
			// By doing so (assigning 'null'), the 'mainStore' variable now can be used to remember a newly focused component (like the object at the second next assignment of 'focusedComponent' below). 
			store = null;
		}
	}
	
	// Saves necessary data about the mouse, e.g. position, if it was clicked and which was the last known component what was clicked.
	private void saveHistoricValues()
	{
		clickedBefore = eventHandler.getMouseDriver().isClicking();
		
		boolean uninitialized = focusedComponentBefore == null && focusedComponent != null;
		
		boolean changedMeta = focusedComponentBefore != null && focusedComponent != null && !focusedComponent.equals(focusedComponentBefore);
		
		// Look if it is possible to assign the current component, if there is one actually and if it is a different one from the stored one.
		if(uninitialized || changedMeta)
			focusedComponentBefore = focusedComponent;
	}
	
	// Mainly triggers or resets components if the conditions are given. The given 'logic' is applied to the components.
	private void triggerComponent()
	{
		// Mainly pre-processes the buttons etc. to make them ready to use for the current cycle.
		preprocess();
		
		// After that it will just focus the component at the position of the cursor, if there is one actually.
		// Otherwise this can also be 'null', meaning no component is focused.
		focusedComponent = getFocusedComponent();
		
		// Looks whether the remembered component is still the same.
		// Without this validation the 'reset event' would not work correctly.
		// The 'reset event' would believe there is still the same component if you directly hover over a component which is aligned next to the one you were at with the cursor before.
		// Summed up, this will check if the identities of the hovered components are indeed the same.
		boolean areIdentitiesDifferent = store != null && focusedComponent != null && !focusedComponent.equals(store);
		
		// Checks whether a component can be remembered or stored.
		boolean isStoreEmpty = store == null && focusedComponent != null;
		
		// It will look if the 'mainStore' variable can be updated with a newly focused component.
		if(isStoreEmpty || areIdentitiesDifferent)
		{
			// With this assignment, the 'mainStore' variable is then used later to restore the components original values if the user exits it.
			store = focusedComponent;
		}
		
		// Looks whether a user clicked outside the component before and is still clicking.
		// In this case, the click is invalid.
		boolean invalidClick = clickedBefore;

		// If there is no focused component by the cursor, return directly.
		// It would make no sense to make further evaluations which could cost the performance of the application.
		if(focusedComponent == null)
		{
			saveHistoricValues();
			
			return;
		}
		// For the case, there is a component, it will just continue below with evaluations, component logic etc...
		
		
		/*
		 * Before a click action can actually be processed you will need to know if it is (technically or internally) a valid click.
		 * A 'valid click' is for example the case,
		 * when a user does not (!) hold the mouse button pressed and enter a button afterwards.
		 * If this would not be recognized,
		 * you could trigger button before you actually hover over it first.
		 * That is something,
		 * which can be very annoying if it is not prevented.
		 * Also it is unusual or not commonly done this way.
		 */
		if(!invalidClick)
		{
			// With the control switch, it determines what type of component was focused, 
			// e.g. a button or text-field.
			// The actions in between decide what to do for each type.
			switch(Essentials.typeof(focusedComponent))
			{
			case "EDButton":
				{
					EDButton button = (EDButton) focusedComponent;
					
					defaultButtonLogic.exec(button);
				}
			break;
			
			default:
				// nothing.
			}
		}
		
		saveHistoricValues();
	}
	
	// Resets the values of the background color.
	// Can be the case if the component or button is exited per cursor or such.
	public void reset(EDText target)
	{
		if(target.getBufferedColor() != null)
		{
			target.setBackground(target.getBufferedColor());
			target.setBufferedColor(null);
		}
	}
}

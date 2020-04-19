package core.event;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.Essentials;
import core.driver.MouseDriver;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.EDTextfield;
import core.gui.component.logic.DefaultButtonLogic;
import core.gui.component.logic.DefaultTextfieldLogic;

public class ComponentHandler
{
	// The 'handler'-thread is used to make all components working by checking the state, make evaluations and then decide what to do next, e.g. when a user clicked a button.
	// Because of that, the 'handler'-thread has a high priority.
	// It actually needs to run as fast as possible in order not to miss user interactions etc.
	private LoopedThread handler = null;
	
	// These logic classes define how each EasyDraw type reacts on user interaction.
	// It is very simplified in order to make modifications easy, e.g. adding new components.
	private DefaultButtonLogic defaultButtonLogic = null;
	private DefaultTextfieldLogic defaultTextfieldLogic = null;
	
	// If a text-field was selected, it will be saved as long as the user does not exit it.
	private volatile EDTextfield selectedTextfield = null;
	
	// The 'eventHandler' variable is the reference to the superior service (EventHandler) which provides all necessary references to have access to all services on the upper layer.
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
		
		// Used as a temporary reference to pass trough the MouseDriver of the EventHandler.
		MouseDriver mouseDriver = eventHandler.getMouseDriver();
		
		// The implemented ComponentLogic classes are used to tell the ComponentHandler how to treat each type of component.
		// The MouseDriver is used here to serve the logic classes below with all necessary information about the mouse, e.g. cursor position, if it is clicking etc.
		// If a EventHandler is passed, then the logic will use more than just a mouse driver, e.g. keyboard driver etc.
		this.defaultButtonLogic = new DefaultButtonLogic(mouseDriver);
		this.defaultTextfieldLogic = new DefaultTextfieldLogic(eventHandler);
		
		// The EventHandler is, let's say, the superior class which serves the ComponentHandler as a main source of information for events and management of everything.
		this.eventHandler = eventHandler;
		
		// Here a thread is created which just serves this class to refresh all retrievable information on components.
		handler = new LoopedThread()
		{
			@Override
			public void loop()
			{				
				triggerComponent();
			}
		};
	}
	
	// Returns the handling thread, so the thread which frequently handles all components to make them work.
	public LoopedThread getHandlingThread()
	{
		return handler;
	}
	
	// This method will reset the given types by reverting the background colors.
	// Adding types, will only affect the background color.
	private void initResetTypes()
	{
		resetableTypes.add("EDButton");
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
		boolean isResetNecessary = !eventHandler.getMouseDriver().isFocusing(focusedComponent) && isFocusedTypeResetable;
		
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
	
	// Mainly triggers or resets components if the conditions are given. The given 'logic' is applied to the components.
	private void triggerComponent()
	{
		// Mainly pre-processes the buttons etc. to make them ready to use for the current cycle.
		preprocess();
		
		// After that it will just focus the component at the position of the cursor, if there is one actually.
		// Otherwise this can also be 'null', meaning no component is focused.
		focusedComponent = eventHandler.getMouseDriver().getFocusedComponent();
		
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
		
		// For the case, there is a component, it will just continue below with evaluations, component logic etc...
		
		/*
		 * Notice: an 'invalid click'-check is only applicable for a number of supported types (a.k.a 'resetable types').
		 * 
		 * Before a click action can actually be processed you will need to know if it is (technically or internally) a valid click.
		 * A 'valid click' is for example the case,
		 * when a user does not (!) hold the mouse button pressed and enter a button afterwards.
		 * If this would not be recognized,
		 * you could trigger button before you actually hover over it first.
		 * That is something,
		 * which can be very annoying if it is not prevented.
		 * Also it is unusual or not commonly done this way.
		 */
		if(!invalidClick) // Notice: this will represent the last known click!
		{
			String type = Essentials.typeof(focusedComponent);
			
			int keyStroked = eventHandler.getKeyboardDriver().getActiveKey();
			
			boolean isTextfieldSelected = selectedTextfield != null,
					focusedNothing = focusedComponent == null,
					focusedDifferentType = focusedComponent != null && !focusedComponent.equals(selectedTextfield),
					saveAndExit = focusedNothing && eventHandler.getMouseDriver().isClicking() || focusedDifferentType && eventHandler.getMouseDriver().isClicking(),
					revertAndExit = keyStroked == KeyEvent.VK_ESCAPE;
			
			// The "active color" is the color which is applied to the written part of the text when a user has clicked on the text-field.
			boolean switchToActiveColor = !selectedTextfield.isActive();
			
			if(isTextfieldSelected)
			{
				if(switchToActiveColor)
				{
					selectedTextfield.setActive();
				}
				
				if(saveAndExit)
				{
					selectedTextfield.save();
					defocusTextfield();
				}
				else if(revertAndExit)
				{
					selectedTextfield.revert();
					defocusTextfield();
				}
				else
				{
					char keyChar = (char) keyStroked;
					
					if(Essentials.isAlphanumeric(keyChar))
						selectedTextfield.write(keyChar);
				}
				
				return;
			}
			
			// With the control switch, it determines what type of component was focused, 
			// e.g. a button or text-field.
			// The actions in between decide what to do for each type.
			switch(type)
			{
			case "EDButton":
				{
					EDButton button = (EDButton) focusedComponent;
				
					defaultButtonLogic.exec(button);
				}
			break;
			
			case "EDTextfield":
				{
					EDTextfield textfield = (EDTextfield) focusedComponent;
					
					// Select the focused text-field if it is being clicked.
					if(eventHandler.getMouseDriver().isClicking())
						selectedTextfield = textfield;
				
					defaultTextfieldLogic.exec(textfield);
				}
			break;
			
			case "EDText":
			{
				if(eventHandler.getMouseDriver().isClicking())
					System.out.println("Clicked a EDText component");
			}
			break;
			
			// Cursor is outside any component.
			default:
				{					
					if(eventHandler.getMouseDriver().isClicking())
						System.out.println("Clicked at nothing");
				}
			}
		}
		
		saveHistoricValues();
	}
	
	private void defocusTextfield()
	{
		// Make sure, the background color is reseted to the original one.
		// Thats the color which is applied when a user has exited or unfocused a text-field.
		selectedTextfield.setInactive();
		
		selectedTextfield = null;
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

package core.gui.component.logic;

import java.awt.event.KeyEvent;

import core.driver.KeyboardDriver;
import core.driver.MouseDriver;
import core.event.ComponentHandler;
import core.event.EventHandler;
import core.gui.EDText;
import core.gui.component.EDTextfield;
import core.gui.component.Logic;

public class DefaultTextfieldLogic implements ComponentLogic
{
	private Logic logic = null;
	
	private EDTextfield focused = null;
	
	private EventHandler eventHandler;
	
	private MouseDriver mouseDriver;
	
	private KeyboardDriver keyboardDriver;
	
	private ComponentHandler componentHandler;
	
	public DefaultTextfieldLogic(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
		
		componentHandler = eventHandler.getComponentHandler();
		
		mouseDriver = eventHandler.getMouseDriver();
		keyboardDriver = eventHandler.getKeyboardDriver();
		
		logic = new Logic()
		{
			@Override
			public void exec(Object target)
			{
				EDTextfield targetED = (EDTextfield) target;
				
				boolean notClickedAnyTextfieldYet = mouseDriver.isClicking() && focused == null,
						clickedTheSameFieldAgain = mouseDriver.isClicking() && (focused == targetED),
						clickedAnotherField = mouseDriver.isClicking() && focused != null;

				if(notClickedAnyTextfieldYet)
				{
					// Save or remember the text-field which was clicked.
					//System.out.println("Clicked a text-field now");
					
					focused = targetED;
				}
				else if(clickedTheSameFieldAgain)
				{
					//System.out.println("Clicked the same text-field again");
				}
				else if(clickedAnotherField)
				{
					//System.out.println("Clicked another text-field");
					
					focused = targetED;
				}
			}
		};
	}
	
	@Override
	public void exec(EDText target)
	{
		logic.exec(target);
	}
}
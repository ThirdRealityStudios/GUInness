package core.gui.component.logic;

import java.awt.Color;

import core.driver.MouseDriver;
import core.gui.component.EDButton;
import core.gui.component.Logic;
import core.io.Interrupt;

public class DefaultButtonLogic implements ButtonLogic
{
	private Logic logic = null;
	
	private MouseDriver mouseDriver = null;
	
	public DefaultButtonLogic(MouseDriver mouseDriver)
	{
		this.mouseDriver = mouseDriver;
		
		logic = new Logic()
		{
			@Override
			public void exec(Object target)
			{
				EDButton targetED = (EDButton) target;
				
				// Make sure, the original color is saved to restore when the user exits the area of the button.
				if(targetED.getBufferedColor() == null)
				{
					targetED.setBufferedColor(targetED.getBackground());
				}
				
				// Simply, when a user clicks on a button.
				if(mouseDriver.isClicking())
				{
					// Change the current color of the button but save it before to restore later.
					targetED.setBackground(targetED.getActiveColor());
					
					// The duration of the color which should appear on the button when clicked.
					Interrupt.pauseMillisecond(200);
				}
				else
				{
					// Otherwise just highlight the button with the hover color.
					targetED.setBackground(targetED.getHoverColor());
				}
			}
		};
	}
	
	public void reset(EDButton target)
	{
		// When there was a change in color, restore it.
		if(target.getBufferedColor() != null)
		{
			target.setBackground(target.getBufferedColor());
			target.setBufferedColor(null);
		}
	}
	
	@Override
	public void exec(EDButton target)
	{
		logic.exec(target);
	}
}
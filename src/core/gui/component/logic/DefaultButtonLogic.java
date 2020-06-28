package core.gui.component.logic;

import java.util.Stack;

import core.driver.MouseDriver;
import core.gui.EDComponent;
import core.gui.component.Logic;
import core.gui.component.standard.EDButton;
import core.gui.design.Classic;
import core.gui.design.Design;
import core.io.Interrupt;

public class DefaultButtonLogic implements ComponentLogic
{
	private Logic logic = null;
	
	private static final byte maxActions = 2;
	
	private static Stack<Thread> actions = new Stack<Thread>();
	
	private Design design;
	
	public DefaultButtonLogic(Design design, MouseDriver mouseDriver)
	{
		logic = new Logic()
		{
			@Override
			public void exec(Object target)
			{
				manageActionList();
				
				EDButton targetED = (EDButton) target;
				
				// Make sure, the original color is saved to restore when the user exits the area of the button.
				if(targetED.getBufferedColor() == null)
				{
					targetED.setBufferedColor(design.getBackgroundColor());
				}
				
				// Simply, when a user clicks on a button.
				if(mouseDriver.isClicking())
				{					
					// Execute the corresponding action when a user clicks the given button.
					targetED.onClick();
					
					// Only execute the implemented 'click action' when it is allowed/wanted.
					if(targetED.actsOnClick())
					{
						Thread clickAnimation = new Thread()
						{
							@Override
							public void run()
							{
								// Change the current color of the button but save it before to restore later.
								targetED.setPrimaryColor(design.getActiveColor());
								
								// The duration of the color which should appear on the button when clicked.
								Interrupt.pauseMillisecond(200);
							}
						};
						
						clickAnimation.start();
					}
				}
				else
				{
					// Otherwise just highlight the button with the hover color.
					targetED.setPrimaryColor(design.getHoverColor());
					
					// Only execute the implemented 'hover action' when it is allowed/wanted.
					if(targetED.actsOnHover() && actions.size() < maxActions)
					{
						Thread hoverAction = new Thread()
						{
							@Override
							public void run()
							{
								// Execute the corresponding action when a user hovers the given button.
								targetED.onHover();
								
								// The duration of the color which should appear on the button when clicked.
								Interrupt.pauseMillisecond(200);
							}
						};
						
						actions.push(hoverAction);
					}
				}
			}
		};
	}
	
	private void printActions()
	{
		System.out.println();
		
		for(Thread t : actions)
		{
			System.out.print("> " + t.hashCode());
		}
	}
	
	// Makes sure, there is again space for new actions if one thread is dead already.
	public void manageActionList()
	{
		for(int i = 0; i < actions.size(); i++)
		{
			Thread action = actions.get(i);
			
			if(action != null)
			{
				if(action.getState().equals(Thread.State.NEW))
				{
					action.start();
				}
				else if(!action.isAlive())
				{
					actions.remove(i);
				}
			}
		}
	}
	
	public void reset(EDButton target)
	{
		// When there was a change in color, restore it.
		if(target.getBufferedColor() != null)
		{
			target.setPrimaryColor(target.getBufferedColor());
			target.setBufferedColor(null);
		}
	}

	@Override
	public void exec(EDComponent target)
	{
		logic.exec(target);
	}

	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design design)
	{
		this.design = design;
	}
}
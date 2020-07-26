package core.event;

import java.awt.event.KeyEvent;

import core.frame.LayeredRenderFrame;
import core.gui.EDComponent;
import core.io.Interrupt;
import core.thread.LoopedThread;
import core.thread.ThreadManager;

public class ComponentHandler
{
	// The 'handler'-thread is used to make all components working by checking the state, make evaluations and then decide what to do next, e.g. when a user clicked a button.
	// Because of that, the 'handler'-thread has a high priority.
	// It actually needs to run as fast as possible in order not to miss user interactions etc.
	private LoopedThread handler = null;

	private LayeredRenderFrame renderFrame;

	// This is the component to be remembered, e.g. when user has left the area of a button already.
	private EDComponent lastCycle;

	private ThreadManager hoverTManager, clickTManager;

	// Takes care about that only one time one thread is running for updating the screen (repaint).
	private ThreadManager updateTManager;

	// If there was a text-field selected, it will be stored here for a time.
	private EDComponent textfield;
	
	// This is the lastly focused component from the previous cycle always.
	private EDComponent lastlyFocused;

	public ComponentHandler(LayeredRenderFrame renderFrame)
	{
		this.renderFrame = renderFrame;
		
		final int maximumThreads = 2;
		
		this.hoverTManager = new ThreadManager(maximumThreads);
		this.clickTManager = new ThreadManager(maximumThreads);
		
		this.updateTManager = new ThreadManager(1);

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

	private void executeClick(EDComponent execute)
	{
		if(execute.isRealtimeExecutionOn())
		{
			Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or interactions with the UI.
			{
				@Override
				public void run()
				{
					execute.onClick();
				}
			};
					
			clickTManager.fire(t);
		}
		else
		{
			execute.onClick();
		}
		
		Interrupt.pauseMillisecond(execute.getDelayMilliseconds());
	}
	
	private void executeHover(EDComponent execute)
	{
		if(execute.isRealtimeExecutionOn())
		{
			Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or interactions with the UI.
			{
				@Override
				public void run()
				{					
					execute.onHover();
				}
			};

			hoverTManager.fire(t);
		}
		else
		{
			execute.onHover();
		}
		
		Interrupt.pauseMillisecond(execute.getDelayMilliseconds());
	}
	
	// Is responsible for firing the implemented functions by the component.
	private int triggerGeneralLogic(EDComponent focused, boolean clicking, int keyStroke)
	{
		int graphicalChanges = 0;
		
		if(clicking) // relates to text-fields only.
		{
			boolean canTextfieldBeFocussed = focused != null && focused.getType().contentEquals("textfield");
			
			if(canTextfieldBeFocussed)
			{
				textfield = focused;
			}
			
			boolean shouldDefocusIt = focused != textfield;
			
			if(shouldDefocusIt)
			{
				textfield = null;
			}
		}

		if(textfield != null)
		{
			boolean isDeviceControlCode = textfield.getDesign().getFontLoader().isDeviceControlCode(keyStroke);
			
			if(isDeviceControlCode && !textfield.isCursorAtEnd())
			{
				textfield.write((char) keyStroke);
				
				graphicalChanges++;
			}
			else
			{
				switch(keyStroke)
				{
					case KeyEvent.VK_BACK_SPACE:
					{
						if(!textfield.isCursorAtBeginning())
						{
							textfield.eraseLastChar();
							
							graphicalChanges++;
						}
						
						break;
					}
				}
			}
		}

		if(focused != null)
		{
			if(focused.actsOnHover()) // ask whether it should run the onHover() method if wished by the components configuration.
			{
				// This will decide internally whether the component is being executed by threads or in sequence order.
				executeHover(focused);
			}

			if(clicking && focused.actsOnClick()) // ask whether it should run the onClick() method if wished by the components configuration.
			{
				// This will decide internally whether the component is being executed by threads or in sequence order.
				executeClick(focused);
			}
		}

		return graphicalChanges;
	}
	
	private int triggerAnimation(EDComponent focused, boolean clicking)
	{
		if(focused != null)
		{
			switch(focused.getType())
			{
				case "button":
				{
				    // The next two booleans prevent the redraw algorithm to run again if there was no change in color..
					boolean activeColorIsSame = focused.getPrimaryColor().equals(focused.getDesign().getActiveColor());
					boolean hoverColorIsSame = focused.getPrimaryColor().equals(focused.getDesign().getHoverColor());
					
					if(clicking)
					{
						if(!activeColorIsSame)
						{
							focused.setPrimaryColor(focused.getDesign().getActiveColor());
							
							return 1;
						}
						else
						{
							return 0;
						}
					}
					
					if(!hoverColorIsSame)
					{
						focused.setPrimaryColor(focused.getDesign().getHoverColor());
						
						return 1;
					}
				}
				default:
				{
					if(lastlyFocused != focused && lastlyFocused != null)
					{
						switch(lastlyFocused.getType())
						{
							case "button":
							{
								lastlyFocused.setPrimaryColor(lastlyFocused.getDesign().getBackgroundColor());
								
								return 1;
							}
						}
					}
				}
			}
		}
		
		return 0;
	}
	
	private void triggerComponent()
	{
		EDComponent focused = renderFrame.getEventHandler().getMouseDriver().getFocusedComponent();
		
		boolean clicking = renderFrame.getEventHandler().getMouseDriver().isClicking();
		
		int keyStroke = renderFrame.getEventHandler().getKeyboardDriver().getActiveKey();
		
		int graphicalChanges = 0;

		graphicalChanges += triggerGeneralLogic(focused, clicking, keyStroke);
		graphicalChanges += triggerAnimation(focused, clicking);
		
		if(graphicalChanges > 0)
		{
			Thread screenUpdate = new Thread()
			{
				@Override
				public void run()
				{
					System.out.println("Updating screen..");
					renderFrame.updateEDComponents();
				}
			};
			
			updateTManager.fire(screenUpdate);
		}
		
		lastlyFocused = focused;
	}
}
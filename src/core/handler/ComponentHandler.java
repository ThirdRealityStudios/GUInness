package core.handler;

import java.awt.Cursor;
import java.awt.event.KeyEvent;

import core.exec.LoopedThread;
import core.exec.ThreadManager;
import core.feature.Timer;
import core.gui.Display;
import core.gui.Viewport;
import core.gui.component.EDComponent;
import core.gui.layer.EDLayer;

public class ComponentHandler
{
	// The 'handler'-thread is used to make all components working by checking the
	// state, make evaluations and then decide what to do next, e.g. when a user
	// clicked a button.
	// Because of that, the 'handler'-thread has a high priority.
	// It actually needs to run as fast as possible in order not to miss user
	// interactions etc.
	private LoopedThread handler = null;

	private Display display;

	private ThreadManager hoverTManager, clickTManager;

	// Takes care about that only one time one thread is running for updating the
	// screen (repaint).
	private ThreadManager updateTManager;

	// If there was a text-field selected, it will be stored here for a time.
	private EDComponent textfield;

	// Tells whether a component was clicked before.
	private EDComponent clickedYet = null;

	// Tells by using 'clickedYet' whether the checked component was double clicked.
	private boolean doubleClicked = false;

	// This is the lastly focused component from the previous cycle always.
	private EDComponent lastlyFocused;

	private boolean doubleHovered;

	private EDComponent hoveredYet;

	public ComponentHandler(Display display)
	{
		this.display = display;

		final int maximumThreads = 2;

		this.hoverTManager = new ThreadManager(maximumThreads);
		this.clickTManager = new ThreadManager(maximumThreads);

		this.updateTManager = new ThreadManager(1);

		// Here a thread is created which just serves this class to refresh all
		// retrievable information on components.
		handler = new LoopedThread()
		{
			@Override
			public void loop()
			{
				updateChangedLayers();
				
				triggerComponent();
			}
		};

	}
	
	public void updateChangedLayers()
	{
		Viewport viewport = display.getViewport();
		
		if(viewport != null)
		{
			for(EDLayer layer : viewport.getLayers())
			{
				if(!layer.isVisible())
				{
					display.getViewport().applyChanges();
				}
			}
		}
	}

	// Returns the handling thread, so the thread which frequently handles all
	// components to make them work.
	public LoopedThread getHandlingThread()
	{
		return handler;
	}

	private void executeClick(EDComponent execute)
	{
		if(execute.isRealtimeExecutionOn())
		{
			Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or
									// interactions with the UI.
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

		Timer.pauseMillisecond(execute.getDelayMilliseconds());
	}

	private void executeHover(EDComponent execute)
	{
		if(execute.isRealtimeExecutionOn())
		{
			Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or
									// interactions with the UI.
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

		Timer.pauseMillisecond(execute.getDelayMilliseconds());
	}

	// Is responsible for firing the implemented functions by the component.
	private void triggerGeneralLogic(EDComponent focused, boolean clicking, int keyStroke)
	{
		if(clicking) // relates to text-fields only.
		{
			boolean canTextfieldBeFocussed = focused != null && focused.getType().contentEquals("textfield")
					&& focused.isInteractionEnabled() && focused.actsOnClick();

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

		// This is the actual part where text-fields are modified, meaning the value or
		// text it contains gets changed.
		// If there is no key delivered (KeyEvent.VK_UNDEFINED), this part is ignored
		// for faster execution.
		if(textfield != null && !(keyStroke == KeyEvent.VK_UNDEFINED) && focused.isInteractionEnabled()
				&& focused.actsOnClick())
		{
			boolean isDeviceControlCode = textfield.getDesign().getFontLoader().isDeviceControlCode(keyStroke);

			if(isDeviceControlCode && !textfield.isCursorAtEnd())
			{
				textfield.write((char) keyStroke);
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
						}

						break;
					}
				}
			}
		}

		if(focused != null)
		{
			if(focused.isInteractionEnabled() && focused.actsOnHover()) // ask whether it should run the onHover()
																		// method if wished by the components
																		// configuration.
			{
				// This will decide internally whether the component is being executed by
				// threads or in sequence order.
				executeHover(focused);
			}

			if(clicking && focused.isInteractionEnabled() && focused.actsOnClick()) // ask whether it should run the
																					// onClick() method if wished by the
																					// components configuration.
			{
				// Make sure the user cannot double click the same component multiple times if
				// it is unwanted.
				if(!doubleClicked || focused.isDoubleClickingAllowed())
				{
					// This will decide internally whether the component is being executed by
					// threads or in sequence order.
					executeClick(focused);
				}
			}
		}
	}

	private void triggerAnimation(EDComponent focused, boolean clicking)
	{
		boolean sameComponentFocused = lastlyFocused != focused && lastlyFocused != null;
		
		if(focused != null)
		{
			switch(focused.getType())
			{
				case "button":
				{
					// The next two booleans prevent the redraw algorithm to run again if there was
					// no change in color..
					boolean activeColorIsSame = focused.getPrimaryColor().equals(focused.getDesign().getActiveColor());
					boolean hoverColorIsSame = focused.getPrimaryColor().equals(focused.getDesign().getHoverColor());
					
					if(clicking)
					{
						if(!activeColorIsSame)
						{
							focused.setPrimaryColor(focused.getDesign().getActiveColor());
						}
					}
					else if(!hoverColorIsSame)
					{
						focused.setPrimaryColor(focused.getDesign().getHoverColor());

						// When hovering (once!) over a button the cursor is changed.
						display.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					break;
				}
				case "textfield":
				{
					if(!doubleHovered)
					{
						// When hovering over a text-field the cursor is changed.
						display.setCursor(new Cursor(Cursor.TEXT_CURSOR));
					}

					break;
				}

				default:
				{
					// Make sure the 'default' branch is executed only once.
					if(sameComponentFocused)
					{						
						// When hovering over something else the cursor is set to default.
						display.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						
						switch(lastlyFocused.getType())
						{
							case "button":
							{
								lastlyFocused.setPrimaryColor(lastlyFocused.getDesign().getBackgroundColor());
							}
						}
					}
				}
			}
		}
	}

	private void preEvaluateEvents(EDComponent focused)
	{
		if(hoveredYet == focused)
		{
			this.doubleHovered = true;
		}
		else
		{
			this.doubleHovered = false;
		}
		
		if(clickedYet == focused)
		{
			this.doubleClicked = true;
		}
	}

	private void postEvaluateEvents(boolean clicking, EDComponent focused)
	{
		if(clicking)
		{
			clickedYet = focused;
		}
		else
		{
			clickedYet = null;
			doubleClicked = false;
		}

		hoveredYet = focused;
	}

	private void triggerComponent()
	{
		EDComponent focused = display.getEventHandler().getMouseDriver().getFocusedComponent();

		preEvaluateEvents(focused);

		boolean clicking = display.getEventHandler().getMouseDriver().isClicking();

		// This line means if the KeyAdapter is active, then only read the currently
		// pressed key from it.
		// This is because the KeyAdapter is only available (!= null) when it is
		// necessary to save resources on the CPU.
		// Anyway, in Gaming Mode (see definition of it in LayeredDisplay.java for
		// reference) the KeyAdapter is always initialized and available.
		int keyStroke = KeyEvent.VK_UNDEFINED;

		if(textfield != null)
		{
			display.getEventHandler().enableKeyboardDriver();

			keyStroke = display.getEventHandler().getKeyboardDriver().getActiveKey();
		}
		else if(!display.getEventHandler().isNoKeylistenerActive())
		{
			display.getEventHandler().disableKeyboardDriver();
		}
		
		triggerGeneralLogic(focused, clicking, keyStroke);
		triggerAnimation(focused, clicking);

		postEvaluateEvents(clicking, focused);

		lastlyFocused = focused;
	}
}
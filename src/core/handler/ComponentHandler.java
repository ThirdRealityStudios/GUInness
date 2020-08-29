package core.handler;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import core.exec.LoopedThread;
import core.exec.ThreadManager;
import core.feature.Timer;
import core.gui.Display;
import core.gui.Viewport;
import core.gui.component.GComponent;
import core.gui.special.GCheckbox;
import core.gui.special.selection.GSelectionBox;
import core.gui.special.selection.GSelectionOption;

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

	// If there was a text-field selected, it will be stored here for a time.
	private GComponent textfield;

	// Tells whether a component was clicked before.
	private GComponent clickedYet = null;

	// Tells by using 'clickedYet' whether the checked component was double clicked.
	private boolean doubleClicked = false;

	// This is the lastly focused component from the previous cycle always.
	private GComponent lastlyFocused;

	private boolean doubleHovered;

	private GComponent hoveredYet;

	public ComponentHandler(Display display)
	{
		this.display = display;

		final int maximumThreads = 2;

		this.hoverTManager = new ThreadManager(maximumThreads);
		this.clickTManager = new ThreadManager(maximumThreads);

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
			if(display.getViewport().getLayerModifications() > 0)
			{
				display.getViewport().applyChanges();
				display.getViewport().outputAllComponents();
			}
		}
	}

	// Returns the handling thread, so the thread which frequently handles all
	// components to make them work.
	public LoopedThread getHandlingThread()
	{
		return handler;
	}

	private void executeClick(GComponent execute)
	{
		if(execute.getLogic().isMultithreadingOn())
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

		Timer.pauseMillisecond(execute.getLogic().getDelayMs());
	}

	private void executeHover(GComponent execute)
	{
		if(execute.getLogic().isMultithreadingOn())
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

		Timer.pauseMillisecond(execute.getLogic().getDelayMs());
	}

	// Is responsible for firing the implemented functions by the component.
	private void triggerGeneralLogic(GComponent focused, boolean clicking, Point mouseLocation, int keyStroke)
	{
		if(clicking)
		{
			// relates to text-fields only.
			{
				boolean canTextfieldBeFocussed = focused != null && focused.getType().contentEquals("textfield") && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick();

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
			
			
		}		

		// This is the actual part where text-fields are modified, meaning the value or
		// text it contains gets changed.
		// If there is no key delivered (KeyEvent.VK_UNDEFING), this part is ignored
		// for faster execution.
		if(textfield != null && !(keyStroke == KeyEvent.VK_UNDEFINED) && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick())
		{
			boolean isDeviceControlCode = display.getEventHandler().getKeyAdapter().isDeviceControlCode(keyStroke);

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
			if(focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnHover()) // ask whether it should run the onHover() method if wished by the components configuration.
			{
				// This will decide internally whether the component is being executed by threads or in sequence order.
				executeHover(focused);
			}

			if(clicking && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick()) // ask whether it should run the onClick() method if wished by the components configuration.
			{
				// Make sure the user cannot double click the same component multiple times if it is unwanted.
				if(!doubleClicked || focused.getLogic().isDoubleClickingAllowed())
				{
					switch(focused.getType())
					{
						// Additionally check-boxes are treated here.
						// This will simply enable or disable the check-box this is about..
						case "checkbox":
						{
							GCheckbox checkbox = (GCheckbox) focused;
							
							// Just invert the current setting.
							checkbox.setChecked(!checkbox.isChecked());
							
							break;
						}
						
						case "selectionbox":
						{
							GSelectionBox selectionbox = (GSelectionBox) focused;
							
							ArrayList<Rectangle[]> shapeTable = selectionbox.getShapeTable();
							
							for(int i = 0; i < shapeTable.size(); i++)
							{
								if(shapeTable.get(i)[0].createUnion(shapeTable.get(i)[1]).contains(mouseLocation))
								{
									selectionbox.selectOptionAt(i);
								}
							}
							
							break;
						}
					}
					
					// This will decide internally whether the component is being executed by threads or in sequence order.
					// It actually just runs the defined click action by the user.
					// It is executed here at the end to make sure changes by interacting with the components are recognized by the user defined click action.
					executeClick(focused);
				}
			}
		}
	}

	private void triggerAnimation(GComponent focused, boolean clicking, Point mouseLocation)
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
					boolean activeColorIsSame = focused.getStyle().getPrimaryColor().equals(focused.getStyle().getDesign().getActiveColor());
					boolean hoverColorIsSame = focused.getStyle().getPrimaryColor().equals(focused.getStyle().getDesign().getHoverColor());
					
					if(clicking)
					{
						if(!activeColorIsSame)
						{
							focused.getStyle().setPrimaryColor(focused.getStyle().getDesign().getActiveColor());
						}
					}
					else if(!hoverColorIsSame)
					{
						focused.getStyle().setPrimaryColor(focused.getStyle().getDesign().getHoverColor());

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
								lastlyFocused.getStyle().setPrimaryColor(lastlyFocused.getStyle().getDesign().getBackgroundColor());
							}
						}
					}
				}
			}
		}
	}

	private void preEvaluateEvents(GComponent focused)
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

	private void postEvaluateEvents(boolean clicking, GComponent focused)
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

	// This will trigger the component where the user has performed an action at.
	// Anyway, keep in mind that a component can only be triggered if it is also enabled (see 'isEnabled()' at GComponent).
	private void triggerComponent()
	{
		GComponent focused = display.getEventHandler().getMouseAdapter().getFocusedComponent();
		
		Point mouseLocation = display.getEventHandler().getMouseAdapter().getCursorLocation();
		
		/*
		 *  WARNING! The code below must be executed only under certain circumstances ! ! !
		 *  
		 *  Make sure the UI is only treated / handled when the component is also enabled if you create your own component type (modification).
		 */
		if(focused != null && !focused.isEnabled())
		{
			preEvaluateEvents(focused);
			
			// Pretend there was no component detected.
			postEvaluateEvents(false, null);
			
			// The remaining part of the code is not executed.
			return;
		}

		// If the execution goes until here, it will be triggered in the next steps.
		
		preEvaluateEvents(focused);

		boolean clicking = display.getEventHandler().getMouseAdapter().isClicking();

		// This line means if the KeyAdapter is active, then only read the currently
		// pressed key from it.
		// This is because the KeyAdapter is only available (!= null) when it is
		// necessary to save resources on the CPU.
		// Anyway, in Gaming Mode (see definition of it in LayeredDisplay.java for
		// reference) the KeyAdapter is always initialized and available.
		int keyStroke = display.getEventHandler().getKeyAdapter().getActiveKey();
		
		triggerGeneralLogic(focused, clicking, mouseLocation, keyStroke);
		triggerAnimation(focused, clicking, mouseLocation);

		postEvaluateEvents(clicking, focused);

		lastlyFocused = focused;
	}
}
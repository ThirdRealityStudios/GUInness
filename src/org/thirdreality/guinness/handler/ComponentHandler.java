package org.thirdreality.guinness.handler;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.thirdreality.guinness.exec.LoopedThread;
import org.thirdreality.guinness.exec.ThreadManager;
import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.feature.Timer;
import org.thirdreality.guinness.feature.shape.ShapeTransform;
import org.thirdreality.guinness.gui.Display;
import org.thirdreality.guinness.gui.Viewport;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.placeholder.GWindow;
import org.thirdreality.guinness.gui.component.placeholder.window.GWindowButton;
import org.thirdreality.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.guinness.gui.component.selection.list.GSelectionBox;

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
	
	private Point initialLoc = null;
	
	private GIPoint pxCorrection = new GIPoint();

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
		if(textfield != null && !(keyStroke == KeyEvent.VK_UNDEFINED) && focused != null && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick())
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

			boolean isClickingAllowed = clicking && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick();
			
			if(isClickingAllowed) // ask whether it should run the onClick() method depending on whether it is wanted to interact or click on it.
			{
				// Interactions which do not regard whether it is double clicked
				switch(focused.getType())
				{
					case "window":
					{
						GWindow window = (GWindow) focused;

						/*
						 * The GWindow currently only supports offsets yet delivered by the corresponding Viewport.
						 */
						
						GIPoint offset = window.getStyle().isMovableForViewport() ? new GIPoint(display.getViewport().getOffset()) : new GIPoint();
						
						Polygon outerArea = window.getStyle().getPrimaryLook();

						outerArea = ShapeTransform.movePolygonTo(outerArea, offset.add(outerArea.getBounds().getLocation()).toPoint());

						Polygon innerArea = window.getStyle().getSecondaryLook();
						
						innerArea = ShapeTransform.movePolygonTo(innerArea, offset.add(innerArea.getBounds().getLocation()).toPoint());
						
						Polygon exitButtonArea = window.getExitButton().getStyle().getPrimaryLook();
						
						exitButtonArea = ShapeTransform.movePolygonTo(exitButtonArea, offset.add(exitButtonArea.getBounds().getLocation()).toPoint());
						
						Polygon minimizeButtonArea = window.getMinimizeButton().getStyle().getPrimaryLook();
						
						minimizeButtonArea = ShapeTransform.movePolygonTo(minimizeButtonArea, offset.add(minimizeButtonArea.getBounds().getLocation()).toPoint());
						
						boolean focusedWindowBorderFirstTime = this.initialLoc == null && clicking && !innerArea.contains(mouseLocation) && !exitButtonArea.contains(mouseLocation) && !minimizeButtonArea.contains(mouseLocation);					

						if(focusedWindowBorderFirstTime)
						{
							this.initialLoc = mouseLocation;
						}

						boolean aboutToMoveWindowYet = initialLoc != null;

						if(aboutToMoveWindowYet)
						{
							GIPoint cursorDiff = new GIPoint(mouseLocation).sub(initialLoc);

							GIPoint moved = new GIPoint(window.getStyle().getLocation()).add(cursorDiff);//.div(display.getViewport().getScale(), window.getStyle().isScalableForViewport());

							window.getStyle().setLocation(moved.toPoint());

							this.initialLoc = mouseLocation;
						}

						break;
					}
				}

				boolean isDoubleClickingWanted = !doubleClicked || focused.getLogic().isDoubleClickingAllowed();

				// Make sure the user cannot double click the same component multiple times if it is unwanted.
				if(isDoubleClickingWanted)
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

							ArrayList<Polygon[]> shapeTable = selectionbox.getShapeTable();

							for(int i = 0; i < shapeTable.size(); i++)
							{
								Point offset = display.getViewport() != null ? display.getViewport().getOffset() : new Point();
								
								Polygon rect0 = shapeTable.get(i)[0];
								Polygon rect2 = shapeTable.get(i)[2];
								
								Point pos0 = new Point(rect0.getBounds().getLocation());
								pos0.translate(offset.x, offset.y);

								Point pos2 = new Point(rect2.getBounds().getLocation());
								pos2.translate(offset.x, offset.y);
								
								boolean isViewportAvailable = display.getViewport() != null;
								
								float scale = isViewportAvailable && focused.getStyle().isScalableForViewport() ? display.getViewport().getScale() : 1f;

								// Creates two moved and scaled copies (by the global offset and scale factor).
								Polygon transformed0 = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(rect0, pos0), scale);
								Polygon transformed2 = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(rect2, pos2), scale);
								
								if(transformed0.contains(mouseLocation) || transformed2.contains(mouseLocation))
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
			
			if(!clicking)
			{
				this.initialLoc = null;
				this.pxCorrection = new GIPoint();
			}
		}
	}

	private void resetLastFocus()
	{
		// When hovering over something else the cursor is set to default.
		display.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		switch(lastlyFocused.getType())
		{		
			case "button":
			{
				lastlyFocused.getStyle().setPrimaryColor(lastlyFocused.getStyle().getDesign().getBackgroundColor());

				break;
			}

			case "window":
			{
				GWindow window = (GWindow) lastlyFocused;

				window.getExitButton().getStyle().setPrimaryColor(window.getExitButton().getDefaultColor());
				window.getMinimizeButton().getStyle().setPrimaryColor(window.getMinimizeButton().getDefaultColor());
				
				break;
			}

			default:
			{
				
			}
		}
	}

	private void triggerWindowButtonColor(GWindowButton windowButton, Point mouseLocation, boolean clicking)
	{
		boolean activeColorIsSame = windowButton.getStyle().getPrimaryColor().equals(windowButton.getClickColor());
		boolean hoverColorIsSame = windowButton.getStyle().getPrimaryColor().equals(windowButton.getHoverColor());

		if(display.getViewport().getPolygonRelativeToViewport(windowButton.getStyle().getPrimaryLook()).contains(mouseLocation))
		{
			if(clicking)
			{
				if(!activeColorIsSame)
				{
					windowButton.getStyle().setPrimaryColor(windowButton.getClickColor());
				}
			}
			else if(!hoverColorIsSame)
			{
				windowButton.getStyle().setPrimaryColor(windowButton.getHoverColor());
			}
		}
		else
		{
			windowButton.getStyle().setPrimaryColor(windowButton.getDefaultColor());
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
				
				case "window":
				{
					GWindow window = (GWindow) focused;

					triggerWindowButtonColor(window.getExitButton(), mouseLocation, clicking);
					triggerWindowButtonColor(window.getMinimizeButton(), mouseLocation, clicking);

					break;
				}

				default:
				{
					// Make sure the 'default' branch is executed only once.
					if(sameComponentFocused)
					{
						resetLastFocus();
					}
				}
			}
		}
		else
		{
			if(lastlyFocused != null)
			{
				resetLastFocus();
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

		boolean windowWasMoved = initialLoc != null;

		// Make sure if one window was focused before, the focus does NOT get lost.
		// Otherwise, if you move a window the focus could get lost.
		// This happens usually when the mouse cursor goes beyond the window borders.
		// In this case, the focus would get lost if you wouldn't consider this event here.
		if(windowWasMoved)
		{
			focused = lastlyFocused;
		}

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
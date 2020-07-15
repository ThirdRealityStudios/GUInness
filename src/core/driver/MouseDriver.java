package core.driver;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import core.event.LoopedThread;
import core.frame.LayeredRenderFrame;
import core.gui.EDComponent;
import core.gui.EDLayer;
import core.io.Interrupt;

public class MouseDriver extends LoopedThread implements MouseMotionListener, MouseListener
{
	/*
	 *  'context' is the variable to use
	 *  for calculating mouse movement and additional data.
	 *  The data related to the given RenderFrame can then be used in real-time.
	 *  To do so,
	 *  you can use the given methods below.
	 */
	private LayeredRenderFrame context;
	
	// The variable is used to calculate the mouse speed below.
	private Point cursorLast = null;
	
	// Self-explaining: the mouse speed related to pixels per cycle.
	private volatile double mouseSpeed = 0f;
	
	/* 
	 * The 'action' variable below tells how the user interacts with the components on the RenderFrame.
	 * 
	 * Explanation of the states:
	 * 
	 * false = move
	 * true = click
	 * null = no action or reaction from the mouse.
	 */
	private Boolean action = null;

	public MouseDriver(LayeredRenderFrame context)
	{
		this.context = context;
		
		/*
		 *  'context' is the variable to use
		 *  for calculating mouse movement and additional data.
		 *  The data related to the given RenderFrame can then be used in real-time.
		 *  To do so,
		 *  you can use the given methods below.
		 */
		
		// Add this mouse driver as a MouseListener to in order to work with the context.
		context.addMouseListener(this);
		
		// Add this mouse driver as a MouseListener to in order to work with the context.
		context.addMouseMotionListener(this);
	}
	
	// Calculates the mouse speed with a delay of 10ms to have a difference.
	private double calcMouseSpeed()
	{
		cursorLast = getCursorLocation();
		
		Interrupt.pauseMillisecond(10);
		
		double distance = cursorLast.distance(getCursorLocation());
		
		return distance;
	}
	
	// Returns the general and current mouse speed on screen.
	// It is related to no component or such.
	public double getMouseSpeed()
	{
		return mouseSpeed;
	}
	
	// Tells when the mouse is active, so doing something,
	// like moving the cursor etc.
	private boolean isActive()
	{	
		return isClicking() || mouseSpeed > 0;
	}

	// Tells when the mouse is inactive, so doing nothing.
	public boolean isInactive()
	{		
		return !isActive();
	}

	@Override
	public void loop()
	{
		mouseSpeed = calcMouseSpeed();
		
		if(!isActive())
		{
			action = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent)
	{
		// Not used.
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent)
	{
		// The cursor was generally just moved somewhere on the frame (false).
		action = false;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent)
	{
		// Not used.
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// A general click was performed somewhere on the frame (true).
		action = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		action = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// When the cursor enter the area of the RenderFrame.
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// When the cursor exits the area of the RenderFrame.
	}
	
	// Returns the current action.
	public Boolean getAction()
	{
		return action;
	}
	
	// Tells whether the mouse cursor is moving.
	public boolean isMoving()
	{
		return getAction() != null && getAction() == false;
	}
	
	// Tells whether the mouse is being clicked.
	public Boolean isClicking()
	{
		return getAction() != null && getAction() == true;
	}
	
	// Returns the absolute current cursor location.
	public Point getCursorLocation()
	{
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	// Tests if the cursor is on the position of a component.
	// Meaning: Tests whether the mouse cursor (relative to the RenderFrame) is inside the given component.
	// Returns 'false' if target is 'null'.
	public boolean isFocusing(EDComponent target)
	{
		// If there is no component given,
		// this method assumes no component was found,
		// so the cursor is not over a component.
		if(target == null)
			return false;

		// The current absolute mouse position on screen.
		Point desktopCursor = getCursorLocation();
		
		// Frame offset for the relative cursor position.
		Point frameOffset = new Point(-8, -31);
		
		// The current mouse position relative to the JFrame.
		Point frameCursor = new Point(desktopCursor.x - context.getLocation().x + frameOffset.x, desktopCursor.y - context.getLocation().y + frameOffset.y);
		
		return target.getShape().contains(frameCursor);
	}
	
	// Tests if the user is clicking a component.
	public boolean isClicking(EDComponent edT)
	{
		return isFocusing(edT) && isClicking();
	}
	
	// Returns the first component which is focused by the cursor.
	// Makes the UI more efficient by breaking at the first component already.
	// Returns null if there is no such component.
	public EDComponent getFocusedComponent()
	{
		EDComponent firstMatch = null;
		
		for(EDLayer layer : context.getEventHandler().getRegisteredLayers())
		{
			for(EDComponent selected : layer.getComponentBuffer())
			{
				boolean insideComponent = isFocusing(selected);
				
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
	
	// Checks whether the cursor is over any EasyDraw component.
	// Should be avoided if used too often because of performance reasons.
	public boolean isFocusingAny()
	{
		return getFocusedComponent() != null;
	}
}

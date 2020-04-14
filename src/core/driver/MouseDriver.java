package core.driver;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import core.event.LoopedThread;
import core.frame.LayeredRenderFrame;
import core.io.Interrupt;

public class MouseDriver extends LoopedThread implements MouseMotionListener, MouseListener
{
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
		
		// Interrupt.pauseMillisecond(5);
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
	
	public Point getCursorLocation()
	{
		return MouseInfo.getPointerInfo().getLocation();
	}
}

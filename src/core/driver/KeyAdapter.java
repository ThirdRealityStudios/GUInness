package core.driver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.frame.Display;

public class KeyAdapter implements KeyListener
{
	private int keyActive = KeyEvent.VK_UNDEFINED, keyTyped = KeyEvent.VK_UNDEFINED;
	
	boolean duplicate = false;
	
	public KeyAdapter(Display context)
	{
		/*
		 *  'context' is the variable to use
		 *  for detecting keystrokes and additional data.
		 *  The data related to the given RenderFrame can then be used in real-time.
		 *  To do so,
		 *  you can use the given methods below.
		 */
		
		// Add this KeyAdapter as a KeyListener in order to work with the context.
		context.addKeyListener(this);
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		keyTyped = (int) e.getKeyChar();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if((int) e.getKeyChar() == keyActive)
		{
			duplicate = true;
		}
		else
		{
			duplicate = false;
			
			keyActive = (char) e.getKeyChar();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// Not used currently..
	}
	
	// Returns whether any key is pressed currently.
	public boolean isKeyActive()
	{
		return keyActive != KeyEvent.VK_UNDEFINED;
	}
	
	// Returns the key which is pressed currently.
	// Otherwise returns KeyEvent.VK_UNDEFINED if no key is pressed.
	public int getActiveKey()
	{
		int returnKey = keyActive;
		
		keyActive = KeyEvent.VK_UNDEFINED;
		
		return returnKey;
	}
	
	// Returns the key which was (!) typed before.
	// It is not the key which is pressed currently but the last known key.
	public int getTypedKey()
	{
		return keyTyped;
	}
	
	// Returns the key which is pressed currently.
	// Otherwise returns KeyEvent.VK_UNDEFINED if no key is pressed.
	// It is returned as a char.
	public char getActiveKeyChar()
	{
		char returnKey = (char) keyActive;
		
		keyActive = KeyEvent.VK_UNDEFINED;
		
		return duplicate ? (char) KeyEvent.VK_UNDEFINED : returnKey;
	}

	// Returns the key which was (!) typed before.
	// It is not the key which is pressed currently but the last known key.
	// It is returned as a char.
	public char getTypedKeyChar()
	{
		return (char) keyTyped;
	}
}

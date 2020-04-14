package core.event;

import java.awt.MouseInfo;
import java.awt.Point;

import core.Essentials;
import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.logic.DefaultButtonLogic;

public class ComponentHandler
{
	private LoopedThread handler = null;
	
	private DefaultButtonLogic defaultButtonLogic = null;
	
	private EventHandler eventHandler = null;
	
	public ComponentHandler(EventHandler eventHandler)
	{
		// Used to tell the ComponentHandler how to evaluate all buttons.
		// The mouse driver is used to provide the DefaultButtonLogic with all necessary information.
		this.defaultButtonLogic = new DefaultButtonLogic(eventHandler.getMouseDriver());
		
		this.eventHandler = eventHandler;
		
		handler = new LoopedThread()
		{
			@Override
			public void loop()
			{
				eachButton(defaultButtonLogic);
			}
		};
	}
	
	public LoopedThread getHandlingThread()
	{
		return handler;
	}
	
	private boolean buttonTargeted = false, stillClicking = false;
	
	// Calls all buttons which do exist in the list. The given 'button logic' is to them applied then.
	protected void eachButton(DefaultButtonLogic logic)
	{
		for(EDLayer layer : eventHandler.getRegisteredLayers())
		{
			for(EDText text : layer.getTextBuffer())
			{
				// Make sure buttons are only selected.
				if(Essentials.typeof(text).equals("EDButton"))
				{
					LayeredRenderFrame rF = eventHandler.getLayeredRenderFrame();
					
					// The button to check if it was meant to be interacted with.
					EDButton selected = (EDButton) text;

					// The current absolute mouse position on screen.
					Point desktopCursor = MouseInfo.getPointerInfo().getLocation();
					
					// Frame offset for the relative cursor position.
					Point frameOffset = new Point(-8, -31);
					
					// The current mouse position relative to the JFrame.
					Point frameCursor = new Point(desktopCursor.x - rF.getLocation().x + frameOffset.x, desktopCursor.y - rF.getLocation().y + frameOffset.y);

					// Check whether the cursor is over the button.
					if(selected.getRectangle().contains(frameCursor))
					{
						buttonTargeted = true;
						
						logic.exec(selected);
					}
					else
					{
						if(eventHandler.getMouseDriver().isClicking())
						{
							
						}
						
						// Resets the color if the cursor is outside the area of the button.
						logic.reset(selected);
					}
				}
			}
		}
	}
}

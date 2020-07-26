package core.event;

import java.util.ArrayList;

import core.driver.KeyboardDriver;
import core.driver.MouseDriver;

// The EventRequest is used to bundle all application-specific events in a nutshell to make it easier
// to retrieve some information about which events have been fired.
public class EventRequest
{
	public static final int isMouseMoving = 0, isMouseClicking = 1, isMouseActive = 2, isMouseFocusingComponent = 3,
			isAnyKeyPressed = 4, isRepaintNecessary = 99;

	private EventHandler eH;
	private MouseDriver mA;
	private KeyboardDriver kA;

	private ArrayList<String> types, exceptionalTypes_isRepaintNecessary;

	public EventRequest(EventHandler eH)
	{
		types = new ArrayList<String>();
		
		exceptionalTypes_isRepaintNecessary = new ArrayList<String>();

		exceptionalTypes_isRepaintNecessary.add("image");
		exceptionalTypes_isRepaintNecessary.add("description");

		this.eH = eH;

		this.mA = eH.getMouseDriver();
		this.kA = eH.getKeyboardDriver();
	}

	public ArrayList<String> getExceptionalTypes_RepaintEvent()
	{
		return exceptionalTypes_isRepaintNecessary;
	}

	public boolean isMouseClicking()
	{
		return eH.getMouseDriver().isClicking();
	}

	public boolean isMouseMoving()
	{
		return eH.getMouseDriver().isMoving();
	}

	public boolean isMouseActive()
	{
		return mA.isMoving() || mA.isClicking();
	}

	public boolean isMouseFocusingComponent()
	{
		return mA.isFocusingAny(null);
	}

	public boolean isMouseFocusingButton()
	{
		return false;
	}

	public boolean isAnyKeyPressed()
	{
		return kA.isKeyActive();
	}

	public boolean isRepaintNecessary()
	{
		boolean focusedAny = mA.isFocusingAny(exceptionalTypes_isRepaintNecessary);

		return focusedAny;
	}

	public boolean is(int eventType)
	{
		switch(eventType)
		{
			case isMouseClicking:
			{
				return isMouseClicking();
			}

			case isMouseMoving:
			{
				return isMouseMoving();
			}

			case isMouseActive: // if mouse is clicking or moving around..
			{
				return isMouseActive();
			}

			case isMouseFocusingComponent: // if mouse is focusing / hovering over buttons or similar components.
			{
				return isMouseFocusingComponent();
			}

			/*
			case isMouseFocusingButton:
			{
				return isMouseFocusingButton();
			}

			case isMouseFocusingTextfield:
			{
				return isMouseFocusingTextfield();
			}

			case isMouseFocusingImage:
			{
				return isMouseFocusingImage();
			}

			case isMouseFocusingDescription:
			{
				return isMouseFocusingDescription();
			}
			*/

			case isAnyKeyPressed: // if any key was being pressed on the keyboard at this moment..
			{
				return isAnyKeyPressed();
			}

			case isRepaintNecessary:
			{
				return isRepaintNecessary();
			}

			default:
			{
				return false;
			}
		}
	}
}
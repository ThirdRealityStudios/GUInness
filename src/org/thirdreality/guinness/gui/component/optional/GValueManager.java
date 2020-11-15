package org.thirdreality.guinness.gui.component.optional;

public abstract class GValueManager
{
	private int length = 0;

	protected volatile String value = "";

	private volatile String bufferedValue = null;

	public GValueManager()
	{
		
	}

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getMaxLength();

		if(noOverflow)
		{
			setValue(getValue() + key);
		}
	}

	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseLastChar()
	{
		// Checking whether deleting one more char is still possible due to the length
		// of 'value'.
		if(getValue().length() > 0)
		{
			setBufferedValue(getBufferedValue());

			char[] charValues = getValue().toCharArray();

			setValue(String.valueOf(charValues, 0, charValues.length - 1));
		}
	}

	// Tells you whether the cursor of 'value' is at the beginning,
	// meaning 'value' is empty.
	public boolean isCursorAtBeginning()
	{
		return getValue().length() == 0;
	}

	// Tells you whether the cursor of 'value' is at the end,
	// meaning 'value' is full.
	public boolean isCursorAtEnd()
	{
		return (getValue().length() + 1) > getMaxLength();
	}

	public synchronized void revert()
	{
		setValue(getBufferedValue());
	}

	public synchronized String getValue()
	{
		return value;
	}

	// The implementation depends on the type,
	// e.g. a text-field is treated differently than an image.
	public abstract void setValue(String val);

	public synchronized void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public synchronized String getBufferedValue()
	{
		return bufferedValue;
	}

	public int getMaxLength()
	{
		return length;
	}

	public void setMaxLength(int length)
	{
		this.length = length;
	}
}

package core.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.UUID;

// Contains an amount of drawable components.
public class EDLayer implements Comparable
{
	private UUID id;

	private ArrayList<EDComponent> compBuffer;

	private int priority;

	private boolean visible;
	
	private Color backgroundColor = null;
	
	private boolean backgroundEnabled = false;
	
	protected Dimension size = null; // Keeps the dimension determined by all components contained within this layer.

	public EDLayer(int priority, boolean visible)
	{
		id = UUID.randomUUID();

		compBuffer = new ArrayList<EDComponent>();

		this.priority = priority;
		this.visible = visible;
	}
	
	public EDLayer(int priority, boolean visible, Color backgroundColor, boolean backgroundEnabled)
	{
		this(priority, visible);
		
		this.backgroundColor = backgroundColor;
		this.backgroundEnabled = backgroundEnabled;
	}

	// Checks whether the newly given EDComponent is at the same place as another component
	// which is added yet to the layer.
	// In this case it returns false.
	private boolean isPositionValid(EDComponent check)
	{
		for(EDComponent comp : getComponentBuffer())
		{
			// Checks whether both components would be in conflict with each other when appearing at the same position.
			// In future there needs to be function which is able to test shapes regardless of whether it is a rectangle or something else.
			if(check.getShape().intersects(comp.getShape().getBounds2D()))
			{
				return false;
			}
		}
		
		return true;
	}

	public ArrayList<EDComponent> getComponentBuffer()
	{
		return compBuffer;
	}

	// Is "protected" because you need to make sure no components are at the same position.
	// To use a new ArrayList of type EDComponent, create a new EDLayer instead.
	protected void setComponentBuffer(ArrayList<EDComponent> compBuffer)
	{
		this.compBuffer = compBuffer;
	}

	public void add(EDComponent comp) throws IllegalArgumentException
	{
		if(isPositionValid(comp))
		{
			compBuffer.add(comp);
		}
		else
		{
			throw new IllegalArgumentException("tried to add a component to the position of another component (intersection).\nMore details:\n" + comp);
		}
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public boolean isBackgroundEnabled()
	{
		return backgroundEnabled;
	}

	public void setBackgroundEnabled(boolean backgroundEnabled)
	{
		this.backgroundEnabled = backgroundEnabled;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public UUID getUUID()
	{
		return id;
	}
	
	protected void setSize(Dimension size)
	{
		this.size = size;
	}

	@Override
	public int compareTo(Object o)
	{
		EDLayer comp = (EDLayer) o;

		return comp.getPriority() - this.getPriority();
	}

}

package core.gui.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import core.gui.component.EDComponent;
import core.gui.design.Classic;
import core.gui.design.Design;

// Contains an amount of drawable components.
public class EDLayer implements Comparable
{
	private UUID id;

	private CopyOnWriteArrayList<EDComponent> compBuffer;

	private int priority;

	private boolean visible;
	
	private Design design;

	protected Dimension size = null; // Keeps the dimension determined by all components contained within this layer.

	public EDLayer(int priority, boolean visible)
	{
		id = UUID.randomUUID();

		compBuffer = new CopyOnWriteArrayList<EDComponent>();

		this.priority = priority;
		this.visible = visible;
		
		// Create a default classic design nothing was specified like below at the next constructor.
		design = new Classic(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK, 2, 1);
	}
	
	public EDLayer(int priority, boolean visible, Design design)
	{
		this(priority, visible);
		
		this.design = design;
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

	public CopyOnWriteArrayList<EDComponent> getComponentBuffer()
	{
		return compBuffer;
	}

	// Is "protected" because you need to make sure no components are at the same position.
	// To use a new ArrayList of type EDComponent, create a new EDLayer instead.
	protected void setComponentBuffer(CopyOnWriteArrayList<EDComponent> compBuffer)
	{
		this.compBuffer = compBuffer;
	}

	public void add(EDComponent comp) throws IllegalArgumentException
	{		
		if(isPositionValid(comp))
		{
			comp.setDesign(getDesign());
			
			compBuffer.add(comp);
		}
		else
		{
			// if the position is invalid, also no changes are applied to the component including design.
			
			throw new IllegalArgumentException("Tried to add a component to the position of another component (intersection).\nMore details:\n" + comp);
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

		return this.getPriority() - comp.getPriority();
	}

	public Design getDesign()
	{
		return design;
	}

}

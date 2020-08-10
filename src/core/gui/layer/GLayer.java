package core.gui.layer;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import core.Meta;
import core.gui.component.GComponent;
import core.gui.design.Design;
import core.gui.design.Sample;

// Contains an amount of drawable components.
public class GLayer implements Comparable<GLayer>, Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private UUID id;
	
	// Determine whether the layer should be enabled or not.
	// If it's disabled, it is not just invisible but also you cannot interact with it anymore (including all components of course).
	private boolean enabled = true;

	private CopyOnWriteArrayList<GComponent> compBuffer;

	private int priority;

	private boolean visible = true;
	
	private Design design;

	protected Dimension size = null; // Keeps the dimension determined by all components contained within this layer.

	public GLayer(int priority, boolean visible)
	{
		init(priority, visible);
		
		// Use the default classic design.
		design = Sample.classic;
	}
	
	public GLayer(int priority, boolean visible, Design design) throws NullPointerException
	{
		if(design == null)
		{
			throw new NullPointerException("The passed design in the constructor cannot be 'null' !\nIf you don't want the layer to use a design, leave the \"Design design\" parameter completely out.");
		}
		
		init(priority, visible);
		
		this.design = design;
	}
	
	// Used to initialize the base values in the constructor.
	private void init(int priority, boolean visible)
	{
		id = UUID.randomUUID();

		compBuffer = new CopyOnWriteArrayList<GComponent>();

		this.priority = priority;
		this.visible = visible;
	}

	// Checks whether the newly given GComponent is at the same place as another component
	// which is added yet to the layer.
	// In this case it returns false.
	private boolean isPositionValid(GComponent check)
	{
		for(GComponent comp : getComponentBuffer())
		{
			// Checks whether both components would be in conflict with each other when appearing at the same position.
			// In future there needs to be function which is able to test shapes regardless of whether it is a rectangle or something else.
			if(check.getStyle().getShape().intersects(comp.getStyle().getShape().getBounds2D()))
			{
				return false;
			}
		}
		
		return true;
	}

	public CopyOnWriteArrayList<GComponent> getComponentBuffer()
	{
		return compBuffer;
	}

	// Is "protected" because you need to make sure no components are at the same position.
	// To use a new CopyOnWriteArrayList of type GComponent, create a new GLayer instead.
	protected void setComponentBuffer(CopyOnWriteArrayList<GComponent> compBuffer)
	{
		this.compBuffer = compBuffer;
	}
	
	// When a new design was applied the shape needs to be updated too.
	// The shape of a component depends on the component of course.
	// If there is no design applied yet to a component,
	// the supplied design of the GLayer is used.
	private void updateDesign(GComponent comp)
	{
		boolean isNoDesignAppliedYet = comp.getStyle().getDesign() == null;

		if(isNoDesignAppliedYet)
		{
			comp.getStyle().setDesign(getDesign());
		}

		boolean updateForShapeNecessary = !comp.getType().contentEquals("image");

		if(updateForShapeNecessary)
		{
			comp.getStyle().getDesign().updateDefaultShape(comp);
		}
	}

	public void add(GComponent comp) throws IllegalArgumentException
	{
		if(isPositionValid(comp))
		{
			updateDesign(comp);
			
			// Make sure all components are "synchronized" with the same settings as the layer.
			comp.setEnabled(isEnabled());
			comp.getStyle().setVisible(isVisible());
			
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
	
	private void setAllComponentsVisible(boolean visible)
	{
		for(GComponent current : compBuffer)
		{
			current.getStyle().setVisible(visible);
		}
	}
	
	private void setAllComponentsEnabled(boolean enabled)
	{
		for(GComponent current : compBuffer)
		{
			current.setEnabled(enabled);
		}
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
		
		setAllComponentsVisible(visible);
	}

	public UUID getUUID()
	{
		return id;
	}
	
	protected void setSize(Dimension size)
	{
		this.size = size;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		
		setAllComponentsEnabled(enabled);
	}

	@Override
	public int compareTo(GLayer o)
	{
		GLayer comp = (GLayer) o;

		return this.getPriority() - comp.getPriority();
	}

	public Design getDesign()
	{
		return design;
	}
}

package core.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

import core.gui.component.GComponent;
import core.gui.layer.GLayer;
import core.handler.EventHandler;

public class Viewport extends JPanel
{
	private static final long serialVersionUID = 1L;

	// This contains all (prioritized) layers added to this Viewport.
	// The same priority can only exist once in a Viewport!
	private CopyOnWriteArrayList<GLayer> layers;

	// This list contains all GComponents which are added by new layers but not yet recognized by the system.
	private CopyOnWriteArrayList<GComponent> compBuffer;

	// After all components have been added all components are added from 'compBuffer' above.
	// This ensures that no errors can appear while adding new layers and reduces "performance waste".
	private GComponent[] compOutput;

	private EventHandler eventHandler;
	
	private int layerModifications = 0;
	
	private Point offset = new Point();
	
	private float scale = 1f;

	public Viewport(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
		
		addMouseDetection();
		
		compBuffer = new CopyOnWriteArrayList<GComponent>();
		compOutput = new GComponent[0];
		
		layers = new CopyOnWriteArrayList<GLayer>();
	}
	
	// The most important method for displaying all components and graphics.
	// This is the main (recursive) loop for rendering.
	@Override
	public void paintComponent(Graphics g)
	{
		drawBackground(g);

		drawComponents(g);

		repaint();
	}
	
	// In the beginning this will just draw a background.
	// In the sample program, a GImage is used as a background.
	// This is why you can't see the applied background color below in the code because it is overwritten by the GImage.
	private void drawBackground(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	// Draws all components from the output list 'compOutput'.
	// When adding new layers, they are not yet added to the output directly.
	// First, it is being waited until all (new and old) components have been read (again (for old components yet stored)).
	// Only then the components are directly outputed by just changing the reference.
	private void drawComponents(Graphics g)
	{
		// Render all GUInness components.
		for(GComponent edC : compOutput)
		{
			if(edC.getStyle().isVisible() && edC.isEnabled())
			{
				edC.getStyle().getDesign().drawContext(g, edC, getOffset(), getScale());
			}
		}
	}
	
	// Adds the MouseAdapter as a Mouse(Motion)Listener in order to work with the Viewport when mouse actions have to be evaluated.
	private void addMouseDetection()
	{
		addMouseListener(eventHandler.getMouseAdapter());
		
		addMouseMotionListener(eventHandler.getMouseAdapter());
	}

	// Erases the internal buffer.
	public synchronized void erase()
	{
		compBuffer.clear();
	}

	// Outputs all components of the buffer immediately to the output, 
	// so all changes will be visible then first.
	public void outputAllComponents()
	{
		compOutput = compBuffer.toArray(compOutput);
	}

	// Adds all components of a layer to the internal component buffer (which is used for drawing only).
	private void apply(GLayer target)
	{
		// Add every component of the current layer.
		for(GComponent comp : target.getComponentBuffer())
		{
			compBuffer.add(comp);
		}
	}

	// If a layer was changed, you can call this method to apply all changes.
	// Is very inefficient if it's called frequently.
	public synchronized void applyChanges()
	{
		erase(); // If buggy, re-instantiate

		Collections.sort(layers);

		if(layers != null && layers.size() > 0)
		{
			if(layers.size() > 1)
			{
				for(GLayer layer : layers)
				{
					apply(layer);
				}
			}
			else
			{
				apply(layers.get(0));
			}
		}
		
		layerModifications = 0;
	}
	
	// This will check whether a given layer has the same priority as a layer which is added yet to the list.
	private boolean isDoublePriority(GLayer layer)
	{
		for(GLayer current : layers)
		{
			if(current.getPriority() == layer.getPriority())
			{
				return true;
			}
		}
		
		return false;
	}
	
	// The priority of a layer has to be at least zero or greater.
	private boolean isValidPriority(GLayer layer)
	{
		return layer.getPriority() >= 0 && !isDoublePriority(layer);
	}

	public void addLayer(GLayer layer) throws IllegalArgumentException
	{
		if(isValidPriority(layer))
		{
			layers.add(layer);
			
			layerModifications++;
		}
		else
		{
			throw new IllegalArgumentException("The given layers priority is invalid (< 0 or reason is \"double priority\")");
		}
	}

	public void removeLayer(String uuid)
	{
		erase();

		int index = 0;

		for (GLayer current : layers)
		{
			if(current.getUUID().toString().equals(uuid))
				break;

			index++;
		}

		layers.remove(index);
		
		layerModifications++;
	}

	public CopyOnWriteArrayList<GLayer> getLayers()
	{
		return layers;
	}

	public GLayer getLayer(int index)
	{
		return layers.get(index);
	}
	
	public int getLayerModifications()
	{
		return layerModifications;
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}

package core.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import core.gui.component.GComponent;
import core.gui.layer.GLayer;
import core.handler.EventHandler;

public class Viewport extends JPanel
{	
	private static final long serialVersionUID = 1L;
	
	private CopyOnWriteArrayList<GLayer> layers;

	private CopyOnWriteArrayList<GComponent> compBuffer, compOutput;
	
	private EventHandler eventHandler;
	
	public Viewport(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
		
		addMouseDetection();
		
		compBuffer = new CopyOnWriteArrayList<GComponent>();
		compOutput = new CopyOnWriteArrayList<GComponent>();
		
		layers = new CopyOnWriteArrayList<GLayer>();
		
		// Add all new components..
		for(GLayer edL : layers)
		{
			eventHandler.registerLayer(edL);
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		drawBackground(g);

		drawComponents(g);
		
		repaint();
	}
	
	private void drawBackground(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void drawComponents(Graphics g)
	{
		// Render all GUInness components.
		for(GComponent edC : compOutput)
		{
			if(edC.getStyle().isVisible() && edC.isEnabled())
			{
				edC.getStyle().getDesign().drawContext(g, edC);
			}
		}
	}
	
	// Adds the MouseAdapter as a Mouse(Motion)Listener in order to work with the Viewport when mouse actions have to be evaluated.
	private void addMouseDetection()
	{
		addMouseListener(eventHandler.getMouseAdapter());
		
		addMouseMotionListener(eventHandler.getMouseAdapter());
	}
	
	private CopyOnWriteArrayList<GComponent> copyComponents()
	{
		CopyOnWriteArrayList<GComponent> mirror = new CopyOnWriteArrayList<GComponent>();

		for (GComponent p : compBuffer)
		{
			mirror.add(p);
		}

		return mirror;
	}

	// Erases the internal buffer.
	public synchronized void erase()
	{
		compBuffer.clear();
	}

	// Copies the buffer immediately to the output, 
	// so all changes will be visible then first.
	public synchronized void copy()
	{
		compOutput = copyComponents();
	}
	
	// Adds all components of a layer to the internal component buffer (which is used for drawing only).
		private void apply(GLayer target)
		{
			// Add every component of the current layer.
			for(GComponent comp : target.getComponentBuffer())
			{					
				compBuffer.add(comp);
			}

			eventHandler.registerLayer(target);
		}

		// If a layer was changed, you can call this method to apply all changes.
		// Is very inefficient if it's called frequently.
		public synchronized void applyChanges()
		{
			erase(); // If buggy, re-instantiate 

			eventHandler.unregisterAllLayers();

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

			copy();
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
			}
			else
			{
				throw new IllegalArgumentException("The given layers priority is invalid (< 0 or reason is \"double priority\")");
			}

			applyChanges();
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

			eventHandler.unregisterLayer(uuid);

			applyChanges();
		}

		public CopyOnWriteArrayList<GLayer> getLayers()
		{
			return layers;
		}

		public GLayer getLayer(int index)
		{
			return layers.get(index);
		}

}

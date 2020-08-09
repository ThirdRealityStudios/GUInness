package core.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import core.gui.component.EDComponent;
import core.gui.design.Classic;
import core.gui.layer.EDLayer;
import core.handler.EventHandler;

public class Viewport extends JPanel
{
	private CopyOnWriteArrayList<EDLayer> layers;

	private CopyOnWriteArrayList<EDComponent> compBuffer, compOutput;
	
	private EventHandler eventHandler;
	
	private Classic defaultDesign;
	
	public Viewport(EventHandler eventHandler)
	{
		defaultDesign =  new Classic(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK, 2, 1);
		
		this.eventHandler = eventHandler;
		
		compBuffer = new CopyOnWriteArrayList<EDComponent>();
		compOutput = new CopyOnWriteArrayList<EDComponent>();
		
		layers = new CopyOnWriteArrayList<EDLayer>();
		
		// Add all new components..
		for(EDLayer edL : layers)
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
		// Render all EasyDraw components.
		for(EDComponent edC : compOutput)
		{
			if(isVisible())
			{
				edC.getDesign().drawContext(g, edC);
			}
		}
	}
	
	private CopyOnWriteArrayList<EDComponent> copyComponents()
	{
		CopyOnWriteArrayList<EDComponent> mirror = new CopyOnWriteArrayList<EDComponent>();

		for (EDComponent p : compBuffer)
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
		private void apply(EDLayer target)
		{
			if(target.isVisible())
			{
				// Add every component of the current layer.
				for(EDComponent comp : target.getComponentBuffer())
				{
					boolean updateForShapeNecessary = !comp.getType().contentEquals("image");
					
					if(updateForShapeNecessary)
					{
						comp.getDesign().updateDefaultShape(comp);
					}
					
					compBuffer.add(comp);
				}

				eventHandler.registerLayer(target);
			}
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
					for(EDLayer layer : layers)
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
		private boolean isDoublePriority(EDLayer layer)
		{
			for(EDLayer current : layers)
			{
				if(current.getPriority() == layer.getPriority())
				{
					return true;
				}
			}
			
			return false;
		}
		
		// The priority of a layer has to be at least zero or greater.
		private boolean isValidPriority(EDLayer layer)
		{
			return layer.getPriority() >= 0 && !isDoublePriority(layer);
		}

		public void addLayer(EDLayer layer) throws IllegalArgumentException
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

			for (EDLayer current : layers)
			{
				if(current.getUUID().toString().equals(uuid))
					break;

				index++;
			}

			layers.remove(index);

			eventHandler.unregisterLayer(uuid);

			applyChanges();
		}

		public CopyOnWriteArrayList<EDLayer> getLayers()
		{
			return layers;
		}

		public EDLayer getLayer(int index)
		{
			return layers.get(index);
		}

}

package core.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.gui.component.EDComponent;
import core.gui.design.Design;
import core.gui.layer.EDLayer;
import core.handler.EventHandler;

public class Display extends JFrame
{
	private Design design;
	
	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design design)
	{
		this.design = design;
	}

	private ArrayList<EDLayer> layers;

	private ArrayList<EDComponent> compBuffer, compOutput;

	private volatile EventHandler eH;

	private JPanel renderPanel = new JPanel()
	{
		private void drawBackground(Graphics g)
		{
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		private void drawComponents(Graphics g)
		{
			// Render all EasyDraw components.
			for (EDComponent edC : compOutput)
			{
				if(isVisible())
				{
					design.drawContext(g, edC);
				}
			}
		}

		@Override
		public void paintComponent(Graphics g)
		{
			drawBackground(g);

			drawComponents(g);

			repaint();
		}
	};
	
	public Display(Design design)
	{
		System.gc(); // This should just make up more space for this application.
		
		setDesign(design);
		
		layers = new ArrayList<EDLayer>();

		compBuffer = new ArrayList<EDComponent>();
		compOutput = new ArrayList<EDComponent>();

		setTitle("Standard Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);

		renderPanel.setVisible(true);
		add(renderPanel);

		eH = new EventHandler(this);
		
		for(EDLayer edL : layers)
		{
			eH.registerLayer(edL);
		}

		eH.start();
		
		eH.enableKeyboardDriver();
	}

	private ArrayList<EDComponent> copyComponents()
	{
		ArrayList<EDComponent> mirror = new ArrayList<EDComponent>();

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
				compBuffer.add(comp);
			}

			eH.registerLayer(target);
		}
	}

	// If a layer was changed, you can call this method to apply all changes.
	// Is very inefficient if it's called frequently.
	public synchronized void applyChanges()
	{
		erase(); // If buggy, re-instantiate 

		eH.unregisterAllLayers();

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

		eH.unregisterLayer(uuid);

		applyChanges();
	}

	public ArrayList<EDLayer> getLayers()
	{
		return layers;
	}

	public EDLayer getLayer(int index)
	{
		return layers.get(index);
	}

	public void center()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xScreenMiddle = (screen.width / 2), yScreenMiddle = (screen.height / 2);
		int xWindowMiddle = (getWidth() / 2), yWindowMiddle = (getHeight() / 2);
		
		int xMiddle = xScreenMiddle - xWindowMiddle, yMiddle = yScreenMiddle - yWindowMiddle;
		
		setLocation(xMiddle, yMiddle);
	}
	
	public EventHandler getEventHandler()
	{
		return eH;
	}
	
	public JPanel getRenderPanel()
	{
		return renderPanel;
	}
}

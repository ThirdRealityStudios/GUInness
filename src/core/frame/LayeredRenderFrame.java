package core.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.event.EventHandler;
import core.gui.EDComponent;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.classic.EDTextfield;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;
import core.tools.gui.FontLoader;
import core.tools.gui.UICreator;

public class LayeredRenderFrame extends JFrame implements RenderFrame
{
	private ArrayList<EDLayer> layers;

	private ArrayList<EDComponent> compBuffer, compOutput;

	private volatile EventHandler eH = new EventHandler(this);

	private UICreator uiCreator = new UICreator();
	
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
				edC.draw(g);
			}
		}

		@Override
		public void paint(Graphics g)
		{
			drawBackground(g);

			drawComponents(g);

			repaint();
		}
	};

	public LayeredRenderFrame()
	{
		layers = new ArrayList<EDLayer>();

		uiCreator = new UICreator();

		compBuffer = new ArrayList<EDComponent>();
		compOutput = new ArrayList<EDComponent>();

		setTitle("Standard RenderFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);

		renderPanel.setVisible(true);
		add(renderPanel);

		for(EDLayer edL : layers)
		{
			eH.registerLayer(edL);
		}

		eH.start();
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

	public void addComponent(EDLayer edL)
	{
		layers.add(edL);
	}

	public void removeComponent(EDComponent edC)
	{
		compBuffer.remove(compBuffer.indexOf(edC));
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

	// Write the entered alphabetic chars into the text field.
	public void loadNext(char input, EDTextfield target)
	{
		if(uiCreator.getFontLoader().isValid(input) && (target.getValue().length() + 1) <= target.getLength()) 
			target.setValue(target.getValue() + input);
	}

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

	public void addLayer(EDLayer layer)
	{
		layers.add(layer);

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
}

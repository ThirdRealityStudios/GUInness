package org.thirdreality.guinness.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.feature.shape.ShapeTransform;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.layer.GLayer;
import org.thirdreality.guinness.handler.EventHandler;

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
	
	public int key = 0;
	
	// This will tell you whether this Viewport is used in a GWindow context (simulated) or in a Display context (real).
	// The Viewport will know it is simulated by just passing 'null' to the constructor when creating it.
	// In this case, the event handling is fully taken over by the "real Viewport" which makes use of its EventHandler yet.
	// That means on the other hand, an EventHandler can only be used with Displays, not with GWindows !
	private final boolean isSimulated;
	
	/* The origin can be set in order to apply an additional offset to the Viewport.
	 * The origin is a special implementation in order to enable component content in GWindows.
	 * Imagine, you have a GWindow but how should the main renderer know that there is an offset for a Viewport?
	 * Normally, there are offsets only for components directly added to a Display (JFrame).
	 * But in this case, you need to know further details to correctly position this Viewport in a GWindow.
	 * 
	 * By default, the origin is at (0|0) relative to the upper-left Display corner (also at (0|0) by nature).
	 * Anyway, the origin is at the position of the GWindow content frame when it is used for "simulated" GUI environments..
	 */
	private Point origin = new Point();
	
	// You can define with this variable a so called "clipping area".
	// This will prevent components beyond these borders from being rendered and recognized.
	// It might help you to save performance but on the other hand it also serves a GWindow to avoid overlapping by the just simulated components.
	// The clipping area must be regarded absolute, meaning it really clips off components no matter what scale or offset they have in the end.
	// Also the clipping area always begins at (0|0) at the upper-left corner of the Display or GWindow.
	private Dimension clippingArea;

	// This is used for pure evaluation whether a component is still inside the clipping area or not.
	private Rectangle clippingRectangle;

	public Viewport(EventHandler eventHandler, boolean isSimulated)
	{
		this.eventHandler = eventHandler;

		this.isSimulated = isSimulated;

		addMouseDetection();

		compBuffer = new CopyOnWriteArrayList<GComponent>();
		compOutput = new GComponent[0];

		layers = new CopyOnWriteArrayList<GLayer>();

		updateClippingRectangle(new Dimension());
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

	// In the beginning this will just draw a background color which will erase the content of the last draw cycle.
	private void drawBackground(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	// Draws all components from the output list 'compOutput'.
	// When adding new layers, they are not yet added to the output directly.
	// First, it is being waited until all (new and old) components have been read (again (for old components yet stored)).
	// Only then the components are directly outputed by just changing the reference.
	public void drawComponents(Graphics g)
	{
		drawComponentsByArray(g, compOutput);
	}

	public void drawComponentsByArray(Graphics g, GComponent[] components)
	{		
		// Render all GUInness components.
		for(GComponent component : components)
		{
			if(component.getStyle().isVisible() && component.isEnabled() && isContained(component))
			{
				component.getStyle().getDesign().drawContext(g, this, component, getOrigin(), getOffset(), getScale());
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
	public void outputComponentBuffer()
	{
		compOutput = compBuffer.toArray(compOutput);
	}

	// Adds all components of a layer to the internal component buffer (which is used for drawing only).
	private void addLayerToComponentBuffer(GLayer target)
	{
		// Add every component of the current layer.
		for(GComponent comp : target.getComponentBuffer())
		{
			compBuffer.add(comp);
		}
	}

	// If a layer was changed, you can call this method to apply all changes.
	// Is very inefficient if it's called frequently.
	public synchronized void updateComponentBuffer()
	{
		erase(); // If buggy, re-instantiate

		Collections.sort(layers);

		if(layers != null && layers.size() > 0)
		{
			if(layers.size() > 1)
			{
				for(GLayer layer : layers)
				{
					addLayerToComponentBuffer(layer);
				}
			}
			else
			{
				addLayerToComponentBuffer(layers.get(0));
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

	// The priority of a layer has to be at least zero or greater and mustn't not appear more than twice in the same Viewport.
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

			updateComponentBuffer();
			outputComponentBuffer();
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
			{
				break;
			}

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

	// Returns the current offset, regardless of any Viewport scaling (!).
	// If you want to consider the Viewport scaling in your application and more precision,
	// use the getRelativeOffset()-method below please.
	public Point getOffset()
	{
		return offset;
	}

	// Returns the exact offset relative to the current Viewport scale.
	// This method is definitely recommended when you need high precision,
	// though its frequent execution could cause a worse impact on the performance (remind floating point calculations and object creation).
	public Point.Float getRelativeOffset()
	{		
		return new Point.Float(getScale() * getOffset().x, getScale() * getOffset().y);
	}

	public void setOffset(Point offset)
	{
		this.offset = offset;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	// Use this method to retrieve a copy of a polygon which fits the scale and offset given by the Viewport.
	public Polygon getPolygonRelativeToViewport(Polygon p)
	{
		return ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(p, new GIPoint(p.getBounds().getLocation()).add(getOffset()).toPoint()), getScale());
	}

	// Use this method to retrieve a location which considers the scale and offset given by this Viewport.
	public Point getLocationRelativeToViewport(Point location)
	{
		return new Point((int) ((location.x + offset.x) * scale), (int) ((location.y + offset.y) * scale));
	}

	public boolean isSimulated()
	{
		return isSimulated;
	}

	public Point getOrigin()
	{
		return origin;
	}

	public void setOrigin(Point origin)
	{
		this.origin = origin;
	}

	public int sizeOfComponentBuffer()
	{
		return compBuffer.size();
	}

	public int sizeOfComponentOutput()
	{
		return compOutput.length;
	}

	public GComponent[] getComponentOutput()
	{
		return compOutput;
	}

	public Dimension getClippingArea()
	{
		return clippingArea;
	}

	public void setClippingArea(Dimension clippingArea)
	{
		this.clippingArea = clippingArea;

		createClippingRectangle();
	}

	private void createClippingRectangle()
	{
		clippingRectangle = new Rectangle(getOrigin(), getClippingArea());
	}

	private void updateClippingRectangle(Dimension clippingArea)
	{
		setClippingArea(clippingArea);

		clippingRectangle.setSize(clippingArea);
		clippingRectangle.setLocation(getOrigin());
	}

	// Tells you whether a component can be rendered or recognized by IO, depending on an area which defines this (see 'clippingArea' and 'clippingRectangle' on top).
	public boolean isContained(GComponent component)
	{
		if(isSimulated())
		{
			clippingRectangle.setLocation(getOrigin());
			
			Rectangle componentBounds = component.getStyle().getPrimaryLook().getBounds();

			Rectangle componentBoundsRelative = new Rectangle(new GIPoint(getOrigin()).add(getOffset()).add(componentBounds.getLocation()).toPoint(), componentBounds.getSize());

			boolean isContained = clippingRectangle.contains(componentBoundsRelative);

			return isContained;
		}

		return true;
	}
}
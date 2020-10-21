package org.thirdreality.guinness.gui.design.classic;

import java.awt.Graphics;
import java.awt.Point;

import org.thirdreality.guinness.feature.GIPoint;
import org.thirdreality.guinness.gui.Viewport;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.component.placeholder.GWindow;

public class SimulatedWindowDrawAdapter
{
	// This saves the offset of the viewport which is used by the display.
	private Point displayViewpointOffset;
	
	public SimulatedWindowDrawAdapter(Point displayViewportOffset)
	{
		this.displayViewpointOffset = displayViewportOffset;
	}
	
	// Draws the content for windows (for type GWindow).
	// This is a safe method, meaning it checks the components type for a GWindow.
	public void drawSimulatedContext(Graphics g, GComponent c)
	{
		if(c.getType().contentEquals("window"))
		{
			drawSimulatedViewport(g, (GWindow) c);
		}
	}

	private void drawSimulatedViewport(Graphics context, GWindow target)
	{
		if(target.hasViewport())
		{
			// Save the prior origin and offset.
			Point priorOrigin = target.getViewport().getOrigin();

			// Tell the renderer (Viewport) to render its components at the given location (origin).
			// In this case, it is the upper-left corner of the inner frame of the window.
			target.getViewport().setOrigin(new GIPoint(target.getStyle().getSecondaryLook().getBounds().getLocation()).add(displayViewpointOffset).toPoint());

			// Now render here all components of the Viewport at the given origin (location),
			renderEachComponent(context, target.getViewport());

			// Reset the origin in order not to influence further drawings of other components different than GWindow.
			target.getViewport().setOrigin(priorOrigin);
		}
	}

	private void renderEachComponent(Graphics context, Viewport source)
	{
		source.drawComponents(context);
	}
}

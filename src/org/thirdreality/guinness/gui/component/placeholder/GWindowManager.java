package org.thirdreality.guinness.gui.component.placeholder;

import java.util.ArrayList;
import java.util.Comparator;

import org.thirdreality.guinness.Meta;
import org.thirdreality.guinness.gui.Viewport;
import org.thirdreality.guinness.gui.component.GComponent;
import org.thirdreality.guinness.gui.layer.GLayer;

// Manages all GWindows (adds or removes them, changes its priorities, moves one window to the front or behind one another, etc.).
public class GWindowManager
{
	private Comparator<GLayer> windowComparator;

	/*  "windowsLayer" is the layer array which contains all GWindows.
	 *  When they are need, the program will just request the corresponding GLayer in this class (per method "getWindows()").
	 *  It is ensured, that all windows will have the highest priority, so they detected and rendered first before anything else during runtime.
	 *  To realize that, the layer just has the highest possible priority (see Integer.MAX_VALUE).
	 *  This is also why you can't have two window managers for the same Viewport.
	 *  It is best practice not to use multiple window managers in general in order not to have a conflict with "double priorities" later (as they are called when an IllegalArgumentException is thrown).
	 *  If you are aware of this possible error (just as described), you can of course use multiple managers..
	 */
	private ArrayList<GLayer> windowLayers;

	private int priorityMinUsage = Meta.WINDOW_PRIORITY_MIN;
	
	private Viewport displayViewport;

	public GWindowManager(Viewport displayViewport)
	{		
		// For a description, see its block-comment.
		windowLayers = new ArrayList<GLayer>(Meta.WINDOW_CAP);
		
		this.displayViewport = displayViewport;
	}
	
	// Returns a priority relative to the location of the given window in the list.
	private int getRelativePriority(int index)
	{
		return index + Meta.WINDOW_PRIORITY_MIN;
	}
	
	private void updatePriority(GLayer windowLayer, int index)
	{
		// Gets the priority relative to the position of the window in the list.
		int synchronizablePriority = getRelativePriority(index);

		((GWindow) windowLayer.getComponentBuffer().get(0)).priority = synchronizablePriority;

		windowLayer.setPriority(synchronizablePriority);
	}
	
	// Keeps all priorities again up-to-date, relative to the order in the list.
	private void updatePriorityOfAll()
	{
		for(int i = 0; i < windowLayers.size(); i++)
		{
			updatePriority(windowLayers.get(i), i);
		}
	}
	
	private void initPriority(GLayer windowLayer)
	{
		updatePriority(windowLayer, windowLayers.size() - 1);
	}
	
	// Adds a GWindow to the manager and sorts all registered windows, according to their priorities, directly.
	public void addWindow(GWindow window)
	{
		if(windowLayers.size() < Meta.WINDOW_CAP)
		{
			GLayer windowLayer = new GLayer(0, true);
			
			windowLayer.add(window);
			
			windowLayers.add(0, windowLayer);
			
			// Initializes the priority of the layer. Do not change this order (must be at the end)!
			initPriority(windowLayer);
			
			displayViewport.addLayer(windowLayer);
		}
	}
	
	private int indexOf(GWindow window)
	{
		for(int i = 0; i < windowLayers.size(); i++)
		{
			GLayer currentLayer = windowLayers.get(i);
			
			GComponent comparedWindow = currentLayer.getComponentBuffer().get(0);
			
			if(comparedWindow.hashCode() == window.hashCode())
			{
				// Return the index of the window which was found.
				return i;
			}
		}
		
		// No window matching the parameter was found.
		return -1;
	}

	// Removes a GWindow from the manager and sorts all registered windows, according to their priorities, directly.
	public boolean removeWindow(GWindow toRemove)
	{
		int index = indexOf(toRemove);
		
		boolean wasIndexDetermined = index > -1;
		
		if(wasIndexDetermined)
		{
			GLayer removed = windowLayers.remove(index);
			
			boolean successfulRemovedInternally = removed != null;
			
			boolean successfulRemovedGlobally = false;
			
			if(successfulRemovedInternally)
			{
				updatePriorityOfAll();
				
				successfulRemovedGlobally = displayViewport.removeLayer(removed) != null;
			}
			
			return successfulRemovedInternally && successfulRemovedGlobally;
		}
		else
		{
			return false;
		}
	}
	
	// Returns all windows registered in this window manager.
	public ArrayList<GLayer> getWindows()
	{
		return windowLayers;
	}
}

package org.thirdreality.guinness.gui.component.placeholder;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

// Manages all GWindows (adds or removes them, changes its priorities etc.).
public class GWindowManager
{
	private Comparator<PriorityContext> priorityComparator;
	
	private class PriorityContext implements Comparable
	{
		public int priority;
		
		public GWindow window;
		
		public PriorityContext(int priority, GWindow window)
		{
			this.priority = priority;
			this.window = window;
		}

		@Override
		public int compareTo(Object priorityContext)
		{
			PriorityContext context = (PriorityContext) priorityContext;
			
			return context.priority - priority;
		}
	}
	
	private int priority = 0;
	
	// This contains all (prioritized) windows added to this WindowManager.
	private CopyOnWriteArrayList<PriorityContext> priorityContext;
	
	public GWindowManager()
	{
		priorityContext = new CopyOnWriteArrayList<GWindowManager.PriorityContext>();
		
		priorityComparator = new Comparator<GWindowManager.PriorityContext>()
		{
			@Override
			public int compare(PriorityContext arg0, PriorityContext arg1)
			{
				return arg1.priority - arg0.priority;
			}
		};
	}
	
	// Adds a GWindow to the manager and sorts all registered windows, according to their priorities, directly.
	public void addWindow(GWindow window)
	{
		priorityContext.add(new PriorityContext(priority++, window));
	
		priorityContext.sort(priorityComparator);
	}

	// Removes a GWindow from the manager and sorts all registered windows, according to their priorities, directly.
	public void removeLayer(PriorityContext toRemove)
	{
		int index = 0;

		for(PriorityContext current : priorityContext)
		{
			if(current.hashCode() == toRemove.hashCode())
			{
				break;
			}

			index++;
		}

		priorityContext.remove(index);
		
		priorityContext.sort(priorityComparator);
	}
	
	// Returns all windows with their id as a copy.
	public CopyOnWriteArrayList<PriorityContext> getWindows()
	{
		return new CopyOnWriteArrayList<GWindowManager.PriorityContext>(priorityContext);
	}
}

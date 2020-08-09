package core.exec;

import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadManager
{
	private CopyOnWriteArrayList<Thread> threads;
	
	private int maximumThreads = 0;
	
	public ThreadManager(int maximumThreads)
	{
		threads = new CopyOnWriteArrayList<Thread>();
		
		this.maximumThreads = maximumThreads;
	}
	
	private void removeDeaths()
	{
		for(int i = 0; i < threads.size(); i++)
		{
			if(threads.get(i) != null && !threads.get(i).isAlive())
			{
				threads.remove(i);
			}
		}
		
		// Clear space up for other data and remove the dead threads with the GBC.
		// Otherwise related applications could freeze up, like the IDE.
		// Also it is just a better practice..
		System.gc();
	}
	
	public boolean fire(Thread t)
	{
		removeDeaths();
		
		if(threads.size() + 1 <= maximumThreads)
		{
			threads.add(t);
			
			t.start();
			
			return true;
		}
		
		return false;
	}
	
	public int size()
	{
		return threads.size();
	}
}

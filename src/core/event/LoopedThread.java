package core.event;

import core.io.Interrupt;

public abstract class LoopedThread
{
	private Thread loop = null;

	// Needs to be 'volatile'. Otherwise other processes would not get the current value.
	private volatile boolean breakLoop = false;

	public LoopedThread()
	{
		loop = new Thread()
		{
			@Override
			public void run()
			{
				while(!tryingBreak())
				{
					loop();
				}
			}
		};
	}

	public boolean tryingBreak()
	{
		return breakLoop;
	}

	public void breakLoop()
	{
		this.breakLoop = true;
	}

	public Thread getThread()
	{
		return loop;
	}

	// A loop must not be empty. If it is 
	public abstract void loop();
}
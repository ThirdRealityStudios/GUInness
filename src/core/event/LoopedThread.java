package core.event;

public abstract class LoopedThread
{
	private Thread loop = null;

	private boolean breakLoop = false;

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

	public abstract void loop();
}
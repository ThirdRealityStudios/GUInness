package core.io.state;

public class FlagHolder
{
	private volatile boolean update, stop;
	
	public synchronized void update(boolean update)
	{
		this.update = update;
	}
	
	public synchronized void stop(boolean stop)
	{
		this.stop = stop;
	}
	
	public synchronized boolean stop()
	{
		return this.stop;
	}
	
	public synchronized boolean update()
	{
		return this.update;
	}
}

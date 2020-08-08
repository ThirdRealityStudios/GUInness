package core;

// This is a small, simple, passively, manually triggered FPS counter which allows you to use it in your game loop cycles.
// In this case,
// it was prevented to use an active counter due to performance reasons (CPU usage) and because counting the FPS is only a small, quick task.
// For this task, it wouldn't be worth using threads because of inefficiency.
// Anyway,
// because this FPS counter is passively running the FPS values may be a little rough or not very up-to-date when requesting them.
public class FPSCounter
{
	long start, diff, lastDiff;
	
	boolean newCycle = true;
	
	int frames;
	
	private volatile float fps;
	
	// This is a simple FPS counter which needs to be triggered manually every game loop cycle.
	public void count()
	{
		long current = System.currentTimeMillis();
		
		if(newCycle)
		{
			// Reset and set all initial values to determine the FPS count later.
			
			start = System.currentTimeMillis();
			
			frames = 0;
			
			newCycle = false;
		}
		else
		{
			diff = current - start;
			
			if(diff <= 1000)
			{
				frames++;
			}
			else
			{
				fps = getCorrectedFPS(diff, frames);
				
				lastDiff = diff;
				
				newCycle = true;
			}
		}
	}
	
	// This will give you the correct FPS count by down-scaling the value using the percentage of elapsed time.
	private float getCorrectedFPS(long diff, int frames)
	{
		return frames / (diff / 1000f);
	}
	
	// This will give you the current elapsed time since the timer is running (every second again).
	// If the cycle durates much longer than a second, the value is of course much bigger.
	// In this the FPS count is nearly at zero (freezing screen).
	public long getCurrentDiffMs()
	{
		return diff;
	}
	
	// Just returns the current FPS.
	public float getFPS()
	{
		return fps;
	}
}

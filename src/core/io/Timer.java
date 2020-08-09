package core.io;

public class Timer
{
	/*
	 * Do not use these methods too often as the while loop could stress your CPU
	 * and RAM. Hence, there are better solutions on the internet, though the code
	 * itself is reliable. The javax.swing.Timer is not very reliable though this
	 * solution just needs some more resources.
	 */
	
	private static final long ms = 1000000, s = ms*1000, min = s*60, hour = min*60;
	
	private static void pause(long ns)
	{
		final long start = System.nanoTime();
		
		final long timeout = ns;
		
		while((System.nanoTime() - start) <= timeout);
	}
	
	public static void pauseMillisecond(long milliseconds)
	{		
		final long timeout = ms*milliseconds;
		
		pause(timeout);
	}

	public static void pauseSecond(long seconds)
	{
		final long timeout = s*seconds;
		
		pause(timeout);
	}

	public static void pauseMinute(long minutes)
	{
		final long timeout = min*minutes;
		
		pause(timeout);
	}

	public static void pauseMinute(double minutes)
	{
		final long timeout = (long) ((double) min*minutes);
		
		pause(timeout);
	}

	public static void pauseHour(long hours)
	{
		final long timeout = hour*hours;
		
		pause(timeout);
	}

	public static void pauseHour(double hours)
	{
		final long timeout = (long) ((double) hour*hours);
		
		pause(timeout);
	}
}

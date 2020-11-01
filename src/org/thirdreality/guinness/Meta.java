package org.thirdreality.guinness;

public interface Meta
{
	/*
	 * This will provide all classes with a serial version ID.
	 * The serial version ID is used for several cases:
	 * - to save performance on runtime, so the JRE doesn't need to generate one.
	 * - to guarantee compatibility when serialized classes of older versions are loaded again.
	 * 		-> This will then ensure if the read class is still compatible with the latest version.
	 * 		-> For example, you save the object of a GButton on your hard-drive.
	 * 		   The save file will then contain information about the title or font size etc.
	 * 		   Now the version has changed and let's say, the font size attribute was removed lately.
	 * 		   If you would now load the file again on runtime this would result in an error
	 * 		   because the attribute from the save file does not exist anymore.
	 * 		   To avoid this ugly behavior a serial version is always specified.
	 * 		   On runtime, the file can then be checked if it is still compatible.
	 * 		   In the end this prevents undefined reactions or exceptions from the JRE and the program doesn't crash.
	 * - when there was a change and it is unknown whether the changes are still compatible with the latest version.
	 * 		-> in this the serial version will be incremented.
	 */
	public static long serialVersionUID = 8L;
	
	@Deprecated
	// The values below describe occupation space of GWindows.
	// Changing these values can allow you to enable more GWindows at the same time.
	// Anyway, I do not recommend this as it makes the program more unstable probably.
	// Mainly, a window cap exists due to the need of priorities which cannot be used infinitely and more than twice the same number.
	public static final int WINDOW_CAP = 64;
	
	public static final int WINDOW_PRIORITY_MAX = Integer.MAX_VALUE;
	public static final int WINDOW_PRIORITY_CAP = WINDOW_CAP * WINDOW_CAP * WINDOW_CAP;
	public static final int WINDOW_PRIORITY_MIN = WINDOW_PRIORITY_MAX - WINDOW_PRIORITY_CAP;
}

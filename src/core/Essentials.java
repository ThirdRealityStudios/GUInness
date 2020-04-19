package core;

public class Essentials
{
	// Returns the type of an object.
	// Returns an empty String if the given reference is null.
	public static String typeof(Object o)
	{
		return o != null ? o.getClass().getSuperclass().getSimpleName() : "";
	}
}

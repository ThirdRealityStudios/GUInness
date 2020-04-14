package core;

public class Essentials
{
	public static String typeof(Object o)
	{
		return o.getClass().getSuperclass().getSimpleName();
	}
}

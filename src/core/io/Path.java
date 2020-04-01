package core.io;

import java.io.File;

public interface Path
{
	public static final String CORE_PATH = new File(System.getProperty("user.dir") + "\\src").exists()
			? (System.getProperty("user.dir") + "\\src")
			: (System.getProperty("user.dir") + "\\bin");

	public static final String CORE_MEDIA = Path.CORE_PATH + "\\media";
}

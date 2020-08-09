package core.feature;

import java.io.File;

public interface Path
{
	public static final String CORE_PATH = new File(System.getProperty("user.dir") + "\\src\\core").exists()
			? (System.getProperty("user.dir") + "\\src\\core")
			: (System.getProperty("user.dir") + "\\bin\\core");

	public static final String FONTS = Path.CORE_PATH + "\\gui\\font\\image";
}

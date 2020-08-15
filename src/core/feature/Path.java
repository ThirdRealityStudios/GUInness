package core.feature;

import java.io.File;

public interface Path
{	
	public static final String CORE_PATH = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "core").exists() ? (System.getProperty("user.dir") + File.separator + "src" + File.separator + "core") : (System.getProperty("user.dir") + File.separator + "bin" + File.separator + "core");

	public static final String GUI_PATH = CORE_PATH + File.separator + "gui";
	
	public static final String FONTS = Path.CORE_PATH + File.separator + "gui" + File.separator + "font" + File.separator + "image";
}

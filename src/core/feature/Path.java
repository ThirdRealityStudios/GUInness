package core.feature;

import java.io.File;

public interface Path
{	
	public static final String ROOT = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "core").exists() ? (System.getProperty("user.dir") + File.separator + "src" + File.separator + "core") : (System.getProperty("user.dir") + File.separator + "bin" + File.separator + "core");
	
	public static final String GUI = ROOT + File.separator + "gui";
	
	public static final String FONT = Path.ROOT + File.separator + "gui" + File.separator + "image" + File.separator + "font";
	
	public static final String ICON = Path.ROOT + File.separator + "gui" + File.separator + "image" + File.separator + "icon";
}

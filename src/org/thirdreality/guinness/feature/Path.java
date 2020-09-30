package org.thirdreality.guinness.feature;

import java.io.File;

public interface Path
{
	public static final String USER_DIR = System.getProperty("user.dir");
	
	public static final String GUINNESS_FOLDER = (File.separator + "org" + File.separator + "thirdreality" + File.separator + "guinness");
	
	public static final String SRC_FOLDER = USER_DIR + File.separator + "src" + GUINNESS_FOLDER;
	
	public static final String BIN_FOLDER = USER_DIR + File.separator + "bin" + GUINNESS_FOLDER;
	
	public static final String ROOT_FOLDER = new File(SRC_FOLDER).exists() ? SRC_FOLDER : BIN_FOLDER;
	
	public static final String GUI_FOLDER = ROOT_FOLDER + File.separator + "gui";
	
	public static final String FONT_FOLDER = GUI_FOLDER + File.separator + "image" + File.separator + "font";
	
	public static final String ICON_FOLDER = GUI_FOLDER + File.separator + "image" + File.separator + "icon";
}

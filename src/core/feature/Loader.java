package core.feature;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Loader
{
	public static Image loadImage(String path)
	{
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path)).getImage();
	}
}

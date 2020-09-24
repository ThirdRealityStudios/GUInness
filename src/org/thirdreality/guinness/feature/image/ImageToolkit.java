package org.thirdreality.guinness.feature.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageToolkit
{
	public static Image loadImage(String path)
	{
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path)).getImage();
	}
	
	public static BufferedImage colorize(BufferedImage image, Color color)
    {
        int w = image.getWidth();
        int h = image.getHeight();
        
        BufferedImage colorized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = colorized.createGraphics();
        
        g.drawImage(image, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0,0,w,h);
        g.dispose();

        return colorized;
    }
}

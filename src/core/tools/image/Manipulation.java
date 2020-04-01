package core.tools.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Manipulation
{
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

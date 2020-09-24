package org.thirdrealitystudios.guinness.gui.font;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.thirdrealitystudios.guinness.feature.Path;

public class Font
{
	private String name;
	
	private BufferedImage image;
	
	private File file;
	
	private final static String defaultFilepath = Path.FONT + File.separator + "StandardFont.png";
	
	private Color fontColor = Color.BLACK;
	
	// This is the font size in pixels.
	private int fontSize = 17;

	public Font(String name, String filepath) throws NullPointerException, IllegalArgumentException
	{
		if(name != null)
		{
			if(name.length() > 0)
			{
				this.name = name;
			}
			else
			{
				throw new IllegalArgumentException("The constructor parameter 'name' cannot be empty.\nMake sure the passed name of the Font has at least a length greater than zero (>0) !");
			}
		}
		else
		{
			throw new NullPointerException("The constructor parameter 'name' cannot be null.\nAre you sure, you gave the Font a name?");
		}
		
		if(filepath != null)
		{
			File file = new File(filepath);
			
			if(file.exists() && file.isFile())
			{
				this.file = file;
				
				image = loadPattern(file.getAbsolutePath());
			}
			else
			{
				System.out.println(filepath);
				throw new IllegalArgumentException("The constructor parameter 'filepath' is invalid.\nMake sure you have specified a valid directory and file for the path of the Font!");
			}
		}
		else
		{
			throw new NullPointerException("The constructor parameter 'filepath' cannot be null.\nAre you sure, you gave the Font a file path?");
		}
	}
	
	public Font(String name, String filepath, int fontSize) throws NullPointerException, IllegalArgumentException
	{
		this(name, filepath);
		
		if(fontSize > 0)
		{
			setFontSize(fontSize);
		}
		else
		{
			throw new IllegalArgumentException("The constructor parameter 'fontSize' cannot be null.\nAre you sure, you gave the Font a valid font size (>0 px)?");
		}
	}
	
	private BufferedImage loadPattern(String filepath)
	{
		try
		{
			BufferedImage loaded = null;

			String path = filepath;

			loaded = ImageIO.read(new File(path));

			return loaded;
		}
		catch (IOException e)
		{
			e.printStackTrace();

			return null;
		}
	}
	
	public int getFontSize()
	{
		return fontSize;
	}

	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}
	
	public Color getFontColor()
	{
		return fontColor;
	}
	
	public String getName()
	{
		return name;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public File getFile()
	{
		return file;
	}

	public static String getDefaultFilepath()
	{
		return defaultFilepath;
	}
}

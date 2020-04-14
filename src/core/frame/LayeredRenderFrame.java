package core.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.event.EventHandler;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDTextfield;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;
import core.io.state.FlagHolder;
import core.tools.gui.UICreator;

public class LayeredRenderFrame extends JFrame implements RenderFrame, KeyListener
{
	private ArrayList<EDLayer> layers;

	private ArrayList<EDPath> pathBuffer, pathOutput;

	private ArrayList<EDText> textBuffer, textOutput;

	private ArrayList<EDImage> imgBuffer, imgOutput;

	private EventHandler eH = new EventHandler(this);

	private UICreator uiCreator = new UICreator();

	public LayeredRenderFrame()
	{
		layers = new ArrayList<EDLayer>();

		uiCreator = new UICreator();

		pathBuffer = new ArrayList<EDPath>();
		pathOutput = new ArrayList<EDPath>();
		textBuffer = new ArrayList<EDText>();
		textOutput = new ArrayList<EDText>();
		imgBuffer = new ArrayList<EDImage>();
		imgOutput = new ArrayList<EDImage>();

		setTitle("Standard RenderFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);

		JPanel content = new JPanel()
		{
			private void drawBackground(Graphics g)
			{
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}

			private void drawPath(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;

				// Render all paths.
				for (EDPath edP : pathOutput)
				{
					if(edP.isVisible())
					{
						g.setColor(edP.getDrawColor());

						if (edP.isFill())
							g2d.fill(edP.getPath());
						else
							g2d.draw(edP.getPath());
					}
					else
						continue;
				}
			}

			private void drawComponents(Graphics g)
			{
				// Render all text components.
				for (EDText edT : textOutput)
				{
					if(edT.isVisible())
						uiCreator.createText(g, edT);
					else
						continue;
				}
			}

			private void drawImages(Graphics g)
			{
				for (EDImage img : imgOutput)
				{
					if(img.isVisible())
						g.drawImage(img.getContent(), img.getRectangle().getLocation().x, img.getRectangle().getLocation().y, img.getRectangle().getSize().width, img.getRectangle().getSize().height, null);
					else
						continue;
				}
			}

			@Override
			public void paint(Graphics g)
			{
				drawBackground(g);

				drawImages(g);

				drawPath(g);

				drawComponents(g);

				repaint();
			}
		};

		content.setVisible(true);
		add(content);

		for (EDLayer edL : layers)
		{
			eH.registerLayer(edL);
		}

		eH.start();

		addKeyListener(this);
	}

	private ArrayList<EDPath> copyPath()
	{
		ArrayList<EDPath> mirror = new ArrayList<EDPath>();

		for (EDPath p : pathBuffer)
		{
			p.getPath().closePath();
			mirror.add(p);
		}

		return mirror;
	}

	private ArrayList<EDText> copyText()
	{
		ArrayList<EDText> mirror = new ArrayList<EDText>();

		for (EDText p : textBuffer)
		{
			mirror.add(p);
		}

		return mirror;
	}

	private ArrayList<EDImage> copyImage()
	{
		ArrayList<EDImage> mirror = new ArrayList<EDImage>();

		for (EDImage p : imgBuffer)
		{
			mirror.add(p);
		}

		return mirror;
	}

	public void addText(EDLayer edL)
	{
		layers.add(edL);
	}

	public void removeImage(EDImage edI)
	{
		imgBuffer.remove(imgBuffer.indexOf(edI));
	}

	// Erases the internal buffer.
	public synchronized void erase()
	{
		pathBuffer.clear();
		textBuffer.clear();
		imgBuffer.clear();
	}

	// Copies the buffer immediately to the output, 
	// so all changes will be visible then first.
	public synchronized void copy()
	{
		pathOutput = copyPath();
		textOutput = copyText();
		imgOutput = copyImage();
	}
	
	// Write the entered alphabetic chars into the text field.
	public void loadNext(char input, EDTextfield target)
	{
		if(uiCreator.getFontLoader().isValid(input) && (target.getValue().length() + 1) <= target.getLength()) 
			target.setValue(target.getValue() + input);
	}

	// Handles all interaction with the interface components.
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		for (EDLayer currentLayer : eH.getRegisteredLayers())
		{
			for (EDText current : currentLayer.getTextBuffer())
			{
				String simpleType = Summary.typeof(current); // Get the type in upper-case.

				if(simpleType.equals("EDTEXTFIELD"))
				{
					EDTextfield text = (EDTextfield) current;

					if(text.isActive())
					{
						loadNext(e.getKeyChar(), text);

						// See if Enter was hit to save the changes.
						if(e.getKeyCode() == KeyEvent.VK_ENTER)
						{
							text.setBufferedValue(text.getValue());

							text.setBackground(text.getBufferedColor());
							text.setBufferedColor(null);

							text.setInactive();
						}
						else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						{
							if(text.getValue().length() > 0)
							{
								String clippedEnd = text.getValue().substring(0, text.getValue().length() - 1);

								text.setValue(clippedEnd);
							}
						}
						else if(e.getKeyChar() == KeyEvent.VK_ESCAPE) // Leave without saving changes.
						{
							eH.reset(text);
						}
					}
					else
					{
						text.setBufferedValue(text.getValue());
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

	private void apply(EDLayer current)
	{
		if (current.isVisible())
		{
			// Add every path of the current layer.
			for (EDPath path : current.getPathBuffer())
			{
				pathBuffer.add(path);
			}

			// Add every text component, e.g. a button, of the current layer.
			for (EDText text : current.getTextBuffer())
			{
				textBuffer.add(text);
			}

			// Add every image of the current layer.
			for (EDImage img : current.getImgBuffer())
			{
				imgBuffer.add(img);
			}

			eH.registerLayer(current);
		}
	}

	// If a layer was changed, you can call this method to apply all changes.
	// Is very inefficient if it's called frequently.
	public synchronized void applyChanges()
	{
		erase(); // If buggy, re-instantiate 

		eH.unregisterAllLayers();

		Collections.sort(layers);

		if(layers != null && layers.size() > 0)
		{
			if(layers.size() > 1)
			{
				for(EDLayer layer : layers)
				{
					apply(layer);
				}
			}
			else
			{
				apply(layers.get(0));
			}
		}
		
		copy();
	}

	public void addLayer(EDLayer layer)
	{
		layers.add(layer);

		applyChanges();
	}

	public void removeLayer(String uuid)
	{
		erase();

		int index = 0;

		for (EDLayer current : layers)
		{
			if(current.getUUID().toString().equals(uuid))
				break;

			index++;
		}

		layers.remove(index);

		eH.unregisterLayer(uuid);

		applyChanges();
	}

	public ArrayList<EDLayer> getLayers()
	{
		return layers;
	}

	public EDLayer getLayer(int index)
	{
		return layers.get(index);
	}
	
	public void center()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xScreenMiddle = (screen.width / 2), yScreenMiddle = (screen.height / 2);
		int xWindowMiddle = (getWidth() / 2), yWindowMiddle = (getHeight() / 2);
		
		int xMiddle = xScreenMiddle - xWindowMiddle, yMiddle = yScreenMiddle - yWindowMiddle;
		
		setLocation(xMiddle, yMiddle);
	}
	
	public EventHandler getEventHandler()
	{
		return eH;
	}
}

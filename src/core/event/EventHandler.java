package core.event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.EDTextfield;
import core.io.Interrupt;

public class EventHandler
{
	private LayeredRenderFrame rF = null;

	private ArrayList<EDLayer> registeredLayers;

	// Used to prevent other text fields from being selected at once.
	private EDTextfield textfieldLocked = null;
	
	private volatile boolean isHovering = false;
	
	private volatile Point mouseLoc = null;

	private boolean inside(Point loc, Dimension dim, Point comparedLoc)
	{
		return ((comparedLoc.x >= loc.x && comparedLoc.x <= (loc.x + dim.width))
				&& (comparedLoc.y >= loc.y && comparedLoc.y <= (loc.y + dim.height)));
	}
	
	public void reset(EDTextfield text)
	{
		if(text.getBufferedValue() != null)
			text.setValue(text.getBufferedValue());

		text.setBackground(text.getBufferedColor());
		text.setBufferedColor(null);

		text.setBackground(text.getBackground());
		text.setInactive();
		unlockTextfield();
	}

	private MouseListener mouseListener = new MouseListener()
	{
		@Override
		public void mouseClicked(MouseEvent mouseEvent)
		{
			mouseLoc = new Point(mouseEvent.getPoint().x - 8, mouseEvent.getPoint().y - 31);

			Rectangle textArea = null;

			for (EDLayer layer : registeredLayers)
			{
				// Tests for all available events whether a button was clicked.
				// It will the corresponding implementation.
				for (EDText current : layer.getTextBuffer())
				{
					String type = current.getClass().getGenericSuperclass().getTypeName();
					int last = type.lastIndexOf('.') + 1;
					String simpleType = type.substring(last, type.length()).toUpperCase();

					textArea = new Rectangle(current.getRectangle().getSize());
					textArea.setLocation(current.getRectangle().getLocation());

					// Execute onClick() of a graphical component if it was clicked.
					if(inside(textArea.getLocation(), textArea.getSize(), mouseLoc))
					{
						switch (simpleType)
						{
							case ("EDTEXTFIELD"):
							{
								if (textfieldLocked == null)
								{
									if (current.getBufferedColor() == null)
									{
										current.setBufferedColor(current.getBackground());
										current.setBackground(current.getActiveColor());
									}

									EDTextfield edTf = (EDTextfield) current;

									edTf.setActive();
									edTf.setBufferedValue(edTf.getValue()); // Save current value to restore it if there
																			// will be
																			// no changes saved.

									textfieldLocked = (EDTextfield) current;
	
									current.onClick();
								}
								else
								{
									// Discard previous focused text field.
									{
										if (textfieldLocked.getBufferedValue() != null)
											textfieldLocked.setValue(textfieldLocked.getBufferedValue());

										textfieldLocked.setBackground(textfieldLocked.getBufferedColor());
										textfieldLocked.setBufferedColor(null);

										textfieldLocked.setBackground(textfieldLocked.getBackground());
										textfieldLocked.setInactive();
										unlockTextfield();
										
									}

									// Focus newly clicked text field.
									{
										if (current.getBufferedColor() == null)
										{
											current.setBufferedColor(current.getBackground());
											current.setBackground(current.getActiveColor());
										}

										EDTextfield edTf = (EDTextfield) current;

										edTf.setActive();

										textfieldLocked = (EDTextfield) current;

										current.onClick();
									}
								}
							
								break;
							}

							case ("EDBUTTON"):
							{
								EDButton button = (EDButton) current;
								
								if(button.isInteractionEnabled())
								{
									if (button.getBufferedColor() == null)
									{
										Thread animation = new Thread()
										{
											@Override
											public void run()
											{
												isHovering = false;
												
												if(!isHovering)
													button.setBufferedColor(current.getBackground());
												
												button.setBackground(Color.RED);

												Interrupt.pauseMillisecond(300);

												button.setBackground(current.getBufferedColor());
												button.setBufferedColor(null);
											}
										};

										animation.start();
									}

									if(button.actsOnClick())
										button.onClick();
								}
							
								break;
							}

							default:
							{
								
							}
						}
					}
					else // If the focus is outside the current checked object (handles 'click-on-background-event')
					{
						switch (simpleType)
						{
							case ("EDTEXTFIELD"):
							{
								EDTextfield edT = (EDTextfield) current;
								
								if(edT.isActive())
								{
									reset(edT);
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{

		}

		@Override
		public void mouseEntered(MouseEvent e)
		{

		}

		@Override
		public void mouseExited(MouseEvent e)
		{

		}
	};
	
	private MouseMotionListener mML = new MouseMotionListener()
	{
		@Override
		public void mouseDragged(MouseEvent mouseEvent)
		{
			
		}

		@Override
		public void mouseMoved(MouseEvent mouseEvent)
		{
			mouseLoc = new Point(mouseEvent.getPoint().x - 8, mouseEvent.getPoint().y - 31);

			Rectangle textArea = null;

			for (EDLayer layer : registeredLayers)
			{
				// Tests for all available events whether a button was clicked.
				// It will the corresponding implementation.
				for (EDText current : layer.getTextBuffer())
				{
					String type = current.getClass().getGenericSuperclass().getTypeName();
					int last = type.lastIndexOf('.') + 1;
					String simpleType = type.substring(last, type.length()).toUpperCase();

					textArea = new Rectangle(current.getRectangle().getSize());
					textArea.setLocation(current.getRectangle().getLocation());

					// Execute onClick() of a graphical component if it was clicked.
					if(inside(textArea.getLocation(), textArea.getSize(), mouseLoc))
					{
						switch (simpleType)
						{
							case ("EDTEXTFIELD"):
							{
								break;
							}
							case ("EDBUTTON"):
							{								
								if(!isHovering) // Checks for the 'animation thread' (below) of a hovered button when it may stop the animation.
								{
									isHovering = true;
									
									if(((EDButton) current).isInteractionEnabled())
									{
										EDButton button = (EDButton) current;
										
										if(current.getBufferedColor() == null) // Check whether the button is used currently, through the use of the buffer.
										{
											// Animation thread for hovering.
											Thread animation = new Thread()
											{
												Rectangle textArea_copy = new Rectangle(current.getRectangle().getSize());
												
												private void pause()
												{
													if(inside(textArea_copy.getLocation(), textArea_copy.getSize(), mouseLoc) && isHovering)
													{
														Interrupt.pauseMillisecond(250);
														
														pause();
													}
													else
														return;
												}
												
												@Override
												public void run()
												{
													current.setBufferedColor(current.getBackground());
													current.setBackground(Color.BLUE);

													textArea_copy.setLocation(current.getRectangle().getLocation());
													
													pause(); // Pauses as long as the user hovers over the button.

													current.setBackground(current.getBufferedColor());
													current.setBufferedColor(null);
													
													isHovering = false;
												}
											};

											animation.start();
										}
										
										if(button.actsOnHover())
											button.onHover();
									}
								}
							
								break;
							}

							default:
							{
								
							}
						}
					}
					else // If the focus is outside the current checked object (handles 'click-on-background-event')
					{
						switch (simpleType)
						{
							case ("EDTEXTFIELD"):
							{
								// Currently does nothing.. Will stay here anyway despite of performance losses..
							}
						}
					}
				}
			}
		}
	};

	public EventHandler(LayeredRenderFrame rF)
	{
		if (rF != null)
			this.rF = rF;
		else
			throw new NullPointerException("Passed RenderFrame is null!");

		registeredLayers = new ArrayList<EDLayer>();
	}

	public synchronized void registerLayer(EDLayer edL)
	{
		registeredLayers.add(edL);
	}

	public void unregisterLayer(String uuid)
	{
		int index = 0;

		for (EDLayer current : registeredLayers)
		{
			if (current.getUUID().toString().equals(uuid))
				break;

			index++;
		}

		registeredLayers.remove(index);
	}

	public synchronized void unregisterAllLayers()
	{
		registeredLayers = new ArrayList<EDLayer>();
	}

	public void start()
	{
		rF.addMouseListener(mouseListener);
		rF.addMouseMotionListener(mML);
	}

	public void stop()
	{
		rF.removeMouseListener(mouseListener);
		rF.removeMouseMotionListener(mML);
	}

	public ArrayList<EDLayer> getRegisteredLayers()
	{
		return registeredLayers;
	}

	public void unlockTextfield()
	{
		textfieldLocked = null;
	}
}

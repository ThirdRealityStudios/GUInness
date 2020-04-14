package core.event;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import core.frame.LayeredRenderFrame;
import core.frame.Summary;
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
	
	private volatile boolean isHovering = false, isClicking = false;
	
	private volatile Point mouseLoc = null;
	
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
	
	private void enableInput(EDTextfield edT)
	{
		if(edT.getBufferedColor() == null)
		{
			edT.setBufferedColor(edT.getBackground());
			edT.setBackground(edT.getActiveColor());
		}

		edT.setActive();
		edT.setBufferedValue(edT.getValue()); /* Save current value to restore it if there
		 									   * will be
		  									   * no changes saved.
		  									   */

		textfieldLocked = edT;

		edT.onClick();
	}
	
	// Discard previous focused text field.
	public void discardChanges(EDTextfield edT)
	{
		if(edT.getBufferedValue() != null)
			edT.setValue(edT.getBufferedValue());

		edT.setBackground(edT.getBufferedColor());
		edT.setBufferedColor(null);

		edT.setBackground(edT.getBackground());
		edT.setInactive();
		unlockTextfield();
	}
	
	private MouseListener mouseListener = new MouseListener()
	{
		@Override
		public void mouseClicked(MouseEvent mouseEvent)
		{
			mouseLoc = Summary.getFrameLocation(mouseEvent.getPoint());
			
			for (EDLayer layer : registeredLayers)
			{
				// Tests for all available events whether a button was clicked.
				// It will the corresponding implementation.
				for (EDText current : layer.getTextBuffer())
				{
					String simpleType = Summary.typeof(current); // Get the type in upper-case.

					// Execute onClick() of a graphical component if it was clicked.
					if(current.getRectangle().contains(mouseLoc))
					{
						switch (simpleType)
						{
							case ("EDTEXTFIELD"):
							{
								EDTextfield target = (EDTextfield) current;
								
								if(textfieldLocked == null)
								{
									enableInput(target);
								}
								else
								{
									// Discard previous focused text field.
									discardChanges(target);

									// Focus newly clicked text field.
									enableInput(target);
								}
							
								break;
							}

							case ("EDBUTTON"):
							{
								EDButton button = (EDButton) current;
								System.out.println("Click");
								if(button.isInteractionEnabled())
								{									
									if (button.getBufferedColor() == null)
									{
										Thread animation = new Thread()
										{
											@Override
											public void run()
											{
												isClicking = true; // Indicate the click animation wants to start.
												
												System.out.println(">" + isClicking);
												
												while(isHovering)// Wait until the hover animation was cancelled.
												{
													System.out.println("Still hover animating...");
												}
												
												System.out.println(">" + isClicking);
												
												// if(!isHovering)
												button.setBufferedColor(current.getBackground());

												button.setBackground(Color.RED);

												Interrupt.pauseMillisecond(300);

												button.setBackground(current.getBufferedColor());
												button.setBufferedColor(null);
												
												isClicking = false;
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
			mouseLoc = Summary.getFrameLocation(mouseEvent.getPoint());
			
			for (EDLayer layer : registeredLayers)
			{
				// Tests for all available events whether a button was clicked.
				// It will the corresponding implementation.
				for (EDText current : layer.getTextBuffer())
				{
					String simpleType = Summary.typeof(current); // Get the type in upper-case.

					// Execute onClick() of a graphical component if it was clicked.
					if(current.getRectangle().contains(mouseLoc))
					{
						switch (simpleType)
						{
							case("EDTEXTFIELD"):
							{
								break;
							}
							case("EDBUTTON"):
							{
								if(((EDButton) current).isInteractionEnabled())
								{
									EDButton button = (EDButton) current;
									
									if(current.getBufferedColor() == null) // Check whether the button is used currently, through the use of the buffer.
									{
										// Animation thread for the hover animation.
										Thread animation = new Thread()
										{
											Rectangle rect_copy = new Rectangle(current.getRectangle());
											
											private void pause()
											{
												if(rect_copy.contains(mouseLoc) && !isClicking)
												{
													Interrupt.pauseMillisecond(200);
													
													pause();
												}
												else if(isClicking)
												{
													System.out.println("Clicking...");
													return;
												}
											}
											
											@Override
											public void run()
											{
												if(!isClicking)
												{
													isHovering = true;
													
													current.setBufferedColor(current.getBackground());
													current.setBackground(Color.BLUE);

													rect_copy.setLocation(current.getRectangle().getLocation());

													pause(); // Pauses as long as the user hovers over the button.

													current.setBackground(current.getBufferedColor());
													current.setBufferedColor(null);

													isHovering = false;
												}
											}
										};

										animation.start();
									}
									
									if(button.actsOnHover())
										button.onHover();
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

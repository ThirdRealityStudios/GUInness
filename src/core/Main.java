package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.component.EDButton;
import core.gui.component.EDDescription;
import core.gui.component.EDTextfield;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;
import core.io.Loader;
import core.maker.Path2DMaker;

public class Main
{
	private LayeredRenderFrame rF;

	private EDButton start, exit;

	private EDTextfield input1, input2, input3;

	private EDImage img0;

	private EDLayer layer0, layer1;

	public static void main(String[] args)
	{
		Main m = new Main();

		m.init();
		m.run();
	}

	public void init()
	{
		start = new EDButton(Color.GRAY, Color.LIGHT_GRAY, new Color(0.7f, 0.7f, 0.7f), new Point(50, 50), "START", Color.BLACK, 20, 5, 1, Color.BLACK, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hovered START!: " + this.getBackground());
			}

			private boolean clicked = false;

			@Override
			public void onClick()
			{
				if(!clicked)
				{
					img0.setVisible(false);

					clicked = true;
				}
				else
				{
					img0.setVisible(true);
					
					clicked = false;
				}
				
				/*
				// You can print onto the console when the image was hidden or displayed.
				if(!clicked)
				{
					System.out.println("Enabled!");
				}
				else
				{
					System.out.println("Disabled!");
				}
				*/
				
				rF.applyChanges();
			}
		};

		start.actsOnHover(false);

		exit = new EDButton(Color.GRAY, Color.YELLOW, Color.BLUE, new Point(50, 150), "EXIT", Color.BLACK, 20, 5, 1, Color.BLACK, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hovered EXIT!: " + this.getBackground());
			}

			@Override
			public void onClick()
			{
				System.out.println("Exiting..");
				System.exit(0);
			}
		};
		
		exit.actsOnHover(false);

		input1 = new EDTextfield(Color.GRAY, Color.RED, new Point(220, 300), "EMPTY STR", 10, Color.BLACK, 20, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input2 = new EDTextfield(Color.GRAY, Color.RED, new Point(220, 400), "SECOND", 10, Color.BLACK, 20, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		input3 = new EDTextfield(Color.GRAY, Color.WHITE, new Point(220, 500), "THIRD", 50, Color.BLACK, 10, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		Image i = Loader.loadImage("C:\\Users\\Hobby\\test.png");

		img0 = new EDImage(new Point(220, 100), 300, true, i, true);
	}
	
	public void setupLayer0()
	{		
		EDDescription edD = new EDDescription(new Point(220, 50), "Money here for nothing!", 25, Color.WHITE, true);
		
		layer0.add(new EDPath(Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RED, true, new Point(0, 300), true));
		layer0.add(new EDPath(Path2DMaker.makeRectangle(0, 570-31, 800, 30), Color.PINK, true, new Point(100, 300), true));
		
		layer0.add(img0);
		
		layer0.add(start);
		layer0.add(exit);
		layer0.add(input1);
		layer0.add(input2);
		layer0.add(input3);
		layer0.add(edD);
	}

	public void run()
	{		
		rF = new LayeredRenderFrame();
		rF.setAlwaysOnTop(true);

		rF.setSize(800, 600);
		rF.center();
		rF.setVisible(true);
		
		layer0 = new EDLayer(0, true);
		layer1 = new EDLayer(0, true);
		
		setupLayer0();

		rF.addLayer(layer0);
		rF.addLayer(layer1);
	}
}

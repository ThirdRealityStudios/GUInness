package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.EDDescription;
import core.gui.component.EDTextfield;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;
import core.io.Interrupt;
import core.io.Loader;
import core.io.state.FlagHolder;
import core.maker.Path2DMaker;

public class Main
{
	private volatile FlagHolder flags;

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
		flags = new FlagHolder();
		
		flags.update(true);
		
		start = new EDButton(Color.GRAY, Color.RED, Color.BLUE, new Point(100, 50), "START", Color.BLACK, 20, 5, 1, Color.BLACK, true)
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
				
				if(!clicked)
				{
					System.out.println("Enabled!");
				}
				else
				{
					System.out.println("Disabled!");
				}
				
				rF.applyChanges();
			}
		};
		
		start.actsOnHover(false);

		exit = new EDButton(Color.GRAY, Color.RED, Color.BLUE, new Point(250, 50), "EXIT", Color.BLACK, 20, 5, 1, Color.BLACK, true)
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

		input1 = new EDTextfield(Color.GRAY, Color.RED, new Point(350, 100), "EMPTY", 10, Color.BLACK, 20, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		input2 = new EDTextfield(Color.GRAY, Color.RED, new Point(350, 200), "SECOND", 10, Color.BLACK, 20, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		input3 = new EDTextfield(Color.GRAY, Color.WHITE, new Point(350, 300), "THIRD", 50, Color.BLACK, 10, 5, 1, Color.DARK_GRAY, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		Image i = Loader.loadImage("C:\\Users\\Hobby\\test.png");

		img0 = new EDImage(new Point(0, 0), 300, true, i, true);
	}


	public void run()
	{		
		rF = new LayeredRenderFrame(flags);
		rF.setAlwaysOnTop(true);

		rF.setSize(800, 600);
		rF.setVisible(true);
		
		EDDescription edD = new EDDescription(new Point(100, 100), "Hello World!", 25, Color.RED, true);
		
		layer0 = new EDLayer(0, true);
		layer1 = new EDLayer(0, true);
		
		{
			layer0.addPath(new EDPath(Path2DMaker.makeRectangle(0, 0, 200, 100), Color.RED, true, new Point(0, 100), true));
			layer0.addPath(new EDPath(Path2DMaker.makeRectangle(200, 200, 400, 20), Color.RED, true, new Point(100, 300), true));
			
			layer0.addText(start);
			layer0.addText(exit);
			layer0.addText(input1);
			layer0.addText(input2);
			layer0.addText(input3);
			layer0.addText(edD);

			layer1.addImage(img0);
		}

		rF.addLayer(layer0);
		rF.addLayer(layer1);
	}
}

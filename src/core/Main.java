package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import core.frame.LayeredRenderFrame;
import core.gui.EDLayer;
import core.gui.component.standard.EDButton;
import core.gui.special.EDImage;
import core.gui.special.EDTextfield;
import core.gui.component.standard.EDDescription;
import core.gui.design.Classic;
import core.gui.design.Design;
import core.io.Loader;

public class Main
{
	private LayeredRenderFrame rF;

	private EDButton start, exit;
	
	private EDTextfield input1, input2, input3;
	
	private EDImage img0;

	private EDLayer layer0, layer1;
	
	private final Color brightGray = new Color(0.7f, 0.7f, 0.7f);
	
	private Design design;

	public static void main(String[] args)
	{
		Main m = new Main();

		m.init();
		m.run();
	}

	public void init()
	{
		design = new Classic(Color.BLACK, brightGray, Color.GRAY, Color.LIGHT_GRAY, Color.BLACK, 2, 1);
		
		
		start = new EDButton(design, new Point(50, 50), "START", 20, true)
		{
			@Override
			public void onHover()
			{
				
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

				rF.applyChanges();
			}
		};

		start.actsOnHover(false);
		

		exit = new EDButton(design, new Point(0, 0), "EXIT", 20, true)
		{
			@Override
			public void onHover()
			{
				
			}

			@Override
			public void onClick()
			{
				System.out.println("Exiting..");
				System.exit(0);
			}
		};

		exit.actsOnHover(true);

		/*
		input1 = new EDTextfield(design, new Point(220, 300), "EMPTY STR", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input2 = new EDTextfield(design, new Point(220, 400), "SECOND", 10, 20, true)
		{
			@Override
			public void onHover()
			{

			}
		};
		*/

		input3 = new EDTextfield(design, new Point(200, 500), "Zuhaelter", 10, 20, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		Image i = Loader.loadImage("C:\\Users\\Hameg\\Desktop\\4.jpg");

		img0 = new EDImage(design, new Point(20, 100), 300, false, i, true);
	}

	public void setupLayer0()
	{
		EDDescription edD = new EDDescription(design, new Point(0, 50), "Money here for nothing!", 25, true);
		
		//layer0.add(new EDPath(design, Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RED, true, new Point(0, 300), true));
		//layer0.add(new EDPath(design, Path2DMaker.makeRectangle(0, 0, 800, 30), Color.PINK, true, new Point(100, 300), true));
		
		layer0.add(img0);
		
		layer0.add(start);
		layer0.add(exit);
		//layer0.add(input3);
		//layer0.add(input2);
		//layer0.add(input1);
		//layer0.add(edD);
	}

	public void run()
	{		
		rF = new LayeredRenderFrame(design);
		rF.setAlwaysOnTop(true);

		rF.setSize(800, 600);
		rF.center();
		rF.setVisible(true);

		layer0 = new EDLayer(0, true);
		layer1 = new EDLayer(1, true);

		setupLayer0();

		rF.addLayer(layer0);
		rF.addLayer(layer1);
	}
}

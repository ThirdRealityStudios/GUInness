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
		design = new Classic(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK, 2, 1);

		rF = new LayeredRenderFrame(design);

		start = new EDButton(rF, new Point(20, 75), "START", 20, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hover babe!");
			}

			@Override
			public void onClick()
			{
				rF.setGamingMode(!rF.isGamingModeOn());
				System.out.println("Gaming mode is on! Watch your CPU usage and see which mode is more efficient :D..");
			}
		};

		start.actsOnHover(false);
		start.actsOnClick(true);
		start.setRealtimeExecution(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).
		

		exit = new EDButton(rF, new Point(20, 150), "EXIT", 20, true)
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

		exit.actsOnHover(false);
		exit.actsOnClick(true);

		input1 = new EDTextfield(rF, new Point(20, 300), "GERMAN", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input2 = new EDTextfield(rF, new Point(20, 375), "DEUTSCH", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input3 = new EDTextfield(rF, new Point(20, 450), "ALEMAN", 10, 20, true)
		{
			@Override
			public void onHover()
			{

			}
		};

		Image i = Loader.loadImage("C:\\Users\\Hameg\\Desktop\\4.jpg");

		img0 = new EDImage(rF, new Point(0, 0), 600, false, i, true);
	}

	public void setupLayer0()
	{
		EDDescription edD = new EDDescription(rF, new Point(20, 520), "Money here for nothing!", 25, true);
		
		//layer0.add(new EDPath(design, Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RED, true, new Point(0, 300), true));
		//layer0.add(new EDPath(design, Path2DMaker.makeRectangle(0, 0, 800, 30), Color.PINK, true, new Point(100, 300), true));
		
		layer0.add(img0);
		
		layer1.add(edD);
		layer1.add(exit);
		layer1.add(start);
		
		layer1.add(input3);
		layer1.add(input2);
		layer1.add(input1);
	}

	public void run()
	{
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

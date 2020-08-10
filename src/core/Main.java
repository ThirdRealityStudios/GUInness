package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import core.feature.Loader;
import core.gui.Display;
import core.gui.Viewport;
import core.gui.component.standard.GButton;
import core.gui.special.GImage;
import core.gui.special.GTextfield;
import core.gui.component.standard.GDescription;
import core.gui.design.Classic;
import core.gui.design.Design;
import core.gui.layer.GLayer;

public class Main
{
	private Display display;

	private GButton start, exit;

	private GTextfield input1, input2, input3;
	
	private GImage img0;
	
	private GDescription description;

	private GLayer layer0, layer1;
	
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
		design = new Classic(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK, 5, 1);

		display = new Display();
		
		display.setViewport(new Viewport(display.getEventHandler()));
		
		start = new GButton(new Point(20, 75), "Hide image", 20, true)
		{
			@Override
			
			public void onHover()
			{
				System.out.println("Hover babe!");
			}

			@Override
			public void onClick()
			{
				img0.setVisible(!img0.isVisible());
				this.setValue((img0.isVisible() ? "Hide again " : "Show ") + "image");
			}
		};

		start.actsOnHover(false);
		start.actsOnClick(true);
		start.setRealtimeExecution(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		exit = new GButton(new Point(20, 150), "EXIT", 20, true)
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

		input1 = new GTextfield(new Point(20, 300), "GERMAN", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hover");
			}
		};
		
		input1.setInteraction(false);
		input1.actsOnClick(false);

		input2 = new GTextfield(new Point(20, 375), "DEUTSCH", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input3 = new GTextfield(new Point(20, 450), "ALEMAN", 10, 20, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		Image i = Loader.loadImage("C:\\Users\\Hameg\\Desktop\\4.jpg");

		img0 = new GImage(new Point(0, 0), 600, false , i, true);
		img0.actsOnHover(false);
	}

	public void setupLayer0()
	{
		description = new GDescription(new Point(20, 520), "Money here for nothing!", 25, true);
		
		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RG, true, new Point(0, 300), true));
		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 800, 30), Color.PINK, true, new Point(100, 300), true));
		
		layer0.add(img0);
		
		layer1.add(description);
		layer1.add(exit);
		layer1.add(start);
		
		layer1.add(input3);
		layer1.add(input2);
		layer1.add(input1);
	}

	public void run()
	{
		display.setAlwaysOnTop(true);

		display.setSize(800, 600);
		display.center();
		display.setVisible(true);

		layer0 = new GLayer(0, true);
		layer1 = new GLayer(1, true);

		setupLayer0();

		display.getViewport().addLayer(layer0);
		display.getViewport().addLayer(layer1);
	}
}

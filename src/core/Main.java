package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import core.feature.Path;
import core.feature.image.ImageToolkit;
import core.gui.Display;
import core.gui.Viewport;
import core.gui.component.standard.GButton;
import core.gui.special.GCheckbox;
import core.gui.special.GImage;
import core.gui.special.GTextfield;
import core.gui.component.standard.GDescription;
import core.gui.design.Classic;
import core.gui.design.Design;
import core.gui.font.Font;
import core.gui.layer.GLayer;

public class Main
{
	private Display display;

	private GButton start, exit;

	private GTextfield input1, input2, input3;
	
	private GImage img0;
	
	private GDescription description;
	
	private GCheckbox checkbox1;

	private GLayer layer0, layer1, layer2;
	
	private final Color brightGray = new Color(0.7f, 0.7f, 0.7f);
	
	private Design design;
	
	private Font default1 = new Font("default1", Font.getDefaultFilepath(), 25), default2 = new Font("default2", Font.getDefaultFilepath());

	public static void main(String[] args)
	{
		Main m = new Main();

		m.init();
		m.run();
	}
	
	private void initComponents()
	{
		checkbox1 = new GCheckbox(new Point(20, 200), false, true, 30)
		{
			@Override
			public void onClick()
			{
				layer2.setEnabled(isChecked());
			}

			@Override
			public void onHover()
			{
				
			}
		};
		
		start = new GButton(new Point(20, 75), "Disable second layer", default2, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hover babe!");
			}

			@Override
			public void onClick()
			{
				layer2.setEnabled(!layer2.isEnabled());
				setValue((layer2.isEnabled() ? "Disable again " : "Enable ") + "second layer");
				
				checkbox1.setChecked(!layer2.isEnabled());
			}
		};

		start.getLogic().setActionOnHover(false);
		start.getLogic().setActionOnClick(true);
		start.getLogic().setMultithreading(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		exit = new GButton(new Point(20, 150), "EXIT", default1, true)
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

		exit.getLogic().setActionOnHover(false);
		exit.getLogic().setActionOnClick(true);

		input1 = new GTextfield(new Point(20, 300), "GERMAN", 10, default2, true)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hover");
			}
		};
		
		input1.getLogic().setInteractable(false);
		input1.getLogic().setActionOnClick(false);

		input2 = new GTextfield(new Point(20, 375), "DEUTSCH", 10, default2, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input3 = new GTextfield(new Point(20, 450), "ALEMAN", 10, default2, true)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		Image i = ImageToolkit.loadImage(Path.CORE_PATH + "\\media\\samples\\MountainLake.jpg");

		img0 = new GImage(new Point(0, 0), 600, false , i, true);
		img0.getLogic().setActionOnHover(false);
	}

	public void init()
	{
		default1.setFontColor(Color.RED);
		
		design = new Classic(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK, 5, 1);

		display = new Display();
		
		display.setViewport(new Viewport(display.getEventHandler()));
		
		initComponents();
	}

	public void setupLayers()
	{
		description = new GDescription(new Point(20, 520), "Money here for nothing!", default2, true);
		
		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RG, true, new Point(0, 300), true));
		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 800, 30), Color.PINK, true, new Point(100, 300), true));
		
		layer0.add(img0);
		
		layer1.add(start);
		
		layer1.add(checkbox1);
		
		layer2.add(description);
		layer2.add(exit);
		
		layer2.add(input3);
		layer2.add(input2);
		layer2.add(input1);
	}

	public void run()
	{
		display.setAlwaysOnTop(true);

		display.setSize(800, 600);
		display.center();
		display.setVisible(true);

		layer0 = new GLayer(0, true);
		layer1 = new GLayer(1, true);
		layer2 = new GLayer(2, true);
		
		setupLayers();

		display.getViewport().addLayer(layer0);
		display.getViewport().addLayer(layer1);
		display.getViewport().addLayer(layer2);
	}
}

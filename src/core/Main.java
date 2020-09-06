package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;

import core.feature.Path;
import core.feature.image.ImageToolkit;
import core.gui.Display;
import core.gui.Viewport;
import core.gui.component.decoration.GImage;
import core.gui.component.decoration.GRectangle;
import core.gui.component.input.GTextfield;
import core.gui.component.selection.GCheckbox;
import core.gui.component.selection.list.GSelectionBox;
import core.gui.component.selection.list.GSelectionOption;
import core.gui.component.standard.GButton;
import core.gui.component.standard.GDescription;
import core.gui.component.standard.GPolyButton;
import core.gui.design.Classic;
import core.gui.design.Design;
import core.gui.font.Font;
import core.gui.layer.GLayer;

public class Main
{
	private Display display;
	
	private GRectangle rect;

	private GButton start, exit;

	private GTextfield input1, input2, input3;
	
	private GImage img0;
	
	private GDescription description;
	
	private GCheckbox checkbox1;
	
	// Some sample options for the selection box below.
	private ArrayList<GSelectionOption> options;
	
	private GSelectionBox gSB;

	private GLayer layer0, layer1, layer2;

	private Design design;
	
	private Font default1 = new Font("default1", Font.getDefaultFilepath(), 25), default2 = new Font("default2", Font.getDefaultFilepath());

	public static void main(String[] args)
	{
		Main m = new Main();

		m.init();
		m.run();
	}

	private GPolyButton getPolyButton0()
	{
		Polygon poly = new Polygon();
		
		poly.addPoint(0, 0);
		poly.addPoint(100, 0);
		poly.addPoint(150, 50);
		poly.addPoint(150, 100);
		poly.addPoint(75, 125);
		poly.addPoint(0, 125);
		
		GPolyButton gPolyButton = new GPolyButton(new Point(450, 370), "CLICK ME", default2, poly)
		{
			@Override
			public void onHover()
			{
				// System.out.println("Fuckin hovered a GPolyButton!");
			}
			
			@Override
			public void onClick()
			{
				System.out.println("Clicked GPolyButton!");
			}
		};
		
		gPolyButton.getStyle().setOpacity(0.7f);
		gPolyButton.getStyle().setTextAlign(1);
		
		return gPolyButton;
	}
	
	private GPolyButton getPolyButton1()
	{
		Polygon poly = new Polygon();
		
		poly.addPoint(0, 0);
		poly.addPoint(100, 0);
		poly.addPoint(150, 50);
		poly.addPoint(150, 100);
		poly.addPoint(125, 125);
		poly.addPoint(0, 250);
		
		GPolyButton gPolyButton = new GPolyButton(new Point(250, 310), "Button", default2, poly)
		{
			@Override
			public void onHover()
			{
				// System.out.println("Fuckin hovered a GPolyButton!");
			}
			
			@Override
			public void onClick()
			{
				System.out.println("Clicked GPolyButton!");
			}
		};
		
		gPolyButton.getStyle().setPrimaryColor(Color.RED);
		gPolyButton.getStyle().setTextAlign(1);
		gPolyButton.getStyle().setTextTransition(new Point(0, -40));
		
		return gPolyButton;
	}
	
	private void initComponents()
	{
		rect = new GRectangle(0, 0, new Dimension(800, 86), Color.RED, 0.5f);
		
		// The button ("start" variable) is focused later during runtime instead.
		rect.getLogic().setFocusable(false);
		
		options = new ArrayList<GSelectionOption>();
		
		GSelectionOption option0 = new GSelectionOption("Win a price", false), option1 = new GSelectionOption("Loose everything", true), option2 = new GSelectionOption("Loose your vibes", false);
		
		option0.getStyle().setPaddingBottom(10);		
		option1.getStyle().setPaddingBottom(10);
		option2.getStyle().setPaddingBottom(10);
		
		option1.getStyle().setPaddingTop(10);
		option2.getStyle().setPaddingTop(10);
		
		options.add(option0);
		options.add(option1);
		options.add(option2);
		
		// The first option should have a different background color.
		option0.getStyle().setPrimaryColor(Color.WHITE);
		
		gSB = new GSelectionBox(new Point(200, 150), options);
		
		checkbox1 = new GCheckbox(new Point(20, 200), true, 20)
		{
			@Override
			public void onClick()
			{
				layer2.setEnabled(isChecked());
				
				start.setValue((layer2.isEnabled() ? "Disable again " : "Enable ") + "second layer");
			}

			@Override
			public void onHover()
			{
				
			}
		};
		
		start = new GButton(new Point(20, 75), "Disable second layer", default2)
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
				
				checkbox1.setChecked(layer2.isEnabled());
			}
		};

		start.getLogic().setActionOnHover(false);
		start.getLogic().setActionOnClick(true);
		start.getLogic().setMultithreading(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		exit = new GButton(new Point(20, 150), "EXIT", default1)
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

		input1 = new GTextfield(new Point(20, 300), "GERMAN", 10, default2)
		{
			@Override
			public void onHover()
			{
				System.out.println("Hover");
			}
		};
		
		input1.getLogic().setInteractable(false);
		input1.getLogic().setActionOnClick(false);

		input2 = new GTextfield(new Point(20, 375), "DEUTSCH", 10, default2)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		input3 = new GTextfield(new Point(20, 450), "ALEMAN", 10, default2)
		{
			@Override
			public void onHover()
			{
				
			}
		};

		Image i = ImageToolkit.loadImage(Path.ROOT + File.separator + "media" + File.separator + "samples" + File.separator + "MountainLake.jpg");

		img0 = new GImage(new Point(0, 0), 600, false , i);
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
		description = new GDescription(new Point(20, 520), "Money here for nothing!", default2);

		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 200, 300), Color.RG, true, new Point(0, 300), true));
		//layer0.add(new GPath(design, Path2DMaker.makeRectangle(0, 0, 800, 30), Color.PINK, true, new Point(100, 300), true));

		layer0.add(img0);
		
		layer1.add(getPolyButton0());
		layer1.add(getPolyButton1());
		layer1.add(start);
		layer1.add(checkbox1);

		layer2.add(description);
		layer2.add(exit);

		layer2.add(input3);
		layer2.add(input2);
		layer2.add(input1);
		
		layer2.add(gSB);
		layer2.add(rect);
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

package core.gui.special.selection;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.Meta;
import core.gui.component.GComponent;

public class GSelectionBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private ArrayList<GSelectionOption> options;
	
	// This list contains all shapes related to an option.
	// This makes it easier for the rendering progress to just access the data more easily.
	// Also it will prevent all shapes from being calculated too frequently which enhances the performance actually.
	private ArrayList<Rectangle[]> updateShapeTable;
	
	private int index = -1;
	
	private boolean defaultOptionActive = false;

	public GSelectionBox(Point location)
	{
		super("selectionbox", location);
		
		updateShapeTable = new ArrayList<Rectangle[]>();
		
		options = new ArrayList<GSelectionOption>();
	}
	
	public GSelectionBox(Point location, ArrayList<GSelectionOption> options)
	{
		super("selectionbox", location);
		
		updateShapeTable = new ArrayList<Rectangle[]>();
		
		if(areValidOptions(options))
		{
			this.options = options;
			
			// Automatically selects a default option.
			selectDefaultOption();
		}
		else
		{
			throw new IllegalArgumentException("You wanted to add an ArrayList with options (type 'GSelectionOption') to a GSelectionBox.\nAnyway, one or more of the passed options are 'null' which is not valid, or there are multiple default options (added to) in the ArrayList passed to the GSelectionBox!\nThere can only be one default option for a whole GSelectionBox!\nMake sure you don't add double default options by using the method \"isValidOption(GSelectionOption option)\" or \"areValidOptions(ArrayList<GSelectionOption> options)\" respectively..");
		}
		
		// This will actually calculate a grid for every single option you add to this GSelectionBox.
		// Also, do not change order as the grid is important for the next method to determine the shape of the whole GSelectionBox.
		updateShapeTable();
		
		// Make sure, the current shape is updated with the correct size with the new options added.
		updateSelectionBoxShape();
	}
	
	public ArrayList<GSelectionOption> getOptions()
	{
		return options;
	}
	
	public ArrayList<Rectangle[]> getShapeTable()
	{
		return updateShapeTable;
	}
	
	// This will select the default option officially if there is one.
	// Otherwise the first option is just the default option.
	private void selectDefaultOption()
	{
		for(int i = 0; i < options.size(); i++)
		{
			if(options.get(i).isDefaultOption())
			{
				options.get(i).setChecked(true);
				
				index = i;
				
				// Break off at this point before it would mistakenly continue after the loop.
				return;
			}
		}
		
		if(options.size() > 0)
		{
			options.get(0).setDefaultOption(true);
			options.get(0).setChecked(true);
			
			index = 0;
		}
	}
	
	// Makes sure, the current shape is updated with the correct size with new options added or removed.
	private void updateSelectionBoxShape()
	{
		int maxWidth = 0;
		
		int sumHeight = 0;
		
		for(int i = 0; i < getShapeTable().size(); i++)
		{
			maxWidth = Math.max(maxWidth, (getShapeTable().get(i)[0].width + getShapeTable().get(i)[1].width));
		}
		
		Point originalLocation = getStyle().getLocation();
		
		Rectangle lastShape = getShapeTable().get(getShapeTable().size()-1)[0];
		
		sumHeight = (lastShape.y + lastShape.height) - originalLocation.y;
		
		getStyle().setShape(new Rectangle(originalLocation.x, originalLocation.y, maxWidth, sumHeight));
	}

	// This will actually calculate a grid for every single option you add to this GSelectionBox.
	// It will to simplify the rendering process by not having to calculate these values frequently.
	// It can directly the draw method all necessary measurements of the symbol and title text of an option added.
	// Because of this, the "shape table" steadily needs to be updated when it is changed or just at the beginning.
	private void updateShapeTable()
	{
		int lastHeights = 0;
		
		for(int i = 0; i < options.size(); i++)
		{
			// Stores the current option.
			GSelectionOption option = getOptions().get(i);
			
			int fontSize = option.getStyle().getFont().getFontSize();
			
			Rectangle optionSymbolShape = null, optionTitleShape = null, optionPaddingTop = null, optionPaddingBottom = null;
			
			// The location of this GSelectionBox.
			Point location = new Point(getStyle().getLocation().x, getStyle().getLocation().y + lastHeights);
			
			// Pre-calculating all locations (only)..
			// After having calculated all positions the measurements such as width and height will be determined.
			{
				// Sizes calculated here..
				{
					optionPaddingBottom = new Rectangle(fontSize + fontSize * option.getValue().length(), option.getStyle().getPaddingBottom());
					
					optionSymbolShape = new Rectangle(fontSize, fontSize);
					
					optionTitleShape = new Rectangle(fontSize * option.getValue().length(), fontSize);
					
					optionPaddingTop = new Rectangle(fontSize + fontSize * option.getValue().length(), option.getStyle().getPaddingTop());
				}
				
				// Position applied here..
				{
					optionPaddingBottom.setLocation(location);
					
					optionSymbolShape.setLocation(location.x, optionPaddingBottom.y + optionPaddingBottom.height);
					
					optionTitleShape.setLocation(location.x + optionSymbolShape.width, optionPaddingBottom.y + optionPaddingBottom.height);
					
					optionPaddingTop.setLocation(location.x, optionTitleShape.y + optionTitleShape.height);
				}
				
				/*
				optionPaddingBottom = new Rectangle(new Point(location.x, location.y));
				optionPaddingBottom.setSize(fontSize + option.getValue().length() * fontSize, option.getStyle().getPaddingBottom());
				
				System.out.println("Height on the bottom> " + option.getStyle().getPaddingBottom());
				
				optionSymbolShape = new Rectangle(new Point(location.x, optionPaddingBottom.y + optionPaddingBottom.height));
				optionSymbolShape.setSize(fontSize, fontSize);
				
				optionTitleShape = new Rectangle(new Point(location.x + optionSymbolShape.width, optionSymbolShape.y + optionSymbolShape.height));
				optionTitleShape.setSize(fontSize * option.getValue().length(), fontSize);
				
				optionPaddingTop = new Rectangle(new Point(location.x, optionTitleShape.y + optionTitleShape.height));
				optionPaddingTop.setSize(fontSize + option.getValue().length() * fontSize, option.getStyle().getPaddingTop());
				*/
			}
			
			// Creates an array of shapes for each option and adds it to the list.
			// This will give every option a set of shapes.
			{
				Rectangle[] optionShapes = new Rectangle[4];
				
				optionShapes[0] = optionSymbolShape;
				optionShapes[1] = optionTitleShape;
				
				optionShapes[2] = optionPaddingBottom;
				optionShapes[3] = optionPaddingTop;
				
				updateShapeTable.add(optionShapes);
			}
			
			// Make sure, the next options offset to the current option in this cycle is correctly.
			// In this case, the height of "optionSymbolShape" is taken because it is identical to the height of "optionTitleShape" (it would make no difference which one).
			lastHeights += (optionPaddingBottom.height + optionSymbolShape.height + optionPaddingTop.height);
		}
	}
	
	public boolean isDefaultOptionActive()
	{
		return defaultOptionActive;
	}
	
	// Tells you whether the given option is valid.
	// Checks whether there is a default option yet which is not valid.
	// Only one option can be the default option at all!
	public boolean isValidOption(GSelectionOption option)
	{
		return option != null && option.isDefaultOption() && !isDefaultOptionActive();
	}
	
	// Simply the same thing as "isValidOption(GSelectionOption option)" but checks a whole ArrayList for the correctness of all options.
	// Also, this method is not related to the state of GSelectionBox itself.
	// That means, this method is only applicable for checking NEW ArrayLists which should be used for this GSelectionBox.
	public boolean areValidOptions(ArrayList<GSelectionOption> options)
	{
		int defaultOptions = 0;
		
		for(GSelectionOption option : options)
		{
			if(option.isDefaultOption())
			{
				defaultOptions++;
			}
			
			if(defaultOptions > 1 || option == null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void addOption(GSelectionOption option) throws IllegalArgumentException
	{
		if(!isValidOption(option))
		{
			throw new IllegalArgumentException("You wanted to add an option of type 'GSelectionOption' to a GSelectionBox.\nAnyway, the passed option is 'null' which is not valid, or there is a default option (added) yet in the selection box!\nThere can only be one default option for a GSelectionBox!\nMake sure you don't add a double default option by using the method \"isValidOption(GSelectionOption option)\"..");
		}
		
		options.add(option);
		
		// Checks whether it is a default option (presumed there are no double default options anymore..).
		// Will select the default option also automatically at the beginning.
		if(option.isDefaultOption())
		{
			defaultOptionActive = true;
			
			index = options.size() - 1;
		}
		
		updateShapeTable();
		updateSelectionBoxShape();
	}
	
	private void unselectCompletelyAt(int index)
	{
		if(index < options.size())
		{
			options.get(index).setChecked(false);
			options.get(index).setDefaultOption(false);
		}
	}
	
	public void selectOptionAt(int index)
	{
		if(index < options.size())
		{
			// If there is the same option which wants to be selected then there is no change for a default option.
			// That means if you select an default option it will stay the default option and won't loose its state / recognition.
			if(this.index != index)
			{
				defaultOptionActive = false;
				
				// Completely unselect the old one including the default status..
				unselectCompletelyAt(this.index);
				
				// Simply sets the new index.
				this.index = index;
				
				// Also need to update the GSelectionOption object too to make all changes visible everywhere..
				options.get(index).setChecked(true);
			}
		}
		else
		{
			throw new IllegalArgumentException("You wanted to mark or set an option active of the specified index " + index + ".\nAnyway, this index does not exist and would exceed the real amount of available options in your GSelectionBox!");
		}
	}
	
	@Override
	public void onClick()
	{
		
	}
	
	@Override
	public void onHover()
	{
		
	}
	
	@Override
	public void setValue(String val)
	{
		
	}
}

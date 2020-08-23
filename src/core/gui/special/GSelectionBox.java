package core.gui.special;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import core.Meta;
import core.feature.Path;
import core.feature.image.ImageToolkit;
import core.gui.component.GComponent;

public abstract class GSelectionBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private int optionIndex;

	private ArrayList<GRadioButton> options;

	public GSelectionBox(Point location, ArrayList<GRadioButton> options) throws IllegalArgumentException
	{
		super("selectionbox", location, null, 0, null, null);

		init(options);
	}
	
	public GSelectionBox(Point location, boolean visible, ArrayList<GRadioButton> options) throws IllegalArgumentException
	{
		this(location, options);
		
		getStyle().setVisible(visible);
	}

	// Just some values to be set which are equally set in both constructors.
	private void init(ArrayList<GRadioButton> options) throws IllegalArgumentException
	{
		int defaultOptions = 0;
		
		int maxComparedWidth = 0, calculatedHeight = 0;
		
		// See if there are multiple options (radio buttons) which are checked by default (which is not allowed / wouldn't make any sense).
		// Also set the correct size for the GSelectionBox. It depends on the size of every single radio button which is added.
		for(GRadioButton option : options)
		{
			// Figure out which option has the largest text and appearance measured in length.
			// The value is used later in order to create a correctly sized background or rectangle for all radio-buttons.
			{
				int comparedWidth = option.getValue().length() * option.getStyle().getFont().getFontSize();
				
				// Because all radio buttons can be different size,
				// all of them have to be added or measured.
				// Later, the selection box will also have its own size.
				// The creation of an own size plays a role so far because it makes the UI more efficient.
				// This way first the selection box is being detected (when clicking on it) and first after that it is checked
				// which radio button was clicked now exactly.
				// It is more efficient now actually because it reduces the amount of components which have to be read
				// internally in order to know which one was interacted with.
				// That means the shape of the GSelectionBox represents n or nc components (radio buttons).
				{
					// The maximum available width is taken for the width of the whole GSelectionBox.
					maxComparedWidth = Math.max(maxComparedWidth, comparedWidth);
					
					// All radio buttons can have a different height which is considered here.
					calculatedHeight += option.getStyle().getFont().getFontSize() + option.getStyle().getPaddingBottom() + option.getStyle().getPaddingTop();
				}
			}
			
			// This is the part where it is checked whether there more than one or none radio buttons which are set up for being the default one (which is invalid).
			if(option.isDefaultRadioButton() && defaultOptions == 0)
			{
				defaultOptions++;
			}
			else
			{
				throw new IllegalArgumentException("The passed value 'options' of type 'ArrayList<GRadioButton>' cannot have multiple options checked by default.\nMake sure, only one radio button (option) is checked by default.");
			}
		}
		
		// One or none default option is allowed.
		// When no default option is available,
		// the user is free to decide whether he selects a radio button or not.
		// If selected one radio button, the selection cannot be reverted unless the programmer didn't offer a way to do so.
		if(defaultOptions >= 0 && defaultOptions <= 1)
		{
			this.options = options;
			
			getStyle().setShape(new Rectangle(getStyle().getLocation(), new Dimension(maxComparedWidth, calculatedHeight)));
		}
	}
	
	public ArrayList<GRadioButton> getOptions()
	{
		return options;
	}
	
	// Returns the number of options.
	public int getOptionAmount()
	{
		return options.size();
	}
	
	// Will give you the current checked radio buttons index.
	public int getOptionIndex()
	{
		return optionIndex;
	}

	@Override
	public abstract void onClick();

	@Override
	public abstract void onHover();
	
	public boolean isUnchecked()
	{
		return getValue() == null;
	}
	
	public boolean isChecked()
	{
		return !isUnchecked();
	}
	
	// The 'value' attribute is changed here, meaning if 'value' is null then the first radio button is unchecked and otherwise true.
	public void setFirstChecked(boolean checked)
	{
		value = checked ? "" : null;
	}
}

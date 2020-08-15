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

	public GSelectionBox(Point location, boolean visible, ArrayList<GRadioButton> options) throws IllegalArgumentException
	{
		super("selectionbox", location, null, 0, null, null, true);

		init(options);
	}

	// Just some values to be set which are equally set in both constructors.
	private void init(ArrayList<GRadioButton> options) throws IllegalArgumentException
	{
		int defaultOptions = 0;
		
		int maxComparedWidth = 0, calculatedHeight = 0;
	
		// See if there are multiple options (radio buttons) which are checked by default (which is not allowed / wouldn't make any sense).
		for(GRadioButton option : options)
		{
			// Figure out which option has the largest text and appearance measured in length.
			// The value is used later in order to create a correctly sized background or rectangle for all radio-buttons.
			{
				int comparedWidth = option.getValue().length() * option.getStyle().getFont().getFontSize();
				
				maxComparedWidth = Math.max(maxComparedWidth, comparedWidth);
				
				calculatedHeight += option.getStyle().getFont().getFontSize() + option.getStyle().getPaddingBottom() + option.getStyle().getPaddingTop();
			}
			
			if(option.isDefaultRadioButton() && defaultOptions == 0)
			{
				defaultOptions++;
			}
			else
			{
				throw new IllegalArgumentException("The passed value 'options' of type 'ArrayList<GRadioButton>' cannot have multiple options checked by default.\nMake sure, only one radio button (option) is checked by default.");
			}
		}
		
		if(defaultOptions == 1)
		{
			this.options = options;
			
			getStyle().setShape(new Rectangle(getStyle().getLocation(), new Dimension(maxComparedWidth, calculatedHeight)));
		}
		
		getStyle().setImage(ImageToolkit.loadImage(Path.GUI_PATH + "\\special\\image\\check_sign.png"));
		
		int size_scaled = getStyle().getShape().getBounds().width - 4*getStyle().getDesign().getBorderThickness();
		
		getStyle().setImage(getStyle().getImage().getScaledInstance(size_scaled, size_scaled, Image.SCALE_SMOOTH));
	}
	
	// Returns the number of options.
	public int getOptions()
	{
		return options.size();
	}
	
	public int getOptionIndex()
	{
		return optionIndex;
	}

	@Override
	public abstract void onClick();

	@Override
	public abstract void onHover();

	@Deprecated
	@Override
	// This method is used to set the value (true (!= null) or false (null)) for the check-box.
	// rather use the method below as it saves more memory with a boolean parameter and thus is more efficient.
	public void setValue(String val)
	{
		value = (val != null) ? "" : null;
	}
	
	public boolean isUnchecked()
	{
		return getValue() == null;
	}
	
	public boolean isChecked()
	{
		return !isUnchecked();
	}
	
	// The 'value' attribute is changed here, meaning if 'value' is null then the checkbox is unchecked and otherwise true.
	public void setChecked(boolean checked)
	{
		value = checked ? "" : null;
	}
}

package core.gui.special.selection;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.Meta;
import core.gui.component.GComponent;

public abstract class GSelectionBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private ArrayList<GSelectionOption> options;
	
	private int index = -1;
	
	public GSelectionBox(Point location)
	{
		super("selectionbox", location, new Rectangle());
	}
	
	public GSelectionBox(Point location, ArrayList<GSelectionOption> options)
	{
		this(location);
		
		this.options = options;
	}
	
	protected ArrayList<GSelectionOption> getOptions()
	{
		// Make sure there is always a valid option list returned.
		return options == null ? (options = new ArrayList<GSelectionOption>()) : options;
	}
	
	public void addOption(GSelectionOption option) throws IllegalArgumentException
	{
		if(options == null)
		{
			options = new ArrayList<GSelectionOption>();
		}
		
		if(option == null)
		{
			throw new IllegalArgumentException("You wanted to add an option of type 'GSelectionOption' to a GSelectionBox.\nAnyway, the passed option is 'null' which is not valid!");
		}
		
		options.add(option);
	}
	
	public void setOptionSelectedAt(int index)
	{
		if(index < options.size())
		{
			this.index = index;
		}
		else
		{
			throw new IllegalArgumentException("You wanted to mark or set an option active of the specified index " + index + ".\nAnyway, this index does not exist and would exceed the real amount of available options in your GSelectionBox!");
		}
	}
}

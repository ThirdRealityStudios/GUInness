package org.thirdreality.guinness.gui.design;

import java.awt.Color;

import org.thirdreality.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.guinness.gui.component.style.property.GPaddingProperty;

public class Sample
{
	public static final Design classic = new Classic(new DesignColor(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK), new GBorderProperty(2), new GPaddingProperty(1));
}

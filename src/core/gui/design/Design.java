package core.gui.design;

import java.awt.Graphics;

import core.gui.EDComponent;

public interface Design
{
	// Every design has its own draw method in order to know how to draw each component.
	void drawContext(Graphics g, EDComponent c);
}

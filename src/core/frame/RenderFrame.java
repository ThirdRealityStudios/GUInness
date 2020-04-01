package core.frame;

import java.awt.event.MouseListener;

public interface RenderFrame
{

	// Erases the internal buffer.
	public void erase();

	// Copies the buffer immediately to the output, so all changes will be visible
	// then first.
	public void copy();

	public void addMouseListener(MouseListener mL);

	public void removeMouseListener(MouseListener mL);
}

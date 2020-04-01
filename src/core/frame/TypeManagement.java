package core.frame;

import java.awt.geom.Path2D;

import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;

public interface TypeManagement
{
	public void addPath(EDPath p);

	public void addText(EDText edT);

	public void addImage(EDImage edI);

	public void removePath(Path2D.Double p);

	public void removeButton(EDButton edB);

	public void removeImage(EDImage edI);
}

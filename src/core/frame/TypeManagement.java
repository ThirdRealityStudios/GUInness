package core.frame;

import java.awt.geom.Path2D;

import core.gui.EDComponent;
import core.gui.component.standard.EDButton;
import core.gui.special.EDImage;
import core.gui.special.EDPath;

public interface TypeManagement
{
	public void addPath(EDPath p);

	public void addComponent(EDComponent edT);

	public void addImage(EDImage edI);

	public void removePath(Path2D.Double p);

	public void removeButton(EDButton edB);

	public void removeImage(EDImage edI);
}

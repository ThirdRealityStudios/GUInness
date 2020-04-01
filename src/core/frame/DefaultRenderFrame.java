package core.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.event.EventHandler;
import core.gui.EDLayer;
import core.gui.EDText;
import core.gui.component.EDButton;
import core.gui.component.EDTextfield;
import core.gui.decoration.EDImage;
import core.gui.decoration.EDPath;
import core.tools.gui.UICreator;

public class DefaultRenderFrame extends JFrame // implements RenderFrame, TypeManagement, KeyListener
{
	/*
	 * private EDLayer defaultLayer;
	 * 
	 * private ArrayList<EDPath> pathOutput;
	 * 
	 * private EventHandler eH = new EventHandler(this);
	 * 
	 * private UICreator uiCreator = new UICreator();
	 * 
	 * public DefaultRenderFrame(EDLayer layer) { defaultLayer = layer;
	 * 
	 * uiCreator = new UICreator();
	 * 
	 * defaultLayer = new EDLayer(0, true);
	 * 
	 * pathOutput = new ArrayList<EDPath>();
	 * 
	 * setTitle("Standard RenderFrame");
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setResizable(false);
	 * setSize(1280, 720);
	 * 
	 * JPanel content = new JPanel() { private void drawBackground(Graphics g) {
	 * g.setColor(Color.BLUE); g.fillRect(0, 0, this.getWidth(), this.getHeight());
	 * }
	 * 
	 * private void drawPath(Graphics g) { Graphics2D g2d = (Graphics2D) g;
	 * 
	 * // Render all paths. for (EDPath edP : pathOutput) {
	 * g.setColor(edP.getDrawColor());
	 * 
	 * if (edP.isFill()) g2d.fill(edP.getPath()); else g2d.draw(edP.getPath()); } }
	 * 
	 * private void drawComponents(Graphics g) { // Render all text components. for
	 * (EDText edT : defaultLayer.getTextBuffer()) { uiCreator.createText(g, edT); }
	 * }
	 * 
	 * private void drawImages(Graphics g) { for (EDImage img :
	 * defaultLayer.getImgBuffer()) { g.drawImage(img.getContent(),
	 * img.getLocation().x, img.getLocation().y, img.getSize().width,
	 * img.getSize().height, null); } }
	 * 
	 * @Override public void paint(Graphics g) { drawBackground(g);
	 * 
	 * drawImages(g);
	 * 
	 * drawPath(g);
	 * 
	 * drawComponents(g);
	 * 
	 * repaint(); } };
	 * 
	 * content.setVisible(true); add(content);
	 * 
	 * eH.registerLayer(defaultLayer); eH.start();
	 * 
	 * addKeyListener(this); }
	 * 
	 * private ArrayList<EDPath> copy() { ArrayList<EDPath> mirror = new
	 * ArrayList<EDPath>();
	 * 
	 * for (EDPath p : defaultLayer.getPathBuffer()) { p.getPath().closePath();
	 * mirror.add(p); }
	 * 
	 * return mirror; }
	 * 
	 * public void addPath(EDPath p) { defaultLayer.addPath(p); }
	 * 
	 * public void addText(EDText edT) { defaultLayer.addText(edT); }
	 * 
	 * public void addImage(EDImage edI) { defaultLayer.addImage(edI); }
	 * 
	 * public void removePath() {
	 * defaultLayer.getPathBuffer().remove(defaultLayer.getPathBuffer().size()-1); }
	 * 
	 * public void removeButton(EDButton edB) {
	 * defaultLayer.getTextBuffer().remove(defaultLayer.getTextBuffer().indexOf(edB)
	 * ); }
	 * 
	 * public void removeImage(EDImage edI) {
	 * defaultLayer.getImgBuffer().remove(defaultLayer.getImgBuffer().indexOf(edI));
	 * }
	 * 
	 * // Erases the internal buffer. public void erase() {
	 * defaultLayer.getPathBuffer().clear();
	 * 
	 * defaultLayer.getTextBuffer().clear();
	 * 
	 * defaultLayer.getImgBuffer().clear(); }
	 * 
	 * // Copies the buffer immediately to the output, so all changes will be
	 * visible // then first. public void enable() { pathOutput = copy(); }
	 * 
	 * // Handles all interaction with the interface components.
	 * 
	 * @Override public void keyTyped(KeyEvent e) {
	 * 
	 * }
	 * 
	 * @Override public void keyPressed(KeyEvent e) { for (EDText current :
	 * eH.getRegisteredLayers().get(0).getTextBuffer()) { String type =
	 * current.getClass().getGenericSuperclass().getTypeName(); int last =
	 * type.lastIndexOf('.') + 1; String simpleType = type.substring(last,
	 * type.length()).toUpperCase();
	 * 
	 * if (simpleType.equals("EDTEXTFIELD")) { EDTextfield text = (EDTextfield)
	 * current;
	 * 
	 * if (text.isActive()) { // Write the entered alphabetic char into the text
	 * field. { char textfieldInput = e.getKeyChar();
	 * 
	 * if (uiCreator.getFontLoader().isValid(textfieldInput) &&
	 * (text.getValue().length() + 1) <= text.getLength())
	 * text.setValue(text.getValue() + textfieldInput); }
	 * 
	 * // See if Enter was hit to save the changes. if (e.getKeyCode() ==
	 * KeyEvent.VK_ENTER) { text.setBufferedValue(text.getValue());
	 * 
	 * text.setBackground(text.getBufferedColor()); text.setBufferedColor(null);
	 * 
	 * text.setInactive(); eH.unlockTextfield(); } else if (e.getKeyCode() ==
	 * KeyEvent.VK_BACK_SPACE) { if (text.getValue().length() > 0) { String
	 * clippedEnd = text.getValue().substring(0, text.getValue().length() - 1);
	 * 
	 * text.setValue(clippedEnd); } } else if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
	 * // Leave without saving changes. { if (text.getBufferedValue() != null)
	 * text.setValue(text.getBufferedValue());
	 * 
	 * text.setBackground(text.getBufferedColor()); text.setBufferedColor(null);
	 * 
	 * text.setBackground(text.getBackground()); text.setInactive();
	 * eH.unlockTextfield(); } } else { text.setBufferedValue(text.getValue()); } }
	 * } }
	 * 
	 * @Override public void keyReleased(KeyEvent e) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void removePath(Double p) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 */
}

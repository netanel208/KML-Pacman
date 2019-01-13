package GUI;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * This class use to painting all components from the main JFrame 
 * @author Netanel
 * @author Carmel
 *
 */
public class PanelComp extends JPanel{
	Image imag = Toolkit.getDefaultToolkit().getImage("ariel111.png");
	MyPanel myPanel;
	public PanelComp(MyPanel myPanel) {
		super();
		this.myPanel = myPanel;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(imag, 0, 0, this.getWidth(), this.getHeight(), myPanel);
		myPanel.paintComponent(g);
	}
	
	/**
	 * 
	 * @return image Image - of ariel image
	 */
	public Image getImag() {
		return imag;
	}
}

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

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
	
	public Image getImag() {
		return imag;
	}
}

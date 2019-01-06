import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class PanelComp extends JPanel{
	Image imag = Toolkit.getDefaultToolkit().getImage("ariel111.png");
	
	@Override
	public void paintComponent(Graphics g)
	{
//		super.paintComponent(g);
		g.drawImage(imag, 0, 0, this);
	}
}

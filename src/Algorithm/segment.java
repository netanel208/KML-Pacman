package Algorithm;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import GIS.Rec;
import Geom.Point3D;

/**
 * 
 * @author Carmel
 *@author Netanel
 */
public class segment   {
	
	ArrayList<Rec> boxs;
	ArrayList<Line2D> lines;
	final Line2D upFrame = new Line2D.Double(32.105728,35.212416,32.105728,35.202369);
	final Line2D downFrame = new Line2D.Double(32.10189,35.20237,32.10189,35.212416);
	final Line2D leftFrame = new Line2D.Double(32.105728,35.202369,32.10189,35.20237);
	final Line2D rightFrame = new Line2D.Double(32.105728,35.212416,32.10189,35.212416);

	
	/**
	 * This constructor get list of boxes and create from any box 4 lines 
	 * @param boxs  list of boxes
	 */
	public segment (ArrayList<Rec> boxs ) {
		
		this.boxs = boxs;
		
		 lines = new ArrayList<Line2D> ();
		 // loop over the boxes and create lines
		for (int i = 0; i < boxs.size(); i++) {
			
			Line2D lineUp = new Line2D.Double(boxs.get(i).getUpLeftP().x(),  boxs.get(i).getUpLeftP().y(),  
					boxs.get(i).getUpRightP().x(),  boxs.get(i).getUpRightP().y());
			lines.add(lineUp);
			
			Line2D lineLeft = new Line2D.Double(boxs.get(i).getUpLeftP().x(),  boxs.get(i).getUpLeftP().y(),  
					boxs.get(i).getDownLeftP().x(),  boxs.get(i).getDownLeftP().y());
			lines.add(lineLeft);
			
			Line2D lineDown = new Line2D.Double(boxs.get(i).getDownLeftP().x(),  boxs.get(i).getDownLeftP().y(),  
					boxs.get(i).getDownRightP().x(),  boxs.get(i).getDownRightP().y());
			lines.add(lineDown);
			
			Line2D lineRight = new Line2D.Double(boxs.get(i).getUpRightP().x(),  boxs.get(i).getUpRightP().y(),  
					boxs.get(i).getDownRightP().x(),  boxs.get(i).getDownRightP().y());
			lines.add(lineRight);
			
		}
		lines.add(upFrame);
		lines.add(downFrame);
		lines.add(leftFrame);
		lines.add(rightFrame);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean Intersect (Point3D a,Point3D b) {
		double x_a = a.x();
		double y_a = a.y();
		double x_b = b.x();
		double y_b = b.y();
		
		Line2D l = new Line2D.Double(x_a,y_a,x_b,y_b);
		
		// Check if the line (l) intersects the lines of the boxes
		for (int i = 0; i <lines.size(); i++) {
			if ( lines.get(i).intersectsLine(l))
				return true;
		}
		
		return false;
	}
	
	
	
}


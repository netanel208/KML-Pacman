package GIS;
import Coords.MyCoords;
import Geom.Point3D;
/**
 * This class represents an object of a rectangle of our game ie a box with four corners
 * @author Netanel
 * @author Carmel
 *
 */
public class Rec {

	Point3D downLeftP;
	Point3D upRightP;
	Point3D downRightP;
	Point3D upLeftP;
	int ID;
	/**
	 * This constructor accept line that contains information of the corner and id of this Rec
	 * @param line - String[]
	 */
	public Rec(String[] line)
	{
		downLeftP = new Point3D(line[2],line[3],"0.0");
		upRightP = new Point3D(line[5],line[6],"0.0");
		upLeftP = new Point3D(line[5],line[3],"0.0");
		downRightP = new Point3D(line[2],line[6],"0.0");
		ID = Integer.parseInt(line[1]);
		
	}

	/**
	 * @param p - some point
	 * @return true - if p is in this Rec or Box 
	 */
	public boolean IsIn (Point3D p) {
		if ( p.x() <= upLeftP.x() && p.x() >downLeftP.x() )
		if ( p.y() >= upLeftP.y() && p.y() < upRightP.y() )
			return true;
		
		return false;		
		
	}
	 /**
	  * @return Down left corner + eps
	  */
	public Point3D getDownLeftEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(downLeftP.x()-eps, downLeftP.y()-eps, downLeftP.z());
		return p;
	}
	/**
	 * @return Up right corner + eps
	 */
	public Point3D getUpRightEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(upRightP.x()+eps, upRightP.y()+eps, upRightP.z());
		return p;
	}
	
	/**
	 * @return Down right corner + eps
	 */
	public Point3D getDownRightEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(downRightP.x()-eps, downRightP.y()+eps, downRightP.z());
		return p;
	}
	
	/**
	 * @return Up left corner + eps
	 */
	public Point3D getUpLeftEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(upLeftP.x()+eps, upLeftP.y()-eps, upLeftP.z());
		return p;
	}

	public Point3D getDownLeftP() {
		return downLeftP;
	}

	public Point3D getUpRightP() {
		return upRightP;
	}

	public Point3D getDownRightP() {
		return downRightP;
	}

	public Point3D getUpLeftP() {
		return upLeftP;
	}
	
	public int getID() {
		return ID;
	}
}

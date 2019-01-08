package GIS;
import Coords.MyCoords;
import Geom.Point3D;

public class Rec {

	Point3D downLeftP;
	Point3D upRightP;
	Point3D downRightP;
	Point3D upLeftP;
	
	
	public Rec(String[] line)
	{
		downLeftP = new Point3D(line[2],line[3],"0.0");
		upRightP = new Point3D(line[5],line[6],"0.0");
		upLeftP = new Point3D(line[5],line[3],"0.0");
		downRightP = new Point3D(line[2],line[6],"0.0");
		
	}
	

	public boolean IsIn (Point3D p) {
		if ( p.x() <= upLeftP.x() && p.x() >downLeftP.x() )
		if ( p.y() >= upLeftP.y() && p.y() < upRightP.y() )
			return true;
		
		return false;		
		
	}
	
	public Point3D getDownLeftEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(downLeftP.x()-eps, downLeftP.y()-eps, downLeftP.z());
		return p;
	}
	
	public Point3D getUpRightEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(upRightP.x()+eps, upRightP.y()+eps, upRightP.z());
		return p;
	}
	
	public Point3D getDownRightEps() {
		double eps= 0.00001;
		Point3D p = new Point3D(downRightP.x()-eps, downRightP.y()+eps, downRightP.z());
		return p;
	}
	
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
	
	public static void main(String[] args) {
		
			
	}
}

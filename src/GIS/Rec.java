package GIS;
import Geom.Point3D;

public class Rec {

	Point3D downLeftP;
	Point3D upRightP;
	Point3D downRightP;
	Point3D upLeftP;
	
	public Rec(String[] line)
	{
		downLeftP = new Point3D(line[2],line[3],"0.0");
		System.out.println("downLeftP :"+downLeftP);
		upRightP = new Point3D(line[5],line[6],"0.0");
		System.out.println("upRightP :"+upRightP);
		upLeftP = new Point3D(line[5],line[3],"0.0");
		System.out.println("downRightP :"+upLeftP);
		downRightP = new Point3D(line[2],line[6],"0.0");
		System.out.println("upLeftP :"+downRightP);
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
}

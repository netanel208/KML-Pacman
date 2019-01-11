package GIS;

import java.util.ArrayList;

import Coords.LatLonAlt;
import Coords.MyCoords;
import Geom.Point3D;

/**
 * This class represent a gis element with meta data like : speed and radius
 * @author Netanel
 * @author Carmel 
 * 
 */
public class Packmen extends element{

	double speed;
	double radius;
	long time;

	
	/**
	 * This constructor get array of String and build pacman
	 * @param line 
	 */	
	public Packmen(String[] line)
	{
		super(line);
		speed = Double.parseDouble(line[5]);
		radius = Double.parseDouble(line[6]);
	}
	
	/**
	 * copy  constructor
	 * @param packmen
	 */
	public Packmen(Packmen packmen)
	{
		super(packmen);
		this.speed = packmen.speed;
		this.radius = packmen.radius;
	}
	
	  
	
	/**
	 * get the radius of the pacman
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * get the speed of the pacman
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * get the time that the fruit is eaten 
	 * @return time 
	 */
	public long getTime() {
		return time;
	}

	/**
	 * set the time that the fruit is eaten
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean IsInRadius (Point3D p) {
		MyCoords c = new MyCoords();
		double des= c.distance3d(p, (Point3D)this.getGeom());
		if ( des <= this.radius)
		return true;
		
		return false;
	}
	/**
	 * Print the pacman elemnt 
	 */
	public String toString()
	{
		return super.Type+","+super.id+","+super.lat+","+super.lon+","+super.alt+","+this.speed+
				","+this.radius;
	}
}

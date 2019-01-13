package GIS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Coords.MyCoords;
import File_format.CsvArrayList;
import Geom.Point3D;
import Robot.Packman;

/**
 * This class represent a Game that include fruits and pacmans, each one of them contains GPS data.
 * @author Netanel
 * @author Carmel
 *
 */
public class Game {

	CsvArrayList cal;
	ArrayList<Packmen> packmens;
	ArrayList<Packmen> ghosts;
	ArrayList<Fruit> fruits;
	ArrayList<Rec> boxs;
	private Packmen MyPlayer;
	ArrayList<Integer> idPackmens;
	ArrayList<Integer> idFruits;

	/**
	 * This constructor accept csv file name and convert it to collections of pacmans and fruit
	 * @param csvFileName
	 */
	public Game(String csvFileName) {
		cal = new CsvArrayList(csvFileName); 
		packmens = new ArrayList<Packmen>();
		fruits = new ArrayList<Fruit>(); 
		ghosts = new ArrayList<Packmen>();
		boxs = new ArrayList<Rec>();
		createCollections(cal.getLines());

		//create arraylist of ids of elements
		idPackmens = new ArrayList<Integer>();
		idFruits = new ArrayList<Integer>();
		for (int i = 0; i < packmens.size() ; i++) {
			idPackmens.add(packmens.get(i).getId());
		}
		for (int i = 0; i < fruits.size() ; i++) {
			idFruits.add(fruits.get(i).getId());
		}
	}

	/**
	 * This method accept ArrayList<String[]> and create from it collections of fruits and pacmans
	 * @param lines
	 */
	public void createCollections(ArrayList<String[]> lines)
	{
		for(int i=0 ; i<lines.size() ; i++)
		{
			String[] line = lines.get(i);
			String type = lines.get(i)[0];
			if(type.equals("P"))
			{
				packmens.add(new Packmen(line));
			}
			if(type.equals("F"))
			{
				fruits.add(new Fruit(line));
			}

			if(type.equals("M")) {

				this.MyPlayer= new Packmen(line);
			}

			if(type.equals("G"))
			{
				ghosts.add(new Packmen(line));
			}

			if(type.equals("B"))
			{
				boxs.add(new Rec(line));
			}
		}
	}

	/**
	 * This method accepts the new game data and updates them in this game
	 * @param board_data - ArrayList<String>
	 */
	public void update (ArrayList<String> board_data) {
		ArrayList<Integer> idFruits_u = new ArrayList<Integer>();
		ArrayList<Integer> idPacmans_u = new ArrayList<Integer>();
		ArrayList<Point3D> PointPacmans_u = new ArrayList<Point3D>();

		int ghostIn =0 ;
		int pacIn=0;
		for (int i = 0; i <board_data.size(); i++) {
			String line = board_data.get(i);
			System.out.println(line);
			String[] array = line.split(",");
			Point3D newPoint  = new Point3D(array[2],array[3],array[4]);
			if (array[0].equals("M")) {
				MyPlayer.setGeom(newPoint);
			}			
			if(array[0].equals("G"))
			{
				ghosts.get(ghostIn).setGeom(newPoint);
				ghostIn++;
			}
			if ( array[0].equals("F")) {
				idFruits_u.add(Integer.parseInt(array[1]));
			}
			if ( array[0].equals("P")) {
				idPacmans_u.add(Integer.parseInt(array[1]));
				PointPacmans_u.add(newPoint);
				pacIn++;
			}
		}

		for (int j = 0; j < idFruits.size(); j++) {

			if (!idFruits_u.contains(idFruits.get(j))) {
				fruits.get(j).delete();
				fruits.remove(j);
				idFruits.remove(j);
			}
		}

		for (int i = 0; i < idPackmens.size(); i++) {
			if (idPacmans_u.contains(idPackmens.get(i))) {
				packmens.get(i).setGeom(PointPacmans_u.get(i));
			}
			else {
				packmens.get(i).delete();
				packmens.remove(i);
				idPackmens.remove(i);
			}
		}
	}

	/**
	 * Clear this game
	 */
	public void clear() { 

		packmens.clear();
		ghosts.clear();
		fruits.clear();
		MyPlayer = null;
	}

	/**
	 * @param p - Point3D
	 * @return true - if the point is inside some boxs
	 */
	public boolean IsInBoxes (Point3D p) {

		for (int i = 0; i < boxs.size(); i++) {
			if (boxs.get(i).IsIn(p))
				return true;
		}
		return false;
	}

	/**
	 * @return true - if MyPlayer close to ghost, in specific radius
	 */
	public boolean IsCloseGhots() {
		MyCoords c = new MyCoords();
		for (int i = 0; i < ghosts.size(); i++) {
			if ( c.distance3d((Point3D)ghosts.get(i).getGeom(), (Point3D)MyPlayer.getGeom()) <= 30)
				return true;
		}
		return false;
	}

	/**
	 * @return ArrayList<Packmen>
	 */
	public ArrayList<Packmen> getPackmens() {
		return packmens;
	}

	/**
	 * @return ArrayList<Packmen>
	 */
	public ArrayList<Packmen> getGhosts() {
		return ghosts;
	}

	/**
	 * @return MyPlayer
	 */
	public Packmen getMyPlayer() {
		return MyPlayer;
	}

	public void setMyPlayer(Packmen myPlayer) {
		MyPlayer = myPlayer;
	}

	public ArrayList<Rec> getBoxs() {
		return boxs;
	}


	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
}

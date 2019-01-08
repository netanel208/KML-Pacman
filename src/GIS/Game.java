package GIS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
		//		String []  str= {"M","0","0.0","0.0","0.0","20.0","1.0"};
		//		MyPlayer= new Packmen(str);

	}


	/**
	 * This constructor accept collections of pacmans and fruits and convert its to csv file(output.csv)
	 * @param packmens
	 * @param fruits
	 */
	public Game(ArrayList<Packmen> packmens, ArrayList<Fruit> fruits, ArrayList<Packmen> ghosts)
	{
		this.packmens = packmens;
		this.fruits = 	fruits;	
		this.ghosts = ghosts;

		//create arraylist of ids of elements
		idPackmens = new ArrayList<Integer>();
		idFruits = new ArrayList<Integer>();
		for (int i = 0; i < packmens.size() ; i++) {
			idPackmens.add(packmens.get(i).getId());
		}
		for (int i = 0; i < fruits.size() ; i++) {
			idFruits.add(fruits.get(i).getId());
		}

		String []  str= {"M","0","0.0","0.0","0.0","20.0","1.0"};
		MyPlayer= new Packmen(str);

		createCsv();

	}


	/**
	 * This method create csv file from this fruits and pacmans collections.
	 */
	public void createCsv()
	{
		String fileName = "output.csv";
		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File(fileName));//create new file with name: "output.csv"
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder sb = new StringBuilder();

		//the first line in csv file(titels)
		sb.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+packmens.size()+","+fruits.size()+"\n");

		//after write titels write the pacmans details
		for(int i=0 ; i<packmens.size() ; i++)
		{
			sb.append(packmens.get(i).toString()+"\n");
		}

		//after write pacmans details write the fruits details
		for(int i=0 ; i<fruits.size() ; i++)
		{
			sb.append(fruits.get(i).toString()+"\n");
		}

		pw.write(sb.toString());
		pw.close();
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


	public ArrayList<Integer>[] update(ArrayList<String> board_data) { // 1 2 3 6 8 9 <- Idpackmens
		ArrayList<Integer> idPacmans_u = new ArrayList<Integer>();
		ArrayList<Integer> idFruits_u = new ArrayList<Integer>();

		int pacIn = 0;// +1
		int fruIn = 0;//
		int ghostIn = 0;
		int numBox = 0;
		for(int i=0;i<board_data.size();) {
			String line = board_data.get(i);
			System.out.println(line);
			String[] array = line.split(",");
			Point3D newPoint  = new Point3D(array[2],array[3],array[4]);
			int ID = Integer.parseInt(array[1]);
			if (array[0].equals("M")) {
				MyPlayer.setGeom(newPoint);
				i++;
			}

			if (array[0].equals("P")) {//1 3 4 6 7 8 9 10 11 (game packmens)
				//1 3 4 6 8 9 11   (get board packmens)	
				if(ID == idPackmens.get(pacIn))
				{
					idPacmans_u.add(ID);//1 3 4 6 -1 8 9 -1 11
					packmens.get(pacIn).setGeom(newPoint);
					i++;
				}
				else {
					idPacmans_u.add(-1);
				}
				pacIn++;
			}
			System.out.println(Thread.currentThread());
			synchronized(this) {
				if(array[0].equals("F"))
				{
					synchronized(this) {
						if(ID == idFruits.get(fruIn))
						{
							idFruits_u.add(ID);
							i++;
						}
						else {
							idFruits_u.add(-1);
						}
						fruIn++;
					}
				}
			}
			if(array[0].equals("G"))
			{
				ghosts.get(ghostIn).setGeom(newPoint);
				ghostIn++;
				i++;
			}
			if(array[0].equals("B"))////////
			{
				numBox++;
				i++;
			}
		}//1 2 -1 4 5 7 

		for (int i = 0; i < idPacmans_u.size(); i++) {
			if(idPacmans_u.get(i) == -1)
			{
				packmens.get(i).delete();
				packmens.remove(i);
				idPackmens.remove(i);
			}
		}
		for (int i = 0; i < idFruits_u.size(); i++) {
			if(idFruits_u.get(i) == -1)
			{
				fruits.get(i).delete();
				//				System.out.println("delete fruit:"+fruits.get(i));
				fruits.remove(i);
				idFruits.remove(i);
			}
		}

		//check the end of board
		int sizeTotal = 1 + fruits.size() + packmens.size() + ghosts.size() + numBox;////
		if(sizeTotal != board_data.size() && board_data.size() > 0)
		{
			String line = board_data.get(board_data.size()-1);
			String[] array = line.split(",");
			int ID = Integer.parseInt(array[1]);
			if(fruits.size() > 0)
			{
				if(fruits.get(fruits.size()-1).getId() != ID)
				{
					fruits.get(fruits.size()-1).delete();
					//					System.out.println("delete fruit:"+fruits.get(fruits.size()-1));
					fruits.remove(fruits.size()-1);
					idFruits.remove(idFruits.size()-1);
				}
			}
			else if(packmens.size() > 0)
			{
				if(packmens.get(packmens.size()-1).getId() != ID)
				{
					packmens.get(packmens.size()-1).delete();
					packmens.remove(packmens.size()-1);
					idPackmens.remove(packmens.size()-1);
				}
			}
		}


		ArrayList<Integer>[] ans = new ArrayList[2];
		//		System.out.println(idFruits_u.toString());
		//		System.out.println(idPacmans_u.toString());
		//		System.out.println(idFruits.toString());
		//		System.out.println(idPackmens.toString());
		ans[0]= idFruits_u;
		ans [1] = idPacmans_u;
		return ans;
	}


	public void clear() { 

		packmens.clear();
		ghosts.clear();
		fruits.clear();
		MyPlayer = null;
	}
	
	public boolean IsInBoxes (Point3D p) {
		
		for (int i = 0; i < boxs.size(); i++) {
			if (boxs.get(i).IsIn(p))
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

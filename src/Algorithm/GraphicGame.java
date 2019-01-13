package Algorithm;

import java.util.ArrayList;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import GIS.Rec;
import Geom.Point3D;
import graph.Edge;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

/**
 * This department belongs to the algorithmic part of the game, the role of the department is to get a game 
 * and build a graph that represents the game.
 * You can perform various operations on the graph, such as finding the shortest route,
 * the price per calculation track, and more.
 * 
 * @author Netanel
 * @author Carmel
 *
 */
public class GraphicGame {
	Game game;
	ArrayList<Rec> boxs;
	ArrayList<Fruit> fruits;
	ArrayList<Packmen> pacmans;

	Graph G;
	segment seg;
	ArrayList<Point3D> pointOfBoxes;
	ArrayList<Point3D> pointOfFruit;
	ArrayList<Point3D> pointOfPacman;
	MyCoords m;

	/**
	 * This constructor accept game and build graph from it
	 * @param game - Game
	 */
	public GraphicGame ( Game game) {

		m = new MyCoords();
		G= new Graph();
		this.game=game;
		boxs= game.getBoxs();
		fruits= game.getFruits();
		pacmans= game.getPackmens();
		seg = new segment(boxs);
		pointOfBoxes = new ArrayList<Point3D>();
		pointOfFruit = new ArrayList<Point3D>();
		pointOfPacman = new ArrayList<Point3D>();

		createGraph();
	}

	/**
	 * This method build new graph
	 */
	public void createGraph() {
		createBoxGraph();
		createFruitGraph();
		createPacmanGraph();
	}

	/**
	 * This method updates the graph every time, creating new nodes and arches according to the updated game it receives
	 * @param game - Game - accept the update game
	 * @return the short path to specific target
	 */
	public ArrayList<Point3D> update (Game game) {
		G= new Graph();
		this.game=game;
		fruits= game.getFruits();
		pacmans= game.getPackmens();
		pointOfFruit = new ArrayList<Point3D>();
		pointOfBoxes = new ArrayList<Point3D>();
		pointOfPacman = new ArrayList<Point3D>();
		createGraph();           
		return shortPath ();
	}

	/**
	 * The method uses the Dijkstra algorithm to calculate the shortest path to a particular node that is a fruit or a pacman,
	 * and returns the required path to reach the goal. 
	 * The route is represented as a sequence of GPS points that the player must pass.
	 * @return shortPath - ArrayList<Point3D> - represent the route that My Player need to move
	 */
	public ArrayList<Point3D> shortPath () {
		Node graph_src = new Node("source");
		Point3D gps_src = (Point3D) game.getMyPlayer().getGeom();
		G.add(graph_src);


		for (int i = 0; i < G.size()-1; i++) {
			Node graph_dst = G.getNodeByIndex(i);
			Point3D gps_dst = null;
			if ( i>=0 && i<pointOfBoxes.size() ) {
				gps_dst= pointOfBoxes.get(i);
			}

			if (i>=pointOfBoxes.size() && i<pointOfFruit.size()+pointOfBoxes.size()) {
				gps_dst= pointOfFruit.get(i-pointOfBoxes.size());
			}

			if (i>=pointOfFruit.size()+ pointOfBoxes.size() && i < pointOfFruit.size()+ pointOfBoxes.size() + pointOfPacman.size()  ) {
				gps_dst= pointOfPacman.get(i-(pointOfBoxes.size()+pointOfFruit.size()));
			}

			if (!seg.Intersect(gps_src, gps_dst))
			{
				double dist_in_meter = m.distance3d(gps_src, gps_dst);
				G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);
			}
		}

		Graph_Algo.dijkstra(G, "source");

		double min = Integer.MAX_VALUE;
		Node min_node = null;
		for (int i = 0; i < G.size()-1; i++) {
			if(G.getNodeByIndex(i).getDist() < min && i>= pointOfBoxes.size() ) { //min and fruit or pacman
				min = G.getNodeByIndex(i).getDist();
				min_node = G.getNodeByIndex(i);
			}
		}
		ArrayList<Point3D> shortPath =new ArrayList<Point3D>();
		if(min_node != null)
		{
			ArrayList<String> path =min_node.getPath();
			path.add(min_node.get_name()); // add the target to the end of the path
			System.out.println("path:"+path);


			for (int i = 0; i < path.size(); i++) {
				Node n = G.getNodeByName(path.get(i));
				int index = n.get_id();

				if(n.get_name().startsWith("P")) {
					Point3D p = (Point3D) pacmans.get(index-(pointOfBoxes.size()+pointOfFruit.size())).getGeom();
					shortPath.add(p);

				}
				if (n.get_name().startsWith("F")) {
					Point3D p = (Point3D) fruits.get(index-pointOfBoxes.size()).getGeom();
					shortPath.add(p);

				}
				if(n.get_name().startsWith("B")) {
					Point3D p = (Point3D) pointOfBoxes.get(index);	
					shortPath.add(p);
				}
			}
		}
		return shortPath;
	}


	/**
	 * The method builds the graph for the boxes in the game, 
	 * all the neighbors of the box point is the point of another box.
	 */
	private void createBoxGraph ( ) {
		if (boxs.size()!=0) {
			for (int i = 0; i <boxs.size(); i++) {
				if ( !game.IsInBoxes(boxs.get(i).getDownLeftEps())) {// if the point is not in one of the boxs
					Node n = new Node("B_DL"+boxs.get(i).getID());
					G.add(n);
					pointOfBoxes.add(new Point3D(boxs.get(i).getDownLeftEps()));
				}
				if ( !game.IsInBoxes(boxs.get(i).getDownRightEps())) {
					Node n = new Node("B_DR:"+boxs.get(i).getID());
					G.add(n);
					pointOfBoxes.add(new Point3D(boxs.get(i).getDownRightEps()));
				}
				if ( !game.IsInBoxes(boxs.get(i).getUpLeftEps())) {
					Node n = new Node("B_UL:"+boxs.get(i).getID());
					G.add(n);
					pointOfBoxes.add(new Point3D(boxs.get(i).getUpLeftEps()));
				}
				if ( !game.IsInBoxes(boxs.get(i).getUpRightEps())) {
					Node n = new Node("B_UR:"+boxs.get(i).getID());
					G.add(n);
					pointOfBoxes.add(new Point3D(boxs.get(i).getUpRightEps()));
				}
			}

			m = new MyCoords();
			//create boxs edges
			for(int i=0 ; i<G.size() ; i++)
			{
				Node graph_src = G.getNodeByIndex(i);
				Point3D gps_src = pointOfBoxes.get(i);
				for(int j=0 ; j<G.size() ; j++)//for all graphic point of box finf all edges 
				{
					if (j>i) {        //avoid multyplication of edges
						Node graph_dst = G.getNodeByIndex(j);
						Point3D gps_dst = pointOfBoxes.get(j);
						if (!seg.Intersect(gps_src, gps_dst))
						{
							double dist_in_meter = m.distance3d(gps_src, gps_dst);
							G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);
						}
					}
				}
			}
		}
	}

	/**
	 * The method builds the graph for the fruit in the game after a graph has been built for the
	 * boxes in the game. Each fruit has an edges of type: fruit to box, fruit to fruit.
	 */
	private void createFruitGraph () {

		// create node in thre name of the id
		m = new MyCoords();

		for (int i = 0; i <fruits.size(); i++) {
			Node graph_src = new Node("F:"+fruits.get(i).getId());
			Point3D gps_src= (Point3D)fruits.get(i).getGeom();
			G.add(graph_src);

			for (int j = 0; j <G.size()-i-1; j++) {
				Node graph_dst = G.getNodeByIndex(j);
				Point3D gps_dst = pointOfBoxes.get(j);
				if (!seg.Intersect(gps_src,gps_dst))
				{
					double dist_in_meter = m.distance3d(gps_src, gps_dst);
					G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);

				}
			}

			pointOfFruit.add(gps_src);

		}

		for (int i = 0; i <fruits.size(); i++) {
			Node graph_src = G.getNodeByIndex(pointOfBoxes.size()+i);
			Point3D gps_src = pointOfFruit.get(i);
			for (int j = 0; j < fruits.size(); j++) {
				if ( j> i) {
					Node graph_dst = G.getNodeByIndex(pointOfBoxes.size()+j);
					Point3D gps_dst = pointOfFruit.get(j);
					if (!seg.Intersect(gps_src, gps_dst))
					{
						double dist_in_meter = m.distance3d(gps_src, gps_dst);
						G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);
					}
				}
			}

		}
		// create the edges 


	}

	/**
	 * The method builds the graph for the game playmen, the method is activated after there are already
	 * nodes and arches between boxes and fruits and vice versa.
	 * Now edges are created: Pacman for box, Pekman for fruit, Pacman for Pacman.
	 */
	private void createPacmanGraph () {

		for (int i = 0; i< pacmans.size()  ; i++) {
			Point3D gps_src= (Point3D)pacmans.get(i).getGeom();
			if (!game.IsInBoxes(gps_src)) {

				Node graph_src = new Node("P:"+pacmans.get(i).getId());
				G.add(graph_src);
				Point3D gps_dst = null;
				for (int j = 0; j < G.size()-i-1; j++) {
					Node graph_dst = G.getNodeByIndex(j);

					if ( j>=0 && j<pointOfBoxes.size() ) {
						gps_dst= pointOfBoxes.get(j);
					}

					else if (j>=pointOfBoxes.size() && j<pointOfFruit.size()+pointOfBoxes.size()) {
						gps_dst= pointOfFruit.get(j-pointOfBoxes.size());
					}

					if (!seg.Intersect(gps_src, gps_dst))
					{
						double dist_in_meter = m.distance3d(gps_src, gps_dst);
						G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);
					}

				}
				pointOfPacman.add(gps_src);
			}
		}


		for (int i = 0; i <pointOfPacman.size(); i++) {
			Node graph_src = G.getNodeByIndex(pointOfBoxes.size()+pointOfFruit.size()+i);
			Point3D gps_src = pointOfPacman.get(i);
			for (int j = 0; j < pointOfPacman.size(); j++) {
				if ( j> i) {
					Node graph_dst = G.getNodeByIndex(pointOfBoxes.size()+pointOfFruit.size()+j);
					Point3D gps_dst = pointOfPacman.get(j);
					if (!seg.Intersect(gps_src, gps_dst))
					{
						double dist_in_meter = m.distance3d(gps_src, gps_dst);
						G.addEdge(graph_src.get_name(), graph_dst.get_name(), dist_in_meter);
					}
				}
			}

		}

	}

	/**
	 * @param goThere
	 * @param az_ghost
	 * @return new_az - the new angle that the player need to move to run away from ghost
	 */
	public double runAway(double goThere , double az_ghost)
	{
		double new_az = 0;
		if(goThere <= 90)
		{
			if(az_ghost<=180) {
				new_az = az_ghost+180;
				return new_az;
			}
			else if(az_ghost<=360) {
				new_az = az_ghost+180-360;
				return new_az;
			}
		}
		else if(goThere <= 180)
		{
			if(az_ghost<=180) {
				new_az = az_ghost+180;
				return new_az;
			}
			else if(az_ghost<=270) {
				new_az = az_ghost+180-360;
				return new_az;
			}
		}
		else if(goThere <= 270)
		{
			if(az_ghost<=180 && az_ghost>=90)
			{
				new_az = az_ghost+180;
				return new_az;
			}
			else if(az_ghost<=360 && az_ghost>=90)
			{
				new_az = az_ghost+180-360;
				return new_az;
			}
		}
		else if(goThere <= 360)
		{
			if(az_ghost<=90) {
				new_az = az_ghost+180;
				return new_az;
			}
			else if(az_ghost>180)
			{
				new_az = az_ghost+180-360;
				return new_az;
			}
		}
		return new_az;
	}

	/**
	 * The method finds the position where it is best to start the game, the method determines the starting position
	 * of the player according to the conditions of the game. If there are Pacmans in the game you will find the place 
	 * to start from so that the game will end as soon as possible to earn more points.
	 * But if there is no Pacman in the game we prefer to start the game where there are more fruits close.
	 * @return starting point - Point3D 
	 */
	public Point3D startingPoint() {

		double minSumOfE1 = 0; //the minimum sum of edges
		int minInPac = 0; //the index of pacman that minimum sum of edges
		int pac1Max = 0;  //amount of close pacmans
		int pac1MaxIn = 0;//the index of pacman that amount of close pacmans is max 
		int fru1Max = 0;
		int fru1MaxIn = 0;

		//if there are pacmans in the game
		if (pointOfPacman.size()!=0) {
			minSumOfE1 = Double.MAX_VALUE;
			pac1Max = Integer.MIN_VALUE;
			fru1Max = Integer.MIN_VALUE;
			for (int i = pointOfBoxes.size()+pointOfFruit.size(); i <G.size(); i++) {
				double minLen = 0;
				int pac1 = 1;
				int fru1 = 0;
				ArrayList<Edge> neighbors= G.getNodeByIndex(i).get_ni();
				for (int j = 0; j < neighbors.size(); j++) {
					int in = neighbors.get(j).getInd();
					if(in >= pointOfBoxes.size())//if the nighbor is fruit or pacman
					{
						minLen += neighbors.get(j).getW();
					}
					if(in >= pointOfBoxes.size()+pointOfFruit.size())//if the nighbor is pacman
					{
						pac1++;   //count the number of pacmans for one pacman
					}
					if(in >= pointOfBoxes.size() && in < pointOfBoxes.size()+pointOfFruit.size()) {
						fru1++;
					}
				}
				if(minLen<minSumOfE1){//find the minimum distance 
					minSumOfE1 = minLen;
					minInPac = i-(pointOfBoxes.size()+pointOfFruit.size());
				}
				if(pac1>pac1Max) {//find the max pacman edge
					pac1Max = pac1;
					pac1MaxIn = i-(pointOfBoxes.size()+pointOfFruit.size());
				}
				if(fru1>fru1Max) {//find the max fruit edge
					fru1Max = fru1;
					fru1MaxIn = i - (pointOfBoxes.size()+pointOfFruit.size());
				}
			}
		}

		double minSumOfE2 = 0;
		int minInFru = 0;
		int pac2Max = 0;
		int pac2MaxIn = 0;
		int fru2Max = 0;
		int fru2MaxIn = 0;

		if(pointOfFruit.size()!=0)
		{
			minSumOfE2 = Double.MAX_VALUE;
			pac2Max = Integer.MIN_VALUE;
			fru2Max = Integer.MIN_VALUE;
			for (int i = pointOfBoxes.size(); i <G.size()-pointOfPacman.size(); i++) {
				double minLenFru = 0;
				int pac2 = 0;
				int fru2 = 1;
				ArrayList<Edge> neighbors= G.getNodeByIndex(i).get_ni();
				for (int j = 0; j <neighbors.size(); j++) {
					int in = neighbors.get(j).getInd();
					if(in >= pointOfBoxes.size())
					{
						minLenFru += neighbors.get(j).getW();
					}
					if(in >= pointOfBoxes.size()+pointOfFruit.size())
					{
						pac2++; //count the number of pacmans
					}
					if(in >= pointOfBoxes.size() && in < pointOfBoxes.size()+pointOfFruit.size()) {
						fru2++;
					}
				}
				if(minLenFru<minSumOfE2){//find the minimum distance
					minSumOfE2 = minLenFru;
					minInFru = i-pointOfBoxes.size();
				}
				if(pac2>pac2Max) {//find the max pacman edge
					pac2Max = pac2;
					pac2MaxIn = i-pointOfBoxes.size();
				}
				if(fru2>fru2Max) {//find the max fruit edge
					fru2Max = fru2;
					fru2MaxIn = i - pointOfBoxes.size();
				}
			}
		}

		if(!pacmans.isEmpty() && !fruits.isEmpty())
		{	
			if(pac1Max > pac2Max) {//there are more pacman nighbor to someone pacman 
				return pointOfPacman.get(pac1MaxIn);
			}
			else if(pac1Max <= pac2Max) {
				if(minSumOfE1 < minSumOfE2) {
					return pointOfPacman.get(minInPac);
				}
				return pointOfFruit.get(minInFru);
			}
			else
				return pointOfFruit.get(pac2MaxIn);
		}
		else if(pacmans.isEmpty()) {
			return pointOfFruit.get(fru2MaxIn);
		}
		else
			return null;
	}

	public String toString () {
		String str = "";
		for (int i = 0; i < G.size(); i++) {
			str += G.getNodeByIndex(i).toString()+"\n";
		}
		return str;
	}
}

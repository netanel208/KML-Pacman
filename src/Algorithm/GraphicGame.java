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
	public void createGraph() {
		createBoxGraph();
		createFruitGraph();
		createPacmanGraph();

	}

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
		System.out.println(min_node);
		ArrayList<String> path =min_node.getPath();
		path.add(min_node.get_name()); // add the target to the end of the path
		System.out.println("path:"+path);
		ArrayList<Point3D> shortPath =new ArrayList<Point3D>();

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

		return shortPath;

	}


	public void createBoxGraph ( ) {
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


	public void createFruitGraph () {

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


	public void createPacmanGraph () {

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


	public String toString () {
		String str = "";
		for (int i = 0; i < G.size(); i++) {
			str += G.getNodeByIndex(i).toString()+"\n";
		}
		return str;
	}



	public double runAway (double goThere , double az_ghost) {
		double new_az = 0;
		if(Math.abs(az_ghost-goThere) <= 90)
		{
			if(az_ghost-goThere>=0)
				new_az+=90;
			else
				new_az-=90;
		}

		if (new_az <0 )
			new_az+=360;

		if(new_az >360)
			new_az-=360;

		return new_az;
	}

//	public Point3D startingPoint() {
//
//		if (pointOfPacman.size()!=0) {
//			int maxNei= 0;
//			for (int i = pointOfBoxes.size()+pointOfFruit.size(); i <G.size(); i++) {
//				ArrayList<Edge> neighbors= G.getNodeByIndex(i).get_ni();
//				for (int j = 0; j <neighbors.size(); j++) {
//					neighbors.get(i).
//				}
//			}
//
//		}
//
//	}
//
//	public ArrayList<Node> getNeighbors (Node n){
//
//	}


	//	public static void main(String[] args) {
	//
	//		Game g = new Game("data/Ex4_OOP_example5.csv");
	//		GraphicGame alg = new GraphicGame(g);
	//		System.out.println(alg.toString());
	//		System.out.println("after");
	//		alg.update(g);
	//		System.out.println(alg.toString());
	//
	//
	//	}
}

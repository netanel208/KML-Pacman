package Algorithm;

import java.util.ArrayList;

import GIS.Fruit;
import GIS.Game;
import GIS.Rec;
import Geom.Point3D;
import graph.Graph;
import graph.Node;

public class GraphicGame {
	Game game;
	ArrayList<Rec> boxs;
	ArrayList<Fruit> fruits;
	Graph G;
	segment seg;
	ArrayList<Point3D> pointOfboxes;

	public GraphicGame ( Game game) {
		G= new Graph();
		this.game=game;
		boxs= game.getBoxs();
		fruits= game.getFruits();
		CreateNode( boxs,fruits);
		seg = new segment(boxs);


	}

	public void CreateNode (ArrayList<Rec> boxs, ArrayList<Fruit> fruits ) {

		for (int i = 0; i <boxs.size(); i++) {
			if ( !game.IsInBoxes(boxs.get(i).getDownLeftEps())) {// if the point is in one of the boxs
				Node n = new Node("B:"+G.size());
				G.add(n);
			}
			if ( !game.IsInBoxes(boxs.get(i).getDownRightEps())) {
				Node n = new Node("B:"+G.size());
				G.add(n);
			}
			if ( !game.IsInBoxes(boxs.get(i).getUpLeftEps())) {
				Node n = new Node("B:"+G.size());
				G.add(n);
			}
			if ( !game.IsInBoxes(boxs.get(i).getUpRightEps())) {
				Node n = new Node("B:"+G.size());
				G.add(n);
			}
		}
		
		//create edges
		for(int i=0 ; i<G.size() ; i++)
		{
			Node n = G.getNodeByIndex(i);
			for(int j=0 ; j<G.size() ; j++)
			{
				if ( j>i) {
					if (seg.Intersect(a, b)G.getNodeByIndex(j))
				}
			}
		}
	}
}

package dataStructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import gameClient.fruit;
import gameClient.MyGameGUI;
import gameClient.play;
import gameClient.robot;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;


public class test {

	public static void main(String[] args) throws JSONException {
		game_service game = Game_Server.getServer(2);
		play p = new play(game);
		//p.locatefruit();
		MyGameGUI h = new MyGameGUI(); 
		/*
		graph_algorithms t = new Graph_Algo();
		
		DGraph temp = new DGraph();

		Point3D m = new Point3D(1, 3, 4);
		Point3D m1 = new Point3D(2, 4, 5);
		Point3D m2 = new Point3D(3, 5, 6);
		Point3D m3 = new Point3D(4, 6, 7);
		Point3D m4 = new Point3D(5, 6, 7);

		NodeData a = new NodeData(1, m);
		NodeData b = new NodeData(2, m1);
		NodeData c = new NodeData(3, m2);
		NodeData d = new NodeData(4, m3);
		NodeData e = new NodeData(5, m4);
		
		temp.addNode(a);
		temp.addNode(b);
		temp.addNode(c);
		temp.addNode(d);
		temp.addNode(e);
		
		temp.connect(a.getKey(), b.getKey(), 1);
		temp.connect(b.getKey(), c.getKey(), 3);
		temp.connect(c.getKey(), d.getKey(), 3);
		temp.connect(d.getKey(), e.getKey(), 2);
		temp.connect(b.getKey(), d.getKey(), 1);
		temp.connect(c.getKey(), e.getKey(), 2);
		
		t.init(temp);
		
		
		List<node_data> lis = t.shortestPath(1, 5);
		for (int i = lis.size()-2; i >= 0; i--) {
			//System.out.print(lis.get(i).getKey()+" ");
		}
		*/
		
	}
}

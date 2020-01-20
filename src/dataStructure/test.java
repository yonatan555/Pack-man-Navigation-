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

			//MyGameGUI h = new MyGameGUI(); 
		game_service game = Game_Server.getServer(0);	
		int i =0;
		for (String k: game.getFruits()) {
			fruit frut = new fruit(k);
			i++;
			int value = frut.getValue();
			int type= frut.getType();
			Point3D po = frut.getPos();
			System.out.println("value: "+ value + " type: " + type + " po: " +" x " +po.x() +" y: " + po.y()  );
		}
		
		
				
	}
}

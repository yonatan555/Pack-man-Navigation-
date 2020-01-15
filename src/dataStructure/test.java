package dataStructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import gameClient.fruit;
import gameClient.MyGameGUI;
import gameClient.play;
import gameClient.robot;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;

public class test {

	public static void main(String[] args) throws JSONException {
	/*game_service game = Game_Server.getServer(2);
		play p = new play (game);
		p.locateRobots();
		for (int i = 0;i < p.fru.size(); i++) {
			game.addRobot(p.fru.get(i).getSrc());
		}
		p.moverob(game);
		System.out.println(p.rob.get(0).getSrc());
		game.startGame();
		while(game.isRunning()) {
			game.chooseNextEdge(0, 10);
			game.move();
		}
		p.moverob(game);
		System.out.println(p.rob.get(0).getSrc());*/
		
		MyGameGUI t = new MyGameGUI();
		
	}
}

package gameClient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.node_data;

public class auto {
	
play p;

public int getrobs(game_service game) throws JSONException {
	int i = 0;

	JSONObject m = new JSONObject(game.toString());
	try {
		JSONObject ro = m.getJSONObject("GameServer");
		i = ro.getInt("robots");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return i;
}


public  void StartAuto(game_service game) {
	try {
		
		p = new play(game);
		int count = getrobs(game);
		MyGameGUI gui = new MyGameGUI(p.grp);
		gui.setplay(game);
		gui.initGUI();
		gui.p.locatefruit();
		for (int i = 0; i < count; i++) {
			game.addRobot(gui.p.fru.get(i).getSrc());
		}
		gui.p.moverob(game);
		Graph_Algo g = new Graph_Algo(gui.p.grp);
		game.startGame();
		while (game.isRunning()) {
			MyGameGUI.time = game.timeToEnd() / 1000;
			List<node_data> l = new ArrayList<node_data>();
			for (int i = 0, j = 0; i < count && j < gui.p.fru.size(); i++, j++) {
				System.out.println(gui.p.rob.get(i).getSrc());
				System.out.println(gui.p.fru.get(j).getdest());
				if (gui.p.rob.get(i).getSrc() != gui.p.fru.get(j).getdest()) {
					System.out.println("55555");
					l = g.shortestPath(gui.p.rob.get(i).getSrc(), gui.p.fru.get(j).getdest());
					if (l != null) {
						for (int k = l.size() - 2; k >= 0; k--) {
							System.out.print(l.get(k).getKey() + ", ");
							game.chooseNextEdge(i, l.get(k).getKey());
							gui.p.locatefruit();
							gui.p.moverob(game);
							gui.paint();

						}
						game.move();
					} else {
						System.out.println("asd");
						break;
					}
				} else {
					game.chooseNextEdge(i, gui.p.fru.get(j).getSrc());
				}
			}
			game.move();
			gui.p.movefrut(game);
			gui.p.moverob(game);
			MyGameGUI.score = showScore(game);
			gui.p.locatefruit();
			gui.paint();

		}
	}

	catch (Exception e) {
		e.printStackTrace();
	}

}
public String showScore(game_service game) throws JSONException {

	JSONObject m = new JSONObject(game.toString());
	try {

		JSONObject ro = m.getJSONObject("GameServer");
		int grade = ro.getInt("grade");
		int moves = ro.getInt("moves");
	MyGameGUI.score = "grade: " + grade + "moves :" + moves;
		return MyGameGUI.score;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "";

}
}

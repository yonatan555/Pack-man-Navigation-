package dataStructure;

import org.json.JSONException;

import Server.Game_Server;
import Server.game_service;
import gameClient.fruit;
import gameClient.graph_gui;
import gameClient.robot;

public class test {

	public static void main(String[] args) throws JSONException {
		game_service game = Game_Server.getServer(0);
		graph_gui t = new graph_gui(game);
		t.setVisible(true);
		
	//	System.out.println(game.getGraph().toString());
	}

}

package gameClient;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.graph;

public class play {
	
	ArrayList<robot> rob  ;
	ArrayList<fruit> fru ;
	graph grp ;
	
	public play() {
		this.rob = new ArrayList<robot>();
		this.fru = new ArrayList<fruit>();
		this.grp = null;
	}
	public play(game_service game) throws JSONException
	{
		this.fru = new ArrayList<fruit>();
		for(String fruit1 : game.getFruits() ) 
		{
			fru.add(new fruit(fruit1));
		}
		this.rob = new ArrayList<robot>();
		for(String ro : game.getRobots())
		{
			rob.add(new robot(ro));
		}
		this.grp = new DGraph();
		((DGraph) this.grp).init(game.getGraph().toString());
	}
	public void locatefruit(game_service game) throws JSONException {
		int count = game.getFruits().size();
		play s = new play(game);
		
	}
	
}

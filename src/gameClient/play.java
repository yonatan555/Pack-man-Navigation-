package gameClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;

public class play {
	public ArrayList<robot> rob;
	public ArrayList<fruit> fru;
	public graph grp;
	public game_service game;
	public play() {
		this.rob = new ArrayList<robot>();
		this.fru = new ArrayList<fruit>();
		this.grp = null;
	}

	public play(game_service game) throws JSONException {
		this.fru = new ArrayList<fruit>();
		for (String fruit1 : game.getFruits()) {
			fru.add(new fruit(fruit1));
		}
		this.rob = new ArrayList<robot>();
		for (String ro : game.getRobots()) {
			rob.add(new robot(ro));
		}

		this.grp = new DGraph();
		this.game = game ;
		((DGraph) this.grp).init(game.getGraph().toString());
	}
	
	
	
	
	
	public void movefrut(game_service game) throws JSONException {
		this.fru = new ArrayList<fruit>();
		for (String fruit1 : game.getFruits()) {
			fru.add(new fruit(fruit1));
		}
	}
	
	public void moverob(game_service game) throws JSONException {
		this.rob = new ArrayList<robot>();
		for (String ro : game.getRobots()) {
			rob.add(new robot(ro));
		}
		
	}

	public void locatefruit() throws JSONException {
		double eps = 0.00000000001;
		double x = 0;
		double y = 0;
		double x1 = 0;
		double y1 = 0;
		double x2 = 0;
		double y2 = 0;
		double sum = 0;
		double sum2, sum3 = 0;
		Collection<node_data> n = this.grp.getV();
		for (int i = 0; i < this.fru.size(); i++) {
			x = this.fru.get(i).getPos().x();
			y = this.fru.get(i).getPos().y();
			for (node_data no : n) {
				Collection<edge_data> ed = this.grp.getE(no.getKey());
				for (edge_data e : ed) {
					x1 = this.grp.getNode(e.getSrc()).getLocation().x();
					y1 = this.grp.getNode(e.getSrc()).getLocation().y();
					x2 = this.grp.getNode(e.getDest()).getLocation().x();
					y2 = this.grp.getNode(e.getDest()).getLocation().y();
					sum = Math.sqrt((Math.pow((x1 - x), 2)) + (Math.pow((y1 - y), 2)));
					sum2 = Math.sqrt((Math.pow((x - x2), 2)) + (Math.pow((y - y2), 2)));
					sum3 = Math.sqrt((Math.pow((x1 - x2), 2)) + (Math.pow((y1 - y2), 2)));
					if (((sum + sum2) - sum3) >= -eps && ((sum + sum2) - sum3) <= eps) {
						if (this.fru.get(i).getType() == -1) {
							if (e.getDest() < e.getSrc()) {
								this.fru.get(i).setsrc((e.getSrc()));
								this.fru.get(i).setdest(e.getDest());
							} else {
								this.fru.get(i).setsrc((e.getDest()));
								this.fru.get(i).setdest(e.getSrc());
							}
						} else {
							if (e.getDest() > e.getSrc()) {
								this.fru.get(i).setsrc((e.getDest()));
								this.fru.get(i).setdest(e.getSrc());
							} else {
								this.fru.get(i).setsrc((e.getSrc()));
								this.fru.get(i).setdest(e.getDest());
							}

						}
						this.fru.get(i).setedge(e);
						break;
					}
				}

			}

		}
	}

	public void locateRobots() throws JSONException {
		for (int i = 0; i < this.rob.size() && i < this.fru.size(); i++) {
			this.rob.get(i).setSrc(this.fru.get(i).src);
		}
	}
	
	
}

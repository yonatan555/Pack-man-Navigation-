package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.edge_data;
import utils.Point3D;

public class fruit {

	int value;
	int type;
	Point3D pos;
	int src;
	int dest;
	edge_data edge;
	
	public fruit () {
		
		this.value=0;
		this.type=0;
		this.pos= Point3D.ORIGIN;
		
	}
	public fruit(String fru) throws JSONException {
		JSONObject m = new JSONObject(fru);
		try {
			JSONObject frut = m.getJSONObject("Fruit");
			
			this.value = frut.getInt("value");
			this.type = frut.getInt("type");
			this.pos = new Point3D(frut.getString("pos"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getValue() {
		return this.value;
	}
	public int getType() {
		return this.type;
	}
	public int getsrc() {
		return this.src;
	}
	public int getdest() {
		return this.dest;
	}
	public edge_data getedge() {
		return this.edge;
	}
	public Point3D getPos() {
		return this.pos;
	}
	public void setValue(int value) {
		this.value = value ;
	}
	public void setType(int type) {
		this.type = type ;
	}
	public void setsrc(int src) {
		this.src = src ;
	}
	public void setdest(int dest) {
		this.dest = dest ;
	}
	public void setedge(edge_data e) {
		this.edge = e ;
	}
	public void setPoint(String s) {
		this.pos = new Point3D(s);
	}
	
}

package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import utils.Point3D;

public class fruit {

	int value;
	int type;
	Point3D pos;
	
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
	public Point3D getPos() {
		return this.pos;
	}
	public void setValue(int value) {
		this.value = value ;
	}
	public void setType(int type) {
		this.type = type ;
	}
	public void setPoint(String s) {
		this.pos = new Point3D(s);
	}
	
}

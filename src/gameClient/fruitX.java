package gameClient;

import org.json.JSONException;

import dataStructure.edge_data;
import utils.Point3D;

public interface fruitX {
	/**
	 * This interface repressents fruit  class  , that indicate the score  when robot is eat "fruits" , 
	 *  that located at (src,dest) of the most close  distance from each robot to it
	 * the first function(construcor) is to init the fruit from json objcet
	 * @return 
	 * 
*/
	/**
	 * return fruit values
	 * @param g
	 */
	public int getValue();
	/**
	 * return fruit type
	 * @param g
	 */
	public int getType();
	/**
	 * return fruit src 
	 * @param g
	 */
	public int getSrc();
	/**
	 * return fruit dest
	 * @param g
	 */
	public int getdest();
	/**
	 * return fruit edge
	 * @param g
	 */
	public edge_data getedge();
	/**
	 * return fruit pos
	 * @param g
	 */
	public Point3D getPos();
}

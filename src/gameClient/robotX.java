package gameClient;

import org.json.JSONException;

import utils.Point3D;

public interface robotX {
/**
 * This interface repressents Robot class  , that indicate the robot that eat the "fruits" , 
 *  that located at (src,dest) of the most close  distance from each fruit
 * the first function (construcor) is to init the Robot from json objcet
 * @return 
 * 
 * 
 * 
*/
	/**
	 * return robot dest
	 * @param g
	 */
	public int getDest();
	
	/**
	 * return robot src
	 * @param g
	 */
	public int getSrc();
	/**
	 * * return robot value
	 * @param g
	 */
	public double getValue() ;
	/**
	 * * return robot id
	 * @param g
	 */
	public int getId() ;
	/**
	 * * return robot speed
	 * @param g
	 */
	public double getSpeed() ;
	/**
	 * * return robot pos
	 * @param g
	 */
	public Point3D getPos() ;
	
}

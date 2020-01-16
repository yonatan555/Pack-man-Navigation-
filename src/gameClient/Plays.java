package gameClient;

import org.json.JSONException;

import Server.game_service;

public interface Plays {
/**
 *This interface repressts class will come together the all pieces of objects (grp, robot, and fruits) ,and make connections and initilize the all params that 
 *locateed at grpah by reading from json files...
 *if u implemts this interface u must to make use at the functions that are doing opertions at graph
 *like locates robots and frutis and  move the fruits on the graph .
 *
 */
	
	
	/**
	 * moving the fruits and update the locations of the fruits by the server commands on the graph
	 * @param game
	 * @throws JSONException
	 */
	public void movefrut(game_service game) throws JSONException ;

	/**
	 * moving the robots and update the locations of the fruits by the server commands on the graph
	 * @param game
	 * @throws JSONException
	 */
	public void moverob(game_service game) throws JSONException;

	/**
	 * locate the fruits at graph by the json that the server is sending
	 * and decide which fruit is eaten
	 * @param game
	 * @throws JSONException
	 */
	public void locatefruit() throws JSONException;

	/**
	 * locate the robot by the fruits locations to make the max score
	 * @param game
	 * @throws JSONException
	 */
	public void locateRobots() throws JSONException;
}

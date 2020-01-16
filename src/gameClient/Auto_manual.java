package gameClient;


import org.json.JSONException;

import Server.game_service;
import dataStructure.graph;

public interface Auto_manual {
	/**
	 * this interface is repressets the way that we choose to make active the graph
	 * move , by auto or manual , the interface make using at stddraw to print and
	 * show the gui and set of actions of the class opertions
	 */

	/**
	 * retrun the x ,y min and max for scales of gui
	 * 
	 * @param g
	 */
	public void set(graph g);

	/**
	 * make the window of gui show
	 * 
	 * @param g
	 */
	public void initGUI();

	/**
	 * show to the user the option to make use at the graph and the abbilty to move
	 * robots at graph by clicks at mouse
	 * 
	 * @param g
	 */
	public void StartManual(String gameNumber);

	/**
	 * show to the user automutticly using at graph
	 * 
	 * @param gameNumber
	 */

	public void StartAuto(String gameNumber);

	/**
	 * reutrn the move and grade durring the using
	 * 
	 * @param g
	 */
	public String showScore(game_service game) throws JSONException;

	/**
	 * paint the whole board of the graph with the robots and
	 */
	public void paint();

	/**
	 * 
	 * return play class
	 * 
	 * @return
	 */

	public play getPlay();

	/**
	 * return the num of robots at the boatd from the server
	 * @param game
	 * @return
	 * @throws JSONException
	 */
	public int getrobs(game_service game) throws JSONException;
	/**
	 * 
	 * return x and y value of the class 
	 * @param x
	 * @param y
	 */
	public void setXandY(double x, double y);

}

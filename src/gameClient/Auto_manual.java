import org.json.JSONException;

import Server.game_service;
import dataStructure.graph;

public interface Auto_manual {
/**
 * this interface is repressets the way that we choose to make active the graph move , by auto or manual ,
 * the interface make using at stddraw to print and show the gui and  set of actions of the class opertions
 */
	/**
	 * 
	 * @param g
	 */
	public void set(graph g) ;
	public void setXandY();
	public void initGUI();
	public void StartManual(String gameNumber);
	public void StartAuto(String gameNumber) ;
	public String showScore(game_service game) throws JSONException ;
	public void paint();
	public play getPlay() ;
	public int getrobs(game_service game) throws JSONException ;
	public void setXandY(double x, double y);
	
	
}

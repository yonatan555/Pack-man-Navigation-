package gameClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.node_data;

public class auto {
	
	int numbergame;
	play p;
	KML_Logger kmlLog;

	//consturctor
	public auto() {
		this.kmlLog = null;
		this.p = null;
		this.numbergame = 0;
	}
	

	// setter
	public void setgamenumber(int sen) {
		this.numbergame = sen;
	}

	// threadforkml start from other point and initlize a kml file for send the file to server
	Thread t;
	
	public void threadForKML(game_service game) {
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (game.isRunning()) {
					try {
						Thread.sleep(99);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String time = java.time.LocalDate.now() + "T" + java.time.LocalTime.now();
					LocalTime end = java.time.LocalTime.now();
					end = end.plusNanos(100 * 1000000);
					String endTime = java.time.LocalDate.now() + "T" + end;
					kmlLog.setFruits(time, endTime);
					kmlLog.setBots(time, endTime);

				}
			}
		});
		t.start();
	}

	// getting the num of robots
	public int getrobs(game_service game) throws JSONException {
		int i = 0;

		JSONObject m = new JSONObject(game.toString());
		try {
			JSONObject ro = m.getJSONObject("GameServer");
			i = ro.getInt("robots");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// algoritem for thee automaticly mode 
	/*
	 * 
	 * 	The algo is workking lke we search the shortest way from robot i and other fruit by
	 *	shortest path algoritm , and we are using with another calculate is calculating the realtive between
	 *fruit value shortest path ,we are locating the fruts and the robot and updating the them every running (while the game is running).
	 *	we are sending each robot to another fruit
	 * **/
	
	public  void StartAuto(game_service game) {
		try {
			Game_Server.login(206087702);
			System.out.println(game.toString());
			p = new play(game);
			int count = getrobs(game);
			MyGameGUI gui = new MyGameGUI(p.grp);
			gui.setplay(game);
			gui.initGUI();
			gui.p.locatefruit();
			for (int i = 0; i < count; i++) {
				game.addRobot(gui.p.fru.get(i).getSrc());
			}
			gui.p.moverob(game);
			Graph_Algo g = new Graph_Algo(gui.p.grp);
			double sum=0;
			double min=Double.MAX_VALUE;
			int fru=0;
			game.startGame();
			kmlLog = new KML_Logger(p.grp);
			kmlLog.BuildGraph();
			kmlLog.setGame(game);
			threadForKML(game);
			long time_l=game.timeToEnd();
			while (game.isRunning()) {
					if(time_l-game.timeToEnd()>70) {
						game.move();
						time_l=game.timeToEnd();
					}	
					MyGameGUI.time = game.timeToEnd() / 1000;
					List<node_data> l = new ArrayList<node_data>();
					for (int i = 0; i < count; i++) {
						for (int j = 0; j < gui.p.fru.size(); j++) {
								sum=g.shortestPathDist(gui.p.rob.get(i).getSrc(), gui.p.fru.get(j).getdest());
								if(sum<=min && gui.p.fru.get(j).getTag()!=100) {
									fru=j;
									min=sum;
								}
						}
						gui.p.fru.get(fru).setTag(100);
								if (gui.p.rob.get(i).getSrc() != gui.p.fru.get(fru).getdest()) {
								l = g.shortestPath(gui.p.rob.get(i).getSrc(), gui.p.fru.get(fru).getdest());
								if (l != null) {
									for (int k = l.size() - 2; k >= 0; k--) {
										game.chooseNextEdge(i, l.get(k).getKey());
										gui.p.locatefruit();
										gui.p.moverob(game);
										gui.paint();
									}
								} 
								else break;
							}
							else game.chooseNextEdge(i, gui.p.fru.get(fru).getSrc());
								min=Double.MAX_VALUE;
								/*if(time_l-game.timeToEnd()>80) {
									game.move();
									time_l=game.timeToEnd();
								}*/
					}
				gui.p.movefrut(game);
				gui.p.moverob(game);
				MyGameGUI.score = showScore(game);
				gui.p.locatefruit();
				gui.paint();
			}
			kmlLog.save("data/"+numbergame+".kml");
			String kmlFile =  kmlStr("data/"+numbergame+".kml");
			game.sendKML(kmlFile);
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	//we kml file to String and save it 
	private String kmlStr(String path)
	{
		String st=""; 
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file)); 

			String str;
			while ((str = br.readLine()) != null) 
			{
				st+=str+"\n"; 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	/*public String readKML(String a) {
		
	}*/

	// show the score on the monitor every each minute
	public String showScore(game_service game) throws JSONException {

		JSONObject m = new JSONObject(game.toString());
		try {

			JSONObject ro = m.getJSONObject("GameServer");
			int grade = ro.getInt("grade");
			int moves = ro.getInt("moves");
			MyGameGUI.score = "grade: " + grade + "moves :" + moves;
			return MyGameGUI.score;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
}

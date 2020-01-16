package gameClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;

public class MyGameGUI implements Auto_manual {
	static String score;
	static long time = 0;
	double Min_x = Integer.MAX_VALUE;
	double Max_x = Integer.MIN_VALUE;
	double Min_y = Integer.MAX_VALUE;
	double Max_y = Integer.MIN_VALUE;
	play p;
	double x;
	double y;

	public MyGameGUI() {
		initGUI();
		StdDraw.setGraph(this);
	}

	// find the the min max for resulotion
	@Override
	public void set(graph g) {
		Min_x = Integer.MAX_VALUE;
		Max_x = Integer.MIN_VALUE;
		Min_y = Integer.MAX_VALUE;
		Max_y = Integer.MIN_VALUE;
		double x[] = new double[g.getV().size()];
		double y[] = new double[g.getV().size()];
		int i = 0;
		for (node_data n : g.getV()) {
			x[i] = g.getNode(n.getKey()).getLocation().x();
			y[i] = g.getNode(n.getKey()).getLocation().y();
			i++;
		}
		Arrays.sort(x);
		Arrays.sort(y);
		this.Max_x = x[x.length - 1];
		this.Min_x = x[0];
		this.Max_y = y[y.length - 1];
		this.Min_y = y[0];
	}

	@Override
	public void setXandY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * init gui
	 * 
	 */
	@Override
	public void initGUI() {

		StdDraw.setCanvasSize(800, 600);
		if (p != null) {
			set(p.grp);
			StdDraw.setXscale(Min_x, Max_x);
			StdDraw.setYscale(Min_y, Max_y);
			StdDraw.enableDoubleBuffering();
			paint();
		}
	}
	@Override
	public void StartManual(String gameNumber) {
		boolean flag = false;
		try {
			game_service game = Game_Server.getServer(Integer.parseInt(gameNumber));
			p = new play(game);
			int count = getrobs(game);
			p.locatefruit();
			for (int i = 0; i < count; i++) {
				game.addRobot(p.fru.get(i).getSrc());
			}
			int server = 0;
			p.movefrut(game);
			p.moverob(game);
			initGUI();
			game.startGame();
			while (game.isRunning()) {
				time = game.timeToEnd() / 1000;
				paint();
				double x1 = 0;
				double y1 = 0;
				double sum = 0;
				Point3D t = new Point3D(Point3D.ORIGIN);
				robot rb = new robot();
				if (!flag) {
					for (int i = 0; i < p.rob.size(); i++) {
						t = p.rob.get(i).getPos();
						x1 = t.x();
						y1 = t.y();
						sum = Math.sqrt((Math.pow((x - x1), 2)) + (Math.pow((y - y1), 2)));
						if (sum <= 0.0007) {
							rb = p.rob.get(i);
							x = 0;
							y = 0;
							// game.move();
							p.movefrut(game);
							p.moverob(game);
							flag = true;
							break;
						}
					}
				}

				else {
					x1 = 0;
					y1 = 0;
					sum = 0;

					for (int i = 0; i < p.grp.getV().size(); i++) {
						t = p.grp.getNode(i).getLocation();
						x1 = t.x();
						y1 = t.y();
						sum = Math.sqrt((Math.pow((x - x1), 2)) + (Math.pow((y - y1), 2)));
						if (sum <= 0.0005) {
							server = i;
							game.chooseNextEdge(rb.getId(), p.grp.getNode(i).getKey());
							x = 0;
							y = 0;
							flag = false;
							p.movefrut(game);
							p.moverob(game);
							break;
						}
					}
				}

				game.move();
				p.movefrut(game);
				p.moverob(game);
				score = showScore(game);
				paint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void StartAuto(String gameNumber) {
		try {
			game_service game = Game_Server.getServer(Integer.parseInt(gameNumber));
			p = new play(game);
			int count = getrobs(game);
			p.locatefruit();
			for (int i = 0; i < count; i++) {
				game.addRobot(p.fru.get(i).getSrc());
			}
			p.moverob(game);
			initGUI();
			Graph_Algo g = new Graph_Algo(p.grp);
			game.startGame();
			while (game.isRunning()) {
				time = game.timeToEnd() / 1000;
				List<node_data> l = new ArrayList<node_data>();
				for (int i = 0, j = 0; i < count && j < p.fru.size(); i++, j++) {

					System.out.println(p.rob.get(i).getSrc());
					System.out.println(p.fru.get(j).getdest());
					if (p.rob.get(i).getSrc() != p.fru.get(j).getdest()) {
						System.out.println("55555");
						l = g.shortestPath(p.rob.get(i).getSrc(), p.fru.get(j).getdest());
						if (l != null) {
							for (int k = l.size() - 2; k >= 0; k--) {
								System.out.print(l.get(k).getKey() + ", ");
								game.chooseNextEdge(i, l.get(k).getKey());
								p.locatefruit();
								p.moverob(game);
								paint();

							}
							game.move();
						} else {
							System.out.println("asd");
							break;
						}
					} else {
						game.chooseNextEdge(i, p.fru.get(j).getSrc());
					}
				}
				game.move();
				p.movefrut(game);
				p.moverob(game);
				score = showScore(game);
				p.locatefruit();
				paint();

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public String showScore(game_service game) throws JSONException {

		JSONObject m = new JSONObject(game.toString());
		try {

			JSONObject ro = m.getJSONObject("GameServer");
			int grade = ro.getInt("grade");
			int moves = ro.getInt("moves");
			score = "grade: " + grade + "moves :" + moves;
			return score;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	@Override
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

	public play getPlay() {
		return this.p;
	}

	/**
	 * paint the wanted graph that entered
	 * 
	 */
	@Override
	public void paint() {
		StdDraw.clear();
		node_data dest = null;
		DecimalFormat df = new DecimalFormat("#.##");
		if (this.p != null && this.p.grp != null) {
			// System.out.println("22");
			for (int i = 0; i < p.fru.size(); i++) {
				if (p.fru.get(i).type == 1) {
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.filledCircle(p.fru.get(i).pos.x(), p.fru.get(i).pos.y(), 0.0001);
				} else if (p.fru.get(i).type == -1) {
					StdDraw.setPenColor(Color.GREEN);
					StdDraw.filledCircle(p.fru.get(i).pos.x(), p.fru.get(i).pos.y(), 0.0001);
				}
			}

			for (int i = 0; i < p.rob.size(); i++) {
				double x = p.rob.get(i).pos.x();
				double y = p.rob.get(i).pos.y();
				StdDraw.setPenColor(Color.ORANGE);
				StdDraw.filledCircle(x, y, 0.0002);
			}

			for (node_data no : this.p.grp.getV()) {
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.filledCircle(no.getLocation().x(), no.getLocation().y(), 0.0001); // draw src point
				StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the oval
				StdDraw.text(no.getLocation().x() + 0.00001, no.getLocation().y() + 0.0002, "" + no.getKey()); // draw
				// the
				// num // of
				// src
				// point

				for (edge_data ed : this.p.grp.getE(no.getKey())) {
					dest = this.p.grp.getNode(ed.getDest());

					if (ed.getTag() == 200) {
						StdDraw.setPenColor(Color.black);
						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),
								dest.getLocation().y()); // draw edge
					} else if (ed.getTag() == 300) {

						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.setPenColor(Color.GRAY);
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),
								dest.getLocation().y()); // draw edge
					} else {
						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.setPenColor(Color.RED);
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),
								dest.getLocation().y()); // draw edge
					}
					StdDraw.setPenColor(Color.black);
					StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the string
					StdDraw.text((dest.getLocation().x() + no.getLocation().x()) / 2,
							((no.getLocation().y() + dest.getLocation().y()) / 2) + 0.00001,
							"" + df.format(ed.getWeight()));// draw weight of edge point
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 3)); // set the font of the oval
					StdDraw.filledCircle((no.getLocation().x() + dest.getLocation().x() * 8) / 9,
							(no.getLocation().y() + dest.getLocation().y() * 8) / 9, 0.00001); // draw enterance point

				}
			}
			StdDraw.setPenColor(Color.black);
			StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			StdDraw.text(this.Min_x + 0.00004, this.Min_y + 0.000001, "time: " + time);

			StdDraw.setPenColor(Color.black);
			StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			StdDraw.text(this.Min_x + 0.0023, this.Min_y + 0.0004, "score : " + score);
		} else {
			return;
		}
		StdDraw.show();
	}

	
}

package gameClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import utils.StdDraw;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;

public class graph_gui  {
	
	double Min_x = Integer.MAX_VALUE;
	double Max_x = Integer.MIN_VALUE;
	double Min_y = Integer.MAX_VALUE;
	double Max_y= Integer.MIN_VALUE;
	play p;
	
	public graph_gui()  {	
		initGUI();
		StdDraw.setGraph(this);
	}
	
	private void set(graph g) {
		 Min_x = Integer.MAX_VALUE;
		 Max_x = Integer.MIN_VALUE;
		 Min_y = Integer.MAX_VALUE;
		 Max_y= Integer.MIN_VALUE;
		double x [] = new double[g.getV().size()];
		double y [] = new double[g.getV().size()];
		int i =0;
		for(node_data n : g.getV()) {
			x[i]=g.getNode(n.getKey()).getLocation().x();
			y[i]=g.getNode(n.getKey()).getLocation().y();
			i++;
		}
		Arrays.sort(x);
		Arrays.sort(y);
		this.Max_x = x [x.length-1];
		this.Min_x= x [0];
		this.Max_y= y [y.length-1];
		this.Min_y= y [0];
	}
	/**
	 * init gui
	 * 
	 * */
	private void initGUI() {
		
		StdDraw.setCanvasSize(1200, 800);
		if(p != null) {
		set(p.grp);
		StdDraw.setXscale(Min_x, Max_x);
		StdDraw.setYscale(Min_y,Max_y);
		StdDraw.enableDoubleBuffering();
			paint();
		}
	}
	public void StartManual(String gameNumber)
	{
		try
		{
		game_service game = Game_Server.getServer(Integer.parseInt(gameNumber));
		int count = getrobs(game);
		for (int i = 0; i < count; i++) {
			game.addRobot(i);
		}
		p = new play(game);
		p.locateRobots();
		
		initGUI();
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int getrobs(game_service game) throws JSONException {
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

	public play getPlay()
	{
		return this.p;
	}
	
	
	/**
	 * paint the wanted graph that entered
	 * 
	 * */
	public void paint() {
		
		StdDraw.clear();
		node_data dest = null;
		DecimalFormat df = new DecimalFormat("#.##");
		if (this.p != null && this.p.grp != null) {
			
			
			
			for (int i = 0; i < p.fru.size(); i++) {
				if(p.fru.get(i).type == 1){
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.filledCircle(p.fru.get(i).pos.x(), p.fru.get(i).pos.y(),0.0001);
				}
				else if(p.fru.get(i).type == -1)
				{
					StdDraw.setPenColor(Color.GREEN);
					StdDraw.filledCircle(p.fru.get(i).pos.x(),p.fru.get(i).pos.y(),  0.0001);
				}
			}
			
			for (int i = 0; i < p.rob.size(); i++) {
				double x = p.grp.getNode(p.rob.get(i).getSrc()).getLocation().x();
				double y = p.grp.getNode(p.rob.get(i).getSrc()).getLocation().y();
				StdDraw.setPenColor(Color.ORANGE);
				StdDraw.filledCircle(x, y,0.0002);
			}
			
			
			
			for (node_data no : this.p.grp.getV()) {
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.filledCircle(no.getLocation().x(),no.getLocation().y(), 0.0001); // draw src point
				StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the oval
				StdDraw.text(no.getLocation().x()+0.00001, no.getLocation().y()+0.0002,"" + no.getKey()); // draw the num of src  point

				for (edge_data ed : this.p.grp.getE(no.getKey())) {
					dest = this.p.grp.getNode(ed.getDest());
										
					if (ed.getTag() == 200 )
					{
						StdDraw.setPenColor(Color.black);
						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),dest.getLocation().y()); // draw edge 	
					}
					else if (ed.getTag() == 300)
					{
						
						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.setPenColor(Color.GRAY);
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),dest.getLocation().y()); // draw edge 	
					}
					else
					{
						StdDraw.setFont((new Font("TimesRoman", Font.PLAIN, 3)));
						StdDraw.setPenColor(Color.RED);
						StdDraw.line(no.getLocation().x(), no.getLocation().y(), dest.getLocation().x(),dest.getLocation().y()); // draw edge 	
					}
					StdDraw.setPenColor(Color.black);
					StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the string
					StdDraw.text((dest.getLocation().x()+no.getLocation().x())/ 2, ((no.getLocation().y()+dest.getLocation().y())/ 2) +0.00001,"" +df.format(ed.getWeight()));// draw weight of edge point
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 3)); // set the font of the oval
					StdDraw.filledCircle((no.getLocation().x() + dest.getLocation().x()*8)/9,(no.getLocation().y()+dest.getLocation().y()*8)/9, 0.00001); // draw enterance point

				}
			}
		}
		else 
		{
			return;
		}
		StdDraw.show();
	}
}

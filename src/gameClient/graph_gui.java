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
import javax.swing.JOptionPane;

import org.json.JSONException;

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

public class graph_gui extends JFrame implements ActionListener, Serializable {
	/**
	 * fildes
	 * 
	 * */
	private static JFrame frame;
	double Min_x;
	double Max_x;
	double Min_y;
	double Max_y;
	play p;
/**
 * init empty graph
 * 
 * */

	public graph_gui() {
		initGUI();
	}
	/**
	 * init graph from graph
	 * @throws JSONException 
	 * 
	 * */
	public graph_gui(game_service game) throws JSONException {
		this.p= new play(game);
		
		set(p.grp);
		initGUI();
	}
	/**
	 * change apearance from jframes to stddraw
	 * 
	 * */
	private void set(graph g) {
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
		
		this.setSize(900, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MenuBar menuBar = new MenuBar();

		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		MenuItem item1 = new MenuItem("Paint_Graph");
		item1.addActionListener(this);
		menu.add(item1);

		MenuItem item2 = new MenuItem("Save");
		item2.addActionListener(this);
		menu.add(item2);

		MenuItem item3 = new MenuItem("Load");
		item3.addActionListener(this);
		menu.add(item3);

		MenuItem item4 = new MenuItem("Is Connected");
		item4.addActionListener(this);
		menu.add(item4);

		MenuItem item5 = new MenuItem("Shortest_Path_Dist");
		item5.addActionListener(this);
		menu.add(item5);

		MenuItem item6 = new MenuItem("Shortest_Path");
		item6.addActionListener(this);
		menu.add(item6);

		MenuItem item7 = new MenuItem("TSP");
		item7.addActionListener(this);
		menu.add(item7);
		
		
	}

	/**
	 * 
	 * @param data denote some data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return
	 */
	private double scale(double data, double r_min, double r_max, double t_min, double t_max)
	{
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}
	/**
	 * paint the wanted graph that entered
	 * 
	 * */
	public void paint(Graphics g) {

		super.paint(g);

		node_data dest = null;

		if (this.p.grp != null) {
			for (int i = 0; i < p.fru.size(); i++) {
				double x = scale(p.fru.get(i).pos.x(), Min_x, Max_x, 20, getWidth()-100);
				double y = scale(p.fru.get(i).pos.y(), Min_y, Max_y, 100, getHeight()-50);
				if(p.fru.get(i).type == 1){
				g.setColor(Color.PINK);
				g.fillOval((int)x, (int)y, 15, 15);
				}
				else
				{
					g.setColor(Color.GREEN);
					g.fillOval((int)x, (int)y, 15, 15);
				}
			}
			for (node_data no : this.p.grp.getV()) {
				double x = scale(no.getLocation().x(), Min_x, Max_x, 20, getWidth()-100);
				double y = scale(no.getLocation().y(), Min_y, Max_y, 100, getHeight()-50);
				g.setColor(Color.BLUE);
				g.fillOval((int)x-2, (int)y-5, 15, 15); // draw src point
				g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); // set the font of the oval
				g.drawString("" + no.getKey(), (int)x, (int)y -7); // draw the num of src  point

				for (edge_data ed : this.p.grp.getE(no.getKey())) {
					dest = this.p.grp.getNode(ed.getDest());
					
					double x1 = scale( dest.getLocation().x(), Min_x, Max_x,20, getWidth()-100);
					double y1 = scale( dest.getLocation().y(), Min_y, Max_y,100, getHeight()-50);
					
					if (ed.getTag() == 200 )
					{
						g.setColor(Color.black);
						g.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
						g.drawLine((int) x +2, (int) y+2, (int) x1+2,(int) y1+2); // draw edge 	
					}
					else if (ed.getTag() == 300)
					{
						g.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
						g.setColor(Color.GRAY);
						g.drawLine((int) x +2 , (int) y +2, (int) x1 +2 ,(int) y1+ 2); // draw edge 	
					}
					else
					{
						g.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
						g.setColor(Color.RED);
						g.drawLine((int) x, (int) y, (int) x1,(int) y1); // draw edge point	
					}
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); // set the font of the string
					g.drawString("" + ed.getWeight(), ((int)x1+(int)x )/ 2, // draw weight of edge point
							(((int)y+(int)y1 )/ 2) + 1);
					g.setColor(Color.YELLOW);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the oval
					g.fillOval(((int)x +(int)x1*8)/9,((int)y+(int)y1*8)/9, 7, 7); // draw enterance point

				}
			}
		}
		else 
		{
			return;
		}
	}
	/**
	 * getting orders from the gui
	 * 
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		String op = e.getActionCommand();

		if (op.equals("Save")) {
			Save();
		} else if (op.equals("Load")) {
			Load();
			repaint();
		}
		if (this.p.grp == null) {
			JFrame Shortest = new JFrame();
			JOptionPane.showMessageDialog(Shortest, "the graph is not initilized");
			return;
		}
		if (op.equals("Paint_Graph")) {
			isclear();
			Collection<node_data> m = this.p.grp.getV();
			Iterator<node_data> i = m.iterator();
			while (i.hasNext()) {
				i.next().setTag(0);
			}
			repaint();
		} else if (op.equals("Is Connected")) {
			isConnected();
			repaint();
		}  else if (op.equals("Shortest_Path_Dist")) {
			Shortest_Path_Dist();
			repaint();
		} else if (op.equals("Shortest_Path")) {
			Shortest_Path();
			repaint();
		} else if (op.equals("TSP")) {
			TSP();
			repaint();
		}
	}
	/**
	 * help function to actionPerformed (isConnected)
	 * 
	 * */
	private void isConnected() {
		Graph_Algo m = new Graph_Algo();
		m.init(this.p.grp);
		JFrame isC = new JFrame();
		JOptionPane.showMessageDialog(isC, "The graph is connected? :" + m.isConnected());
	}
	/**
	 * help function to actionPerformed (Save)
	 * 
	 * */
	private void Save() {
		Graph_Algo temp = new Graph_Algo();
		temp.init(this.p.grp);

		FileDialog chooser = new FileDialog(graph_gui.frame, "", FileDialog.SAVE);
		chooser.setVisible(true);
		String filename = chooser.getFile();
		if (filename != null) {
			temp.save(chooser.getDirectory() + filename + ".txt");
		}

	}
	/**
	 * help function to actionPerformed (Load)
	 * 
	 * */
	public void Load() { // init from fiile
		try {
			Graph_Algo temp = new Graph_Algo();
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			JFrame frame = new JFrame("Test");

			frame.setLayout(new FlowLayout());
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();

				temp.init(selectedFile.getPath());
				this.p.grp = (DGraph) temp.copy();
				repaint();
			}

			frame.pack();
		} catch (Exception e) {
			System.out.println("the file dosnt exist / couldnt read the file");
		}
	}
	/**
	 * help function to actionPerformed (Shortest_Path_Dist)
	 * 
	 * */
	private void Shortest_Path_Dist() {
		isclear();
		Graph_Algo m = new Graph_Algo();
		m.init(this.p.grp);

		JFrame Shortest = new JFrame();

		String x = JOptionPane.showInputDialog(Shortest, "Enter start point");
		String y = JOptionPane.showInputDialog(Shortest, "Enter end point");
		try {
			int ans = Integer.parseInt(x);
			int ans1 = Integer.parseInt(y);
			JOptionPane.showMessageDialog(Shortest, "" + m.shortestPathDist(ans, ans1));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Shortest, "the char is not aviablle");
		}
	}
	/**
	 * clear the graph
	 * 
	 * */
	private void isclear() {
		Collection<node_data> node = this.p.grp.getV();
		for (node_data node_data : node) {
			Collection<edge_data> ed = this.p.grp.getE(node_data.getKey());
			for (edge_data e : ed) {
				this.p.grp.getEdge(node_data.getKey(), e.getDest()).setTag(0);
			}
		}
	}
	/**
	 * help function to actionPerformed (Shortest_Path)
	 * 
	 * */
	private void Shortest_Path() {
		isclear();
		Graph_Algo m = new Graph_Algo();
		m.init(this.p.grp);
		JFrame Shortest = new JFrame();
		String x = JOptionPane.showInputDialog(Shortest, "Enter start point");
		String y = JOptionPane.showInputDialog(Shortest, "Enter end point");
		try {
			int ans = Integer.parseInt(x);
			int ans1 = Integer.parseInt(y);
			List<node_data> lis = m.shortestPath(ans, ans1);
			int len = lis.size()-1;
			for (int i = len; i >0 ; i--) {
				System.out.println(lis.get(i).getKey()+"->"+ lis.get(i-1).getKey());
				this.p.grp.getEdge(lis.get(i).getKey(), lis.get(i-1).getKey()).setTag(200);	
			}
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Shortest, "the char is not aviablle ");
		}
		repaint();
	}
	/**
	 * help function to actionPerformed (TSP)
	 * 
	 * */
	private void TSP() {
		isclear();
		Graph_Algo m = new Graph_Algo();
		m.init(this.p.grp);

		JFrame Shortest = new JFrame();
		String x = JOptionPane.showInputDialog(Shortest, "Enter points / --with spaces ");

		try {
			List<Integer> targets = new ArrayList<Integer>();
			String str[] = x.split(" ");
			for (int i = 0; i < str.length; i++) {
				targets.add(Integer.parseInt(str[i]));
			}
			List<node_data> lis = m.TSP(targets);
			if(lis==null) {
				JOptionPane.showMessageDialog(Shortest, "The graph is not connected");
			}
			else {
				for (int i = 0; i < lis.size()-1; i++) {
					edge_data ed = this.p.grp.getEdge(lis.get(i).getKey(), lis.get(i+1).getKey());
					this.p.grp.getEdge(ed.getSrc(), ed.getDest()).setTag(300);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Shortest, "the char is not aviablle ");
		}
		repaint();
	}
}

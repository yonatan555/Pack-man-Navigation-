package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

	private static JFrame frame;
	graph grp = null;

	public graph_gui() {
		initGUI();
	}

	public graph_gui(graph g) {
		this.grp = g;
		initGUI();
	}

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

		MenuItem item8 = new MenuItem("intialize a graph");
		item8.addActionListener(this);
		menu.add(item8);

	}

	public void paint(Graphics g) {

		super.paint(g);

		node_data dest = null;
		if (this.grp != null) {
			for (node_data no : this.grp.getV()) {
				g.setColor(Color.BLUE);
				g.fillOval(no.getLocation().ix(), no.getLocation().iy(), 20, 20); // draw src point
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); // set the font of the oval
				g.drawString("" + no.getKey(), no.getLocation().ix(), no.getLocation().iy() + 1); // draw the num of src
				// point

				for (edge_data ed : this.grp.getE(no.getKey())) {
					dest = this.grp.getNode(ed.getDest());
					if (grp.getNode(ed.getDest()).getTag() == 200 && grp.getNode(ed.getSrc()).getTag() == 200) {
						g.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
						g.setColor(Color.black);
						g.drawLine(no.getLocation().ix(), no.getLocation().iy(), dest.getLocation().ix(), // draw edge point	
								dest.getLocation().iy());
					} else if (grp.getNode(ed.getDest()).getTag() == 300 && grp.getNode(ed.getSrc()).getTag() == 300) {
						g.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
						g.setColor(Color.GRAY);
						g.drawLine(no.getLocation().ix(), no.getLocation().iy(), dest.getLocation().ix(), // draw edge point
								dest.getLocation().iy());
					} else {
						g.setColor(Color.RED);
						g.drawLine(no.getLocation().ix(), no.getLocation().iy(), dest.getLocation().ix(), // draw edge point
								dest.getLocation().iy());
					}
					g.setColor(Color.black);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); // set the font of the string
					g.drawString("" + ed.getWeight(), (no.getLocation().ix() + dest.getLocation().ix()) / 2, // draw weight of edge point

							((no.getLocation().iy() + dest.getLocation().iy()) / 2) + 1);
					g.setColor(Color.YELLOW);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); // set the font of the oval
					g.fillOval(dest.getLocation().ix() - 5, dest.getLocation().iy() - 3, 10, 10); // draw enterance point

				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String op = e.getActionCommand();

		if (op.equals("intialize a graph")) {
			intialize();
			repaint();
		} else if (op.equals("Save")) {
			Save();
		} else if (op.equals("Load")) {
			Load();
			repaint();
		}
		if (this.grp == null) {
			JFrame Shortest = new JFrame();
			JOptionPane.showMessageDialog(Shortest, "the graph is not initilized \nTo Initialize click menu then :intialize a graph");
			return;
		}

		if (op.equals("Paint_Graph")) {
			Collection<node_data> m = this.grp.getV();
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

	private void isConnected() {
		Graph_Algo m = new Graph_Algo();
		m.init(this.grp);
		JFrame isC = new JFrame();
		JOptionPane.showMessageDialog(isC, "The graph is connected? :" + m.isConnected());
	}

	private void Save() {
		Graph_Algo temp = new Graph_Algo();
		temp.init(grp);

		FileDialog chooser = new FileDialog(graph_gui.frame, "", FileDialog.SAVE);
		chooser.setVisible(true);
		String filename = chooser.getFile();
		if (filename != null) {
			temp.save(chooser.getDirectory() + filename + ".txt");
		}

	}

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
				grp = temp.copy();
				repaint();
			}

			frame.pack();
		} catch (Exception e) {
			System.out.println("the file dosnt exist / couldnt read the file");
		}
	}

	private void Shortest_Path_Dist() {
		Graph_Algo m = new Graph_Algo();
		m.init(this.grp);
		
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

	private void Shortest_Path() {
		Graph_Algo m = new Graph_Algo();
		m.init(this.grp);
		JFrame Shortest = new JFrame();
		String x = JOptionPane.showInputDialog(Shortest, "Enter start point");
		String y = JOptionPane.showInputDialog(Shortest, "Enter end point");
		try {

			int ans = Integer.parseInt(x);
			int ans1 = Integer.parseInt(y);
			List<node_data> lis = m.shortestPath(ans, ans1);
			for (int i = 0; i < lis.size(); i++) {
				lis.get(i).setTag(200);
			}
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Shortest, "the char is not aviablle ");
		}
		repaint();
	}

	private void TSP() {
		Graph_Algo m = new Graph_Algo();
		m.init(this.grp);

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
				for (int i = 0; i < lis.size(); i++) {
					lis.get(i).setTag(300);
				}
			}
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Shortest, "the char is not aviablle ");
		}
		repaint();
	}

	private void intialize() {
		if (this.grp != null) {
			JFrame Shortest = new JFrame();
			JOptionPane.showMessageDialog(Shortest, "The graph is allready exist");
			return;
		}
		graph grp = new DGraph();
		this.grp = grp; 

		Point3D m = new Point3D(250, 550, 4);
		Point3D m1 = new Point3D(800, 210, 5);
		Point3D m2 = new Point3D(250, 300, 6);
		Point3D m3 = new Point3D(560, 200, 7);
		Point3D m4 = new Point3D(340, 80, 7);
		Point3D m5 = new Point3D(260, 180, 7);

		NodeData a = new NodeData(1, m);
		NodeData b = new NodeData(2, m1);
		NodeData c = new NodeData(3, m2);
		NodeData d = new NodeData(4, m3);
		NodeData e = new NodeData(5, m4);
		NodeData f = new NodeData(6, m5);

		this.grp.addNode(a);
		this.grp.addNode(b);
		this.grp.addNode(c);
		this.grp.addNode(d);
		this.grp.addNode(e);
		this.grp.addNode(f);

		this.grp.connect(a.getKey(), b.getKey(), 2);
		this.grp.connect(c.getKey(), a.getKey(), 10);
		this.grp.connect(b.getKey(), c.getKey(), 6);
		this.grp.connect(c.getKey(), d.getKey(), 1);
		this.grp.connect(f.getKey(), e.getKey(), 5);
		this.grp.connect(e.getKey(), a.getKey(), 3);
		this.grp.connect(e.getKey(), f.getKey(), 3);
		this.grp.connect(b.getKey(), f.getKey(), 3);
		this.grp.connect(c.getKey(), b.getKey(), 3);
		this.grp.connect(f.getKey(), c.getKey(), 3);
		this.grp.connect(d.getKey(), e.getKey(), 3);

		repaint();
	}
}

package gui;

import javax.swing.JFrame;

import dataStructure.DGraph;
import dataStructure.NodeData;
import utils.Point3D;

public class Main {

	public static void main(String[] args) {
		DGraph temp = new DGraph();

		Point3D m = new Point3D(100, 550, 4);
		Point3D m1 = new Point3D(400, 210, 5);
		Point3D m2 = new Point3D(120, 300, 6);
		Point3D m3 = new Point3D(130,200, 7);
		Point3D m4 = new Point3D(340, 80, 7);
		Point3D m5 = new Point3D (60,180,7);

		NodeData a = new NodeData(1, m);
		NodeData b = new NodeData(2, m1);
		NodeData c = new NodeData(3, m2);
		NodeData d = new NodeData(4, m3);
		NodeData e = new NodeData(5, m4);
		NodeData f = new NodeData(6, m5);

		temp.addNode(a);
		temp.addNode(b);
		temp.addNode(c);
		temp.addNode(d);
		temp.addNode(e);
		temp.addNode(f);
		
		temp.connect(a.getKey(), b.getKey(), 2);
		temp.connect(c.getKey(), a.getKey(), 10);
		temp.connect(b.getKey(), c.getKey(), 6);
		temp.connect(c.getKey(), d.getKey(), 1);
		temp.connect(f.getKey(), e.getKey(), 5);
		temp.connect(e.getKey(), a.getKey(), 3);
		temp.connect(e.getKey(), f.getKey(), 3);
		temp.connect(b.getKey(), f.getKey(), 3);
		temp.connect(f.getKey(), c.getKey(), 3);
		temp.connect(d.getKey(), e.getKey(), 3);
	//	graph_gui window1 = new graph_gui();
		graph_gui window = new graph_gui(temp);
		
		window.setVisible(true);
	}

}

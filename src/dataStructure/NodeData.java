
package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class NodeData implements node_data,Serializable {
	
	int ID;
	double weight ;
	int tag;
	public String metadata ;
	public Point3D point;

	public NodeData() {
		this.ID = 0;
		this.weight = Double.MAX_VALUE;
		this.metadata = "no path";
		this.tag = 0;
		this.point = point.ORIGIN;
	}

	public NodeData(int id, Point3D point) {
		this.ID = id;
		this.weight = Double.MAX_VALUE;
		this.point = new Point3D(point);
	}

	public NodeData(int id, String metadata, int tag, Point3D point) {
		this.ID = id;
		this.weight = Double.MAX_VALUE;
		this.point = new Point3D(point);
		this.metadata = metadata;
		this.tag = tag;
	}

	@Override
	public int getKey() {
		return this.ID;
	}

	@Override
	public Point3D getLocation() {
		return point;
	}

	@Override
	public void setLocation(Point3D p) {
		this.point = new Point3D(p);
	}

	@Override
	public double getWeight() {

		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.metadata;
	}

	@Override
	public void setInfo(String s) {
		this.metadata = s;

	}

	@Override
	public int getTag() {
			return this.tag ;	
	}

	@Override
	public void setTag(int t) {
		
		this.tag = t ;

	}
	public node_data copy() {
		if(this == null) return null;
		NodeData n = new NodeData();
		
		n.ID = this.ID;
		n.tag = this.tag;
		n.weight = this.weight;
		n.metadata = new String(this.metadata);
		n.point = new Point3D(this.point);
		
		return n ; 
	}
}

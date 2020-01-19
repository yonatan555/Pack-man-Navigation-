package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import utils.Point3D;

public class DGraph implements graph, Serializable {

	public Hashtable<Integer, node_data> vertex;
	public Hashtable<node_data, Hashtable<Integer, edge_data>> edge;

	int MC = 0;
	int edgesNum = 0;

	public DGraph() {
		this.edge = new Hashtable<node_data, Hashtable<Integer, edge_data>>();
		this.vertex = new Hashtable<Integer, node_data>();
		MC++;
	}
	//get node from the user by key
	@Override
	public node_data getNode(int key) {
		MC++;
		return vertex.get(key);
	}
	//get edge from the user by src and dest
	@Override
	public edge_data getEdge(int src, int dest) {
		MC++;
		return edge.get(getNode(src)).get(dest);
	}
	// add node to graph by node
	@Override
	public void addNode(node_data n) {
		if (n == null)
			throw new RuntimeException("The node null ");
		MC++;
		vertex.put(n.getKey(), n);
		edge.put(n, new Hashtable<Integer, edge_data>());
	}
	//connect 2 nodes to edge
	@Override
	public void connect(int src, int dest, double w) {
		if (w < 0 || src == dest || this.getEdge(src, dest) != null)  
			throw new RuntimeException("The weight cant be Negative or somthing else");
		MC++;
		edgesNum++;
		edge.get(vertex.get(src)).put(dest, new EdgeData(src, dest, w));
	}
	// gte the whole vertexes of the grpah
	@Override
	public Collection<node_data> getV() {
		MC++;
		return vertex.values();
	}
	// gte the whole edges of the grpah by specific node
	@Override
	public Collection<edge_data> getE(int node_id) {
		MC++;
		node_data m = vertex.get(node_id);
		return edge.get(m).values();
	}
	//remove node from grpah
	@Override
	public node_data removeNode(int key) {
		if (this.getNode(key) != null) {
			MC++;
			Collection<node_data> v = getV(); // check if the node is exist and remove it
			Iterator<node_data> ite = v.iterator();
			while (ite.hasNext()) {
				node_data m = ite.next();
				if (edge.get(m).containsKey(key)) {
					edge.get(m).remove(key);
					edgesNum--;
				}
			}
			int counter = edge.get(getNode(key)).size();
			edge.remove(getNode(key));
			edgesNum -= counter;
			return vertex.remove(key);
		}
		throw new RuntimeException("The node is not exist");
	}
	//remove edge from grpah	
	@Override
	public edge_data removeEdge(int src, int dest) {
		MC++;
		edgesNum--;
		return edge.get(getNode(src)).remove(dest);
	}

	//return the size of nodes
	@Override
	public int nodeSize() {
		MC++;
		return this.vertex.size();
	}
	//return the size of edges
	@Override
	public int edgeSize() {
		MC++;
		return edgesNum;
	}
	//num of actions that executed at grpah
	@Override
	public int getMC() {
		return this.MC;
	}
	//copyobject
	public DGraph copy() {
		if (this == null)
			return null;

		DGraph m = new DGraph();
		m.MC = 0;

		Collection<node_data> node = this.getV();
		Iterator<node_data> it = node.iterator();
		Iterator<node_data> it2 = node.iterator();
		while (it2.hasNext()) {
			NodeData n = new NodeData();
			n = (NodeData) it2.next();
			m.addNode(new NodeData(n.getKey(), n.metadata, n.getTag(),
					new Point3D(n.point.x(), n.point.y(), n.point.z())));
		}
		while (it.hasNext()) {
			NodeData n = new NodeData();
			n = (NodeData) it.next();
			Collection<edge_data> edge = this.getE(n.getKey());
			Iterator<edge_data> ite = edge.iterator();
			while (ite.hasNext()) {
				EdgeData ed = new EdgeData();
				ed = (EdgeData) ite.next();
				m.connect(n.getKey(), ed.getDest(), ed.getWeight());
			}
		}
		return m;
	}
//init a grpah from the server
	public void init(String g) throws JSONException {
		
		
		JSONObject m = new JSONObject(g);
		
		try {
			JSONArray node = m.getJSONArray("Nodes");
			JSONArray edge = m.getJSONArray("Edges");
			
			
			for (int i = 0; i < node.length(); i++) {
				JSONObject n = node.getJSONObject(i);
				
				NodeData k = new NodeData(n.getInt("id"),new Point3D(n.getString("pos")));
				this.addNode(k);
			}
			for (int i = 0; i < edge.length(); i++) {
				JSONObject ed = edge.getJSONObject(i);
				EdgeData e = new EdgeData(ed.getInt("src"), ed.getInt("dest"), ed.getDouble("w"));
				connect(e.getSrc(), e.getDest(), e.getWeight());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
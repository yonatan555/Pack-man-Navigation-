package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONObject;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import de.micromata.opengis.kml.v_2_2_0.Data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.NetworkLink;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;
import utils.Point3D;

public class KML_Logger {
	game_service game;
	graph grp;
	Kml km;
	Document file;
	
	
	//setter
	public KML_Logger(graph g) {
		this.grp = g;
	}
	//setter 
	public void setGame(game_service game) {
		this.game = game;
	}
	//build the graph from a game to Kml file
	public void BuildGraph() {
		km = new Kml();
		file = km.createAndSetDocument().withName("KML").withOpen(true); // crate a file withname kml
		Folder folder = file.createAndAddFolder(); // crate a folder
		folder.withName("Folder").withOpen(true);

		Icon icon = new Icon().withHref("http://maps.google.com/mapfiles/kml/paddle/ylw-blank.png");// take an icon
		Style placeMarkStyle = file.createAndAddStyle();
		placeMarkStyle.withId("placemarkid").createAndSetIconStyle().withIcon(icon).withScale(1.2)
				.withColor("ffff0000"); // give params to the icon

		Collection<node_data> nd = grp.getV();
		for (node_data node_data : nd) {
			// createPlacemarkWithChart(doc, folder, node_data.getLocation().x(),
			// node_data.getLocation().y(), node_data.getKey()+"", 20);
			Placemark p = file.createAndAddPlacemark(); // create a placemark and put it
			p.withName(node_data.getKey() + "");
			p.withStyleUrl("#placemarkid");
			p.createAndSetPoint().addToCoordinates(node_data.getLocation().x(), node_data.getLocation().y());// add to
																												// google
																												// earth
																												// an
																												// index

			Style redStyle = file.createAndAddStyle();
			redStyle.withId("redstyle").createAndSetLineStyle().withColor("ff0000ff").setWidth(3.0);
			;
			Collection<edge_data> ed = grp.getE(node_data.getKey());
			for (edge_data edgess : ed) {
				Placemark p2 = file.createAndAddPlacemark();
				p2.withStyleUrl("#redstyle");

				Point3D loc = grp.getNode(edgess.getSrc()).getLocation();
				Point3D locNext = grp.getNode(edgess.getDest()).getLocation();

				p2.createAndSetLineString().withTessellate(true).addToCoordinates(loc.x(), loc.y())
						.addToCoordinates(locNext.x(), locNext.y()); // draw a line between 2 points
			}
		}

	}
	//sace to file kml
	public void save(String fileName) {
		try {
			km.marshal(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//init frutis to a kml file
	public void setFruits(String time, String end) {
		Icon iconGreen = new Icon().withHref("http://maps.google.com/mapfiles/kml/shapes/snowflake_simple.png");
		Style greenStyle = file.createAndAddStyle();
		greenStyle.withId("snow").createAndSetIconStyle().withIcon(iconGreen).withScale(1.2);
		Icon iconYellow = new Icon().withHref("http://maps.google.com/mapfiles/kml/shapes/sunny.png");
		Style yelloStyle = file.createAndAddStyle();
		yelloStyle.withId("sun").createAndSetIconStyle().withIcon(iconYellow).withScale(1.2);
		List<String> frus = game.getFruits();
		for (String json : frus) {
			try {
				JSONObject obj = new JSONObject(json);
				JSONObject CurrFruit = (JSONObject) obj.get("Fruit");
				String pos = CurrFruit.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				Point3D p = new Point3D(x, y, z);
				int type = CurrFruit.getInt("type");

				Placemark fr = file.createAndAddPlacemark();
				if (type == -1) {
					fr.setStyleUrl("#snow");
				} else {
					fr.setStyleUrl("#sun");
				}
				fr.createAndSetPoint().addToCoordinates(x, y);
				fr.createAndSetTimeSpan().withBegin(time).withEnd(end);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//init robot to kml file
	public void setBots(String time, String end) {
		Icon BusIcon = new Icon().withHref("http://maps.google.com/mapfiles/kml/shapes/man.png");
		Style busStyle = file.createAndAddStyle();
		busStyle.withId("man").createAndSetIconStyle().withIcon(BusIcon).withScale(1.2);
		List<String> robos = game.getRobots();
		for (String string : robos) {
			try {
				JSONObject obj = new JSONObject(string);
				JSONObject CurrBot = (JSONObject) obj.get("Robot");
				String pos = CurrBot.getString("pos");
				String[] arr = pos.split(",");
				double x = Double.parseDouble(arr[0]);
				double y = Double.parseDouble(arr[1]);
				double z = Double.parseDouble(arr[2]);
				Point3D posP = new Point3D(x, y, z);
				int id = CurrBot.getInt("id");
				Placemark bot = file.createAndAddPlacemark();
				bot.setStyleUrl("#man");
				bot.createAndSetPoint().addToCoordinates(x, y);// put a robot at specific
				bot.createAndSetTimeSpan().withBegin(time).withEnd(end);// man is running
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

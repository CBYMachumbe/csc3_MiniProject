package ui;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GraphPane extends StackPane
{
	private Graph<Station> Eskom;
	
	private ArrayList<Vertex<Station>> stations;
	private ArrayList<Edge<Station>> connections;
	ListView<String> lstStation;
	private int vCount = 1;
	private int eCount = 0;
	
	
	public GraphPane()
	{
		Eskom = new Graph<Station>();
		stations = new ArrayList<Graph.Vertex<Station>>();
		connections = new ArrayList<Graph.Edge<Station>>();
		
		Vertex<Station> s1 = new Vertex<Station>(new Station("Mabopane", true, false));
		Vertex<Station> s2 = new Vertex<Station>(new Station("Hex", true, false));
		Vertex<Station> s3 = new Vertex<Station>(new Station("Mdansene", true, true));
		Vertex<Station> s4 = new Vertex<Station>(new Station("Blur", false, false));
		
		/*Edge<String> e1 = new Edge<String>(20, s1, s2);
		Edge<String> e2 = new Edge<String>(3, s1, s3);
		Edge<String> e3 = new Edge<String>(70, s1, s4);
		
		connections.add(e1);
		connections.add(e2);
		connections.add(e3);*/
		stations.add(s1);
		stations.add(s2);
		stations.add(s3);
		stations.add(s4);
		
		
		MenuBar menubar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem openFile = new MenuItem("Open File");
		
		menuFile.getItems().add(openFile);
		menubar.getMenus().addAll(menuFile);
		
		Accordion accordion = new Accordion();
		lstStation = new ListView<String>();
		
		//Start: Adding of a Vertex (ui)
		lstStation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lstStation.setItems((ObservableList<String>) makeStationList());
		
		
		Label lblVID = new Label("Station Name:");
		TextField txtVID = new TextField();
		
		Label lblType = new Label("Is this outlet a station(Yes/No):");
		TextField txtType = new TextField();
		
		Label lblIsOn = new Label("Does this outlet work (Yes/No):");
		TextField txtIsOn = new TextField();
		
		HBox typeBox = new HBox(14);
		typeBox.getChildren().addAll(lblType,txtType);
		
		HBox onBox = new HBox(14);
		onBox.getChildren().addAll(lblIsOn,txtIsOn);
		
		Button btnSubmit = new Button("Add Outlet");
		
		HBox addID = new HBox(100);
		addID.getChildren().addAll(lblVID,txtVID);
		
		VBox Box = new VBox(10);
		Box.getChildren().addAll(addID,typeBox,onBox,btnSubmit);
		
		HBox vbox = new HBox(30);
		vbox.getChildren().addAll(Box,lstStation);
		
		Button btnOutlet = new Button("Remove Outlet");
		Button btnEditVertex = new Button("Adjust Outlet");
		
		HBox btnVEditBox = new HBox(80);
		btnVEditBox.getChildren().addAll(btnEditVertex,btnOutlet);
		
		VBox vListBox = new VBox(10);
		vListBox.getChildren().addAll(lstStation,btnVEditBox);
		
		
		HBox VertextBox = new HBox(10);
		VertextBox.getChildren().addAll(vbox,vListBox);
		
		
		
		TitledPane addVertex = new TitledPane("Outlet Management (Vertex)",VertextBox);
		//End: Adding of a Vertex
		
		//Start: Outlet Management Actions
		btnSubmit.setOnAction(e -> 
		{
			boolean isOn = makeBool(txtIsOn.getText(), "Is the outlet working?");
			boolean isStation = makeBool(txtType.getText(), "Is this vertex a station?");
			addVertex(new Vertex<Station>(new Station(txtVID.getText(),isOn,isStation)));
			System.out.println(Eskom);
			
			addVertex.setContent(VertextBox);
		});
		
		//Used to removed an outlet
		btnOutlet.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstStation.getSelectionModel().getSelectedItems();
			
			StringTokenizer removeToken = null;
			
			for(String rm : toRemove)
			{
				removeToken = new StringTokenizer(rm,",");
				removeVertex(removeToken.nextToken());
				lstStation.getItems().remove(rm);
			}
			
		});
		
		btnEditVertex.setOnAction(e -> 
		{
			ObservableList<String>  toAdjust = lstStation.getSelectionModel().getSelectedItems();
			
			StringTokenizer adjustToken = null;

			for(String ad : toAdjust)
			{
				lstStation.getItems().remove(ad);
				adjustToken = new StringTokenizer(ad,",");
				editVertex(adjustToken.nextToken());
				
			}
		});
		
		//End: Add Stations Actions
		
		
		//Start: Adding of a edge (ui)
		
		//Add side
		ListView<String> lstEdge = new ListView<String>();
		lstEdge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		if(vCount != 0)
			for(Vertex<Station> st : stations)
				lstEdge.getItems().add(st.getValue().toString());
		
		Label lblList = new Label("Select Two Outlets");
		VBox lstBox = new VBox(10);
		Label lblCost = new Label("Distance Between Them:");
		TextField txtCost = new TextField();
		HBox costBox = new HBox(20);
		costBox.getChildren().addAll(lblCost,txtCost);
		Button btnSubmitEdge = new Button("Add Distance Between Outlets");
		
		lstBox.getChildren().addAll(lblList,lstEdge,costBox,btnSubmitEdge);
		
		//Remove side
		ListView<String> lstEdges = new ListView<String>();
		lstEdges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		if(eCount != 0)
			for(Edge<Station> e : connections)
				lstEdges.getItems().add(e.toString());
		
		Label lblEdgelst = new Label("Select Edge(s):");
		
		VBox ebox = new VBox();
		ebox.getChildren().addAll(lblEdgelst,lstEdges);
		
		Button btnRemoveEdge = new Button("Remove Connection");
		Button btnEditEdge = new Button("Adjust Connection");
		
		HBox btnEditBox = new HBox(10);
		btnEditBox.getChildren().addAll(btnEditEdge,btnRemoveEdge);
		
		VBox removeBox = new VBox(10);
		removeBox.getChildren().addAll(ebox,btnEditBox);
		
		
		HBox edgeBox = new HBox(30);
		edgeBox.getChildren().addAll(lstBox,removeBox);
				
		TitledPane addEdge = new TitledPane("Connections Management (Edges)",edgeBox);
		// End: Adding of a edge
		
		//Start: Actions for Edge management
		
		btnSubmitEdge.setOnAction(e -> 
		{
			ObservableList<String> toAdd = lstEdge.getSelectionModel().getSelectedItems();		
			
			StringTokenizer st1Tokens = new StringTokenizer(toAdd.get(0),",");
			StringTokenizer st2Tokens = new StringTokenizer(toAdd.get(1),",");
			
			Vertex<Station> st1 = getStation(st1Tokens.nextToken());
			Vertex<Station> st2 = getStation(st2Tokens.nextToken());
			int distance = Integer.parseInt(txtCost.getText());
			
			Edge<Station> edge = new Edge<Station>(distance,st1,st2);
			addEdge(st1, st2, distance);
			
			lstEdges.getItems().add(edge.toString());
		});
		
		btnRemoveEdge.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstEdges.getSelectionModel().getSelectedItems();
			
			for(String rm : toRemove)
			{
				removeEdge(rm);
				lstEdges.getItems().remove(rm);
			}
		});
		
		btnEditEdge.setOnAction(e ->
		{
			ObservableList<String> toAdjust = lstEdges.getSelectionModel().getSelectedItems();

			for(String ad : toAdjust)
			{
				lstEdges.getItems().remove(ad);
				Edge<Station> edge = editEdge(ad);
				lstEdges.getItems().add(edge.toString());
				
			}
		});
		
		//End: Actions for Edge management
		
		//Start: Finding Paths (ui)
		
		Label lblSt1fp = new Label("Station 1 Name:");
		TextField txtSt1fp = new TextField();
		HBox st1Boxfp = new HBox(44);
		
		st1Boxfp.getChildren().addAll(lblSt1fp,txtSt1fp);
		
		Label lblSt2fp = new Label("Station 2 Name:");
		TextField txtSt2fp = new TextField();
		HBox st2Boxfp = new HBox(10);
		
		st2Boxfp.getChildren().addAll(lblSt2fp,txtSt2fp);
		
		HBox stBoxfp = new HBox(30);
		stBoxfp.getChildren().addAll(st1Boxfp,st2Boxfp);
		
		Button btnFindPath = new Button("Find Path");
		
		Label lblPaths = new Label("Paths Available:"); 
		TextArea txtPaths = new TextArea();
		VBox pathABox = new VBox();
		pathABox.getChildren().addAll(lblPaths,txtPaths);
		
		Button btnGetShortPath = new Button("Get Shortest Path");
		Label lblShortPath = new Label("Shortest Path:");
		TextField txtShortPath = new TextField();
		
		HBox shortPathBox = new HBox(44);
		shortPathBox.getChildren().addAll(lblShortPath,txtShortPath);
		
		VBox resPathBox = new VBox(10);
		resPathBox.getChildren().addAll(pathABox,btnGetShortPath,shortPathBox);
		
		//resPathBox.setVisible(false);
		
		VBox pathBox = new VBox(10);
		pathBox.getChildren().addAll(stBoxfp,btnFindPath);
		
		btnFindPath.setOnAction(e ->
		{
			pathBox.getChildren().add(resPathBox);
			//resPathBox.setVisible(true);
		});
		
		TitledPane FindPaths = new TitledPane("Find Paths",pathBox);
		//End: Finding Paths
		
		//Start: Display Graph
		graphCanvas canvas = new graphCanvas();
		TitledPane Display = new TitledPane("Display Graph",canvas);
		
		accordion.getPanes().addAll(addVertex,addEdge,FindPaths,Display);
		VBox controlBox = new VBox();
		controlBox.getChildren().addAll(menubar,accordion);
		this.setWidth(200);
		this.setHeight(200);
		this.getChildren().addAll(controlBox);
		
	}
	
	public void addVertex(Vertex<Station> st1)
	{
		Vertex<Station> vertex = new Vertex<Station>(st1);
		
		if(stations.equals(null))
		{
			//Create new instance & use
			stations = new ArrayList<Graph.Vertex<Station>>();
		}
		
		stations.add(vertex);
		Eskom = null;
		Eskom = new Graph<Station>(stations,connections);
		
		if(vCount == 0)
			lstStation.getItems().clear();
		
		lstStation.getItems().add(vertex.getValue().toString());
		
		vCount++;
	}
	
	public void removeVertex(String stationName)
	{
		Vertex<Station> toRemove = null;
		for(Vertex<Station> st : stations)
		{
			if(st.getValue().getName().equals(stationName));
				toRemove = st;
		}
		
		stations.remove(toRemove);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		vCount--;
	}
	
	public void editVertex(String name)
	{
		TextInputDialog tdName = new TextInputDialog();
		tdName.setHeaderText("Enter name of outlet");
		
		TextInputDialog tdStation = new TextInputDialog();
		tdStation.setHeaderText("Is this outlet a station (yes/no)");
		
		TextInputDialog tdWorking = new TextInputDialog();
		tdWorking.setHeaderText("Is this outlet working (yes/no)");
		
		tdName.showAndWait();
		String stName = tdName.getEditor().getText();
		
		tdStation.showAndWait();
		boolean isStaion = makeBool(tdStation.getEditor().getText(),"Is this outlet a station (yes/no)");
		
		tdWorking.showAndWait();
		boolean isOn = makeBool(tdWorking.getEditor().getText(),"Is this outlet working (yes/no)");
		
		int index = stations.indexOf(getStation(name));
		Vertex<Station> update = new Vertex<Station>(new Station(stName,isOn,isStaion));
		
		stations.set(index, update);
		
		System.out.println(stations);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		lstStation.getItems().add(update.getValue().toString());
	}
	
	public Vertex<Station> getStation(String name)
	{
		for(Vertex<Station> st : stations)
		{
			if(st.getValue().getName().equals(name))
				return st;
				
		}
		
		return null;
	}
	
	public void addEdge(Vertex<Station> st1,Vertex<Station> st2, int distance)
	{
		Vertex<Station> sta1 = null;
		Vertex<Station> sta2 = null;
		
		if(isInGraph(st1.getValue().getName()) && isInGraph(st2.getValue().getName()))
		{
			sta1 = getStation(st1.getValue().getName());
			sta2 = getStation(st2.getValue().getName());
		}
		else if(isInGraph(st1.getValue().getName()) && !isInGraph(st2.getValue().getName()))
		{
			sta1 = getStation(st1.getValue().getName());
			sta2 = new Vertex<Station>(st2);
		}
		else if(!isInGraph(st1.getValue().getName()) && isInGraph(st2.getValue().getName()))
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = getStation(st2.getValue().getName());
		}
		else
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = new Vertex<Station>(st2);
		}
		
		Edge<Station> edge = new Edge<Station>(distance, sta1,sta2);
		
		if(connections.equals(null))
		{
			//create new edge instance & use
			connections = new ArrayList<Graph.Edge<Station>>();
		}
		
		connections.add(edge);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		eCount ++;
	}
	
	public void removeEdge(String signature)
	{
		Edge<Station> edge = null;

		if(eCount != 0)
		{
			for(Edge<Station> e : connections)
			{
				if(e.toString().equals(signature))
					edge = e;
			}
		}
		
		connections.remove(edge);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		eCount--;
	}
	
	public Edge<Station> editEdge(String signature)
	{
		
		Edge<Station> edge = null;
		if(eCount != 0)
		{
			for(Edge<Station> e : connections)
			{
				if(e.toString().equals(signature))
					edge = e;
			}
		}
		
		TextInputDialog tdDistance = new TextInputDialog();
		tdDistance.setHeaderText("What is the distance between these outlets");
		tdDistance.showAndWait();
		String strDistance = tdDistance.getEditor().getText();
		int distance = Integer.parseInt(strDistance);
		
		Edge<Station> newConnection = new Edge<Station>(distance, edge.getFromVertex(), edge.getToVertex());
		
		int index = connections.indexOf(edge);
		connections.set(index, newConnection);
		
		System.out.println(connections);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		return newConnection;
	}
	
 	private ObservableList<String> makeStationList()
	{
		ObservableList<String> list = FXCollections.observableArrayList();;
		
		if(vCount != 0)
			for(Vertex<Station> st : stations)
				list.add(st.getValue().toString());
		else
			list.add("No Stations Exist");
		
		return list;
	}
 	
	
	public ArrayList<String> makeEdgeList()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		for(Edge<Station> con : connections)
			list.add(con.toString());
		
		return list;
	}
	
	private boolean makeBool(String verdict, String type) 
	{
		boolean ans = false;
		
		TextInputDialog ipb = new TextInputDialog();
		ipb.setHeaderText(type);
		switch(verdict.toUpperCase())
		{
		case "YES":
			ans = true;
			break;
		case "NO":
			ans = false;
			break;
		default:
			ipb.showAndWait();
			makeBool(ipb.getEditor().getText(),type);
		}
		
		return ans;
	}
	
	private boolean isInGraph(String name)
	{
		for(Vertex<Station> st : stations)
		{
			if(st.getValue().getName().equals(name))
				return true;
				
		}
		
		return false;
	}
}

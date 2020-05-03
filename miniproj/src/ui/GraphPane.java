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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.*;

public class GraphPane extends StackPane
{
	private Graph<Station> Eskom;
	private ArrayList<Vertex<Station>> stations;
	private ArrayList<Edge<Station>> connections;
	
	//Lists used from selecting and viewing nodes in the graph
	private ListView<String> lstStation;
	private ListView<String> lstEdge;
	private ListView<String> lstEdges;
	private ListView<String> lstPaths;
	
	
	public GraphPane()
	{
		Eskom = new Graph<Station>();
		/*stations = new ArrayList<Graph.Vertex<Station>>();
		connections = new ArrayList<Graph.Edge<Station>>();
		
		Vertex<Station> s1 = new Vertex<Station>(new Station("Mabopane", true, false));
		Vertex<Station> s2 = new Vertex<Station>(new Station("Hex", true, false));
		Vertex<Station> s3 = new Vertex<Station>(new Station("Mdansene", true, true));
		Vertex<Station> s4 = new Vertex<Station>(new Station("Blur", false, false));
		Vertex<Station> s5 = new Vertex<Station>(new Station("Alex", true, true));
		Vertex<Station> s6 = new Vertex<Station>(new Station("Gomora", false, false));
		Vertex<Station> s7 = new Vertex<Station>(new Station("Lonely", false, false));
		
		Edge<Station> e1 = new Edge<Station>(20, s1, s2);
		Edge<Station> e2 = new Edge<Station>(30, s2, s3);
		Edge<Station> e3 = new Edge<Station>(20, s3, s4);
		Edge<Station> e4 = new Edge<Station>(10, s4, s5);
		Edge<Station> e5 = new Edge<Station>(70, s5, s6);
		Edge<Station> e6 = new Edge<Station>(40, s2, s4);
		
		s1.addEdge(e1);
		s2.addEdge(e2);
		s2.addEdge(e6);
		s3.addEdge(e3);
		s3.addEdge(e2);
		s4.addEdge(e4);
		s4.addEdge(e3);
		s5.addEdge(e5);
		s5.addEdge(e4);
		s6.addEdge(e5);
		
		connections.add(e1);
		connections.add(e2);
		connections.add(e3);
		connections.add(e4);
		connections.add(e5);
		connections.add(e6);

		stations.add(s1);
		stations.add(s2);
		stations.add(s3);
		stations.add(s4);
		stations.add(s5);
		stations.add(s7);
		stations.add(s6);
		
		Eskom = new Graph<Station>(stations,connections);*/
		
		MenuBar menubar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem openFile = new MenuItem("Open File");
		
		menuFile.getItems().add(openFile);
		menubar.getMenus().addAll(menuFile);
		
		Accordion accordion = new Accordion();
		initLists();
		
		//Start: Adding of a Vertex (ui)
		
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
			if(Eskom.getVertices().size() == 0)
				lstStation.getItems().clear();
			
			boolean isOn = GraphOps.makeBool(txtIsOn.getText(), "Is the outlet working?");
			boolean isStation = GraphOps.makeBool(txtType.getText(), "Is this vertex a station?");
			Vertex<Station> st1 = new Vertex<Station>(new Station(txtVID.getText(),isOn,isStation));
			GraphOps.addVertex(Eskom,st1);
			lstStation.getItems().add(st1.getValue().toString());
			
			//addVertex.setContent(VertextBox);
		});
		
		//Used to removed an outlet
		btnOutlet.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstStation.getSelectionModel().getSelectedItems();
			
			StringTokenizer removeToken = null;
			
			for(String rm : toRemove)
			{
				removeToken = new StringTokenizer(rm,",");
				GraphOps.removeVertex(Eskom, removeToken.nextToken());
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
				Vertex<Station> update = GraphOps.editVertex(Eskom, adjustToken.nextToken());
				lstStation.getItems().add(update.getValue().toString());
			}
		});
		
		//End: Add Stations Actions
		
		
		//Start: Adding of a edge (ui)
		
		//Add side
		
		
		
		if(Eskom.getVertices().size() != 0)
			for(Vertex<Station> st : Eskom.getVertices())
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
		
		
		if(Eskom.getEdges().size() != 0)
			for(Edge<Station> e : Eskom.getEdges())
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
			
			Vertex<Station> st1 = GraphOps.getStation(Eskom,st1Tokens.nextToken());
			Vertex<Station> st2 = GraphOps.getStation(Eskom,st2Tokens.nextToken());
			int distance = Integer.parseInt(txtCost.getText());
			
			Edge<Station> edge = new Edge<Station>(distance,st1,st2);
			GraphOps.addEdge(Eskom, st1, st2, distance);
			
			lstEdges.getItems().add(edge.toString());
		});
		
		btnRemoveEdge.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstEdges.getSelectionModel().getSelectedItems();
			
			for(String rm : toRemove)
			{
				GraphOps.removeEdge(Eskom,rm);
				lstEdges.getItems().remove(rm);
			}
		});
		
		btnEditEdge.setOnAction(e ->
		{
			ObservableList<String> toAdjust = lstEdges.getSelectionModel().getSelectedItems();

			for(String ad : toAdjust)
			{
				lstEdges.getItems().remove(ad);
				Edge<Station> edge = GraphOps.editEdge(Eskom,ad);
				lstEdges.getItems().add(edge.toString());
				
			}
		});
		
		//End: Actions for Edge management
		
		//Start: Finding Paths (ui)
		
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
		
		VBox pathBox = new VBox(10);
		pathBox.getChildren().addAll(lstPaths,btnFindPath);
		pathBox.getChildren().add(resPathBox);
		
		btnFindPath.setOnAction(e ->
		{
			ObservableList<String> path = lstPaths.getSelectionModel().getSelectedItems();
			
			if(path.size() == 2)
			{
				StringTokenizer st1Tokens = new StringTokenizer(path.get(0),",");
				StringTokenizer st2Tokens = new StringTokenizer(path.get(1),",");
				
				Vertex<Station> st1 = GraphOps.getStation(Eskom,st1Tokens.nextToken());
				Vertex<Station> st2 = GraphOps.getStation(Eskom,st2Tokens.nextToken());

				ArrayList<Vertex<Station>> visited = new ArrayList<>();
				GraphOps.DFS(Eskom,visited, st1);
				
				if(visited.contains(st2))
					txtPaths.setText(visited.toString());
				else
					txtPaths.setText("No Path Found!");
			}
			
		});
		
		
		TitledPane FindPaths = new TitledPane("Find Paths",pathBox);
		//End: Finding Paths
		
		accordion.getPanes().addAll(addVertex,addEdge,FindPaths);
		VBox controlBox = new VBox();
		controlBox.getChildren().addAll(menubar,accordion);
		this.setWidth(200);
		this.setHeight(200);
		this.getChildren().addAll(controlBox);
		
	}

	private void initLists()
	{
		lstStation = new ListView<String>();
		lstStation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lstStation.setItems((ObservableList<String>) makeStationList());
		
		lstEdge = new ListView<String>();
		lstEdge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstEdges = new ListView<String>();
		lstEdges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstPaths = new ListView<String>();
		lstPaths.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstEdge.itemsProperty().bind(lstStation.itemsProperty());
		lstPaths.itemsProperty().bind(lstStation.itemsProperty());
	}
	
 	private ObservableList<String> makeStationList()
	{
		ObservableList<String> list = FXCollections.observableArrayList();;
		
		if(Eskom.getVertices().size() != 0)
			for(Vertex<Station> st : Eskom.getVertices())
				list.add(st.getValue().toString());
		else
			list.add("No Stations Exist");
		
		return list;
	}

}

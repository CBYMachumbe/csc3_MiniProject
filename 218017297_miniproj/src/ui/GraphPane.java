package ui;

import java.util.ArrayList;
import java.util.StringTokenizer;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.CostPathPair;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.TYPE;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	private Graph<Outlet> Eskom;
	
	//Lists used from selecting and viewing nodes in the graph
	private ListView<String> lstStation;
	private ListView<String> lstEdge;
	private ListView<String> lstEdges;
	private ListView<String> lstPaths;
	
	
	public GraphPane()
	{
		GraphCanvas canvas = new GraphCanvas();
		
		initGraph();
		Accordion accordion = new Accordion();
		initLists();
		
		//Start: Adding of a Vertex (ui)
		Button btnSubmit = new Button("Add Station");
		Button btnAddHome = new Button("Add Home");
		
		Button btnOutlet = new Button("Remove Outlet");
		Button btnEditVertex = new Button("Adjust Outlet");
		VBox Box = new VBox(10);
		Box.getChildren().addAll(btnSubmit,btnAddHome,btnEditVertex,btnOutlet);
		
		HBox VertextBox = new HBox(100);
		VertextBox.getChildren().addAll(Box,lstStation);
		
		TitledPane addVertex = new TitledPane("Outlet Management (Vertex)",VertextBox);
		//End: Adding of a Vertex
		
		//Start: Outlet Management Actions
		btnSubmit.setOnAction(e -> 
		{
			if(Eskom.getVertices().size() == 0)
				lstStation.getItems().clear();
			
			//Making a station vertex
			Vertex<Outlet> st1 = GraphOps.makeOutlet("Station");
			GraphOps.addVertex(Eskom,st1);
			lstStation.getItems().add(st1.getValue().toString());
			canvas.drawGraph(Eskom);
		});
		
		btnAddHome.setOnAction(e -> 
		{
			if(Eskom.getVertices().size() == 0)
				lstStation.getItems().clear();
			
			//Making a Home vertex
			Vertex<Outlet> st1 = GraphOps.makeOutlet("House");
			GraphOps.addVertex(Eskom,st1);
			lstStation.getItems().add(st1.getValue().toString());
			canvas.drawGraph(Eskom);
		});
		
		//Used to removed an outlet
		btnOutlet.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstStation.getSelectionModel().getSelectedItems();
			
			StringTokenizer removeToken = null;
			
			for(String rm : toRemove)
			{
				removeToken = new StringTokenizer(rm,", :");
				removeToken.nextToken();
				String name =  removeToken.nextToken();
				GraphOps.removeVertex(Eskom,name);
				lstStation.getItems().remove(rm);
				canvas.drawGraph(Eskom);
			}
			
		});
		
		btnEditVertex.setOnAction(e -> 
		{
			ObservableList<String>  toAdjust = lstStation.getSelectionModel().getSelectedItems();
			StringTokenizer adjustToken = null;

			for(String ad : toAdjust)
			{
				
				adjustToken = new StringTokenizer(ad,", :");
				adjustToken.nextToken();
				Vertex<Outlet> update = GraphOps.editVertex(Eskom, adjustToken.nextToken());
				if(update != null)
				{
					lstStation.getItems().remove(ad);
					lstStation.getItems().add(update.getValue().toString());
				}
				
			}
			
			canvas.drawGraph(Eskom);
		});
		//End: Add Stations Actions
		
		
		//Start: Adding of a edge (ui)
		
		//Add side
		if(Eskom.getVertices().size() != 0)
			for(Vertex<Outlet> st : Eskom.getVertices())
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
			for(Edge<Outlet> e : Eskom.getEdges())
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
			
			if(toAdd.size() == 2)
			{
				StringTokenizer st1Tokens = new StringTokenizer(toAdd.get(0),", :");
				StringTokenizer st2Tokens = new StringTokenizer(toAdd.get(1),", :");
				
				st1Tokens.nextToken();
				st2Tokens.nextToken();
				
				Vertex<Outlet> st1 = GraphOps.getStation(Eskom,st1Tokens.nextToken());
				Vertex<Outlet> st2 = GraphOps.getStation(Eskom,st2Tokens.nextToken());
				int distance = Integer.parseInt(txtCost.getText());
				
				if(st1 != null && st2 != null)
				{
					Edge<Outlet> edge = new Edge<Outlet>(distance,st1,st2);
					GraphOps.addEdge(Eskom, st1, st2, distance);
					
					lstEdges.getItems().add(edge.toString());
				}
				
				
			}
			
			canvas.drawGraph(Eskom);
			
		});
		
		btnRemoveEdge.setOnAction(e -> 
		{
			ObservableList<String> toRemove = lstEdges.getSelectionModel().getSelectedItems();
			
			for(String rm : toRemove)
			{
				GraphOps.removeEdge(Eskom,rm);
				lstEdges.getItems().remove(rm);
			}
			
			canvas.drawGraph(Eskom);
		});
		
		btnEditEdge.setOnAction(e ->
		{
			ObservableList<String> toAdjust = lstEdges.getSelectionModel().getSelectedItems();

			for(String ad : toAdjust)
			{
				lstEdges.getItems().remove(ad);
				Edge<Outlet> edge = GraphOps.editEdge(Eskom,ad);
				lstEdges.getItems().add(edge.toString());
			}
			
			canvas.drawGraph(Eskom);
		});
		
		//End: Actions for Edge management
		
		//Start: Finding Paths (ui)
		
		Label lblPaths = new Label("Paths Available:"); 
		TextArea txtPaths = new TextArea();
		VBox pathABox = new VBox();
		pathABox.getChildren().addAll(lblPaths,txtPaths);
		
		Button btnGetShortPath = new Button("Get Shortest Path");
		
		HBox shortPathBox = new HBox(44);
		
		VBox resPathBox = new VBox(10);
		resPathBox.getChildren().addAll(pathABox,btnGetShortPath,shortPathBox);
		
		VBox pathBox = new VBox(10);
		pathBox.getChildren().addAll(lstPaths);
		pathBox.getChildren().add(resPathBox);
		
		btnGetShortPath.setOnAction(e ->
		{
			ObservableList<String> path = lstPaths.getSelectionModel().getSelectedItems();
			
			if(path.size() == 2)
			{
				StringTokenizer st1Tokens = new StringTokenizer(path.get(0),", :");
				StringTokenizer st2Tokens = new StringTokenizer(path.get(1),", :");
				
				st1Tokens.nextToken();
				st2Tokens.nextToken();
				
				Vertex<Outlet> st1 = GraphOps.getStation(Eskom,st1Tokens.nextToken());
				Vertex<Outlet> st2 = GraphOps.getStation(Eskom,st2Tokens.nextToken());

				CostPathPair<Outlet> distance = GraphOps.Dijkstra(Eskom, st1,st2);
				
				if(distance != null)
					txtPaths.setText(GraphOps.displayPath(distance));
				else
					txtPaths.setText("No Path Found!");
			}
			
			canvas.drawGraph(Eskom);
			
		});
		
		
		TitledPane FindPaths = new TitledPane("Find Paths",pathBox);
		//End: Finding Paths
		
		
		Button btnReport = new Button("Get Latest Report");
		TextArea txtReport = new TextArea();
		txtReport.setPrefHeight(400);
		VBox reportBox = new VBox(10);
		reportBox.getChildren().addAll(btnReport,txtReport);
		
		btnReport.setOnAction(e -> {
			
			txtReport.setText(GraphOps.getStatusReport(Eskom));
			
		});
		
		TitledPane Report = new TitledPane("Status Report",reportBox);
		
		canvas.drawGraph(Eskom);
		
		accordion.getPanes().addAll(addVertex,addEdge,FindPaths,Report);
		VBox controlBox = new VBox();
		
		HBox display = new HBox();
		display.getChildren().addAll(accordion,canvas);
		controlBox.getChildren().addAll(display);
		
		this.setWidth(200);
		this.setHeight(200);
		this.getChildren().addAll(controlBox);
		
	}

	private void initGraph()
	{

		GraphOps.makeHouse("Duvha", true, 342.34);
		Vertex<Outlet> s1 = GraphOps.makeStation("Ankerlig", true, 128.7);
		s1.getValue().setCoordinates(20, 128);
		Vertex<Outlet> s2 = GraphOps.makeStation("Arnot", false, 32.54);
		s2.getValue().setCoordinates(202, 34);
		Vertex<Outlet> s3 = GraphOps.makeStation("Duvha", false, 453.6);
		s3.getValue().setCoordinates(402, 350);
		Vertex<Outlet> s4 = GraphOps.makeStation("Matimba", true, 456.78);
		s4.getValue().setCoordinates(300, 200);
		
		Vertex<Outlet> h1 = GraphOps.makeHouse("6609", true, 42.34);
		h1.getValue().setCoordinates(35, 57);
		Vertex<Outlet> h2 = GraphOps.makeHouse("32b", true, 32.34);
		h2.getValue().setCoordinates(476, 212);
		Vertex<Outlet> h3 = GraphOps.makeHouse("093c", false, 2.34);
		h3.getValue().setCoordinates(400, 87);
		Vertex<Outlet> h4 = GraphOps.makeHouse("212a", false, 22.34);
		h4.getValue().setCoordinates(450, 40);
		Vertex<Outlet> h5 = GraphOps.makeHouse("455z", false, 62.34);
		h5.getValue().setCoordinates(92, 130);
		Vertex<Outlet> h6 = GraphOps.makeHouse("213", true, 82.32);
		h6.getValue().setCoordinates(10, 213);
		
		Edge<Outlet> e1 = new Edge<>(234,s1,h1);
		Edge<Outlet> e2 = new Edge<>(23,s1,s2);
		Edge<Outlet> e3 = new Edge<>(3,h1,h3);
		Edge<Outlet> e4 = new Edge<>(12,h3,h4);
		Edge<Outlet> e5 = new Edge<>(98,h3,s4);
		Edge<Outlet> e6 = new Edge<>(87,s4,s2);
		Edge<Outlet> e7 = new Edge<>(7,s4,h6);
		Edge<Outlet> e8 = new Edge<>(45,s3,s4);
		Edge<Outlet> e9 = new Edge<>(3,s3,h5);
		Edge<Outlet> e10 = new Edge<>(10,h2,s4);
		Edge<Outlet> e11 = new Edge<>(10,s2,h4);
		
		ArrayList<Vertex<Outlet>> outlets = new ArrayList<>();
		outlets.add(s1);
		outlets.add(s2);
		outlets.add(s3);
		outlets.add(s4);
		outlets.add(h1);
		outlets.add(h2);
		outlets.add(h3);
		outlets.add(h4);
		outlets.add(h5);
		outlets.add(h6);
		
		ArrayList<Edge<Outlet>> connections = new ArrayList<>();
		connections.add(e1);
		connections.add(e2);
		connections.add(e3);
		connections.add(e4);
		connections.add(e5);
		connections.add(e6);
		connections.add(e7);
		connections.add(e8);
		connections.add(e9);
		connections.add(e10);
		connections.add(e11);
		
		Eskom = new Graph<Outlet>(TYPE.UNDIRECTED,outlets,connections);
	}
	
	private void initLists()
	{
		lstStation = new ListView<String>();
		lstStation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstStation.setPrefWidth(300);
		
		lstEdge = new ListView<String>();
		lstEdge.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstEdges = new ListView<String>();
		lstEdges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstPaths = new ListView<String>();
		lstPaths.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		lstEdge.itemsProperty().bind(lstStation.itemsProperty());
		lstPaths.itemsProperty().bind(lstStation.itemsProperty());
	}

}

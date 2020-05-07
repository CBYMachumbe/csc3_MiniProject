package ui;

import java.util.ArrayList;
import java.util.StringTokenizer;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.CostPathPair;
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
	private Graph<Outlet> Eskom;
	
	//Lists used from selecting and viewing nodes in the graph
	private ListView<String> lstStation;
	private ListView<String> lstEdge;
	private ListView<String> lstEdges;
	private ListView<String> lstPaths;
	
	
	public GraphPane()
	{
		Eskom = new Graph<Outlet>();
		
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
			
			//addVertex.setContent(VertextBox);
		});
		
		btnAddHome.setOnAction(e -> 
		{
			if(Eskom.getVertices().size() == 0)
				lstStation.getItems().clear();
			
			//Making a Home vertex
			Vertex<Outlet> st1 = GraphOps.makeOutlet("Home");
			GraphOps.addVertex(Eskom,st1);
			lstStation.getItems().add(st1.getValue().toString());
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
				
				adjustToken = new StringTokenizer(ad,", :");
				adjustToken.nextToken();
				Vertex<Outlet> update = GraphOps.editVertex(Eskom, adjustToken.nextToken());
				if(update != null)
				{
					lstStation.getItems().remove(ad);
					lstStation.getItems().add(update.getValue().toString());
				}
				
			}
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
				
				Edge<Outlet> edge = new Edge<Outlet>(distance,st1,st2);
				GraphOps.addEdge(Eskom, st1, st2, distance);
				
				lstEdges.getItems().add(edge.toString());
			}
			
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
				Edge<Outlet> edge = GraphOps.editEdge(Eskom,ad);
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
				
				Vertex<Outlet> st1 = GraphOps.getStation(Eskom,st1Tokens.nextToken());
				Vertex<Outlet> st2 = GraphOps.getStation(Eskom,st2Tokens.nextToken());

				ArrayList<Vertex<Outlet>> visited = new ArrayList<>();
				//GraphOps.DFS(Eskom,visited, st1);
				
				if(visited.contains(st2))
					txtPaths.setText(visited.toString());
				else
					txtPaths.setText("No Path Found!");
			}
			
		});
		
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
			
		});
		
		
		TitledPane FindPaths = new TitledPane("Find Paths",pathBox);
		//End: Finding Paths
		
		accordion.getPanes().addAll(addVertex,addEdge,FindPaths);
		VBox controlBox = new VBox();
		controlBox.getChildren().addAll(accordion);
		this.setWidth(200);
		this.setHeight(200);
		this.getChildren().addAll(controlBox);
		
	}

	private void initLists()
	{
		lstStation = new ListView<String>();
		lstStation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lstStation.setItems((ObservableList<String>) makeStationList());
		lstStation.setPrefWidth(430);
		
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
			for(Vertex<Outlet> st : Eskom.getVertices())
				list.add(st.getValue().toString());
		else
			list.add("No Stations Exist");
		
		return list;
	}

}

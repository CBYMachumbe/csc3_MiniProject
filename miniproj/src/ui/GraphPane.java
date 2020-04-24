package ui;

import java.util.ArrayList;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import com.sun.prism.impl.VertexBuffer;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GraphPane extends StackPane
{
	private Graph<String> Eskom;
	private ArrayList<Vertex<String>> stations;
	private ArrayList<Edge<String>> connections;
	private int vertexCounter = 0;
	
	public GraphPane()
	{
		MenuBar menubar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem openFile = new MenuItem("Open File");
		
		Menu menuStations = new Menu("Station In The System");
		
		menuFile.getItems().add(openFile);
		menubar.getMenus().addAll(menuFile,menuStations);
		
		Accordion accordion = new Accordion();
		
		//Start: Adding of a Vertex (ui)
		Label lblVID = new Label("Station Name:");
		TextField txtVID = new TextField();
		
		Label lblConnect = new Label("Number of Stations Connected:");
		TextField txtNConn = new TextField();
		Button btnSubmit = new Button("Submit Vertex");
		
		HBox addID = new HBox(99);
		addID.getChildren().addAll(lblVID,txtVID);
		
		HBox addConn = new HBox(10);
		addConn.getChildren().addAll(lblConnect,txtNConn);
		
		VBox VertextBox = new VBox(10);
		VertextBox.getChildren().addAll(addID,addConn,btnSubmit);
		
		TitledPane addVertex = new TitledPane("Add Station",VertextBox);
		
		Button btnMakeEdges = new Button("Submit Connections");
		//End: Adding of a Vertex
		
		//Start: Add Stations Actions
		btnSubmit.setOnAction(e -> 
		{
			VertextBox.getChildren().add(makeConnectionPanel(4));
			VertextBox.getChildren().add(btnMakeEdges);
			addVertex.setContent(VertextBox);
		});
		//End: Add Stations Actions
		
		
		//Start: Adding of a edge (ui)
		
		Label lblSt1 = new Label("Station 1 Name:");
		TextField txtSt1 = new TextField();
		HBox st1Box = new HBox(44);
		
		st1Box.getChildren().addAll(lblSt1,txtSt1);
		
		Label lblSt2 = new Label("Station 2 Name:");
		TextField txtSt2 = new TextField();
		HBox st2Box = new HBox(10);
		
		st2Box.getChildren().addAll(lblSt2,txtSt2);
		
		HBox stBox = new HBox(30);
		stBox.getChildren().addAll(st1Box,st2Box);
		
		Label lblCost = new Label("Cost Between Them:");
		TextField txtCost = new TextField();
		HBox costBox = new HBox(20);
		
		costBox.getChildren().addAll(lblCost,txtCost);
		Button btnSubmitEdge = new Button("Add New Edge");
		
		VBox edgeBox = new VBox(10);
		edgeBox.getChildren().addAll(stBox,costBox,btnSubmitEdge);
				
		TitledPane addEdge = new TitledPane("Add Edge",edgeBox);
		// End: Adding of a edge
		
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
	
	
	
	public void addVertex(String stationName)
	{
		Vertex<String> vertex = new Vertex<String>(stationName);
		stations.add(vertex);
		Eskom = new Graph<String>(stations,connections);
		vertexCounter++;
		
	}
	
	private VBox makeConnectionPanel(int nEdges)
	{
		VBox edgePane = new VBox(5);
		for(int i = 1; i <= nEdges; i++)
		{
			Label lblVName = new Label("Station Name "+ i +":");
			TextField txtVName = new TextField();
			HBox lBox = new HBox(10);
			
			lBox.getChildren().addAll(lblVName,txtVName);
			
			Label lblCName = new Label("Cost "+ i +":");
			TextField txtCCost = new TextField();
			HBox cBox = new HBox(10);
			
			cBox.getChildren().addAll(lblCName,txtCCost);
			
			HBox set = new HBox(20);
			set.getChildren().addAll(lBox,cBox);
			
			edgePane.getChildren().add(set);
		}
		
		
		return edgePane;
		
	}
	
}

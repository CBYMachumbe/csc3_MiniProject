import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GraphPane;

import java.util.*;;

public class Main extends Application
{
	public static void main(String args[])
	{
		/*Vertex<String> s1 = new Vertex<String>("Mabopane");
		Vertex<String> s2 = new Vertex<String>("Hex");
		Vertex<String> s3 = new Vertex<String>("Bro");
		Vertex<String> s4 = new Vertex<String>("Kujo");
		
		Edge<String> e1 = new Edge<String>(20, s1, s2);
		Edge<String> e2 = new Edge<String>(3, s1, s3);
		Edge<String> e3 = new Edge<String>(70, s1, s4);
		
		ArrayList<Vertex<String>> stations = new ArrayList<Graph.Vertex<String>>();
		ArrayList<Edge<String>> connections = new ArrayList<Edge<String>>();
		connections.add(e1);
		connections.add(e2);
		connections.add(e3);
		stations.add(s1);
		stations.add(s2);
		stations.add(s3);
		stations.add(s4);
		
		Graph<String> eskom = new Graph<String>(stations,connections);
		System.out.println(eskom);
		Vertex<String> s5 = new Vertex<String>("Idle");
		stations.add(s5);
		System.out.println(eskom);*/
		launch(args);
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Eskom Graphs");
		Scene scene = new Scene(new GraphPane(),650,400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
} 

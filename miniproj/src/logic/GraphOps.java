package logic;

import java.util.ArrayList;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import javafx.scene.control.TextInputDialog;


public class GraphOps
{
	public static String getPath(Graph<Station> graph, Vertex<Station> start, Vertex<Station> end)
	{
		
		//Data structure that has edge relaxation({vertex,cost}) objects
		return "";
	}
	
	
	public static void DFS(Graph<Station> Eskom, ArrayList<Vertex<Station>> visited,Vertex<Station> start)
	{
		visited.add(start);
		
		for(Edge<Station> e : start.getEdges())
		{
			Vertex<Station> to = e.getToVertex();
			
			if(!visited.contains(to))
			{
				DFS(Eskom,visited,to);
			}
		}
	}
	
	
	public static boolean isInGraph(Graph<Station> Eskom, String name)
	{
		for(Vertex<Station> st : Eskom.getVertices())
		{
			if(st.getValue().getName().equals(name))
				return true;
				
		}
		
		return false;
	}
	
	public static Edge<Station> editEdge(Graph<Station> Eskom, String signature)
	{
		
		Edge<Station> edge = null;
		if(Eskom.getEdges().size() != 0)
		{
			for(Edge<Station> e : Eskom.getEdges())
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
		
		int index = Eskom.getEdges().indexOf(edge);
		Eskom.getEdges().set(index, newConnection);
		
		return newConnection;
	}
	
	public static void removeEdge(Graph<Station> Eskom, String signature)
	{
		Edge<Station> edge = null;

		if(Eskom.getEdges().size() != 0)
		{
			for(Edge<Station> e : Eskom.getEdges())
			{
				if(e.toString().equals(signature))
					edge = e;
			}
		}
		
		Eskom.getEdges().remove(edge);

	}
	
	public static Vertex<Station> getStation(Graph<Station> eskom,String name)
	{
		for(Vertex<Station> st : eskom.getVertices())
		{
			if(st.getValue().getName().equals(name))
				return st;
				
		}
		
		return null;
	}
	
	public static void addEdge(Graph<Station> Eskom, Vertex<Station> st1,Vertex<Station> st2, int distance)
	{
		Vertex<Station> sta1 = null;
		Vertex<Station> sta2 = null;
		
		if(GraphOps.isInGraph(Eskom,st1.getValue().getName()) && GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = getStation(Eskom,st1.getValue().getName());
			sta2 = getStation(Eskom,st2.getValue().getName());
		}
		else if(GraphOps.isInGraph(Eskom,st1.getValue().getName()) && ! GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = getStation(Eskom,st1.getValue().getName());
			sta2 = new Vertex<Station>(st2);
		}
		else if(!GraphOps.isInGraph(Eskom,st1.getValue().getName()) && GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = getStation(Eskom,st2.getValue().getName());
		}
		else
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = new Vertex<Station>(st2);
		}
		
		Edge<Station> edge = new Edge<Station>(distance, sta1,sta2);
		
		int st1i = Eskom.getVertices().indexOf(sta1);
		sta1.addEdge(edge);
		Eskom.getVertices().set(st1i, sta1);
		
		int st2i = Eskom.getVertices().indexOf(sta2);
		sta2.addEdge(edge);
		Eskom.getVertices().set(st2i, sta2);
		
		Eskom.getEdges().add(edge);
	}
	
	public static void removeVertex(Graph<Station> Eskom, String stationName)
	{
		Vertex<Station> toRemove = null;
		for(Vertex<Station> st : Eskom.getVertices())
		{
			if(st.getValue().getName().equals(stationName));
				toRemove = st;
		}
		
		Eskom.getVertices().remove(toRemove);
	}
	
	public static void addVertex(Graph<Station> Eskom, Vertex<Station> st1)
	{
		Vertex<Station> vertex = new Vertex<Station>(st1);
		Eskom.getVertices().add(vertex);
	}
	
	public static boolean makeBool(String verdict, String type) 
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

	public static Vertex<Station> editVertex(Graph<Station> Eskom, String name)
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
		
		int index = Eskom.getVertices().indexOf(GraphOps.getStation(Eskom, name));
		Vertex<Station> update = new Vertex<Station>(new Station(stName,isOn,isStaion));
		
		
		Eskom.getVertices().set(index, update);
		return update;
		
	}
	
}

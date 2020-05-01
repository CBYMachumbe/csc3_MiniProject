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
		ArrayList<Vertex<Station>> visited = new ArrayList<>();
		visited.add(start);
		
		int distance = 100000000;
		ArrayList<Vertex<Station>> adjacentVertices = new ArrayList<>();

		String display = "__" + " ===> ";
		
		for(Edge<Station> e : start.getEdges())
		{
			Vertex<Station> from = e.getToVertex();
			adjacentVertices.add(from);
		}
		
		for(Vertex<Station> to : adjacentVertices)
		{
			if(!(visited.contains(to)))
			{
				if((to.equals(end)) && (!visited.contains(end)))
				{
					System.out.println(visited);
					visited.add(end);
					display +=  end.getValue().getName();
					return display; 
				}
				else
				{
					Edge<Station> e = start.getEdge(to);
					
					if(distance > e.getCost())
					{	
						distance = e.getCost();
						visited.add(to);
						display =  to.getValue().getName() + " ===> " + getPath(graph,to,end);	
					}
				}
			}
			
		}
		
		/*ArrayList<Vertex<Station>> visited = new ArrayList<>();
		ArrayList<Vertex<Station>> stations = (ArrayList<Vertex<Station>>) graph.getVertices();
		visited.add(start);

		int size = graph.getVertices().size();
		int cursor = 0;
		int distance = 0;
		
		DijkstrasObj pathObj = new DijkstrasObj(size);
		
		for(Vertex<Station> vertical : visited)
		{
			for(Vertex<Station> horizontal : stations)
			{
				Edge<Station> edge = vertical.getEdge(horizontal); 
				
				if(vertical.equals(horizontal))
				{
					pathObj.path[cursor] = vertical;
					pathObj.augmentCost(distance);
				}
				else if(edge != null)
				{
					cursor++;
					pathObj.path[cursor] = horizontal;
					pathObj.augmentCost(edge.getCost());
					visited.add(pathObj.getLowVertex(horizontal));
					distance += edge.getCost();
				}
			}
		}*/
		
		
		
		
		
		return display;
	}
	public static boolean BFS(Graph<Station> graph, Vertex<Station> start, Vertex<Station> end)
	{
		ArrayList<Vertex<Station>> visited = new ArrayList<Graph.Vertex<Station>>();
		ArrayList<Vertex<Station>> nxtToVisit = new ArrayList<Graph.Vertex<Station>>();
		
		nxtToVisit.add(start);
		
		while(!nxtToVisit.isEmpty())
		{
			Vertex<Station> v = nxtToVisit.remove(0);
			if(v.equals(end))
			{
				return true;
			}
			 
			if(visited.contains(v))
			{
				continue;
			}
			
			visited.add(v);
			
			for(Edge<Station> e : v.getEdges())
			{
				nxtToVisit.add(e.getToVertex());
			}
		}
		
		return false;
	}
	
	public static boolean isInGraph(ArrayList<Vertex<Station>> stations, String name)
	{
		for(Vertex<Station> st : stations)
		{
			if(st.getValue().getName().equals(name))
				return true;
				
		}
		
		return false;
	}
	
	public static Edge<Station> editEdge(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations, ArrayList<Edge<Station>> connections ,String signature)
	{
		
		Edge<Station> edge = null;
		if(connections.size() != 0)
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
		
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		return newConnection;
	}
	
	public static void removeEdge(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations, ArrayList<Edge<Station>> connections,String signature)
	{
		Edge<Station> edge = null;

		if(connections.size() != 0)
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

	}
	
	public static Vertex<Station> getStation(ArrayList<Vertex<Station>> stations,String name)
	{
		for(Vertex<Station> st : stations)
		{
			if(st.getValue().getName().equals(name))
				return st;
				
		}
		
		return null;
	}
	
	public static void addEdge(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations, ArrayList<Edge<Station>> connections,Vertex<Station> st1,Vertex<Station> st2, int distance)
	{
		Vertex<Station> sta1 = null;
		Vertex<Station> sta2 = null;
		
		if(GraphOps.isInGraph(stations,st1.getValue().getName()) && GraphOps.isInGraph(stations,st2.getValue().getName()))
		{
			sta1 = getStation(stations,st1.getValue().getName());
			sta2 = getStation(stations,st2.getValue().getName());
		}
		else if(GraphOps.isInGraph(stations,st1.getValue().getName()) && ! GraphOps.isInGraph(stations,st2.getValue().getName()))
		{
			sta1 = getStation(stations,st1.getValue().getName());
			sta2 = new Vertex<Station>(st2);
		}
		else if(!GraphOps.isInGraph(stations,st1.getValue().getName()) && GraphOps.isInGraph(stations,st2.getValue().getName()))
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = getStation(stations,st2.getValue().getName());
		}
		else
		{
			sta1 = new Vertex<Station>(st1);
			sta2 = new Vertex<Station>(st2);
		}
		
		Edge<Station> edge = new Edge<Station>(distance, sta1,sta2);
		
		int st1i = stations.indexOf(sta1);
		sta1.addEdge(edge);
		stations.set(st1i, sta1);
		
		int st2i = stations.indexOf(sta2);
		sta2.addEdge(edge);
		stations.set(st2i, sta2);
		
		
		if(connections.equals(null))
		{
			//create new edge instance & use
			connections = new ArrayList<Graph.Edge<Station>>();
		}
		
		connections.add(edge);
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
	}
	
	public static void removeVertex(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations,ArrayList<Edge<Station>> connections,String stationName)
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
	}
	
	public static void addVertex(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations,ArrayList<Edge<Station>> connections,Vertex<Station> st1)
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

	public static Vertex<Station> editVertex(Graph<Station> Eskom, ArrayList<Vertex<Station>> stations,ArrayList<Edge<Station>> connections,String name)
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
		
		int index = stations.indexOf(GraphOps.getStation(stations, name));
		Vertex<Station> update = new Vertex<Station>(new Station(stName,isOn,isStaion));
		
		stations.set(index, update);
		
		
		Eskom = null;
		Eskom = new Graph<Station>(stations, connections);
		
		return update;
		
	}
	
}

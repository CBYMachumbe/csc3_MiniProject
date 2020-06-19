package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.CostPathPair;
import com.jwetherell.algorithms.data_structures.Graph.CostVertexPair;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import javafx.scene.control.TextInputDialog;


public class GraphOps
{
	public static double canvasWidth = 600;
	public static double canvasHeight = 600;
	
	
	public static String getStatusReport(Graph<Outlet> Eskom)
	{
		String display = "";
		
		ArrayList<Vertex<Outlet>> nonHouses = new ArrayList<>();
		ArrayList<Vertex<Outlet>> nonStations = new ArrayList<>();
		ArrayList<Vertex<Outlet>> workingStations = new ArrayList<>();
		
		for(Vertex<Outlet> v : Eskom.getVertices())
		{
			if(v.getValue().getType().equals("House"))
				if(!v.getValue().isOn())
					nonHouses.add(v);
		}
		
		for(Vertex<Outlet> v : Eskom.getVertices())
		{
			if(v.getValue().getType().equals("Station"))
				if(!v.getValue().isOn())
					nonStations.add(v);
		}
		
		for(Vertex<Outlet> v : Eskom.getVertices())
		{
			if(v.getValue().getType().equals("Station"))
				if(v.getValue().isOn())
					workingStations.add(v);
		}
		
		display += "Houses with no electricity below:\n";
		
		for(Vertex<Outlet> toAssess : nonHouses)
			display +="\t" + toAssess.getValue().getName() +"\n";
		
		display += "\n\n";
		
		display += "Potential Fixes:\n";
		for(Vertex<Outlet> toSupply : nonHouses)
		{
			display += "\tHouse Name: " + toSupply.getValue().getName() + "\n"; 
			for(Vertex<Outlet> stations : workingStations)
			{
				if(!displayPath(Dijkstra(Eskom, stations, toSupply)).equals("No path found"))
					display += "\t\tConnect to station: " + stations.getValue().getName() + "\n";
			}
		}
		
		display += "\n\n";
		
		display += "Stations that need to be fixed:\n";
		for(Vertex<Outlet> toBeReported : nonStations)
			display += "\tStation: " + toBeReported.getValue().getName() + "\n";
		
		return display;
	}
	
	public static boolean isInGraph(Graph<Outlet> Eskom, String name)
	{
		for(Vertex<Outlet> st : Eskom.getVertices())
		{
			if(st.getValue().getName().equals(name))
				return true;
				
		}
		
		return false;
	}
	
	public static Edge<Outlet> editEdge(Graph<Outlet> Eskom, String signature)
	{
		
		Edge<Outlet> edge = null;
		if(Eskom.getEdges().size() != 0)
		{
			for(Edge<Outlet> e : Eskom.getEdges())
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
		
		Edge<Outlet> newConnection = new Edge<Outlet>(distance, edge.getFromVertex(), edge.getToVertex());
		Edge<Outlet> newConnection2 = new Edge<Outlet>(distance, edge.getToVertex(),edge.getFromVertex());
		addeEdgeToVertex(Eskom, edge);
		
		
		Eskom.getEdges().remove(edge);
		Eskom.getEdges().add(newConnection);
		Eskom.getEdges().add(newConnection2);
		return newConnection;
	}
	
	private static Edge<Outlet> getEdge(Graph<Outlet> Eskom, Vertex<Outlet> from,Vertex<Outlet> to)
	{
		for(Edge<Outlet> e : Eskom.getEdges())
		{
			if((e.getFromVertex().getValue().getName().equals(from.getValue().getName())) &&
					(e.getToVertex().getValue().getName().equals(to.getValue().getName())))
			{
				return e;
			}
		}
		
		return null;
	}
	
	public static void removeEdge(Graph<Outlet> Eskom, String signature)
	{
		Edge<Outlet> edge = null;

		if(Eskom.getEdges().size() != 0)
		{
			for(Edge<Outlet> e : Eskom.getEdges())
			{
				if(e.toString().equals(signature))
				{
					edge = e;
					break;
				}
			}
		}
		
		Edge<Outlet> e2 = getEdge(Eskom, edge.getToVertex(), edge.getFromVertex());
		Eskom.getEdges().remove(e2);
		Eskom.getEdges().remove(edge);

	}
	
	public static Vertex<Outlet> getStation(Graph<Outlet> eskom,String name)
	{
		for(Vertex<Outlet> st : eskom.getVertices())
		{
			if(st.getValue().getName().equals(name))
				return st;
				
		}
		
		return null;
	}
	
	private static void addeEdgeToVertex(Graph<Outlet> graph, Edge<Outlet> edge)
	{
		Vertex<Outlet> from = edge.getFromVertex();
		Vertex<Outlet> to = edge.getToVertex();
		
		int index1 = graph.getVertices().indexOf(from);
		int index2 = graph.getVertices().indexOf(to);
		
		from.getEdges().add(edge);
		to.getEdges().add(edge);
		
		graph.getVertices().set(index1, from);
		graph.getVertices().set(index2, to);
	}
	
	public static void addEdge(Graph<Outlet> Eskom, Vertex<Outlet> st1,Vertex<Outlet> st2, int distance)
	{
		Edge<Outlet> edge = new Edge<Outlet>(distance, st1,st2);
		int st1i = Eskom.getVertices().indexOf(st1);
		st1.addEdge(edge);
		Eskom.getVertices().set(st1i, st1);
		Eskom.getEdges().add(edge);
	}
	
	public static ArrayList<Edge<Outlet>> getIncidentEdges(Graph<Outlet> graph, Vertex<Outlet> vertex)
	{
		ArrayList<Edge<Outlet>> edges = new ArrayList<>();
		
		for(Edge<Outlet> e : graph.getEdges())
		{
			if((e.getToVertex() == vertex) || (e.getFromVertex() == vertex))
				edges.add(e);
		}
		
		return edges;
	}
	
	public static void removeVertex(Graph<Outlet> Eskom, String OutletName)
	{
		Vertex<Outlet> toRemove = getStation(Eskom, OutletName);
		
		for(Edge<Outlet> e : getIncidentEdges(Eskom,toRemove))
		{
			Eskom.getEdges().remove(e);
		}
		
		Eskom.getVertices().remove(toRemove);
	}
	
	public static void addVertex(Graph<Outlet> Eskom, Vertex<Outlet> st1)
	{
		Vertex<Outlet> vertex = new Vertex<Outlet>(st1);
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

	public static Vertex<Outlet> editVertex(Graph<Outlet> Eskom, String name)
	{
		Vertex<Outlet> editor = GraphOps.getStation(Eskom, name);
		
		TextInputDialog ipName = new TextInputDialog();
		ipName.setHeaderText("Name of " + editor.getValue().getType() + ": ");
		TextInputDialog ipOn = new TextInputDialog();
		ipOn.setHeaderText("Does " + editor.getValue().getType()  + " work (yes/no)");
		TextInputDialog ipDouble = new TextInputDialog();
		
		if(editor.getValue().getType() == "Staion")
		{
			ipDouble.setHeaderText("Station Electricity Supply (kWhr): ");
		}else
		{
			ipDouble.setHeaderText("Estimated Electricity Consumption (kWhr): ");
		}
		
		ipName.showAndWait();
		String newName = ipName.getEditor().getText();
		
		ipOn.showAndWait();
		boolean isOn = makeBool(ipOn.getEditor().getText(), editor.getValue().getType());
		
		ipDouble.showAndWait();
		double db = makeDouble(ipDouble.getEditor().getText(),  editor.getValue().getType());
		
		int index = Eskom.getVertices().indexOf(editor);
		
		if(index >= 0)
		{
			editor.getValue().setName(newName);
			editor.getValue().setOn(isOn);
			Eskom.getVertices().set(index, editor);
		}
		
		return editor;
		
	}
	
	public static CostPathPair<Outlet> Dijkstra(Graph<Outlet> graph, Vertex<Outlet> start, Vertex<Outlet> end) 
	{
		CostPathPair<Outlet> distance = null;
		
		if(start != null)
		{
			PriorityQueue<CostVertexPair<Outlet>> toVisit = new PriorityQueue<CostVertexPair<Outlet>>();
			Map<Vertex<Outlet>, List<Edge<Outlet>>> string = new HashMap<>();
			Map<Vertex<Outlet>, CostVertexPair<Outlet>> edgeRelexation = new HashMap<>();
			
			for(Vertex<Outlet> v : graph.getVertices())
			{
				string.put(v, new ArrayList<Edge<Outlet>>());
			}
				
			for(Vertex<Outlet> v : graph.getVertices()) 
			{
				CostVertexPair<Outlet> path = null;

				if (v.equals(start))
				{
					path = new CostVertexPair<Outlet>(0, v);
					edgeRelexation.put(v, path);
				}	
				else
				{
					path = new CostVertexPair<Outlet>(Integer.MAX_VALUE, v);
					edgeRelexation.put(v, path);
				}
					
			}

			toVisit.add(edgeRelexation.get(start));
			
			while (!toVisit.isEmpty()) 
			{
				CostVertexPair<Outlet> pair = toVisit.remove();
				Vertex<Outlet> currentNode = pair.getVertex();

				for (Edge<Outlet> e : currentNode.getEdges()) 
				{
					CostVertexPair<Outlet> toPair = edgeRelexation.get(e.getToVertex());
					CostVertexPair<Outlet> lowestCostToThisVertex = edgeRelexation.get(currentNode);
					
					int cost = lowestCostToThisVertex.getCost() + e.getCost();
					
					if (toPair.getCost() == Integer.MAX_VALUE) 
					{
						toVisit.remove(toPair);
						toPair.setCost(cost);
						toVisit.add(toPair);

						List<Edge<Outlet>> set = string.get(e.getToVertex());
						set.addAll(string.get(e.getFromVertex()));
						set.add(e);
						
					} 
					else if (cost < toPair.getCost()) 
					{
						toVisit.remove(toPair);
						toPair.setCost(cost);
						toVisit.add(toPair);

						List<Edge<Outlet>> set = string.get(e.getToVertex());
						set.clear();
						set.addAll(string.get(e.getFromVertex()));
						set.add(e);
					}
				}

				if (currentNode.equals(end)) 
				{
					break;
				}
			}
			
			List<Edge<Outlet>> set = string.get(end);
			CostVertexPair<Outlet> pair = edgeRelexation.get(end);
			distance = new CostPathPair<Outlet>(pair.getCost(), set);
		}
		
		
		return distance;
	}
	
	private static boolean checkPathNulity(CostPathPair<Outlet> path)
	{
		if(path.getPath().size() == 0)
			return false;
		
		for(Edge<Outlet> e : path.getPath())
			if(e.getToVertex() == null || e.getFromVertex() == null)
				return false; 
		
		return true;
	}
	
	public static String displayPath(CostPathPair<Outlet> path)
	{
		if(checkPathNulity(path))
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Cost: " + path.getCost());
			builder.append("\n Path: " + path.getPath().get(0).getFromVertex().getValue().getName());
			
			for(int i = 1; i < (path.getPath().size()); i++)
			{
				builder.append(" ===> " );
				builder.append(path.getPath().get(i).getFromVertex().getValue().getName());
				builder.append(" ===> " );
			}
			
			if(path.getPath().size() == 1)
				builder.append(" ===> " );
			
			builder.append(path.getPath().get((path.getPath().size() - 1)).getToVertex().getValue().getName());
			return builder.toString();
		}
		
		return "No path found";
		
	}
	
	public static Vertex<Outlet> makeHouse(String name, boolean isOn, double consumption)
	{
		Vertex<Outlet> outlet = new Vertex<>(new House(name, isOn, consumption));
		return outlet;
	}

	public static Vertex<Outlet> makeStation(String name, boolean isOn, double supply)
	{
		Vertex<Outlet> outlet = new Vertex<>(new Station(name, isOn, supply));
		return outlet;
	}
	
	public static Vertex<Outlet> makeOutlet(String Type)
	{
		Vertex<Outlet> outlet = null;
		
		TextInputDialog ipName = new TextInputDialog();
		ipName.setHeaderText("Name of " + Type + ": ");
		TextInputDialog ipOn = new TextInputDialog();
		ipOn.setHeaderText("Does " + Type + " work (yes/no)");
		TextInputDialog ipDouble = new TextInputDialog();
		if(Type == "Station")
		{
			ipDouble.setHeaderText("Station Electricity Supply (kWhr): ");
		}
		else
		{
			ipDouble.setHeaderText("Estimated Electricity Consumption (kWhr): ");
		}
		
		ipName.showAndWait();
		String name = ipName.getEditor().getText();
		ipOn.showAndWait();
		boolean isOn = makeBool(ipOn.getEditor().getText(), "Does " + Type + " Work (yes/no): ");
		ipDouble.showAndWait();
		
		double electricity = makeDouble(ipDouble.getEditor().getText(), ipDouble.getHeaderText());
		
		if(Type == "Station")
		{
			outlet = new Vertex<>(new Station(name, isOn, electricity));
		}
		else if(Type == "House")
		{
			outlet = new Vertex<>(new House(name, isOn, electricity));
		}
		
		outlet.getValue().setX(generateCoornate(canvasHeight));
		outlet.getValue().setY(generateCoornate(canvasWidth));
		
		return outlet;
		
	}
	
	public static double makeDouble(String verdict, String type) 
	{
		double ans = 0;
		
		try
		{
			ans = Double.parseDouble(verdict);
		}
		catch(NumberFormatException e)
		{
			TextInputDialog ipb = new TextInputDialog();
			ipb.setHeaderText(type);
			ipb.showAndWait();
			makeDouble(ipb.getEditor().getText(),type);
			
		}
		
		return ans;
	}
	
	public static ArrayList<String> calcConsumptionRate(CostPathPair<Outlet> path, Graph<Outlet> graph,Vertex<Outlet> start, Vertex<Outlet> end)
	{
		ArrayList<String> workingPaths = new ArrayList<>();
		
		for(Edge<Outlet> e : path.getPath())
		{
			if(e.getFromVertex().getValue().getType() == "Staion" &&  e.getFromVertex().getValue().isOn() && start.getValue().getType() == "House")
			{
				workingPaths.add(displayPath(Dijkstra(graph, e.getFromVertex(), start)));
			}
		}
		
		// Have a check for each Outlet
		// See which outlets the Outlet supplies with electricity
		// Check if this place is operating if not give out the path but state its not working
		// If it is share the then give path
		// Save the amount of electricity the Outlet gives out
		// & see if the Outlet can supply all of the houses connected to it
		
		
		return workingPaths;
	}
	
	public static double generateCoornate(double width)
	{
		Random rand = new Random();
		return (rand.nextDouble() * (width-20) + 10);
	}
	
}


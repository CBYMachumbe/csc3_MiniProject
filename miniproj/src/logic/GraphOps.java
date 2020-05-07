package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.CostPathPair;
import com.jwetherell.algorithms.data_structures.Graph.CostVertexPair;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import javafx.scene.control.TextInputDialog;


public class GraphOps
{
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
		
		removeEdgeFromVertex(Eskom,edge);
		
		TextInputDialog tdDistance = new TextInputDialog();
		tdDistance.setHeaderText("What is the distance between these outlets");
		tdDistance.showAndWait();
		String strDistance = tdDistance.getEditor().getText();
		int distance = Integer.parseInt(strDistance);
		
		Edge<Outlet> newConnection = new Edge<Outlet>(distance, edge.getFromVertex(), edge.getToVertex());
		
		addeEdgeToVertex(Eskom, edge);
		
		int index = Eskom.getEdges().indexOf(edge);
		Eskom.getEdges().set(index, newConnection);
		return newConnection;
	}
	
	public static void removeEdge(Graph<Outlet> Eskom, String signature)
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
	
	private static void removeEdgeFromVertex(Graph<Outlet> graph, Edge<Outlet> edge)
	{
		Vertex<Outlet> from = edge.getFromVertex();
		Vertex<Outlet> to = edge.getToVertex();
		
		int index1 = graph.getVertices().indexOf(from);
		int index2 = graph.getVertices().indexOf(to);
		
		from.getEdges().remove(edge);
		to.getEdges().remove(edge);
		
		graph.getVertices().set(index1, from);
		graph.getVertices().set(index2, to);
	}
	
	public static void addEdge(Graph<Outlet> Eskom, Vertex<Outlet> st1,Vertex<Outlet> st2, int distance)
	{
		Vertex<Outlet> sta1 = null;
		Vertex<Outlet> sta2 = null;
		
		if(GraphOps.isInGraph(Eskom,st1.getValue().getName()) && GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = getStation(Eskom,st1.getValue().getName());
			sta2 = getStation(Eskom,st2.getValue().getName());
		}
		else if(GraphOps.isInGraph(Eskom,st1.getValue().getName()) && ! GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = getStation(Eskom,st1.getValue().getName());
			sta2 = new Vertex<Outlet>(st2);
		}
		else if(!GraphOps.isInGraph(Eskom,st1.getValue().getName()) && GraphOps.isInGraph(Eskom,st2.getValue().getName()))
		{
			sta1 = new Vertex<Outlet>(st1);
			sta2 = getStation(Eskom,st2.getValue().getName());
		}
		else
		{
			sta1 = new Vertex<Outlet>(st1);
			sta2 = new Vertex<Outlet>(st2);
		}
		
		Edge<Outlet> edge = new Edge<Outlet>(distance, sta1,sta2);
		
		int st1i = Eskom.getVertices().indexOf(sta1);
		sta1.addEdge(edge);
		Eskom.getVertices().set(st1i, sta1);
		
		int st2i = Eskom.getVertices().indexOf(sta2);
		sta2.addEdge(edge);
		Eskom.getVertices().set(st2i, sta2);
		
		Eskom.getEdges().add(edge);
	}
	
	public static void removeVertex(Graph<Outlet> Eskom, String OutletName)
	{
		Vertex<Outlet> toRemove = null;
		for(Vertex<Outlet> st : Eskom.getVertices())
		{
			if(st.getValue().toString().equals(OutletName));
				toRemove = st;
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
		Vertex<Outlet> update = null;
		int index = Eskom.getVertices().indexOf(editor);
		
		if(index >= 0)
		{
			update = makeOutlet(editor.getValue().getType());
			Eskom.getVertices().set(index, update);
		}
		return update;
		
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
		else
		{
			outlet = new Vertex<>(new House(name, isOn, electricity));
		}
		
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
	
	//TODO:
	// Have a check for each Outlet
	// See which outlets the Outlet supplies with electricity
	// Check if this place is operating if not give out the path but state its not working
	// If it is share the then give path
	// Save the amount of electricity the Outlet gives out
	// & see if the Outlet can supply all of the houses connected to it
	
	
}


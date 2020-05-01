package logic;

import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class DijkstrasObj 
{
	public int cost;
	public Vertex<Station>[] path;
	
	public DijkstrasObj(int size)
	{
		cost = 0;
		path =  (Vertex<Station>[]) new Object[size];
	}
	
	public void augmentCost(int cost)
	{
		this.cost += cost;
	}
	
	public Vertex<Station> getLowVertex(Vertex<Station> from)
	{
		Vertex<Station> lowest = null;
		
		for(int i = 0; i < path.length; i++)
		{
			Edge<Station> edge = from.getEdge(path[i]);
			int min = 0;
			
			if((min != 0) && (min > edge.getCost()))
				lowest = path[i];
		}
		
		return lowest;
	}

}

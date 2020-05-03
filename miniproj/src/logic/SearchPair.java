package logic;

import java.util.ArrayList;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class SearchPair 
{
	private ArrayList<Vertex<Station>> visited;
	private ArrayList<Edge<Station>> path;
	
	public SearchPair()
	{
		visited = new ArrayList<>();
		path = new ArrayList<>();
	}
	
	public ArrayList<Vertex<Station>> getVisited() {
		return visited;
	}
	public void setVisited(ArrayList<Vertex<Station>> visited) {
		this.visited = visited;
	}
	public ArrayList<Edge<Station>> getPath() {
		return path;
	}
	public void setPath(ArrayList<Edge<Station>> path) {
		this.path = path;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Path: ");
		builder.append(visited.get(0).getValue().getName() + " ===> ");
		
		for(int i = 1; i < visited.size(); i++)
			if(i == visited.size() - 1)
				builder.append(visited.get(i).getValue().getName());
			else
				builder.append(visited.get(i).getValue().getName() + " ===> ");
			
		
		return builder.toString();
	}
}

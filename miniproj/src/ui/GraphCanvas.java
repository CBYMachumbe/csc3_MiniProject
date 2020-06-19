package ui;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Outlet;

public class GraphCanvas extends Canvas
{
	GraphicsContext pencil = this.getGraphicsContext2D();
	
	public GraphCanvas()
	{
		super(600,600);
	}
	
	public void drawGraph(Graph<Outlet> graph)
	{
		pencil.setFill(Color.BLACK);
		pencil.fillRect(0,0, 600, 600);
		
		for(Edge<Outlet> e : graph.getEdges())
		{
			drawEdge(e);
		}
			
		
		for(Vertex<Outlet> v : graph.getVertices())
			drawVertex(v);
	}
	
	public void drawVertex(Vertex<Outlet> node)
	{
		if(node.getValue().isOn() == true)
		{
			pencil.setFill(Color.YELLOW); 
			pencil.fillOval(node.getValue().getX()-2, node.getValue().getY()-2, 20, 20);
		}
			
			
		if(node.getValue().getType().equals("Station")) 
		{
			pencil.setFill(Color.ORANGERED);
		}
		else if(node.getValue().getType().equals("House")) 
		{
			pencil.setFill(Color.DARKVIOLET);
		}
		
		pencil.fillOval(node.getValue().getX(), node.getValue().getY(), 16, 16);
		pencil.fillText(node.getValue().getName(), node.getValue().getX()-4, node.getValue().getY()-4);
		
		
			
	}
	
	public void drawEdge(Edge<Outlet> edge)
	{
		pencil.setStroke(Color.GREEN);
		
		//from coordinates + 9 & To coordinates + 52
		double x1 = edge.getFromVertex().getValue().getX();
		double y1 = edge.getFromVertex().getValue().getY();
		
		double x2 = edge.getToVertex().getValue().getX();
		double y2 = edge.getToVertex().getValue().getY();
		
		pencil.strokeLine(x1+4, y1+4, x2+4, y2+4);
		
		//displaying the cost
		pencil.setFill(Color.RED);
		pencil.fillText(edge.getCost()+"", ((x1+x2+1)/2), ((y1+y2+1)/2));
	}
}

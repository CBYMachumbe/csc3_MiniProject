package ui;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class graphCanvas extends Canvas
{
	public graphCanvas()
	{
		super(400,400);
		drawBox();
	}
	
	public void drawBox()
	{
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.DARKVIOLET);
		gc.fillRect(0, 0, 400, 400);
	}
}

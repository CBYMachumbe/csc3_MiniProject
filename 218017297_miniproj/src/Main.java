import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.GraphPane;

public class Main extends Application
{
	public static void main(String args[])
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Eskom Graphs");
		Scene scene = new Scene(new GraphPane(),1200,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
} 
